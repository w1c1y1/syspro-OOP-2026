import java.util.*;

/**
 * Class for card, that contains number of points that it gets and its suit.
 */
public class Card {
    private final Rank value;
    private final Suits suit;

    /**
     * Sets card stats.
     * @param value its value.
     * @param suit its suit.
     */
    public Card(Rank value, Suits suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Getter for value.
     * @return card's value
     */
    public Rank getValue() {
        return value;
    }

    /**
     * Getter for card's suit.
     * @return suit.
     */
    public Suits getSuit() {
        return suit;
    }

    /**
     * Makes string with cards stats.
     * @param points card's points.
     * @return formatted string.
     */
    public String toStringWithPoints(int points) {
        return value.getTitle() + " of " + suit + " (" + points + ")";
    }

    @Override
    public String toString() {
        return toStringWithPoints(value.getPoints());
    }
}