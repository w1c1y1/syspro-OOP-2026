import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectronicGradebookTest {
    int firstSemester = 1;
    int sixthSemester = 6;
    int seventhSemester = 7;
    int eighthSemester = 8;
    int firstControl = 1;
    int secondControl = 2;
    int invalidExamControl = 4;
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
    void testRejectsControlNumberOutsidePlan() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertFalse(gradebook.recordGrade(
                ControlType.exam, firstSemester, invalidExamControl, Grade.excellent));
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
        helper.recordExamSession(sixthSemester, Grade.excellent, Grade.satisfactory);
        helper.recordExamSession(seventhSemester, Grade.good, Grade.excellent);
        helper.recordAllCredits(Grade.passed);

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testTransferToBudgetChangesEducationForm() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester, Grade.excellent, Grade.excellent);
        helper.recordExamSession(seventhSemester, Grade.good, Grade.excellent);
        helper.recordAllCredits(Grade.passed);

        gradebook.transferToBudget();

        assertEquals(ElectronicGradebook.EducationForm.budget, gradebook.getEducationForm());
    }

    @Test
    void testSatisfactoryExamBlocksBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester, Grade.excellent, Grade.excellent);
        helper.recordExamSession(seventhSemester, Grade.satisfactory, Grade.excellent);
        helper.recordAllCredits(Grade.passed);

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testSatisfactoryDifferentiatedCreditIsAllowedForBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester, Grade.excellent, Grade.satisfactory);
        helper.recordExamSession(seventhSemester, Grade.good, Grade.excellent);
        helper.recordAllCredits(Grade.passed);

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testFailedCreditBlocksBudgetTransfer() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.paid, eighthSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordExamSession(sixthSemester, Grade.excellent, Grade.excellent);
        helper.recordExamSession(seventhSemester, Grade.excellent, Grade.excellent);
        helper.recordAllCredits(Grade.passed);
        gradebook.recordGrade(ControlType.credit, firstSemester, firstControl, Grade.failed);

        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testRedDiplomaCanBePredictedWhenRemainingGradesCouldMeetThreshold() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordFirstDiplomaGrades(firstDiplomaGradesCount, Grade.good);

        assertTrue(gradebook.canReceiveRedDiploma());
    }

    @Test
    void testRedDiplomaCannotBePredictedWhenThresholdIsUnreachable() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, 2);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordFirstDiplomaGrades(unreachableDiplomaGradesCount, Grade.good);

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
        helper.recordAll(ControlType.exam, seventhSemester, Grade.excellent);
        helper.recordAll(ControlType.differentiatedCredit, seventhSemester, Grade.excellent);
        helper.recordAll(ControlType.credit, seventhSemester, Grade.passed);

        assertTrue(gradebook.canReceiveIncreasedScholarship());
    }

    @Test
    void testGoodGradeBlocksIncreasedScholarship() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(
                        ElectronicGradebook.EducationForm.budget, seventhSemester);
        ControlHelper helper = new ControlHelper(gradebook);
        helper.recordAll(ControlType.exam, seventhSemester, Grade.excellent);
        helper.recordAll(ControlType.differentiatedCredit, seventhSemester, Grade.excellent);
        helper.recordAll(ControlType.credit, seventhSemester, Grade.passed);
        gradebook.recordGrade(
                ControlType.differentiatedCredit, seventhSemester, firstControl, Grade.good);

        assertFalse(gradebook.canReceiveIncreasedScholarship());
    }

    @Test
    void testPaidStudentCannotReceiveIncreasedScholarship() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(
                        ElectronicGradebook.EducationForm.paid, seventhSemester);
        gradebook.recordGrade(
                ControlType.exam, seventhSemester, firstControl, Grade.excellent);

        assertFalse(gradebook.canReceiveIncreasedScholarship());
    }

    @Test
    void testGetSemesterGradesReturnsGradesForSemester() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);
        gradebook.recordGrade(ControlType.exam, firstSemester, firstControl, Grade.excellent);

        assertEquals(Grade.excellent,
                gradebook.getSemesterGrades(ControlType.exam, firstSemester).get(0));
    }

    @Test
    void testGetSemesterGradesReturnsEmptyForInvalidSemester() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);

        assertTrue(gradebook.getSemesterGrades(ControlType.exam, 0).isEmpty());
    }
}
