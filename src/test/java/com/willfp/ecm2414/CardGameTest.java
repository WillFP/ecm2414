package com.willfp.ecm2414;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CardGameTest {
    private CardGame cardGame;

    @BeforeEach
    void setUp() {
        cardGame = new CardGame(4, "pack.txt");
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(cardGame);
        Assertions.assertEquals(4, cardGame.getPlayers().size());
    }

    @Test
    void playMethodTest() {
        cardGame.play();
    }

    @Test
    void loadPackTest() {
        List<Integer> pack = cardGame.loadPack("pack.txt");
        Assertions.assertNotNull(pack);
        Assertions.assertFalse(pack.isEmpty());
    }
}
