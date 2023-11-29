package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import com.willfp.ecm2414.cards.CardHand;

import java.util.Objects;

/**
 * A player in the card game.
 */
public class Player implements Numbered {
    /**
     * The player's number.
     */
    private final int number;

    /**
     * The deck to the left.
     */
    private final CardDeck leftDeck;

    /**
     * The deck to the right.
     */
    private final CardDeck rightDeck;

    /**
     * The player's current cards.
     */
    private CardHand hand = new CardHand(this);

    /**
     * The action log.
     */
    private final PlayerActionLog log = new PlayerActionLog(this);

    /**
     * Create a new player.
     *
     * @param number    Their number.
     * @param leftDeck  The deck to their left.
     * @param rightDeck The deck to their right.
     */
    public Player(final int number,
                  final CardDeck leftDeck,
                  final CardDeck rightDeck) {
        this.number = number;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    /**
     * Check if the player has been dealt a hand that has immediately won.
     *
     * @return If the player has won.
     */
    public boolean checkInitialWin() {
        if (hand.isWinning()) {
            logWin();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Run a task only if the thread has not been interrupted.
     * <p>
     * This is necessary due to the co-operative nature of threading in Java since Thread#stop
     * was deprecated.
     *
     * @param task The task to run.
     */
    private void runChecked(final Runnable task) {
        if (!Thread.currentThread().isInterrupted()) {
            task.run();
        }
    }

    /**
     * Get the deck to the left.
     *
     * @return The deck.
     */
    public CardDeck getLeftDeck() {
        return leftDeck;
    }

    /**
     * Get the deck to the right.
     *
     * @return The deck.
     */
    public CardDeck getRightDeck() {
        return rightDeck;
    }

    /**
     * Play a turn.
     *
     * @return If the player won.
     */
    public synchronized boolean play() {
        // Removing a card from the left and adding it to the deck as one operation.
        runChecked(() -> {
            int card = leftDeck.drawCard();
            log.log("draws a " + card + " from deck " + leftDeck.getNumber());
            hand.dealCard(card);
        });

        // Discarding a card from the deck and adding it to the right as one operation.
        runChecked(() -> {
            int discarded = hand.discardNonPreferredCard();
            rightDeck.discardCard(discarded);
            log.log("discards a " + discarded + " to deck " + rightDeck.getNumber());
        });

        // Only log current hand if the another player has not won.
        runChecked(() -> {
            log.log("current hand is " + hand.formatCards());
        });

        return hand.isWinning();
    }

    /**
     * Log that this player has won.
     */
    public void logWin() {
        // Don't log win if another player has already won.
        runChecked(() -> {
            log.printLog("wins");
            log.log("wins");
            log.log("exits");
            log.log("final hand: " + hand.formatCards());
            log.stop();
        });
    }

    /**
     * Notify this player that someone else has won.
     *
     * @param winner The winner.
     */
    public void notifyOfWin(final Player winner) {
        // Don't log another player's win if *another* player has already won before.
        runChecked(() -> {
            log.log(winner, "has informed player " + number + " that player " + winner.getNumber() + " has won");
            log.log("exits");
            log.log("hand: " + hand.formatCards());
            log.stop();
        });
    }

    /**
     * Get the hand.
     *
     * @return The hand.
     */
    public CardHand getHand() {
        return hand;
    }

    /**
     * Set the hand.
     *
     * @param hand The hand.
     */
    public void setHand(final CardHand hand) {
        this.hand = hand;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }

        Player player = (Player) obj;

        return player.getNumber() == this.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
