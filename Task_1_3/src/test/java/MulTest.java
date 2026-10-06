import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MulTest {

    @Test
    void testEval() {
        Mul mul = new Mul(new Number(4), new Number(3));
        Assertions.assertEquals(12, mul.eval(""));
    }

    @Test
    void testDerivative() {
        Mul mul = new Mul(new Variable("x"), new Number(5));
        Add expected = new Add(
                new Mul(new Number(1), new Number(5)),
                new Mul(new Variable("x"), new Number(0))
        );
        Assertions.assertEquals(expected, mul.derivative("x"));
    }

    @Test
    void testToString() {
        Mul mul = new Mul(new Variable("x"), new Number(5));
        Assertions.assertEquals("(x*5)", mul.toString());
    }

    @Test
    void testEquals() {
        Mul mul1 = new Mul(new Variable("x"), new Variable("y"));
        Mul mul2 = new Mul(new Variable("x"), new Variable("y"));
        Mul mul3 = new Mul(new Variable("y"), new Variable("x"));

        Assertions.assertEquals(mul1, mul2);
        Assertions.assertNotEquals(mul1, mul3);
    }
}