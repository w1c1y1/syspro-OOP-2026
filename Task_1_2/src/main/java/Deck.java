import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements deck. Initiates and shuffles it.
 */
public class Deck {
    private List<Card> deck;
    private final boolean isCustom;

    /**
     * Puts flag isCustom to false and initiates random deck.
     */
    public Deck() {
        this.isCustom = false;
        initDeck();
    }

    /**
     * Puts flag isCustom to true and makes current deck given pre-formed deck.
     * @param customDeck pre-formed deck
     */
    public Deck(List<Card> customDeck) {
        this.isCustom = true;
        this.deck = new ArrayList<>(customDeck);
    }

    /**
     * Initiates shuffled in random order 52 card deck.
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
     * Shuffles array of cards.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Picks last card from deck. If pre-ordered deck is empty - kills main.
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