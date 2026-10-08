import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    void testParseNumber() {
        Expression expr = Parser.parse("42");
        Assertions.assertEquals(new Number(42), expr);
    }

    @Test
    void testParseVariable() {
        Expression expr = Parser.parse("x");
        Assertions.assertEquals(new Variable("x"), expr);
    }

    @Test
    void testParseAdd() {
        Expression expr = Parser.parse("(x+5)");
        Expression expected = new Add(new Variable("x"), new Number(5));
        Assertions.assertEquals(expected, expr);
    }

    @Test
    void testParseSub() {
        Expression expr = Parser.parse("(a-b)");
        Expression expected = new Sub(new Variable("a"), new Variable("b"));
        Assertions.assertEquals(expected, expr);
    }

    @Test
    void testParseMul() {
        Expression expr = Parser.parse("(x*10)");
        Expression expected = new Mul(new Variable("x"), new Number(10));
        Assertions.assertEquals(expected, expr);
    }

    @Test
    void testParseDiv() {
        Expression expr = Parser.parse("(100/y)");
        Expression expected = new Div(new Number(100), new Variable("y"));
        Assertions.assertEquals(expected, expr);
    }

    @Test
    void testParseComplexExpression() {
        Expression expr = Parser.parse("((x*2)-1)");
        Expression expected = new Sub(
                new Mul(new Variable("x"), new Number(2)),
                new Number(1)
        );
        Assertions.assertEquals(expected, expr);
    }
}