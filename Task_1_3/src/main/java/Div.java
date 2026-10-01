/**
 * Implements "Div" type of expressions.
 */
public class Div extends Expression {
    public Expression left, right;

    /**
     * Sets fields of object.
     * @param left part of expression.
     * @param right part of expression.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates expression.
     * @param values we need to evaluate.
     * @return result of div.
     */
    @Override
    public int eval(String values) {
        return left.eval(values) / right.eval(values);
    }

    /**
     * Counts derivative by rule.
     * @param var we need to diff.
     * @return result by rule.
     */
    @Override
    public Expression derivative(String var) {
        Expression numerator = new Sub(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    /**
     * String format of expression.
     * @return string in brackets.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    /**
     * Checks equality to other expression.
     * @param obj   the reference object with which to compare.
     * @return yes or no.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Div) {
            Div other = (Div) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
