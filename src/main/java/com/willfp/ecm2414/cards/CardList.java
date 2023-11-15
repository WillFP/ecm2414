package com.willfp.ecm2414.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CardList {
    private final List<Integer> cards = Collections.synchronizedList(new ArrayList<>());

    protected CardList() {

    }

    /**
     * Add a card to the list, for starting the game.
     *
     * @param card The list.
     */
    public void dealCard(final int card) {
        cards.add(card);
    }

    /**
     * Get the cards.
     *
     * @return The cards.
     */
    protected List<Integer> getCards() {
        return cards;
    }
}
