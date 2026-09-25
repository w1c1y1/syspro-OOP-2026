import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DealerTest {
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
    void testGetFormattedHandHiddenEmpty() {
        Dealer testDealer = new Dealer();
        String expected = "[]";
        Assertions.assertEquals(expected, testDealer.getFormattedHandHidden());
    }
}
