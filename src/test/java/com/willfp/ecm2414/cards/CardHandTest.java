package com.willfp.ecm2414.cards;

import com.willfp.ecm2414.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class CardHandTest {
    private CardHand cardHand;

    @BeforeEach
    void setUp() {
        Player player = new Player(1, Mockito.mock(CardDeck.class), Mockito.mock(CardDeck.class));
        cardHand = new CardHand(player);
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(cardHand);
    }

    @Test
    void isWinningHandTest() {
        cardHand.getCards().addAll(List.of(1, 1, 1, 1)); // Winning hand
        Assertions.assertTrue(cardHand.isWinning());

        cardHand.getCards().clear();
        cardHand.getCards().addAll(List.of(1, 2, 1, 1)); // Not a winning hand
        Assertions.assertFalse(cardHand.isWinning());
    }

    @Test
    void discardNonPreferredCardTest() {
        cardHand.getCards().addAll(List.of(1, 2, 3, 4)); // Assuming player number is 1
        int discarded = cardHand.discardNonPreferredCard();
        Assertions.assertNotEquals(1, discarded); // Should not discard a card matching the player's number
        Assertions.assertFalse(cardHand.getCards().contains(discarded)); // The discarded card should be removed from the hand
    }
}
