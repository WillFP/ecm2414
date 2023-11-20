package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import com.willfp.ecm2414.cards.CardHand;

import java.util.Objects;

public class Player implements Numbered {
    private final int number;

    private final CardDeck leftDeck;

    private final CardDeck rightDeck;

    private final CardHand hand = new CardHand(this);

    private final PlayerActionLog log = new PlayerActionLog(this);

    public Player(final int number,
                  final CardDeck leftDeck,
                  final CardDeck rightDeck) {
        this.number = number;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
    }

    public boolean checkInitialWin() {
        if (hand.isWinning()) {
            logWin();
            return true;
        } else {
            return false;
        }
    }

    private void runChecked(final Runnable task) {
        if (!Thread.currentThread().isInterrupted()) {
            task.run();
        }
    }

    public CardDeck getLeftDeck() {
        return leftDeck;
    }

    public CardDeck getRightDeck() {
        return rightDeck;
    }

    /**
     * Play a turn.
     *
     * @return If the player won.
     */
    public synchronized boolean play() {
        runChecked(() -> {
            int card = leftDeck.drawCard();
            log.log("draws a " + card + " from deck " + leftDeck.getNumber());
            hand.dealCard(card);
        });

        runChecked(() -> {
            int discarded = hand.discardNonPreferredCard();
            rightDeck.discardCard(discarded);
            log.log("discards a " + discarded + " to deck " + rightDeck.getNumber());
        });

        runChecked(() -> {
            log.log("current hand is " + hand.formatCards());
        });

        return hand.isWinning();
    }

    public void logWin() {
        runChecked(() -> {
            log.printLog("wins");
            log.log("wins");
            log.log("exits");
            log.log("final hand: " + hand.formatCards());
            log.stop();
        });
    }

    public void notifyOfWin(final Player winner) {
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

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player player)) {
            return false;
        }

        return player.getNumber() == this.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
