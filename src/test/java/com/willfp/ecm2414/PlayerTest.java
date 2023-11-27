package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import com.willfp.ecm2414.cards.CardHand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class PlayerTest {
    private Player player;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private CardHand hand;

    @BeforeEach
    void setUp() {
        leftDeck = mock(CardDeck.class);
        rightDeck = mock(CardDeck.class);
        hand = mock(CardHand.class);
        player = new Player(1, leftDeck, rightDeck);

        // Assuming the Player class can set a mocked hand for testing
        player.setHand(hand);
    }

    @Test
    void constructorTest() {
        assertNotNull(player);
        assertEquals(1, player.getNumber());
        assertEquals(leftDeck, player.getLeftDeck());
        assertEquals(rightDeck, player.getRightDeck());
    }

    @Test
    void checkInitialWinTest() {
        when(hand.isWinning()).thenReturn(true);
        assertTrue(player.checkInitialWin());

        when(hand.isWinning()).thenReturn(false);
        assertFalse(player.checkInitialWin());
    }

    @Test
    void playMethodTest() {
        when(leftDeck.drawCard()).thenReturn(5);
        when(hand.discardNonPreferredCard()).thenReturn(7);
        when(hand.isWinning()).thenReturn(false).thenReturn(true);

        assertFalse(player.play()); // First play, not winning
        verify(leftDeck).drawCard();
        verify(hand).dealCard(5);
        verify(hand).discardNonPreferredCard();
        verify(rightDeck).discardCard(7);

        assertTrue(player.play()); // Second play, winning
    }

    @Test
    void logWinMethodTest() {
        // This requires either mocking the internal logger or verifying the output
        when(hand.isWinning()).thenReturn(true);
        player.logWin();
        // Verify that certain actions are logged
    }

    @Test
    void notifyOfWinTest() {
        Player winner = new Player(2, leftDeck, rightDeck);
        player.notifyOfWin(winner);
        // Verify that the win notification is logged correctly
    }

    @Test
    void equalsAndHashCodeTest() {
        Player anotherPlayer = new Player(2, leftDeck, rightDeck);
        assertNotEquals(player, anotherPlayer);
        assertNotEquals(player.hashCode(), anotherPlayer.hashCode());

        Player samePlayer = new Player(1, leftDeck, rightDeck);
        assertEquals(player, samePlayer);
        assertEquals(player.hashCode(), samePlayer.hashCode());
    }
}
