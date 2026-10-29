import java.util.*;

/**
 * Class for entering common assessment results in the record book.
 */
public class ControlHelper {
    ElectronicGradebook gradebook;

    /**
     * Sets the record book used to enter grades.
     * @param gradebook record book to fill.
     */
    public ControlHelper(ElectronicGradebook gradebook) {
        this.gradebook = gradebook;
    }

    /**
     * Records the same grade for every control of a type in one semester.
     * @param type type of control.
     * @param semester semester number.
     * @param subjectNames subject names to record grades for.
     * @param grade grade to record.
     */
    public void recordAll(ControlType type, int semester, List<String> subjectNames, Grade grade) {
        for (String subjectName : subjectNames) {
            gradebook.recordGrade(type, semester, subjectName, grade);
        }
    }

    /**
     * Records grades for exams and differentiated credits in one semester.
     * @param semester semester number.
     * @param examSubjects exam subject names.
     * @param examGrade grade for exams.
     * @param differentiatedCreditSubjects differentiated credit subject names.
     * @param differentiatedCreditGrade grade for differentiated credits.
     */
    public void recordExamSession(int semester, List<String> examSubjects, Grade examGrade,
                                  List<String> differentiatedCreditSubjects, Grade differentiatedCreditGrade) {
        recordAll(ControlType.exam, semester, examSubjects, examGrade);
        recordAll(ControlType.differentiatedCredit, semester, differentiatedCreditSubjects, differentiatedCreditGrade);
    }

    /**
     * Records the same grade for every credit in the curriculum.
     * @param creditSubjects credit subject names.
     * @param grade grade to record.
     */
    public void recordAllCredits(List<String> creditSubjects, Grade grade) {
        for (int semester = 1;
             semester <= gradebook.getControlPlan().getSemesterCount();
             semester++) {
            recordAll(ControlType.credit, semester, creditSubjects, grade);
        }
    }

    /**
     * Records a grade for the first requested diploma assessments.
     * @param diplomaSubjects diploma assessment subject names.
     * @param grade grade to record.
     */
    public void recordFirstDiplomaGrades(List<String> diplomaSubjects, Grade grade) {
        for (String subjectName : diplomaSubjects) {
            gradebook.recordGrade(ControlType.exam, 1, subjectName, grade);
        }
    }
}
