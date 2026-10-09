/**
 * Implements "Sub" type of expression.
 */
public class Sub extends Expression {
    public Expression left;
    public Expression right;

    /**
     * Writes part of an expression.
     * @param left  left part of an expression.
     * @param right right part of an expression.
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public int eval(String values) {
        return left.eval(values) - right.eval(values);
    }


    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }


    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sub) {
            Sub other = (Sub) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
