import java.util.*;
import java.util.stream.*;

/**
 * Class for counts of controls in each semester.
 */
public class ControlPlan {
    int semesterCount = 8;
    EnumMap<ControlType, int[]> controls;

    /**
     * Creates a control plan for eight semesters.
     */
    public ControlPlan() {
        controls = new EnumMap<>(ControlType.class);
        controls.put(ControlType.assignment, new int[]{2, 2, 3, 2, 2, 2, 2, 0});
        controls.put(ControlType.controlWork, new int[]{3, 3, 2, 1, 2, 2, 0, 0});
        controls.put(ControlType.colloquium, new int[]{1, 1, 0, 0, 0, 0, 0, 0});
        controls.put(ControlType.exam, new int[]{3, 3, 2, 5, 3, 2, 1, 0});
        controls.put(ControlType.differentiatedCredit, new int[]{3, 3, 6, 5, 4, 6, 4, 4});
        controls.put(ControlType.credit, new int[]{3, 2, 0, 0, 0, 1, 1, 0});
        controls.put(ControlType.practiceReportDefense, new int[]{0, 0, 0, 0, 0, 0, 1, 2});
        controls.put(ControlType.thesisDefense, new int[]{0, 0, 0, 0, 0, 0, 0, 1});
    }

    /**
     * Gets the number of semesters.
     *
     * @return number of semesters in the plan.
     */
    public int getSemesterCount() {
        return semesterCount;
    }

    /**
     * Gets the number of controls of a given type in a semester.
     *
     * @param type     type of control.
     * @param semester number of semester.
     * @return number of controls or zero if arguments are out of range.
     */
    public int getCount(ControlType type, int semester) {
        if (type == null || semester < 1 || semester > semesterCount) {
            return 0;
        }
        return controls.get(type)[semester - 1];
    }

    /**
     * Gets the number of controls of a type in every semester.
     *
     * @param type type of control.
     * @return list with counts for all semesters.
     */
    public List<Integer> getSemesterCounts(ControlType type) {
        if (type == null) {
            return List.of();
        }
        return IntStream.of(controls.get(type)).boxed().collect(Collectors.toList());
    }

    /**
     * Gets the total number of controls of a type.
     *
     * @param type type of control.
     * @return total number of controls.
     */
    public int getTotalCount(ControlType type) {
        if (type == null) {
            return 0;
        }
        return IntStream.of(controls.get(type)).sum();
    }

    /**
     * Gets the total number of all controls.
     *
     * @return total number of controls in the plan.
     */
    public int getTotalCount() {
        return controls.keySet().stream()
                .mapToInt(this::getTotalCount)
                .sum();
    }

    /**
     * Gets the total number of controls in a semester.
     *
     * @param semester number of semester.
     * @return total number of controls or zero if the semester is out of range.
     */
    public int getSemesterTotal(int semester) {
        if (semester < 1 || semester > semesterCount) {
            return 0;
        }
        return controls.keySet().stream()
                .mapToInt(type -> getCount(type, semester)).sum();
    }
}
