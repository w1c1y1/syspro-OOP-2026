import java.util.*;

/**
 * Class for player representation.
 */
public class Player {
    protected List<Card> hand = new ArrayList<>();

    /**
     * Adds card to a player hand.
     * @param card Card picked from deck.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Returns player's hand.
     * @return returns player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Resets player's hand between rounds.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Checks player's hand and counts total points.
     * @return player's points.
     */
    public int getScore() {
        int total = 0;
        int aces = 0;
        for (Card card : hand) {
            total += card.getValue().getPoints();
            if (card.getValue() == Rank.ACE) {
                aces++;
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }

    /**
     * Checks if player lost.
     * @return yes or no.
     */
    public boolean isBust() {
        return getScore() > 21;
    }

    /**
     * Checks if player have blackjack.
     * @return yes or no.
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getScore() == 21;
    }

    /**
     * Returns string of cards that player have.
     * @return formatted string.
     */
    public String getFormattedHand() {
        int total = 0;
        int aces = 0;
        for (Card card : hand) {
            total += card.getValue().getPoints();
            if (card.getValue() == Rank.ACE) {
                aces++;
            }
        }

        int reducedAces = 0;
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
            reducedAces++;
        }

        List<String> cardStrings = new ArrayList<>();
        for (Card card : hand) {
            if (card.getValue() == Rank.ACE && reducedAces > 0) {
                cardStrings.add(card.toStringWithPoints(1));
                reducedAces--;
            } else {
                cardStrings.add(card.toString());
            }
        }
        return "[" + String.join(", ", cardStrings) + "] => " + getScore();
    }
}