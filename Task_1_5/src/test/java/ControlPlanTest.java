import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControlPlanTest {
    int assignmentCount = 15;
    int controlWorkCount = 13;
    int colloquiumCount = 2;
    int examCount = 19;
    int differentiatedCreditCount = 35;
    int creditCount = 7;
    int practiceReportDefenseCount = 3;
    int thesisDefenseCount = 1;
    int allControlsCount = 95;
    List<Integer> examSemesterCounts = List.of(3, 3, 2, 5, 3, 2, 1, 0);
    List<Integer> differentiatedCreditSemesterCounts =
            List.of(3, 3, 6, 5, 4, 6, 4, 4);
    int eighthSemesterTotal = 7;
    int missingSemesterCount = 0;

    @Test
    void testControlTypeTotals() {
        ControlPlan controlPlan = new ControlPlan();

        assertEquals(assignmentCount, controlPlan.getTotalCount(ControlType.assignment));
        assertEquals(controlWorkCount, controlPlan.getTotalCount(ControlType.controlWork));
        assertEquals(colloquiumCount, controlPlan.getTotalCount(ControlType.colloquium));
        assertEquals(examCount, controlPlan.getTotalCount(ControlType.exam));
        assertEquals(differentiatedCreditCount,
                controlPlan.getTotalCount(ControlType.differentiatedCredit));
        assertEquals(creditCount, controlPlan.getTotalCount(ControlType.credit));
        assertEquals(practiceReportDefenseCount,
                controlPlan.getTotalCount(ControlType.practiceReportDefense));
        assertEquals(thesisDefenseCount,
                controlPlan.getTotalCount(ControlType.thesisDefense));
        assertEquals(allControlsCount, controlPlan.getTotalCount());
    }

    @Test
    void testSemesterCounts() {
        ControlPlan controlPlan = new ControlPlan();

        assertEquals(examSemesterCounts,
                controlPlan.getSemesterCounts(ControlType.exam));
        assertEquals(differentiatedCreditSemesterCounts,
                controlPlan.getSemesterCounts(ControlType.differentiatedCredit));
        assertEquals(eighthSemesterTotal, controlPlan.getSemesterTotal(8));
    }

    @Test
    void testOutOfRangeSemesterReturnsZeroCount() {
        ControlPlan controlPlan = new ControlPlan();
        assertEquals(missingSemesterCount, controlPlan.getCount(ControlType.exam, 0));
        assertEquals(missingSemesterCount, controlPlan.getCount(ControlType.exam, 9));
    }
}
