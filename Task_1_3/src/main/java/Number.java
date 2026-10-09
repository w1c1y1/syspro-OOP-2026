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


    @Override
    public int eval(String values) {
        return value;
    }


    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Number) {
            return this.value == ((Number) obj).value;
        }
        return false;
    }
}
