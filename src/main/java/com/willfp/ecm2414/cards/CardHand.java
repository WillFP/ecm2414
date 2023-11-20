package com.willfp.ecm2414.cards;

import com.willfp.ecm2414.Player;

import java.util.List;
import java.util.Objects;

public class CardHand extends CardList {
    private final Player player;

    public CardHand(final Player player) {
        this.player = player;
    }

    /**
     * Get if the hand is a winning hand (if all the cards are the same).
     *
     * @return If the hand is a winning hand.
     */
    public boolean isWinning() {
        boolean areSame = true;

        List<Integer> cards = getCards();

        for (int i = 0; i < cards.size() - 1; i++) {
            if (!Objects.equals(cards.get(i), cards.get(i + 1))) {
                areSame = false;
                break;
            }
        }

        return areSame;
    }

    /**
     * Discard a card from the hand.
     *
     * @return The discarded card.
     */
    public synchronized int discardNonPreferredCard() {
        List<Integer> canDiscard = getCards().stream().filter(
                it -> it != player.getNumber()
        ).toList();

        Integer randomCard = canDiscard.get((int) (Math.random() * canDiscard.size()));

        getCards().remove(randomCard);

        return randomCard;
    }
}
