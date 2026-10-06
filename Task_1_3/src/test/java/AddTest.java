import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddTest {

    @Test
    void testEval() {
        Add add = new Add(new Number(5), new Number(3));
        Assertions.assertEquals(8, add.eval(""));
    }

    @Test
    void testDerivative() {
        Add add = new Add(new Variable("x"), new Number(5));
        Add expected = new Add(new Number(1), new Number(0));
        Assertions.assertEquals(expected, add.derivative("x"));
    }

    @Test
    void testToString() {
        Add add = new Add(new Variable("x"), new Number(5));
        Assertions.assertEquals("(x+5)", add.toString());
    }

    @Test
    void testEquals() {
        Add add1 = new Add(new Number(1), new Number(2));
        Add add2 = new Add(new Number(1), new Number(2));
        Add add3 = new Add(new Number(2), new Number(1));

        Assertions.assertEquals(add1, add2);
        Assertions.assertNotEquals(add1, add3);
    }
}