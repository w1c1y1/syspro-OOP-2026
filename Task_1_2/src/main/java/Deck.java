import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements deck. Initiates and shuffles it.
 */
public class Deck {
    private List<Card> deck;
    private final boolean isCustom;

    public Deck() {
        this.isCustom = false;
        initDeck();
    }

    public Deck(List<Card> customDeck) {
        this.isCustom = true;
        this.deck = new ArrayList<>(customDeck);
    }

    private void initDeck() {
        deck = new ArrayList<>();
        for (Suits suit : Suits.values()) {
            for (Rank value : Rank.values()) {
                deck.add(new Card(value, suit));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

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