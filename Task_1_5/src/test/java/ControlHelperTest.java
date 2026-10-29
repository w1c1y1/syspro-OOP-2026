import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControlHelperTest {
    int firstSemester = 1;
    int secondSemester = 2;
    String firstControl = "1";
    String secondControl = "2";

    @Test
    void testRecordsAllGradesInSemester() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, firstSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordAll(ControlType.exam, firstSemester,
                List.of(firstControl, secondControl), Grade.excellent);

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

        helper.recordExamSession(secondSemester,
                List.of("Exam"), Grade.excellent,
                List.of("DiffCredit"), Grade.satisfactory);

        assertEquals(Optional.of(Grade.excellent),
                gradebook.getGrade(ControlType.exam, secondSemester, "Exam"));
        assertEquals(Optional.of(Grade.satisfactory),
                gradebook.getGrade(ControlType.differentiatedCredit, secondSemester, "DiffCredit"));
    }

    @Test
    void testRecordsAllCredits() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, secondSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordAllCredits(List.of("Credit1", "Credit2"), Grade.passed);

        assertEquals(Optional.of(Grade.passed),
                gradebook.getGrade(ControlType.credit, firstSemester, "Credit1"));
        assertEquals(Optional.of(Grade.passed),
                gradebook.getGrade(ControlType.credit, secondSemester, "Credit2"));
    }

    @Test
    void testRecordsRequestedNumberOfDiplomaGrades() {
        ElectronicGradebook gradebook =
                new ElectronicGradebook(ElectronicGradebook.EducationForm.budget, secondSemester);
        ControlHelper helper = new ControlHelper(gradebook);

        helper.recordFirstDiplomaGrades(List.of(firstControl, secondControl), Grade.good);

        assertEquals(Optional.of(Grade.good),
                gradebook.getGrade(ControlType.exam, firstSemester, firstControl));
        assertEquals(Optional.of(Grade.good),
                gradebook.getGrade(ControlType.exam, firstSemester, secondControl));
        assertTrue(gradebook.getGrade(ControlType.exam, firstSemester, "3").isEmpty());
    }
}
