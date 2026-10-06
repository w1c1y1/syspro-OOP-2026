import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberTest {

    @Test
    void testEval() {
        Number testNumber = new Number(10);
        Assertions.assertEquals(10, testNumber.eval("x=5"));
    }

    @Test
    void testDerivative() {
        Number testNumber = new Number(5);
        Number expectedValue = new Number(0);
        Assertions.assertEquals(expectedValue, testNumber.derivative("x"));
    }

    @Test
    void testToString() {
        Number testNumber = new Number(5);
        String expected = "5";
        Assertions.assertEquals(expected, testNumber.toString());
    }

    @Test
    void testEquals() {
        Number testNumber1 = new Number(5);
        Number testNumber2 = new Number(5);
        Number testNumber3 = new Number(10);

        Assertions.assertEquals(testNumber1, testNumber2);
        Assertions.assertNotEquals(testNumber1, testNumber3);
    }
}