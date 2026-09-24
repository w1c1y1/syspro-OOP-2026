import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeckTest {
    @Test
    void testDeckClass() {
        Card firstCard = new Card(Rank.EIGHT, Suits.DIAMONDS);
        Card secondCard = new Card(Rank.ACE, Suits.SPADES);
        Deck customDeck = new Deck(List.of(firstCard, secondCard));
        Assertions.assertEquals(secondCard, customDeck.pickCard());
        Assertions.assertEquals(firstCard, customDeck.pickCard());
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