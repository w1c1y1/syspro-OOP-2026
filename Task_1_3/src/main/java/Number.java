/**
 * Implements number in expressions.
 */
public class Number extends Expression {
    public int value;

    /**
     * Sets value to public field.
     * @param value value of number.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Eval method, just return value.
     * @param values value of number.
     * @return value of number.
     */
    @Override
    public int eval(String values) {
        return value;
    }

    /**
     * Always zero, so just return it.
     * @param var number we need to diff.
     * @return zero, as expected.
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * Just int to string.
     * @return string.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Is value of other number equals this one.
     * @param obj   the reference object with which to compare.
     * @return yes or no.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Number) {
            return this.value == ((Number) obj).value;
        }
        return false;
    }
}
