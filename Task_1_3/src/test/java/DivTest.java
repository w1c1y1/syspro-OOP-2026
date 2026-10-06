import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DivTest {

    @Test
    void testEval() {
        Div div = new Div(new Number(10), new Number(2));
        Assertions.assertEquals(5, div.eval(""));
    }

    @Test
    void testDerivative() {
        Div div = new Div(new Variable("x"), new Number(2));
        Expression expectedNumerator = new Sub(
                new Mul(new Number(1), new Number(2)),
                new Mul(new Variable("x"), new Number(0))
        );
        Expression expectedDenominator = new Mul(new Number(2), new Number(2));
        Div expected = new Div(expectedNumerator, expectedDenominator);
        Assertions.assertEquals(expected, div.derivative("x"));
    }

    @Test
    void testToString() {
        Div div = new Div(new Variable("x"), new Number(2));
        Assertions.assertEquals("(x/2)", div.toString());
    }

    @Test
    void testEquals() {
        Div div1 = new Div(new Number(10), new Number(2));
        Div div2 = new Div(new Number(10), new Number(2));
        Div div3 = new Div(new Number(2), new Number(10));

        Assertions.assertEquals(div1, div2);
        Assertions.assertNotEquals(div1, div3);
    }
}