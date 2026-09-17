import java.util.*;

/**
 * Class that implements card deck. Initiates it and shuffles it.
 */
public class Deck {
    private List<Card> deck;

    /**
     * Initiates deck.
     */
    public Deck() {
        initDeck();
    }

    /**
     * Makes deck with all cards and then shuffle it.
     */
    private void initDeck() {
        deck = new ArrayList<>();
        for (Suits suit : Suits.values()) {
            for (ValueToSuit value : ValueToSuit.values()) {
                deck.add(new Card(value, suit));
            }
        }
        shuffle();
    }

    /**
     * Method for shuffling deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Method for taking one card out of deck.
     * @return last card from deck.
     */
    public Card pickCard() {
        if (deck.isEmpty()) {
            initDeck();
        }
        return deck.remove(deck.size() - 1);
    }
}