import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.*;


class BlackJackTest {
    @Test
    void testDeckClass() {
        Card firstCard = new Card(Rank.EIGHT, Suits.DIAMONDS);
        Card secondCard = new Card(Rank.ACE, Suits.SPADES);
        Deck customDeck = new Deck(List.of(firstCard, secondCard));
        Assertions.assertEquals(secondCard, customDeck.pickCard());
        Assertions.assertEquals(firstCard, customDeck.pickCard());
    }


    @Test
    void testCardGetSuit() {
        Card testCard = new Card(Rank.ACE, Suits.DIAMONDS);
        Assertions.assertEquals(Suits.DIAMONDS, testCard.getSuit());
    }


    @Test
    void testCardGetValue() {
        Card testCard = new Card(Rank.ACE, Suits.DIAMONDS);
        Assertions.assertEquals(Rank.ACE, testCard.getValue());
    }


    @Test
    void testPersonClass() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.EIGHT, Suits.CLUBS);
        List<Card> testHand = new ArrayList<>(List.of(testCard, secondTestCard));
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(testPerson.getHand(), testHand);
    }

    @Test
    void testDealerClass() {
        Dealer testDealer = new Dealer();
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.EIGHT, Suits.CLUBS);
        testDealer.addCard(testCard);
        testDealer.addCard(secondTestCard);
        String expectedString = "[Ace of SPADES (11), <hidden card>]";
        Assertions.assertEquals(expectedString,
                testDealer.getFormattedHandHidden());
    }

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
                new Card(Rank.NINE, Suits.DIAMONDS),
                new Card(Rank.NINE, Suits.SPADES),
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
    void testPickCardMoreThanFiftyTwoTimes() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.pickCard();
        }
        Card extraCard = deck.pickCard();
        Assertions.assertInstanceOf(Card.class, extraCard);
    }
}