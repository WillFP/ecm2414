package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ThreadedPlayerTest {
    private Player player;
    private ThreadedPlayer threadedPlayer;

    @BeforeEach
    void setUp() {
        player = new Player(1, new CardDeck(1), new CardDeck(2));
        Assertions.assertEquals(player.getNumber(), 1);
        threadedPlayer = new ThreadedPlayer(player);
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(threadedPlayer);
    }

    @Test
    void setPlayersTest() {
        ThreadedPlayer player1 = new ThreadedPlayer(
                new Player(1, new CardDeck(1), new CardDeck(2))
        );
        ThreadedPlayer player2 = new ThreadedPlayer(
                new Player(2, new CardDeck(2), new CardDeck(3))
        );
        ThreadedPlayer player3 = new ThreadedPlayer(
                new Player(3, new CardDeck(3), new CardDeck(4))
        );
        ThreadedPlayer player4 = new ThreadedPlayer(
                new Player(4, new CardDeck(4), new CardDeck(1))
        );
        List<ThreadedPlayer> players = Arrays.asList(player1, player2, threadedPlayer, player3, player4);

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
        Player anotherPlayer = new Player(2, new CardDeck(2), new CardDeck(3));
        ThreadedPlayer anotherThreadedPlayer = new ThreadedPlayer(anotherPlayer);

        Assertions.assertNotEquals(threadedPlayer, anotherThreadedPlayer);
        Assertions.assertNotEquals(threadedPlayer.hashCode(), anotherThreadedPlayer.hashCode());

        ThreadedPlayer sameThreadedPlayer = new ThreadedPlayer(player);
        Assertions.assertEquals(threadedPlayer, sameThreadedPlayer);
        Assertions.assertEquals(threadedPlayer.hashCode(), sameThreadedPlayer.hashCode());
    }
}
