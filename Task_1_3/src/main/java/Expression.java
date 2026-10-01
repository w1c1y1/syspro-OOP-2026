/**
 * Abstract class that contains fields for other classes.
 */
public abstract class Expression {
    public abstract int eval(String values);
    public abstract Expression derivative(String var);
    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }

    public boolean equals(Object obj) {
        return false;
    }
}
