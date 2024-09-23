package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testPrintWelcomeMessage() {
        assertDoesNotThrow(() -> game.printWelcomeMessage());
    }

    @Test
    void testChooseCategory_ValidInput() {
        String category = "страны";
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return category;
            }
        };
        assertEquals("страны", game.chooseCategory());
    }

    @Test
    void testChooseCategory_InvalidInput() {
        String category = "недопустимая категория";
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return category;
            }
        };
        assertThrows(IllegalArgumentException.class, game::chooseCategory);
    }

    @Test
    void testChooseRandomWord_ValidCategory() {
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return "технологии";
            }
        };
        Word word = game.chooseRandomWord("технологии");
        assertNotNull(word);
        assertFalse(word.word().isEmpty());
    }

    @Test
    void testChooseDifficulty_ValidInput() {
        String difficulty = "сложный";
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return difficulty;
            }
        };
        assertEquals("сложный", game.chooseDifficulty());
    }

    @Test
    void testChooseDifficulty_InvalidInput() {
        String difficulty = "недопустимый уровень";
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return difficulty;
            }
        };
        assertThrows(IllegalArgumentException.class, game::chooseDifficulty);
    }

    @Test
    void testGetAttempt() {
        assertEquals(6, game.getAttempt("легкий"));
        assertEquals(4, game.getAttempt("средний"));
        assertEquals(3, game.getAttempt("сложный"));
    }

    @Test
    void testStart() {
        game.ui = new UserInterface() {
            @Override
            public String getInput(String prompt) {
                return "технологии"; // выберем категорию
            }
        };
        assertDoesNotThrow(game::start);
    }
}
