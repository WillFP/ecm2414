package com.willfp.ecm2414;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class ThreadedPlayerTest {
    private Player player;
    private ThreadedPlayer threadedPlayer;

    @BeforeEach
    void setUp() {
        player = Mockito.mock(Player.class);
        Mockito.when(player.getNumber()).thenReturn(1);
        threadedPlayer = new ThreadedPlayer(player);
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(threadedPlayer);
    }

    @Test
    void setPlayersTest() {
        ThreadedPlayer player1 = new ThreadedPlayer(Mockito.mock(Player.class));
        ThreadedPlayer player2 = new ThreadedPlayer(Mockito.mock(Player.class));
        ThreadedPlayer player3 = new ThreadedPlayer(Mockito.mock(Player.class));
        List<ThreadedPlayer> players = Arrays.asList(player1, player2, threadedPlayer, player3);

        threadedPlayer.setPlayers(players);
        Assertions.assertEquals(3, threadedPlayer.getOtherPlayers().size());
        Assertions.assertFalse(threadedPlayer.getOtherPlayers().contains(threadedPlayer));
    }

    @Test
    void startStopTest() {
        threadedPlayer.start();
        threadedPlayer.stop();
        Assertions.assertTrue(threadedPlayer.getThread().isInterrupted());
    }

    @Test
    void equalsAndHashCodeTest() {
        Player anotherPlayer = Mockito.mock(Player.class);
        ThreadedPlayer anotherThreadedPlayer = new ThreadedPlayer(anotherPlayer);

        Assertions.assertNotEquals(threadedPlayer, anotherThreadedPlayer);
        Assertions.assertNotEquals(threadedPlayer.hashCode(), anotherThreadedPlayer.hashCode());

        ThreadedPlayer sameThreadedPlayer = new ThreadedPlayer(player);
        Assertions.assertEquals(threadedPlayer, sameThreadedPlayer);
        Assertions.assertEquals(threadedPlayer.hashCode(), sameThreadedPlayer.hashCode());
    }
}
