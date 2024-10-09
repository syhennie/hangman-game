package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WordRepositoryTest {
    private WordRepository wordRepository;

    @BeforeEach
    void setUp() {
        wordRepository = new WordRepository();
    }

    @Test
    void testGetRandomWordByCategory_ReturnsWordFromCategory() {
        String category = "технологии";
        Word randomWord = wordRepository.getRandomWordByCategory(category);
        assertNotNull(randomWord, "Случайное слово не должно быть null");
        assertTrue(
            List.of("компьютер", "интернет", "телефон", "сервер", "браузер",
                    "программирование", "робот", "алгоритм", "дроны",
                    "криптография", "нейросеть", "блокчейн", "база")
                .contains(randomWord.word()),
            "Случайное слово должно быть из указанной категории"
        );
    }

    @Test
    void testGetRandomWordByCategory_ReturnsDifferentWords() {
        String category = "животные";
        Set<String> wordsReturned = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Word randomWord = wordRepository.getRandomWordByCategory(category);
            assertNotNull(randomWord);
            wordsReturned.add(randomWord.word());
        }
        assertTrue(wordsReturned.size() > 1, "Должны возвращаться разные слова при нескольких вызовах");
    }

    @Test
    void testGetRandomWordByCategoryAndDifficulty_ReturnsWordOfSpecifiedDifficulty() {
        String category = "технологии";
        String difficulty = "легкий";
        Optional<Word> randomWord = wordRepository.getRandomWordByCategoryAndDifficulty(category, difficulty);
        assertNotNull(randomWord, "Случайное слово не должно быть null");
        assertEquals(WordRepository.EASY_LVL, randomWord.get().difficulty(), "Слово должно быть легким");
    }

    @Test
    void testGetRandomWordByCategoryAndDifficulty_ReturnsNullForInvalidDifficulty() {
        String category = "технологии";
        String invalidDifficulty = "очень сложный";
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> wordRepository.getRandomWordByCategoryAndDifficulty(category, invalidDifficulty));
        assertEquals("Недопустимый уровень сложности: " + invalidDifficulty, exception.getMessage());
    }

    @Test
    void testGetAvailableCategories_ReturnsAllCategories() {
        Set<String> expectedCategories = Set.of("технологии", "страны", "животные");
        Set<String> availableCategories = wordRepository.getAvailableCategories();
        assertEquals(expectedCategories, availableCategories, "Доступные категории должны совпадать");
    }
}
