import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.*;


class EngineTest {
    @Test
    void testPlayerBlackJackWin() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.CLUBS),
                new Card(Rank.ACE, Suits.SPADES),
                new Card(Rank.TWO, Suits.DIAMONDS),
                new Card(Rank.KING, Suits.HEARTS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n"));
        Assertions.assertEquals(1, testEngine.getPlayerScore());
    }

    @Test
    void testDealerBlackJackWin() {
        List<Card> cards = List.of(
                new Card(Rank.KING, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.ACE, Suits.DIAMONDS),
                new Card(Rank.TWO, Suits.CLUBS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n"));
        Assertions.assertEquals(1, testEngine.getDealerScore());
    }

    @Test
    void testBlckJackTie() {
        List<Card> cards = List.of(
                new Card(Rank.ACE, Suits.DIAMONDS),
                new Card(Rank.ACE, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n0\n"));
        Assertions.assertEquals(testEngine.getDealerScore(),
                testEngine.getPlayerScore());
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
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("1\n0\n"));
        Assertions.assertEquals(1, testEngine.getDealerScore());
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
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n0\n"));
        Assertions.assertEquals(1, testEngine.getPlayerScore());
    }

    @Test
    void testInvalidInputHandling() {
        List<Card> cards = List.of(
                new Card(Rank.NINE, Suits.DIAMONDS),
                new Card(Rank.NINE, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("abc\n0\n0\n"));
        Assertions.assertEquals(testEngine.getDealerScore(),
                testEngine.getPlayerScore());
    }

    @Test
    void testPlayerWinByPoints() {
        List<Card> cards = List.of(
                new Card(Rank.SEVEN, Suits.DIAMONDS),
                new Card(Rank.TEN, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.CLUBS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n0\n"));
        int expectedPlayerScore = 1;
        int expectedDealerScore = 0;
        Assertions.assertEquals(expectedPlayerScore,
                testEngine.getPlayerScore());
        Assertions.assertEquals(expectedDealerScore,
                testEngine.getDealerScore());
    }

    @Test
    void testDealerWinByPoints() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.CLUBS),
                new Card(Rank.SEVEN, Suits.SPADES),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.TEN, Suits.DIAMONDS)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n0\n"));
        int expectedPlayerScore = 0;
        int expectedDealerScore = 1;
        Assertions.assertEquals(expectedPlayerScore,
                testEngine.getPlayerScore());
        Assertions.assertEquals(expectedDealerScore,
                testEngine.getDealerScore());
    }

    @Test
    void testDealerWinsByPointsTwoAces() {
        List<Card> cards = List.of(
                new Card(Rank.TEN, Suits.CLUBS),
                new Card(Rank.ACE, Suits.CLUBS),
                new Card(Rank.TEN, Suits.HEARTS),
                new Card(Rank.ACE, Suits.SPADES)
        );
        Deck testDeck = new Deck(cards);
        Engine testEngine = new Engine(testDeck);
        testEngine.start(new Scanner("0\n0\n"));
        int expectedPlayerScore = 0;
        int expectedDealerScore = 1;
        Assertions.assertEquals(expectedPlayerScore,
                testEngine.getPlayerScore());
        Assertions.assertEquals(expectedDealerScore,
                testEngine.getDealerScore());
    }
}