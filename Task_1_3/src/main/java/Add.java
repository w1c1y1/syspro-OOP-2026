/**
 * Class that implements "Add" type of expressions.
 */

public class Add extends Expression {
    public Expression left, right;

    /**
     * Making add type expression.
     * @param left left part of an expression.
     * @param right right part of an expression.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates expression.
     * @param values we need to evaluate.
     * @return int eval of an expression.
     */
    @Override
    public int eval(String values) {
        return left.eval(values) + right.eval(values);
    }

    /**
     * Gets derivative of an expression.
     * @param var   variable we need to diff.
     * @return new "Add" expression.
     */
    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    /**
     * Makes string from "Add" type expression.
     * @return expression in string type.
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    /**
     * Checks is object the same type as referens, if yes - are they equal.
     * @param obj   the reference object with which to compare.
     * @return  is expressions equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Add) {
            Add other = (Add) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
