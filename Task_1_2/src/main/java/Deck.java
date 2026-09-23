import java.util.*;

/**
 * Class that implements card deck. Initiates it and shuffles it.
 */
public class Deck {
    private List<Card> deck;
    private final boolean isCustom;

    /**
     * Initiates standard 52-card deck and shuffles it.
     */
    public Deck() {
        this.isCustom = false;
        initDeck();
    }

    /**
     * Initiates deck with a pre-formed (custom) sequence of cards for testing or deterministic games.
     *
     * @param customDeck Pre-defined list of cards to be used in order.
     */
    public Deck(List<Card> customDeck) {
        this.isCustom = true;
        this.deck = new ArrayList<>(customDeck);
    }

    /**
     * Makes deck with all cards and then shuffle it.
     */
    private void initDeck() {
        deck = new ArrayList<>();
        for (Suits suit : Suits.values()) {
            for (Rank value : Rank.values()) {
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
            if (isCustom) {
                throw new IllegalStateException("Pre-formed deck is empty!");
            }
            initDeck();
        }
        return deck.removeLast();
    }
}