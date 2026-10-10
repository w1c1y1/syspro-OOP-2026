/**
 * Implements "Mul" type of expression.
 */
public class Mul extends Expression {
    public Expression left;
    public Expression right;

    /**
     * Sets fields of an object.
     * @param left  left part of an expression.
     * @param right right part of an expression.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public int eval(String values) {
        return left.eval(values) * right.eval(values);
    }


    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }


    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Mul) {
            Mul other = (Mul) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
