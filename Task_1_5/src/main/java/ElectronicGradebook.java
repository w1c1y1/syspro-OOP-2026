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

    ControlPlan controlPlan;
    EnumMap<ControlType, List<List<Grade>>> grades;
    EducationForm educationForm;
    int currentSemester;

    /**
     * Creates a gradebook with the given education form and current semester.
     * @param educationForm student's form of education.
     * @param currentSemester current semester number.
     */
    public ElectronicGradebook(EducationForm educationForm, int currentSemester) {
        this.controlPlan = new ControlPlan();
        this.grades = new EnumMap<>(ControlType.class);
        this.educationForm = educationForm;
        this.currentSemester = currentSemester;
        for (ControlType type : ControlType.values()) {
            List<List<Grade>> semesters = new ArrayList<>();
            for (int semester = 1; semester <= controlPlan.getSemesterCount(); semester++) {
                semesters.add(new ArrayList<>(
                        Collections.nCopies(controlPlan.getCount(type, semester), Grade.notGraded)));
            }
            grades.put(type, semesters);
        }
    }

    /**
     * Gets the curriculum control plan.
     * @return control plan.
     */
    public ControlPlan getControlPlan() {
        return controlPlan;
    }

    /**
     * Gets student's form of education.
     * @return form of education.
     */
    public EducationForm getEducationForm() {
        return educationForm;
    }

    /**
     * Adds a grade for a control in the given semester.
     * @param type type of control.
     * @param semester semester number.
     * @param controlNumber number of control in the semester.
     * @param grade grade to record.
     * @return true if the grade was added, false if the arguments are invalid.
     */
    public boolean recordGrade(ControlType type, int semester, int controlNumber, Grade grade) {
        if (type == null || grade == null || grade == Grade.notGraded
                || semester < 1 || semester > controlPlan.getSemesterCount()) {
            return false;
        }
        List<Grade> semesterGrades = grades.get(type).get(semester - 1);
        if (controlNumber < 1 || controlNumber > semesterGrades.size()
                || !isValidGrade(type, grade)) {
            return false;
        }
        semesterGrades.set(controlNumber - 1, grade);
        return true;
    }

    /**
     * Gets a grade for a control.
     * @param type type of control.
     * @param semester semester number.
     * @param controlNumber number of control in the semester.
     * @return grade if it was recorded, otherwise an empty value.
     */
    public Optional<Grade> getGrade(ControlType type, int semester, int controlNumber) {
        if (type == null || semester < 1 || semester > controlPlan.getSemesterCount()) {
            return Optional.empty();
        }
        List<Grade> semesterGrades = grades.get(type).get(semester - 1);
        if (controlNumber < 1 || controlNumber > semesterGrades.size()) {
            return Optional.empty();
        }
        Grade grade = semesterGrades.get(controlNumber - 1);
        return grade == Grade.notGraded ? Optional.empty() : Optional.of(grade);
    }

    /**
     * Calculates the average of all numeric grades.
     * @return average grade or zero if there are no numeric grades.
     */
    public double getAverageGrade() {
        return grades.values().stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(Grade::isNumeric)
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0);
    }

    /**
     * Checks whether transfer from paid to budget education is possible.
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
     * @return true if the current grades allow the diploma with honors.
     */
    public boolean canReceiveRedDiploma() {
        List<Grade> diplomaGrades = Stream.of(
                        getAllGrades(ControlType.exam),
                        getAllGrades(ControlType.differentiatedCredit),
                        getAllGrades(ControlType.practiceReportDefense))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (diplomaGrades.isEmpty()
                || getAllGrades(ControlType.thesisDefense).isEmpty()) {
            return false;
        }
        if (hasGrade(ControlType.exam, Grade.satisfactory)
                || hasGrade(ControlType.exam, Grade.unsatisfactory)
                || hasGrade(ControlType.differentiatedCredit, Grade.satisfactory)
                || hasGrade(ControlType.differentiatedCredit, Grade.unsatisfactory)
                || hasGrade(ControlType.practiceReportDefense, Grade.unsatisfactory)
                || hasGrade(ControlType.credit, Grade.failed)
                || hasNonExcellentThesis()) {
            return false;
        }
        long excellentCount = diplomaGrades.stream()
                .filter(grade -> grade == Grade.excellent)
                .count();
        long ungradedCount = diplomaGrades.stream()
                .filter(grade -> grade == Grade.notGraded)
                .count();
        long requiredExcellentCount = (diplomaGrades.size() * 3L + 3) / 4;
        return excellentCount + ungradedCount >= requiredExcellentCount;
    }

    /**
     * Checks whether the student can receive an increased scholarship this semester.
     * @return true if all graded exams and differentiated credits are excellent
     *         and all credits are passed.
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
     * @param semester semester number.
     * @return true if an exam grade is recorded.
     */
    boolean hasRecordedExamGrade(int semester) {
        return getSemesterGrades(ControlType.exam, semester).stream()
                .anyMatch(grade -> grade != Grade.notGraded);
    }

    /**
     * Checks whether all exams and differentiated credits are graded in a semester.
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
     * @param semester semester number.
     * @return true if the grades meet the requirements.
     */
    boolean meetsSessionTransferRequirements(int semester) {
        boolean examsAccepted = getSemesterGrades(ControlType.exam, semester).stream()
                .allMatch(grade -> grade == Grade.excellent || grade == Grade.good);
        boolean differentiatedCreditsAccepted =
                getSemesterGrades(ControlType.differentiatedCredit, semester).stream()
                        .allMatch(grade -> grade != Grade.unsatisfactory);
        return examsAccepted && differentiatedCreditsAccepted;
    }

    /**
     * Checks whether a grade exists for the given control type.
     * @param type type of control.
     * @param grade grade to find.
     * @return true if the grade is found.
     */
    boolean hasGrade(ControlType type, Grade grade) {
        return getAllGrades(type).stream().anyMatch(currentGrade -> currentGrade == grade);
    }

    /**
     * Checks whether the thesis has a grade other than excellent.
     * @return true if a non-excellent grade is recorded.
     */
    boolean hasNonExcellentThesis() {
        return getAllGrades(ControlType.thesisDefense).stream()
                .anyMatch(grade -> grade != Grade.notGraded && grade != Grade.excellent);
    }

    /**
     * Gets all grades of a control type.
     * @param type type of control.
     * @return grades from all semesters.
     */
    List<Grade> getAllGrades(ControlType type) {
        return grades.get(type).stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Gets grades of a control type in one semester.
     * @param type type of control.
     * @param semester semester number.
     * @return grades in the semester or an empty list if the semester is out of range.
     */
    List<Grade> getSemesterGrades(ControlType type, int semester) {
        if (type == null || semester < 1 || semester > controlPlan.getSemesterCount()) {
            return List.of();
        }
        return grades.get(type).get(semester - 1);
    }

    /**
     * Checks whether a grade is valid for a control type.
     * @param type type of control.
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
