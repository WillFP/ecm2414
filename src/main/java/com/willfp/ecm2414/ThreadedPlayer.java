package com.willfp.ecm2414;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A ThreadedPlayer encapsulates a Player and manages their gameplay.
 */
public class ThreadedPlayer implements Runnable {
    /**
     * The encapsulated player.
     */
    private final Player player;

    /**
     * The thread where this player plays.
     */
    private final Thread thread;

    /**
     * The other players.
     */
    private final List<ThreadedPlayer> otherPlayers = new ArrayList<>();

    /**
     * The executor that runs the thread.
     */
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Create a new ThreadedPlayer.
     *
     * @param player The player.
     */
    public ThreadedPlayer(final Player player) {
        this.player = player;
        this.thread = new Thread(this, "Player " + player.getNumber());
    }

    /**
     * Set the other players, removing itself.
     *
     * @param players The other players.
     */
    public void setPlayers(final List<ThreadedPlayer> players) {
        this.otherPlayers.clear();
        for (ThreadedPlayer otherPlayer : players) {
            if (otherPlayer.equals(this)) {
                continue;
            }
            this.otherPlayers.add(otherPlayer);
        }
    }

    /**
     * Start playing.
     */
    public void start() {
        executor.submit(thread);
    }

    /**
     * Stop playing.
     */
    public void stop() {
        thread.interrupt();
        executor.shutdown();
    }

    @Override
    public void run() {
        // Keep playing while the thread is not interrupted.
        // Since threading is co-operative in Java, this is checked each time.
        while (!thread.isInterrupted()) {
            // If the player won, this will return true
            if (this.player.play()) {
                // Notify other players that this player has won, and interrupt them.
                for (ThreadedPlayer otherPlayer : otherPlayers) {
                    otherPlayer.stop();
                    otherPlayer.player.notifyOfWin(player);
                }
                // Check again, in case two players have won simultaneously.
                if (!thread.isInterrupted()) {
                    this.player.logWin();
                }
                // Interrupt thread.
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
