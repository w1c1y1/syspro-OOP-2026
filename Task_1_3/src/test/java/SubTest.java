import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubTest {

    @Test
    void testEval() {
        Sub sub = new Sub(new Number(10), new Number(3));
        Assertions.assertEquals(7, sub.eval(""));
    }

    @Test
    void testDerivative() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Sub expected = new Sub(new Number(1), new Number(0));
        Assertions.assertEquals(expected, sub.derivative("x"));
    }

    @Test
    void testToString() {
        Sub sub = new Sub(new Variable("x"), new Number(5));
        Assertions.assertEquals("(x-5)", sub.toString());
    }

    @Test
    void testEqualsEqual() {
        Sub sub1 = new Sub(new Variable("x"), new Number(1));
        Sub sub2 = new Sub(new Variable("x"), new Number(1));
        Assertions.assertEquals(sub1, sub2);
    }


    @Test
    void testEqualsNotEqual() {
        Sub sub1 = new Sub(new Variable("x"), new Number(1));
        Sub sub2 = new Sub(new Number(1), new Variable("x"));
        Assertions.assertNotEquals(sub1, sub2);
    }

    @Test
    void testEqualsDifferentInstance() {
        Sub sub1 = new Sub(new Variable("x"), new Number(1));
        String sub2 = "Expression";
        Assertions.assertFalse(sub1.equals(sub2));
    }
}