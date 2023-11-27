package com.willfp.ecm2414.cards;

import com.willfp.ecm2414.Numbered;

/**
 * A card deck does not belong to a player.
 */
public class CardDeck extends CardList implements Numbered {
    /**
     * The deck number.
     */
    private final int number;

    /**
     * Create a new card deck.
     *
     * @param number The number.
     */
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
