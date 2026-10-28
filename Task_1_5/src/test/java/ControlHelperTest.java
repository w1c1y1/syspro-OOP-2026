import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControlHelperTest {
    int firstSemester = 1;
    int secondSemester = 2;
    int firstControl = 1;
    int secondControl = 2;

    @Test
    void testRecordsAllGradesInSemester() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordAll(ControlType.exam, firstSemester, Grade.excellent);

        assertEquals(Optional.of(Grade.excellent),
                gradebook.getGrade(ControlType.exam, firstSemester, firstControl));
        assertEquals(Optional.of(Grade.excellent),
                gradebook.getGrade(ControlType.exam, firstSemester, secondControl));
    }

    @Test
    void testRecordsGradesForExamSession() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, secondSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordExamSession(secondSemester, Grade.excellent, Grade.satisfactory);

        assertEquals(Optional.of(Grade.excellent),
                gradebook.getGrade(ControlType.exam, secondSemester, firstControl));
        assertEquals(Optional.of(Grade.satisfactory),
                gradebook.getGrade(ControlType.differentiatedCredit, secondSemester, firstControl));
    }

    @Test
    void testRecordsAllCredits() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, secondSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordAllCredits(Grade.passed);

        assertEquals(Optional.of(Grade.passed),
                gradebook.getGrade(ControlType.credit, firstSemester, firstControl));
        assertEquals(Optional.of(Grade.passed),
                gradebook.getGrade(ControlType.credit, secondSemester, firstControl));
    }

    @Test
    void testRecordsRequestedNumberOfDiplomaGrades() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, secondSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordFirstDiplomaGrades(secondControl, Grade.good);

        assertEquals(Optional.of(Grade.good),
                gradebook.getGrade(ControlType.exam, firstSemester, firstControl));
        assertEquals(Optional.of(Grade.good),
                gradebook.getGrade(ControlType.exam, firstSemester, secondControl));
        assertTrue(gradebook.getGrade(ControlType.exam, firstSemester, 3).isEmpty());
    }
}
