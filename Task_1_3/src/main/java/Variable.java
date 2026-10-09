/**
 * Implements variable for expressions.
 */
public class Variable extends Expression {
    public String name;

    /**
     * Just sets var's name.
     * @param name of variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * @throws IllegalArgumentException if val not given.
     */
    @Override
    public int eval(String values) {
        String[] pairs = values.split(";");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv[0].trim().equals(name)) {
                return Integer.parseInt(kv[1].trim());
            }
        }
        throw new IllegalArgumentException("Illegal argument");
    }


    @Override
    public Expression derivative(String var) {
        if (this.name.equals(var)) {
            return new Number(1);
        }
        return new Number(0);
    }


    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return this.name.equals(((Variable) obj).name);
        }
        return false;
    }
}
