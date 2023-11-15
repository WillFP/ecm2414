package com.willfp.ecm2414;

public class PlayerActionLog {
    private final Player player;

    public PlayerActionLog(final Player player) {
        this.player = player;
    }

    public void log(final String message) {
        log(player, message);
    }

    public void log(final Player player, final String message) {
        System.out.println("player " + player.getNumber() + " " + message);
    }
}
