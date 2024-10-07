package backend.academy.hangman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void testConstructor_ValidInput() {
        String expectedWord = "пример";
        String expectedHint = "это тестовое слово";
        int expectedDifficulty = 1;
        Word word = new Word(expectedWord, expectedHint, expectedDifficulty);
        assertEquals(expectedWord, word.word());
        assertEquals(expectedHint, word.hint());
        assertEquals(expectedDifficulty, word.difficulty());
    }

    @Test
    void testConstructor_NullWord() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> new Word(null, "подсказка", 1));
        assertEquals("Слово и подсказка не могут быть null.", exception.getMessage());
    }

    @Test
    void testConstructor_NullHint() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> new Word("слово", null, 1));
        assertEquals("Слово и подсказка не могут быть null.", exception.getMessage());
    }

    @Test
    void testToString() {
        String expectedWord = "пример";
        String expectedHint = "это тестовое слово";
        int difficulty = 1;
        Word word = new Word(expectedWord, expectedHint, difficulty);
        String result = word.toString();
        String expectedString =
            "Word{word='" + expectedWord + '\'' + ", hint='" + expectedHint + '\'' + ", difficulty=" + difficulty + '}';
        assertEquals(expectedString, result);
    }

    @Test
    void testGetWord() {
        Word word = new Word("тест", "подсказка", 1);
        assertEquals("тест", word.word());
    }

    @Test
    void testGetHint() {
        Word word = new Word("тест", "подсказка", 1);
        assertEquals("подсказка", word.hint());
    }

    @Test
    void testGetDifficulty() {
        Word word = new Word("тест", "подсказка", 1);
        assertEquals(1, word.difficulty());
    }
}
