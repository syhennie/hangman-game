package backend.academy.hangman;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MainTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testMain_InvalidInput() {
        simulateInput("2\n1\n");

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Сделайте выбор ещё раз."), "Неверный вывод при некорректном вводе");
        assertTrue(output.contains("Выход из приложения."), "Неверный вывод при выходе из приложения");
    }

    private void simulateInput(String input) {
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
    }
}
