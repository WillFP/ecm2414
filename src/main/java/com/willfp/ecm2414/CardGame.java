package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CardGame {
    private static final int CARDS_PER_PLAYER = 4;

    private final List<Integer> pack;

    private final List<CardDeck> decks = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    public CardGame(final int playerCount,
                    final String packPath) {
        this.pack = loadPack(packPath);

        for (int i = 1; i <= playerCount; i++) {
            decks.add(new CardDeck(i));
        }

        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(i, decks.get(i - 1), decks.get(i % playerCount)));
        }

        for (int i = 0; i < CARDS_PER_PLAYER; i++) {
            for (Player player : players) {
                player.getHand().dealCard(pack.remove(0));
            }
        }

        // Prevent ConcurrentModificationException
        int cardsLeft = pack.size();
        while (cardsLeft > 0) {
            for (CardDeck deck : decks) {
                deck.dealCard(pack.remove(0));
                cardsLeft--;
            }
        }


    }

    public void play() {
        for (Player player : players) {
            if (player.checkInitialWin()) {
                player.logWin();
                return;
            }
        }

        List<Thread> threads = new ArrayList<>();
        AtomicBoolean won = new AtomicBoolean(false);

        for (Player player : players) {
            threads.add(new Thread(() -> {
                while (!won.get()) {
                    if (player.play()) {
                        won.set(true);
                        player.logWin();
                        for (Player otherPlayer : players) {
                            if (otherPlayer != player) {
                                otherPlayer.notifyOfWin(player);
                            }
                        }
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Loads a pack from a file.
     * <p>
     * The pack contains 8 times as many cards as there are players.
     *
     * @param path The path to the pack file.
     */
    private List<Integer> loadPack(final String path) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        // TODO: Get player count from input
        int players = 4;

        // TODO: Get pack path from input
        String packPath = "pack.txt";

        CardGame game = new CardGame(players, packPath);

        game.play();
    }
}