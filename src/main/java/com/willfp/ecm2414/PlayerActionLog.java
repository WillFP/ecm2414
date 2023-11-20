package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlayerActionLog {
    private final Player player;

    public PlayerActionLog(final Player player) {
        this.player = player;
    }

    private final List<String> logs = new ArrayList<>();

    private boolean isStopped = false;

    public void log(final String message) {
        log(player, message);
    }

    public void printLog(final String message) {
        System.out.println(formatMessage(player, message));
    }

    public void log(final Player player, final String message) {
        logs.add(formatMessage(player, message));
    }

    private String formatMessage(final Player player, final String message) {
        return "player " + player.getNumber() + " " + message;
    }

    public void stop() {
        if (isStopped) {
            throw new IllegalStateException("PlayerActionLog already stopped");
        }

        isStopped = true;

        try {
            Path path = Paths.get("player" + player.getNumber() + "_output.txt");
            Files.writeString(path, String.join("\n", logs));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}
