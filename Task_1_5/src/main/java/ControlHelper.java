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
     * @param grade grade to record.
     */
    public void recordAll(ControlType type, int semester, Grade grade) {
        for (int controlNumber = 1;
             controlNumber <= gradebook.getControlPlan().getCount(type, semester);
             controlNumber++) {
            gradebook.recordGrade(type, semester, controlNumber, grade);
        }
    }

  
    /**
     * Records grades for exams and differentiated credits in one semester.
     * @param semester semester number.
     * @param examGrade grade for exams.
     * @param differentiatedCreditGrade grade for differentiated credits.
     */
    public void recordExamSession(int semester, Grade examGrade, Grade differentiatedCreditGrade) {
        recordAll(ControlType.exam, semester, examGrade);
        recordAll(ControlType.differentiatedCredit, semester, differentiatedCreditGrade);
    }

    /**
     * Records the same grade for every credit in the curriculum.
     * @param grade grade to record.
     */
    public void recordAllCredits(Grade grade) {
        for (int semester = 1;
             semester <= gradebook.getControlPlan().getSemesterCount();
             semester++) {
            recordAll(ControlType.credit, semester, grade);
        }
    }

    /**
     * Records a grade for the first requested diploma assessments.
     * @param count number of assessments to grade.
     * @param grade grade to record.
     */
    public void recordFirstDiplomaGrades(int count, Grade grade) {
        int recorded = 0;
        for (ControlType type : List.of(
                ControlType.exam,
                ControlType.differentiatedCredit,
                ControlType.practiceReportDefense)) {
            for (int semester = 1;
                 semester <= gradebook.getControlPlan().getSemesterCount();
                 semester++) {
                for (int controlNumber = 1;
                     controlNumber <= gradebook.getControlPlan().getCount(type, semester);
                     controlNumber++) {
                    if (recorded >= count) {
                        return;
                    }
                    gradebook.recordGrade(type, semester, controlNumber, grade);
                    recorded++;
                }
            }
        }
    }

}
