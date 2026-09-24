import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PlayerTest {
    @Test
    void testPlayerClass() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.EIGHT, Suits.CLUBS);
        List<Card> testHand = new ArrayList<>(List.of(testCard, secondTestCard));
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(testPerson.getHand(), testHand);
    }

    @Test
    void testPlayerGetScoreUsual() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.EIGHT, Suits.CLUBS);
        int expected = 19;
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.getScore());
    }

    @Test
    void testPlayerGetScoreTwoAces() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.ACE, Suits.CLUBS);
        int expected = 12;
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.getScore());
    }

    @Test
    void testPlayerHasBlackJack() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.TEN, Suits.CLUBS);
        boolean expected = true;
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.hasBlackjack());
    }

    @Test
    void testPlayerGetFormattedHand() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.NINE, Suits.CLUBS);
        String expected = "[Ace of SPADES (11), Nine of CLUBS (9)] => 20";
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.getFormattedHand());
    }

    @Test
    void testPlayerGetFormattedHandWithTwoAces() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.ACE, Suits.CLUBS);
        String expected = "[Ace of SPADES (1), Ace of CLUBS (11)] => 12";
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.getFormattedHand());
    }

    @Test
    void testGetHand() {
        Card testCard = new Card(Rank.ACE, Suits.SPADES);
        Card secondTestCard = new Card(Rank.ACE, Suits.CLUBS);
        List<Card> expected = new ArrayList<>(List.of(testCard, secondTestCard));
        Player testPerson = new Player();
        testPerson.addCard(testCard);
        testPerson.addCard(secondTestCard);
        Assertions.assertEquals(expected, testPerson.getHand());
    }
}
