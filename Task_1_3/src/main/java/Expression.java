/**
 * Abstract class that contains abstract methods for other classes and also print.
 */
public abstract class Expression {
    /**
     * Method for evaluation of an expression.
     * @param values we need to evaluate.
     * @return value of a given expression.
     */
    public abstract int eval(String values);

    /**
     * Gets derivative of a given expression.
     * @param var variable/number we need to diff.
     * @return derivative of a given var or val by rules.
     */
    public abstract Expression derivative(String var);

    /**
     * Makes string out of given expression.
     * @return string view of an expression.
     */
    public abstract String toString();

    /**
     * Prints given expression in string format to terminal.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Compares two given objects.
     * @param obj   the reference object with which to compare.
     * @return true or false, if objects equal/not equal.
     */
    @Override
    public abstract boolean equals(Object obj);
}
