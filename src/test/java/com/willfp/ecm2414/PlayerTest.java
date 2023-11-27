package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import com.willfp.ecm2414.cards.CardHand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PlayerTest {
    private Player player;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private CardHand hand;

    @BeforeEach
    void setUp() {
        leftDeck = new CardDeck(1);
        rightDeck = new CardDeck(2);

        for (int card : List.of(1, 2, 3, 4)) {
            leftDeck.dealCard(card);
        }

        for (int card : List.of(1, 2, 3, 4)) {
            rightDeck.dealCard(card);
        }

        hand = new CardHand(player);


        for (int card : List.of(1, 2, 3, 4)) {
            hand.dealCard(card);
        }

        player = new Player(1, leftDeck, rightDeck);

        // Assuming the Player class can set a mocked hand for testing
        player.setHand(hand);
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(player);
        Assertions.assertEquals(1, player.getNumber());
        Assertions.assertEquals(leftDeck, player.getLeftDeck());
        Assertions.assertEquals(rightDeck, player.getRightDeck());
    }

    @Test
    void playMethodTest() {
        Assertions.assertEquals(leftDeck.drawCard(), 1);
    }

    @Test
    void notifyOfWinTest() {
        Player winner = new Player(2, leftDeck, rightDeck);
        player.notifyOfWin(winner);
    }

    @Test
    void equalsAndHashCodeTest() {
        Player anotherPlayer = new Player(2, leftDeck, rightDeck);
        Assertions.assertNotEquals(player, anotherPlayer);
        Assertions.assertNotEquals(player.hashCode(), anotherPlayer.hashCode());

        Player samePlayer = new Player(1, leftDeck, rightDeck);
        Assertions.assertEquals(player, samePlayer);
        Assertions.assertEquals(player.hashCode(), samePlayer.hashCode());
    }
}
