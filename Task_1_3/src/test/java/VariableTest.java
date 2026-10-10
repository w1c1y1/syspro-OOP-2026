import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariableTest {

    @Test
    void testEvalSuccess() {
        Variable var = new Variable("x");
        Assertions.assertEquals(42, var.eval("y=10; x=42; z=5"));
    }


    @Test
    void testEvalThrowsExceptionWhenVariableNotFound() {
        Variable var = new Variable("x");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var.eval("y=10; z=5");
        });
    }


    @Test
    void testDerivativeSameVariable() {
        Variable var = new Variable("x");
        Number expected = new Number(1);
        Assertions.assertEquals(expected, var.derivative("x"));
    }

    @Test
    void testDerivativeDifferentVariable() {
        Variable var = new Variable("x");
        Number expected = new Number(0);
        Assertions.assertEquals(expected, var.derivative("y"));
    }

    @Test
    void testToString() {
        Variable var = new Variable("myVar");
        Assertions.assertEquals("myVar", var.toString());
    }

    @Test
    void testEquals() {
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("x");
        Variable var3 = new Variable("y");

        Assertions.assertEquals(var1, var2);
        Assertions.assertNotEquals(var1, var3);
    }
}