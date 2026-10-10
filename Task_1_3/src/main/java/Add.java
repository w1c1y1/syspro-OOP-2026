/**
 * Class that implements "Add" type of expressions.
 */
public class Add extends Expression {
    public Expression left;
    public Expression right;

    /**
     * Making add type expression.
     * @param left  left part of an expression.
     * @param right right part of an expression.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public int eval(String values) {
        return left.eval(values) + right.eval(values);
    }


    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }


    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Add) {
            Add other = (Add) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
