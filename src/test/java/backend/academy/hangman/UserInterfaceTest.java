package backend.academy.hangman;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInterfaceTest {
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private UserInterface ui;

    @BeforeEach
    void setUp() {
        ui = new UserInterface();
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    void testDisplayMessage() {
        String message = "Сообщение для отображения";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ui.displayMessage(message);
        assertEquals(message + System.lineSeparator(), outContent.toString());
    }
}
