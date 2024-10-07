package backend.academy.hangman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordRepository {
    public static final int EASY_LVL = 1;
    public static final int MEDIUM_LVL = 2;
    public static final int HARD_LVL = 3;
    private final Map<String, List<Word>> wordCategories;
    private final Random random;

    public WordRepository() {
        wordCategories = new HashMap<>();
        random = new Random();
        initWords();
    }

    private void initWords() {
        wordCategories.put("технологии", List.of(
            new Word("компьютер", "Устройство для вычислений", EASY_LVL),
            new Word("интернет", "Глобальная сеть", EASY_LVL),
            new Word("телефон", "Мобильное устройство для связи", EASY_LVL),
            new Word("сервер", "Компьютер для хранения данных", MEDIUM_LVL),
            new Word("браузер", "Программа для просмотра веб-страниц", MEDIUM_LVL),
            new Word("программирование", "Создание компьютерных программ", HARD_LVL),
            new Word("робот", "Автоматическое устройство", EASY_LVL),
            new Word("алгоритм", "Последовательность шагов для решения задачи", HARD_LVL),
            new Word("дроны", "Беспилотные летающие устройства", MEDIUM_LVL),
            new Word("криптография", "Наука о защите информации", HARD_LVL),
            new Word("нейросеть", "Модель, основанная на работе мозга", HARD_LVL),
            new Word("блокчейн", "Технология распределённого хранения данных", HARD_LVL),
            new Word("база", "Хранилище структурированной информации", EASY_LVL)
        ));

        wordCategories.put("страны", List.of(
            new Word("россия", "Крупнейшая страна в мире", EASY_LVL),
            new Word("сша", "Страна на континенте Северная Америка", EASY_LVL),
            new Word("китай", "Самая населённая страна", EASY_LVL),
            new Word("индия", "Вторая по численности населения", MEDIUM_LVL),
            new Word("япония", "Страна восходящего солнца", MEDIUM_LVL),
            new Word("германия", "Экономический лидер Европы", MEDIUM_LVL),
            new Word("франция", "Известна своей культурой", HARD_LVL),
            new Word("бразилия", "Крупнейшая страна в Южной Америке", MEDIUM_LVL),
            new Word("канада", "Вторая по размеру страна в мире", MEDIUM_LVL),
            new Word("австралия", "Островная страна и континент", MEDIUM_LVL),
            new Word("египет", "Страна с древнейшей историей пирамид", HARD_LVL),
            new Word("италия", "Родина Рима и Ватикана", HARD_LVL),
            new Word("мексика", "Страна с древними цивилизациями майя", MEDIUM_LVL),
            new Word("испания", "Известна корридой и фламенко", HARD_LVL),
            new Word("турция", "Мост между Европой и Азией", HARD_LVL)
        ));

        wordCategories.put("животные", List.of(
            new Word("слон", "Крупное млекопитающее с бивнями", EASY_LVL),
            new Word("тигр", "Крупная кошка с полосками", EASY_LVL),
            new Word("пингвин", "Птица, которая не умеет летать", MEDIUM_LVL),
            new Word("дельфин", "Млекопитающее, живущее в море", MEDIUM_LVL),
            new Word("кенгуру", "Сумчатое животное из Австралии", HARD_LVL),
            new Word("жираф", "Животное с самой длинной шеей", MEDIUM_LVL),
            new Word("медведь", "Лесной хищник с густым мехом", EASY_LVL),
            new Word("кит", "Самое крупное животное на планете", HARD_LVL),
            new Word("орел", "Хищная птица с острым зрением", MEDIUM_LVL),
            new Word("змея", "Пресмыкающееся без ног", MEDIUM_LVL),
            new Word("лягушка", "Зелёное земноводное", EASY_LVL),
            new Word("волк", "Предок домашней собаки", MEDIUM_LVL),
            new Word("обезьяна", "Животное, напоминающее человека", HARD_LVL),
            new Word("крокодил", "Хищное пресмыкающееся с мощными челюстями", HARD_LVL),
            new Word("коала", "Милое сумчатое животное, живущее на деревьях", HARD_LVL)
        ));
    }

    public Word getRandomWordByCategory(String category) {
        var words = wordCategories.get(category);
        if (!words.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        }
        return null;
    }

    public Set<String> getAvailableCategories() { // добавлен метод для определения категорий слов
        return wordCategories.keySet();
    }

    public Word getRandomWordByCategoryAndDifficulty(String category, String difficulty) {
        List<Word> words = wordCategories.get(category);
        if (words != null) {
            int level = switch (difficulty.toLowerCase()) {
                case "легкий" -> EASY_LVL;
                case "средний" -> MEDIUM_LVL;
                case "сложный" -> HARD_LVL;
                default -> throw new IllegalArgumentException("Недопустимый уровень сложности: " + difficulty);
            };
            List<Word> filteredWords = words.stream() // фильтрация слов по уровню сложности
                .filter(word -> word.difficulty() == level)
                .toList();
            if (!filteredWords.isEmpty()) {
                return filteredWords.get(random.nextInt(filteredWords.size()));
            }
        }
        return null;
    }

}
