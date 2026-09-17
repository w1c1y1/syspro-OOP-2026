import java.util.*;


/**
 * Enum for all card suits.
 */
enum Suits {
    DIAMONDS, HEARTS, CLUBS, SPADES
}


/**
 * Enum for all card values and their text names.
 */
enum ValueToSuit {
    TWO(2, "Two"),
    THREE(3, "Three"),
    FOUR(4, "Four"),
    FIVE(5, "Five"),
    SIX(6, "Six"),
    SEVEN(7, "Seven"),
    EIGHT(8, "Eight"),
    NINE(9, "Nine"),
    TEN(10, "Ten"),
    JACK(10, "Jack"),
    QUEEN(10, "Queen"),
    KING(10, "King"),
    ACE(11, "Ace");

    private final int points;
    private final String title;

    ValueToSuit(int points, String title) {
        this.points = points;
        this.title = title;
    }

    public int getPoints() {
        return points;
    }

    public String getTitle() {
        return title;
    }
}


/**
 * Class for card, that contains number of points that it gets and its suit.
 */
public class Card {
    private final ValueToSuit value;
    private final Suits suit;

    /**
     * Sets card stats.
     * @param value its value.
     * @param suit its suit.
     */
    public Card(ValueToSuit value, Suits suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Getter for value.
     * @return card's value
     */
    public ValueToSuit getValue() {
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