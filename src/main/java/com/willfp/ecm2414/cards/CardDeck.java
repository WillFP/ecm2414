package com.willfp.ecm2414.cards;

import com.willfp.ecm2414.Numbered;

public class CardDeck extends CardList implements Numbered {
    private final int number;

    public CardDeck(final int number) {
        this.number = number;
    }

    /**
     * Pick the top card from the deck.
     *
     * @return The top card.
     */
    public synchronized int drawCard() {
        return getCards().remove(0);
    }

    /**
     * Add a card to the bottom of the deck.
     *
     * @param card The card.
     */
    public synchronized void discardCard(final int card) {
        getCards().add(card);
    }

    @Override
    public int getNumber() {
        return number;
    }
}
