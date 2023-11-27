package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Logs actions that the player has completed.
 */
public class PlayerActionLog {
    /**
     * The player.
     */
    private final Player player;

    /**
     * The logs.
     */
    private final List<String> logs = new ArrayList<>();

    /**
     * If the log is stopped (if the game is over).
     */
    private boolean isStopped = false;

    /**
     * Create a new PlayerActionLog.
     *
     * @param player The player.
     */
    public PlayerActionLog(final Player player) {
        this.player = player;
    }

    /**
     * Log a message to the file.
     *
     * @param message The message.
     */
    public void log(final String message) {
        log(player, message);
    }

    /**
     * Print a log to stdout.
     *
     * @param message The message.
     */
    public void printLog(final String message) {
        System.out.println(formatMessage(player, message));
    }

    /**
     * Log a message in respect to another player. This is used when notifying the player that someone else has won.
     *
     * @param player  The player to log for.
     * @param message The message.
     */
    public void log(final Player player, final String message) {
        logs.add(formatMessage(player, message));
    }

    /**
     * Format a message.
     *
     * @param player  The player.
     * @param message The raw message.
     * @return The formatted message.
     */
    public String formatMessage(final Player player, final String message) {
        return "player " + player.getNumber() + " " + message;
    }

    /**
     * Stop and save the logs.
     */
    public void stop() {
        // Prevent stopping an already stopped log.
        if (isStopped) {
            throw new IllegalStateException("PlayerActionLog already stopped");
        }

        isStopped = true;

        // Write the player output.
        try {
            Path path = Paths.get("player" + player.getNumber() + "_output.txt");
            Files.writeString(path, String.join("\n", logs));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the deck output. Since each player has a deck to their left,
        // we write it here.
        try {
            CardDeck deck = this.player.getLeftDeck();
            Path path = Paths.get("deck" + deck.getNumber() + "_output.txt");
            Files.writeString(
                    path,
                    "deck" + deck.getNumber() + " contents: " + deck.formatCards()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the logs.
     *
     * @return The logs.
     */
    public List<String> getLogs() {
        return logs;
    }
}
