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
     * Evals expression with given vars and vals.
     * @param values of vars.
     * @return int or zero in not give.
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
        return 0;
    }

    /**
     * Returns one, by rules of diff.
     * @param var we need to diff.
     * @return derivative on variable.
     */
    @Override
    public Expression derivative(String var) {
        if (this.name.equals(var)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Returns name of variable.
     * @return name of var in string format.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if var equals other var.
     * @param obj   the reference object with which to compare.
     * @return yes or no.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return this.name.equals(((Variable) obj).name);
        }
        return false;
    }
}
