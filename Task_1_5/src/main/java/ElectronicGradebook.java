import java.util.*;
import java.util.stream.*;

/**
 * Class for an MMF student's electronic gradebook.
 */
public class ElectronicGradebook {
    /**
     * Forms of education.
     */
    public enum EducationForm {
        budget,
        paid
    }

    private static class Subject {
        String name;
        Grade grade;

        Subject(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    ControlPlan controlPlan;
    EnumMap<ControlType, List<List<Subject>>> grades;
    EducationForm educationForm;
    int currentSemester;

    /**
     * Creates a gradebook with the given education form and current semester.
     *
     * @param educationForm   student's form of education.
     * @param currentSemester current semester number.
     */
    public ElectronicGradebook(EducationForm educationForm, int currentSemester) {
        this.controlPlan = new ControlPlan();
        this.grades = new EnumMap<>(ControlType.class);
        this.educationForm = educationForm;
        this.currentSemester = currentSemester;
        for (ControlType type : ControlType.values()) {
            List<List<Subject>> semesters = new ArrayList<>();
            for (int semester = 1; semester <= controlPlan.getSemesterCount(); semester++) {
                semesters.add(new ArrayList<>());
            }
            grades.put(type, semesters);
        }
    }

    /**
     * Gets the curriculum control plan.
     *
     * @return control plan.
     */
    public ControlPlan getControlPlan() {
        return controlPlan;
    }

    /**
     * Gets student's form of education.
     *
     * @return form of education.
     */
    public EducationForm getEducationForm() {
        return educationForm;
    }

    /**
     * Adds a grade for a control in the given semester.
     *
     * @param type        type of control.
     * @param semester    semester number.
     * @param subjectName name of the subject.
     * @param grade       grade to record.
     * @return true if the grade was added, false if the arguments are invalid.
     */
    public boolean recordGrade(ControlType type, int semester, String subjectName, Grade grade) {
        if (type == null || subjectName == null || grade == null || grade == Grade.notGraded
                || semester < 1 || semester > controlPlan.getSemesterCount()
                || !isValidGrade(type, grade)) {
            return false;
        }
        List<Subject> semesterGrades = grades.get(type).get(semester - 1);
        Subject existing = semesterGrades.stream()
                .filter(subject -> subject.name.equals(subjectName))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            existing.grade = grade;
        } else {
            semesterGrades.add(new Subject(subjectName, grade));
        }
        return true;
    }

    /**
     * Gets a grade for a control.
     *
     * @param type        type of control.
     * @param semester    semester number.
     * @param subjectName name of the subject.
     * @return grade if it was recorded, otherwise an empty value.
     */
    public Optional<Grade> getGrade(ControlType type, int semester, String subjectName) {
        if (type == null || subjectName == null ||
                semester < 1 || semester > controlPlan.getSemesterCount()) {
            return Optional.empty();
        }
        List<Subject> semesterGrades = grades.get(type).get(semester - 1);
        return semesterGrades.stream()
                .filter(subject -> subject.name.equals(subjectName))
                .map(subject -> subject.grade)
                .filter(grade -> grade != Grade.notGraded)
                .findFirst();
    }

    /**
     * Calculates the average of all numeric grades.
     *
     * @return average grade or zero if there are no numeric grades.
     */
    public double getAverageGrade() {
        return grades.values().stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .map(subject -> subject.grade)
                .filter(Grade::isNumeric)
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0);
    }

    /**
     * Checks whether transfer from paid to budget education is possible.
     *
     * @return true if the last two completed sessions satisfy the requirements.
     */
    public boolean canTransferToBudget() {
        if (educationForm != EducationForm.paid || currentSemester < 1
                || hasUnclearedCredits()) {
            return false;
        }
        List<Integer> examSessions = IntStream.rangeClosed(1, currentSemester)
                .filter(semester -> controlPlan.getCount(ControlType.exam, semester) > 0)
                .filter(this::hasRecordedExamGrade)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .collect(Collectors.toList());
        if (examSessions.size() < 2) {
            return false;
        }
        return examSessions.stream()
                .allMatch(semester -> isCompletedExamSession(semester)
                        && meetsSessionTransferRequirements(semester));
    }

    /**
     * Transfers the student to budget education if the requirements are satisfied.
     *
     * @return true if the transfer was made.
     */
    public boolean transferToBudget() {
        if (!canTransferToBudget()) {
            return false;
        }
        educationForm = EducationForm.budget;
        return true;
    }

    /**
     * Checks whether the student can receive a diploma with honors.
     * Ungraded future controls are considered when making the prediction.
     *
     * @return true if the current grades allow the diploma with honors.
     */
    public boolean canReceiveRedDiploma() {
        if (hasGrade(ControlType.exam, Grade.satisfactory)
                || hasGrade(ControlType.exam, Grade.unsatisfactory)
                || hasGrade(ControlType.differentiatedCredit, Grade.satisfactory)
                || hasGrade(ControlType.differentiatedCredit, Grade.unsatisfactory)
                || hasGrade(ControlType.practiceReportDefense, Grade.unsatisfactory)
                || hasGrade(ControlType.credit, Grade.failed)
                || hasNonExcellentThesis()) {
            return false;
        }

        long totalDiplomaSubjects = IntStream
                .rangeClosed(1, controlPlan.getSemesterCount())
                .mapToLong(semester ->
                        (long) controlPlan.getCount(ControlType.exam, semester)
                                + controlPlan.getCount(ControlType.differentiatedCredit, semester)
                                + controlPlan.getCount(ControlType.practiceReportDefense, semester))
                .sum();

        if (totalDiplomaSubjects == 0) {
            return true;
        }

        List<Grade> diplomaGrades = Stream.of(
                        getAllGrades(ControlType.exam),
                        getAllGrades(ControlType.differentiatedCredit),
                        getAllGrades(ControlType.practiceReportDefense))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        long lockedNonExcellent = diplomaGrades.stream()
                .filter(grade -> grade != Grade.excellent && grade != Grade.notGraded)
                .count();

        long excellentSoFar = diplomaGrades.stream()
                .filter(grade -> grade == Grade.excellent)
                .count();

        long optimisticExcellent = Math.max(
                excellentSoFar,
                totalDiplomaSubjects - lockedNonExcellent);

        long requiredExcellent = (totalDiplomaSubjects * 3L + 3) / 4;
        return optimisticExcellent >= requiredExcellent;
    }

