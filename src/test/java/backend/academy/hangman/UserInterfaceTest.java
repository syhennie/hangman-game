package backend.academy.hangman;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserInterfaceTest {

    @Test
    public void testGetInput() {
        // Arrange
        String simulatedUserInput = "Тестовый ввод\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        UserInterface ui = new UserInterface(printStream);

        // Act
        String input = ui.getInput("Введите что-нибудь:");

        // Assert
        String expectedOutput = "Введите что-нибудь:" + System.lineSeparator();
        assertEquals("Тестовый ввод", input.trim());
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testDisplayMessage() {
        // Arrange
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        UserInterface ui = new UserInterface(printStream);
        String message = "Сообщение для отображения";

        // Act
        ui.displayMessage(message);

        // Assert
        String expectedOutput = message + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
}
