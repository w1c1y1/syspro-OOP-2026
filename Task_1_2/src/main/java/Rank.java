import java.util.*;

/**
 * Enum for all card values and their text names.
 */
public enum Rank {
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

    Rank(int points, String title) {
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