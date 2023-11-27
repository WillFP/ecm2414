package com.willfp.ecm2414;

import com.willfp.ecm2414.cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerActionLogTest {
    private Player player;
    private PlayerActionLog actionLog;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        player = new Player(1, new CardDeck(1), new CardDeck(2));
        actionLog = new PlayerActionLog(player);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void constructorTest() {
        assertNotNull(actionLog);
    }

    @Test
    void logMessageTest() {
        String message = "Test message";
        actionLog.log(message);
        assertTrue(actionLog.getLogs().contains("player 1 Test message"));
    }

    @Test
    void printLogTest() {
        String message = "Print test";
        actionLog.printLog(message);
        assertEquals("player 1 Print test\n", outContent.toString());
    }

    @Test
    void formatMessageTest() {
        String formatted = actionLog.formatMessage(player, "Format test");
        assertEquals("player 1 Format test", formatted);
    }


    @Test
    void stopMethodExceptionTest() {
        actionLog.stop();
        assertThrows(IllegalStateException.class, actionLog::stop);
    }
}
