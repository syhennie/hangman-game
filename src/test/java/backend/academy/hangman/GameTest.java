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
    public void testChooseRandomWord_ValidCategoryAndDifficulty() {
        String category = "страны";
        String difficulty = "легкий";
        Word word = game.chooseRandomWord(category, difficulty);
        assertNotNull(word);
    }

    @Test
    public void testChooseRandomWord_InvalidCategoryAndDifficulty() {
        String category = "недоступная категория";
        String difficulty = "недоступный уровень";
        Word word = game.chooseRandomWord(category, difficulty);
        assertNull(word);
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Категория или уровень сложности не найдены. Попробуйте снова."));
    }

    @Test
    public void testProcessInput_HintRequest() {
        Word secretWord = new Word("тест", "подсказка", 1);
        String input = "!";

        game.processInput(input, secretWord);
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Подсказка: подсказка"));
    }

    @Test
    public void testProcessLetter_CorrectLetter() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.processLetter('т', testWord);

        assertEquals("т__т", game.secretWordView);
    }

    @Test
    public void testProcessLetter_IncorrectLetter() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 0;

        // Обновляем состояние виселицы до старта
        game.scaffold = new Scaffold();  // Инициализация нового состояния
        game.scaffold.getScaffold(game.errorCount);

        // Вводим неверную букву
        boolean result = game.processLetter('ф', testWord);

        assertTrue(result);
        assertEquals(1, game.errorCount);
        assertTrue(game.errorChar.contains('ф'));

        // Проверка состояния виселицы
        String expectedScaffold =
            "  ________\n" +
                "  |      |\n" +
                "  |       \n" +
                "  |        \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n"; // Ожидаемое состояние при 1 ошибке
        assertEquals(expectedScaffold, game.scaffold.getScaffold(game.errorCount));
    }

    @Test
    public void testDisplayEndMessage_Win() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 0;
        game.errorMax = 1;

        game.processLetter('т', testWord);
        game.processLetter('е', testWord);
        game.processLetter('с', testWord);

        game.displayEndMessage(testWord);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Поздравляем! Вы выиграли!"));
    }

    @Test
    public void testDisplayEndMessage_Lose() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 3;
        game.errorMax = 3;

        game.processLetter('к', testWord);
        game.processLetter('ф', testWord);
        game.processLetter('з', testWord);

        game.displayEndMessage(testWord);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Вы проиграли, допустив много ошибок!"));
    }

    @Test
    public void testGameLosesAfterExceedingMaxAttempts() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 3;
        game.errorMax = 3;

        game.processLetter('к', testWord);
        game.processLetter('ф', testWord);
        game.processLetter('з', testWord);

        game.displayEndMessage(testWord);
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Вы проиграли, допустив много ошибок!"));
    }

    @Test
    public void testGameStateChangesOnCorrectAndIncorrectGuesses() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 0;

        // Угадали букву
        game.processLetter('т', testWord);
        assertEquals("т__т", game.secretWordView);
        assertEquals(0, game.errorCount);  // Ошибок не должно быть

        // Угадали неверную букву
        game.processLetter('ф', testWord);
        assertEquals("т__т", game.secretWordView);  // Слово не должно измениться
        assertEquals(1, game.errorCount);  // Ошибка должна увеличиться
    }

    @Test
    public void testInputLongStringPromptsForRetry() {
        Word secretWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        String input = "неверный ввод";
        ui = new UserInterface(new PrintStream(outputStream)) {
            @Override
            public String getInput(String prompt) {
                return input;
            }
        };
        boolean result = game.processInput(input, secretWord);

        assertFalse(result);
        assertEquals("____", game.secretWordView);
        assertEquals(0, game.errorCount);
    }


}
