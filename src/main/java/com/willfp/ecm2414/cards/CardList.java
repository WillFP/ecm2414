package com.willfp.ecm2414.cards;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a list of cards, e.g. a deck or a hand.
 */
public abstract class CardList {
    /**
     * A thread-safe list of cards.
     */
    private final List<Integer> cards = new CopyOnWriteArrayList<>();

    /**
     * Create a new card list.
     */
    protected CardList() {

    }

    /**
     * Add a card to the list, for starting the game.
     *
     * @param card The list.
     */
    public synchronized void dealCard(final int card) {
        cards.add(card);
    }

    /**
     * Get the cards.
     *
     * @return The cards.
     */
    protected synchronized List<Integer> getCards() {
        return cards;
    }

    /**
     * Format cards as a string.
     *
     * @return The formatted cards.
     */
    public final String formatCards() {
        return String.join(" ", getCards().stream().map(String::valueOf).toArray(String[]::new));
    }
}
