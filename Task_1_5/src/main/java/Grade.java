/**
 * Enum for grades that can be recorded in the gradebook.
 */
public enum Grade {
    excellent(5),
    good(4),
    satisfactory(3),
    unsatisfactory(2),
    passed(0),
    failed(0),
    notGraded(0);

    int value;

    /**
     * Sets the numeric value of a grade.
     *
     * @param value numeric value.
     */
    Grade(int value) {
        this.value = value;
    }

    /**
     * Gets the numeric value of a grade.
     *
     * @return grade value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Checks whether a grade has a numeric value.
     *
     * @return true if this is a numeric grade.
     */
    public boolean isNumeric() {
        return this == excellent || this == good
                || this == satisfactory || this == unsatisfactory;
    }
}
