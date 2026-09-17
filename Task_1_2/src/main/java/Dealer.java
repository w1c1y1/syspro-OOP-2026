import java.util.*;


/**
 * Class that extends player class and imitates dealer.
 */
public class Dealer extends Player {
    /**
     * Makes formatted string with hidden card.
     * @return string with hidden card.
     */
    public String getFormattedHandHidden() {
        if (hand.isEmpty()) {
            return "[]";
        }
        return "[" + hand.get(0).toString() + ", <hidden card>]";
    }
}