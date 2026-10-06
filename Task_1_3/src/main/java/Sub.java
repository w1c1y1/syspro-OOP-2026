/**
 * Implements "Sub" type of expression.
 */
public class Sub extends Expression {
    public Expression left;
    public Expression right;

    /**
     * Writes part of an expression.
     *
     * @param left  left part of an expression.
     * @param right right part of an expression.
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates an expression.
     *
     * @param values evaluates expression.
     * @return int of an expression.
     */
    @Override
    public int eval(String values) {
        return left.eval(values) - right.eval(values);
    }

    /**
     * Counts diff of an expression.
     *
     * @param var we need to diff.
     * @return new diff expression.
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    /**
     * Makes string out of expression.
     *
     * @return string with expression.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    /**
     * Is this expression equals other.
     *
     * @param obj the reference object with which to compare.
     * @return is equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sub) {
            Sub other = (Sub) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