    /**
     * Checks whether the student can receive an increased scholarship this semester.
     *
     * @return true if all graded exams and differentiated credits are excellent.
     *      and all credits are passed.
     */
    public boolean canReceiveIncreasedScholarship() {
        if (educationForm != EducationForm.budget
                || currentSemester < 1 || currentSemester > controlPlan.getSemesterCount()) {
            return false;
        }
        List<Grade> semesterGrades = Stream.of(
                        getSemesterGrades(ControlType.exam, currentSemester),
                        getSemesterGrades(ControlType.differentiatedCredit, currentSemester),
                        getSemesterGrades(ControlType.credit, currentSemester))
                .flatMap(Collection::stream)
                .filter(grade -> grade != Grade.notGraded)
                .collect(Collectors.toList());
        return !semesterGrades.isEmpty()
                && semesterGrades.stream().allMatch(grade ->
                grade == Grade.excellent || grade == Grade.passed);
    }

    /**
     * Checks whether a credit is not passed.
     *
     * @return true if there is an unpassed credit up to the current semester.
     */
    boolean hasUnclearedCredits() {
        return IntStream.rangeClosed(1, currentSemester)
                .boxed()
                .flatMap(semester -> getSemesterGrades(ControlType.credit, semester).stream())
                .anyMatch(grade -> grade != Grade.passed);
    }

    /**
     * Checks whether at least one exam grade is recorded in a semester.
     *
     * @param semester semester number.
     * @return true if an exam grade is recorded.
     */
    boolean hasRecordedExamGrade(int semester) {
        return getSemesterGrades(ControlType.exam, semester).stream()
                .anyMatch(grade -> grade != Grade.notGraded);
    }

    /**
     * Checks whether all exams and differentiated credits are graded in a semester.
     *
     * @param semester semester number.
     * @return true if all planned grades are recorded.
     */
    boolean isCompletedExamSession(int semester) {
        return Stream.of(
                        getSemesterGrades(ControlType.exam, semester),
                        getSemesterGrades(ControlType.differentiatedCredit, semester))
                .flatMap(Collection::stream)
                .allMatch(grade -> grade != Grade.notGraded);
    }

    /**
     * Checks grades in a session against transfer requirements.
     *
     * @param semester semester number.
     * @return true if the grades meet the requirements.
     */
    boolean meetsSessionTransferRequirements(int semester) {
        boolean examsAccepted = getSemesterGrades(ControlType.exam, semester).stream()
                .allMatch(grade -> grade == Grade.excellent || grade == Grade.good);
        boolean differentiatedCreditsAccepted =
                getSemesterGrades(ControlType.differentiatedCredit, semester).stream()
                        .allMatch(grade -> grade != Grade.unsatisfactory);
        boolean creditsAccepted = getSemesterGrades(ControlType.credit, semester).stream()
                .allMatch(grade -> grade == Grade.passed);
        return examsAccepted && differentiatedCreditsAccepted && creditsAccepted;
    }

    /**
     * Checks whether a grade exists for the given control type.
     *
     * @param type  type of control.
     * @param grade grade to find.
     * @return true if the grade is found.
     */
    boolean hasGrade(ControlType type, Grade grade) {
        return getAllGrades(type).stream().anyMatch(currentGrade -> currentGrade == grade);
    }

    /**
     * Checks whether the thesis has a grade other than excellent.
     *
     * @return true if a non-excellent grade is recorded.
     */
    boolean hasNonExcellentThesis() {
        return getAllGrades(ControlType.thesisDefense).stream()
                .anyMatch(grade -> grade != Grade.notGraded && grade != Grade.excellent);
    }

    /**
     * Gets all grades of a control type.
     *
     * @param type type of control.
     * @return grades from all semesters.
     */
    List<Grade> getAllGrades(ControlType type) {
        return grades.get(type).stream()
                .flatMap(Collection::stream)
                .map(subject -> subject.grade)
                .collect(Collectors.toList());
    }

    /**
     * Gets grades of a control type in one semester.
     *
     * @param type     type of control.
     * @param semester semester number.
     * @return grades in the semester or an empty list if the semester is out of range.
     */
    List<Grade> getSemesterGrades(ControlType type, int semester) {
        if (type == null || semester < 1 || semester > controlPlan.getSemesterCount()) {
            return List.of();
        }
        return grades.get(type).get(semester - 1).stream()
                .map(subject -> subject.grade)
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a grade is valid for a control type.
     *
     * @param type  type of control.
     * @param grade grade to check.
     * @return true if the grade can be recorded for this type.
     */
    boolean isValidGrade(ControlType type, Grade grade) {
        if (type == ControlType.credit) {
            return grade == Grade.passed || grade == Grade.failed;
        }
        return grade.isNumeric();
    }
}
