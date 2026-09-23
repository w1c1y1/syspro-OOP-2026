import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

class EngineTest {

    private Scanner makeScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testPlayerBlackjackWin() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.CLUBS),
                new Card(Rank.ACE, Suits.SPADES),
                new Card(Rank.TWO, Suits.DIAMONDS),
                new Card(Rank.KING, Suits.HEARTS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n"));
    }

    @Test
    void testDealerBlackjackWin() {
        List<Card> cards = List.of(
                new Card(Rank.KING, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.ACE, Suits.DIAMONDS),
                new Card(Rank.TWO, Suits.CLUBS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n"));
    }

    @Test
    void testBothBlackjackTie() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.SPADES),
                new Card(Rank.ACE, Suits.CLUBS),
                new Card(Rank.ACE, Suits.DIAMONDS),
                new Card(Rank.KING, Suits.HEARTS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n"));
    }

    @Test
    void testPlayerBust() {
        List<Card> cards = List.of(
                new Card(Rank.FIVE, Suits.CLUBS),
                new Card(Rank.SEVEN, Suits.DIAMONDS),
                new Card(Rank.EIGHT, Suits.SPADES),
                new Card(Rank.TEN, Suits.CLUBS),
                new Card(Rank.TEN, Suits.HEARTS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("1\n0\n"));
    }

    @Test
    void testDealerBust() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.DIAMONDS),
                new Card(Rank.SIX, Suits.HEARTS),
                new Card(Rank.TEN, Suits.SPADES),
                new Card(Rank.NINE, Suits.SPADES),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n0\n"));
    }

    @Test
    void testPlayerWinByScore() {
        List<Card> cards = List.of(
                new Card(Rank.SEVEN, Suits.DIAMONDS),
                new Card(Rank.EIGHT, Suits.CLUBS),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.SPADES)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n0\n"));
    }

    @Test
    void testDealerWinByScore() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.DIAMONDS),
                new Card(Rank.EIGHT, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.NINE, Suits.CLUBS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n0\n"));
    }

    @Test
    void testTieScore() {
        List<Card> cards = List.of(
                new Card(Rank.NINE, Suits.DIAMONDS),
                new Card(Rank.NINE, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("0\n0\n"));
    }

    @Test
    void testInvalidInputHandling() {
        List<Card> cards = List.of(
                new Card(Rank.NINE, Suits.DIAMONDS),
                new Card(Rank.NINE, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Engine engine = new Engine(new Deck(cards));
        engine.start(makeScanner("abc\n0\n0\n"));
    }
}