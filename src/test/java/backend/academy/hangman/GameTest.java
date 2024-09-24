package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameTest {

    private UserInterface ui;
    private Game game;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        ui = new UserInterface(new PrintStream(outputStream));
        game = new Game(ui);
    }

    @Test
    public void testPrintWelcomeMessage() {
        // Arrange

        // Act
        game.printWelcomeMessage();
        String output = outputStream.toString().trim();

        // Assert
        assertEquals("Добро пожаловать в игру Виселица!", output);
    }

    @Test
    public void testChooseCategory_ValidInput() {
        // Arrange
        String input = "страны";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };
        game = new Game(ui);

        // Act
        String category = game.chooseCategory();

        // Assert
        assertEquals("страны", category);
    }

    @Test
    public void testChooseCategory_InvalidInput() {
        // Arrange
        String input = "музыка";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };
        game = new Game(ui);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, game::chooseCategory);
        assertEquals("Недопустимая категория: музыка", exception.getMessage());
    }

    @Test
    public void testChooseDifficulty_ValidInput() {
        // Arrange
        String input = "средний";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };
        game = new Game(ui);

        // Act
        String difficulty = game.chooseDifficulty();

        // Assert
        assertEquals("средний", difficulty);
    }

    @Test
    public void testChooseDifficulty_InvalidInput() {
        // Arrange
        String input = "эксперт";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };
        game = new Game(ui);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, game::chooseDifficulty);
        assertEquals("Недопустимый уровень сложности: эксперт", exception.getMessage());
    }

    @Test
    public void testGetAttemptsForDifficulty() {
        // Arrange & Act
        int easyAttempts = game.getAttemptsForDifficulty("легкий");
        int mediumAttempts = game.getAttemptsForDifficulty("средний");
        int hardAttempts = game.getAttemptsForDifficulty("сложный");

        // Assert
        assertEquals(6, easyAttempts);
        assertEquals(4, mediumAttempts);
        assertEquals(3, hardAttempts);
    }

    @Test
    public void testProcessInput_HintRequest() {
        // Arrange
        Word testWord = new Word("тест", "подсказка");
        game = new Game(ui) {
            @Override
            public Word chooseRandomWord(String category) {
                return testWord;
            }
        };
        String input = "!";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };

        // Act
        boolean result = game.processInput(input, testWord);
        String output = outputStream.toString().trim();

        // Assert
        assertFalse(result);
        assertTrue(output.contains("Подсказка: подсказка"));
    }

    @Test
    public void testProcessLetter_CorrectLetter() {
        // Arrange
        Word testWord = new Word("тест", "подсказка");
        game = new Game(ui) {
            @Override
            public Word chooseRandomWord(String category) {
                return testWord;
            }
        };
        game.secretWordView = "_ест";
        game.setScaffoldStates("легкий");
        String input = "т";

        // Act
        boolean result = game.processLetter(input.charAt(0), testWord);

        // Assert
        assertTrue(result);
        assertEquals("тест", game.secretWordView);
    }

    @Test
    public void testProcessLetter_IncorrectLetter() {
        // Arrange
        Word testWord = new Word("тест", "подсказка");
        game = new Game(ui) {
            @Override
            public Word chooseRandomWord(String category) {
                return testWord;
            }
        };
        game.secretWordView = "____";
        game.setScaffoldStates("легкий");
        String input = "к";

        // Act
        boolean result = game.processLetter(input.charAt(0), testWord);

        // Assert
        assertTrue(result);
        assertTrue(game.errorChar.contains('к'));
        assertEquals(1, game.errorCount);
    }
}
