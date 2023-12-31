package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CardGame {
    private static final int CARDS_PER_PLAYER = 4;

    private final List<Player> players = new ArrayList<>();

    public CardGame(final int playerCount, final String packPath) {
        List<Integer> pack = loadPack(packPath);
        List<CardDeck> decks = new ArrayList<>();

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

        List<ThreadedPlayer> threads = new ArrayList<>();

        for (Player player : players) {
            ThreadedPlayer threadedPlayer = new ThreadedPlayer(player);
            threads.add(threadedPlayer);
        }

        for (ThreadedPlayer threadedPlayer : threads) {
            threadedPlayer.setPlayers(threads);
        }

        for (ThreadedPlayer threadedPlayer : threads) {
            threadedPlayer.start();
        }
    }

    /**
     * Loads a pack from a file.
     * <p>
     * The pack contains 8 times as many cards as there are players.
     *
     * @param path The path to the pack file.
     */
    public List<Integer> loadPack(final String path) {
        try {
            List<String> lines = new ArrayList<>();

            // The current directory
            String jarDir = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

            // Build the path to the file in the same directory as the JAR
            File file = Paths.get(jarDir, path).toFile();

            try (InputStream is = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            List<Integer> deck = new ArrayList<>(
                    lines.stream().map(Integer::parseInt).toList()
            );

            Collections.shuffle(deck);

            return deck;
        } catch (Exception e) {
            // Default pack inside JAR resources, for testing.
            try {
                List<String> lines = new ArrayList<>();

                System.out.println("Failed to load pack from file. Loading default pack.");

                try (InputStream is = this.getClass().getResourceAsStream("/" + path);
                     InputStreamReader isr = new InputStreamReader(is);
                     BufferedReader reader = new BufferedReader(isr)) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                List<Integer> deck = new ArrayList<>(
                        lines.stream().map(Integer::parseInt).toList()
                );

                Collections.shuffle(deck);

                return deck;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IllegalStateException("Failed to load pack.");
            }
        }
    }

    /**
     * Get the players.
     *
     * @return The players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            System.out.println("Choose a number of players:");
            int players = Integer.parseInt(in.nextLine());

            System.out.println("Choose the name of the pack");
            String packPath = in.nextLine();

            new CardGame(players, packPath).play();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of players!");
        }
    }
}
