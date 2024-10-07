package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

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
    public void testGetAttemptsForDifficulty() {
        assertEquals(6, game.getAttemptsForDifficulty("легкий"));
        assertEquals(4, game.getAttemptsForDifficulty("средний"));
        assertEquals(3, game.getAttemptsForDifficulty("сложный"));
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
        // Инициализация
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 0;
        game.setScaffoldStates("легкий");
        game.scaffoldStates = new String[]{"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN"}; // Инициализация для теста


        game.updateScaffold();
        assertEquals(Scaffold.valueOf("ZERO"), game.scaffold);

        // Вводим неверную букву
        boolean result = game.processLetter('ф', testWord);


        assertTrue(result);
        assertEquals(1, game.errorCount);
        assertTrue(game.errorChar.contains('ф'));
        game.updateScaffold();
        assertEquals(Scaffold.valueOf("ONE"), game.scaffold);
    }

    @Test
    public void testDisplayEndMessage_Win() {
        Word testWord = new Word("тест", "подсказка", 1);
        game.secretWordView = "____";
        game.errorCount = 0;
        game.errorMax = 1;

        game.setScaffoldStates("легкий");
        game.scaffoldStates = new String[]{"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN"}; // Инициализация для теста

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

        game.setScaffoldStates("легкий");
        game.scaffoldStates = new String[]{"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN"}; // Инициализация для теста

        game.processLetter('к', testWord);
        game.processLetter('ф', testWord);
        game.processLetter('з', testWord);

        game.displayEndMessage(testWord);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Вы проиграли, допустив много ошибок!"));
    }

}
