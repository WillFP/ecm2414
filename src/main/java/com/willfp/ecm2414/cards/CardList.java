package com.willfp.ecm2414.cards;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class CardList {
    private final List<Integer> cards = new CopyOnWriteArrayList<>();

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
}
