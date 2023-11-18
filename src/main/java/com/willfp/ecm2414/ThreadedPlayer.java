package com.willfp.ecm2414;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedPlayer implements Runnable {
    private final Player player;

    private final Thread thread;

    private final List<ThreadedPlayer> otherPlayers = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ThreadedPlayer(final Player player) {
        this.player = player;
        this.thread = new Thread(this, "Player " + player.getNumber());
    }

    public void setPlayers(List<ThreadedPlayer> players) {
        this.otherPlayers.clear();
        for (ThreadedPlayer otherPlayer : players) {
            if (otherPlayer.equals(this)) {
                continue;
            }
            this.otherPlayers.add(otherPlayer);
        }
    }

    public void start() {
        executor.submit(thread);
    }

    public void stop() {
        executor.shutdown();
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            if (this.player.play()) {
                for (ThreadedPlayer otherPlayer : otherPlayers) {
                    otherPlayer.player.notifyOfWin(player);
                    otherPlayer.stop();
                }
                this.player.logWin();
                stop();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ThreadedPlayer p)) {
            return false;
        }

        return p.player.equals(this.player);
    }

    @Override
    public int hashCode() {
        return player.hashCode();
    }
}
