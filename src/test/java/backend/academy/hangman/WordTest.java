package backend.academy.hangman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void testConstructor_ValidInput() {
        String expectedWord = "пример";
        String expectedHint = "это тестовое слово";
        Word word = new Word(expectedWord, expectedHint);
        assertEquals(expectedWord, word.word());
        assertEquals(expectedHint, word.hint());
    }

    @Test
    void testConstructor_NullWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Word(null, "подсказка"));
        assertEquals("Слово и подсказка не могут быть null.", exception.getMessage());
    }

    @Test
    void testConstructor_NullHint() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Word("слово", null));
        assertEquals("Слово и подсказка не могут быть null.", exception.getMessage());
    }

    @Test
    void testToString() {
        String expectedWord = "пример";
        String expectedHint = "это тестовое слово";
        Word word = new Word(expectedWord, expectedHint);
        String result = word.toString();
        String expectedString = "Word{word='" + expectedWord + '\'' + ", hint='" + expectedHint + '\'' + '}';
        assertEquals(expectedString, result);
    }

    @Test
    void testGetWord() {
        Word word = new Word("тест", "подсказка");
        assertEquals("тест", word.word());
    }

    @Test
    void testGetHint() {
        Word word = new Word("тест", "подсказка");
        assertEquals("подсказка", word.hint());
    }
}
