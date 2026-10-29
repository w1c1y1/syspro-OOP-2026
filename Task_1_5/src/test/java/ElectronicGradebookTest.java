import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectronicGradebookTest {
    int firstSemester = 1;
    int sixthSemester = 6;
    int seventhSemester = 7;
    int eighthSemester = 8;
    String firstControl = "1";
    String secondControl = "2";
    String invalidExamControl = "4";
    int firstDiplomaGradesCount = 14;
    int unreachableDiplomaGradesCount = 15;
    double expectedAverageGrade = 4.5;
    double emptyAverageGrade = 0.0;

    @Test
    void testAverageGrade() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        gradebook.recordGrade(ControlType.assignment, firstSemester, firstControl, Grade.excellent);
        gradebook.recordGrade(ControlType.assignment, firstSemester, secondControl, Grade.good);
        gradebook.recordGrade(ControlType.credit, firstSemester, firstControl, Grade.passed);

        assertEquals(expectedAverageGrade, gradebook.getAverageGrade());
    }

    @Test
    void testEmptyAverageGrade() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertEquals(emptyAverageGrade, gradebook.getAverageGrade());
    }

    @Test
    void testGetGradeReturnsEmptyForUngradedControl() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertTrue(gradebook.getGrade(
                ControlType.exam, firstSemester, firstControl).isEmpty());
    }

    @Test
    void testGetGradeReturnsRecordedGrade() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);
        gradebook.recordGrade(ControlType.exam, firstSemester, firstControl, Grade.good);

        assertEquals(java.util.Optional.of(Grade.good),
                gradebook.getGrade(ControlType.exam, firstSemester, firstControl));
    }

    @Test
    void testRejectsNumericGradeForCredit() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertFalse(gradebook.recordGrade(
                ControlType.credit, firstSemester, firstControl, Grade.excellent));
    }

    @Test
    void testAcceptsPassGradeForCredit() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertTrue(gradebook.recordGrade(
                ControlType.credit, firstSemester, firstControl, Grade.passed));
    }

    @Test
    void testRejectsInvalidSubjectName() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertFalse(gradebook.recordGrade(
                ControlType.exam, firstSemester, null, Grade.excellent));
    }

    @Test
    void testRejectsNullGrade() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertFalse(gradebook.recordGrade(ControlType.exam, firstSemester, firstControl, null));
    }

    @Test
    void testRejectsNotGradedValue() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertFalse(gradebook.recordGrade(
                ControlType.exam, firstSemester, firstControl, Grade.notGraded));
    }

    @Test
    void testCanNotTransferWithoutTwoCompletedSessions() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester,
                java.util.List.of("Exam1", "Exam2", "Exam3"), Grade.excellent,
                java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3"), Grade.satisfactory);
        helper.recordExamSession(seventhSemester,
                java.util.List.of("Exam1", "Exam2"), Grade.good,
                java.util.List.of("DiffCredit1"), Grade.excellent);
        helper.recordAllCredits(java.util.List.of("Credit1", "Credit2", "Credit3", "Credit4", "Credit5", "Credit6", "Credit7"), Grade.passed);

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testTransferToBudgetChangesEducationForm() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester,
                java.util.List.of("Exam1", "Exam2", "Exam3"), Grade.excellent,
                java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3"), Grade.excellent);
        helper.recordExamSession(seventhSemester,
                java.util.List.of("Exam1", "Exam2"), Grade.good,
                java.util.List.of("DiffCredit1"), Grade.excellent);
        helper.recordAllCredits(java.util.List.of("Credit1", "Credit2", "Credit3", "Credit4", "Credit5", "Credit6", "Credit7"), Grade.passed);

        gradebook.transferToBudget();

        assertEquals(ElectronicGradebook.EducationForm.budget, gradebook.getEducationForm());
    }

    @Test
    void testSatisfactoryExamBlocksBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester,
                java.util.List.of("Exam1", "Exam2", "Exam3"), Grade.excellent,
                java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3"), Grade.excellent);
        helper.recordExamSession(seventhSemester,
                java.util.List.of("Exam1", "Exam2"), Grade.satisfactory,
                java.util.List.of("DiffCredit1"), Grade.excellent);
        helper.recordAllCredits(java.util.List.of("Credit1", "Credit2", "Credit3", "Credit4", "Credit5", "Credit6", "Credit7"), Grade.passed);

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testSatisfactoryDifferentiatedCreditIsAllowedForBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester,
                java.util.List.of("Exam1", "Exam2", "Exam3"), Grade.excellent,
                java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3"), Grade.satisfactory);
        helper.recordExamSession(seventhSemester,
                java.util.List.of("Exam1", "Exam2"), Grade.good,
                java.util.List.of("DiffCredit1"), Grade.excellent);
        helper.recordAllCredits(java.util.List.of("Credit1", "Credit2", "Credit3", "Credit4", "Credit5", "Credit6", "Credit7"), Grade.passed);

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testFailedCreditBlocksBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester,
                java.util.List.of("Exam1", "Exam2", "Exam3"), Grade.excellent,
                java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3"), Grade.excellent);
        helper.recordExamSession(seventhSemester,
                java.util.List.of("Exam1", "Exam2"), Grade.excellent,
                java.util.List.of("DiffCredit1"), Grade.excellent);
        helper.recordAllCredits(java.util.List.of("Credit1", "Credit2", "Credit3", "Credit4", "Credit5", "Credit6", "Credit7"), Grade.passed);
        gradebook.recordGrade(ControlType.credit, firstSemester, firstControl, Grade.failed);

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testRedDiplomaCanBePredictedWhenRemainingGradesCouldMeetThreshold() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        ControlHelper helper = new ControlHelper(gradebook);
        java.util.List<String> diplomaSubjects = new java.util.ArrayList<>();
        for (int i = 1; i <= firstDiplomaGradesCount; i++) {
            diplomaSubjects.add("Subject" + i);
        }
        helper.recordFirstDiplomaGrades(diplomaSubjects, Grade.good);

        assertTrue(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testRedDiplomaCannotBePredictedWhenThresholdIsUnreachable() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        ControlHelper helper = new ControlHelper(gradebook);
        java.util.List<String> diplomaSubjects = new java.util.ArrayList<>();
        for (int i = 1; i <= unreachableDiplomaGradesCount; i++) {
            diplomaSubjects.add("Subject" + i);
        }
        helper.recordFirstDiplomaGrades(diplomaSubjects, Grade.good);

        assertFalse(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testSatisfactoryExamBlocksRedDiploma() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        gradebook.recordGrade(ControlType.exam, firstSemester, firstControl, Grade.satisfactory);

        assertFalse(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testSatisfactoryDifferentiatedCreditBlocksRedDiploma() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        gradebook.recordGrade(
                ControlType.differentiatedCredit, firstSemester, firstControl, Grade.satisfactory);

        assertFalse(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testFailedCreditBlocksRedDiploma() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        gradebook.recordGrade(ControlType.credit, firstSemester, firstControl, Grade.failed);

        assertFalse(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testNonExcellentThesisBlocksRedDiploma() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        gradebook.recordGrade(
                ControlType.thesisDefense, eighthSemester, firstControl, Grade.good);

        assertFalse(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testIncreasedScholarshipWhenCurrentSemesterGradesAreExcellentOrPassed() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(
                        ElectronicGradebook.EducationForm.budget, seventhSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordAll(ControlType.exam, seventhSemester, java.util.List.of("Exam1", "Exam2"), Grade.excellent);
        helper.recordAll(ControlType.differentiatedCredit, seventhSemester, java.util.List.of("DiffCredit1", "DiffCredit2", "DiffCredit3", "DiffCredit4"), Grade.excellent);
        helper.recordAll(ControlType.credit, seventhSemester, java.util.List.of("Credit1", "Credit2"), Grade.passed);

        assertTrue(gradebook.canReceiveIncreasedScholarship());
    }
}