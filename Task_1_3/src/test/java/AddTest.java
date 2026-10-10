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
    void testEqualsEqual() {
        Add add1 = new Add(new Number(1), new Number(2));
        Add add2 = new Add(new Number(1), new Number(2));
        Assertions.assertTrue(add1.equals(add2));
    }

    @Test
    void testEqualsNotEqual() {
        Add add1 = new Add(new Number(1), new Number(2));
        Add add2 = new Add(new Number(1), new Number(3));
        Assertions.assertFalse(add1.equals(add2));
    }

    @Test
    void testEqualsDifferentInstance() {
        Add add1 = new Add(new Number(1), new Number(2));
        String add2 = "Expression";
        Assertions.assertFalse(add1.equals(add2));
    }
}