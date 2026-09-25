import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
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

}
