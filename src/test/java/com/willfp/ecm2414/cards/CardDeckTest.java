package com.willfp.ecm2414.cards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CardDeckTest {
    private CardDeck cardDeck;

    @BeforeEach
    void setUp() {
        cardDeck = new CardDeck(1);
    }

    @Test
    void constructorTest() {
        Assertions.assertNotNull(cardDeck);
        Assertions.assertEquals(1, cardDeck.getNumber());
    }

    @Test
    void drawCardTest() {
        cardDeck.getCards().addAll(List.of(1, 2, 3, 4));
        int drawnCard = cardDeck.drawCard();
        Assertions.assertEquals(1, drawnCard);
        Assertions.assertEquals(List.of(2, 3, 4), cardDeck.getCards());
    }

    @Test
    void discardCardTest() {
        cardDeck.getCards().addAll(List.of(1, 2, 3));
        cardDeck.discardCard(4);
        Assertions.assertEquals(List.of(1, 2, 3, 4), cardDeck.getCards());
    }

    @Test
    void getNumberTest() {
        Assertions.assertEquals(1, cardDeck.getNumber());
    }
}
