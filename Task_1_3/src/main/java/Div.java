/**
 * Implements "Div" type of expressions.
 */
public class Div extends Expression {
    public Expression left;
    public Expression right;

    /**
     * Sets fields of object.
     * @param left  part of expression.
     * @param right part of expression.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public int eval(String values) {
        return left.eval(values) / right.eval(values);
    }


    @Override
    public Expression derivative(String var) {
        Expression numerator = new Sub(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }


    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Div) {
            Div other = (Div) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }
}
