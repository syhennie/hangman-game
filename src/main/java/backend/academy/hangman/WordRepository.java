package backend.academy.hangman;

import java.util.*;

public class WordRepository {
    private final Map<String, List<Word>> wordCategories;
    private final Random random;

    public WordRepository() {
        wordCategories = new HashMap<>();
        random = new Random();
        initWords();
    }

    private void initWords() {
        wordCategories.put("технологии", List.of(
            new Word("компьютер", "Устройство для вычислений"),
            new Word("интернет", "Глобальная сеть"),
            new Word("телефон", "Мобильное устройство для связи"),
            new Word("сервер", "Компьютер для хранения данных и обслуживания запросов"),
            new Word("браузер", "Программа для просмотра веб-страниц"),
            new Word("программирование", "Создание компьютерных программ"),
            new Word("робот", "Автоматическое устройство"),
            new Word("алгоритм", "Последовательность шагов для решения задачи"),
            new Word("дроны", "Беспилотные летающие устройства"),
            new Word("криптография", "Наука о защите информации"),
            new Word("нейросеть", "Модель, основанная на работе человеческого мозга"),
            new Word("блокчейн", "Технология распределённого хранения данных"),
            new Word("база", "Хранилище структурированной информации данных")
        ));

        wordCategories.put("страны", List.of(
            new Word("россия", "Крупнейшая страна в мире"),
            new Word("сша", "Страна на континенте Северная Америка"),
            new Word("китай", "Самая населённая страна мира"),
            new Word("индия", "Вторая по численности населения страна мира"),
            new Word("япония", "Страна восходящего солнца"),
            new Word("германия", "Экономический лидер Европы"),
            new Word("франция", "Известна своей культурой и искусством"),
            new Word("бразилия", "Крупнейшая страна в Южной Америке"),
            new Word("канада", "Вторая по размеру страна в мире"),
            new Word("австралия", "Островная страна и континент"),
            new Word("египет", "Страна с древнейшей историей пирамид"),
            new Word("италия", "Родина Рима и Ватикана"),
            new Word("мексика", "Страна с древними цивилизациями майя"),
            new Word("испания", "Известна корридой и фламенко"),
            new Word("турция", "Мост между Европой и Азией")
        ));

        wordCategories.put("животные", List.of(
            new Word("слон", "Крупное млекопитающее с бивнями"),
            new Word("тигр", "Крупная кошка с полосками"),
            new Word("пингвин", "Птица, которая не умеет летать"),
            new Word("дельфин", "Млекопитающее, живущее в море"),
            new Word("кенгуру", "Сумчатое животное, обитающее в Австралии"),
            new Word("жираф", "Животное с самой длинной шеей"),
            new Word("медведь", "Лесной хищник с густым мехом"),
            new Word("кит", "Самое крупное животное на планете"),
            new Word("орел", "Хищная птица с острым зрением"),
            new Word("змея", "Пресмыкающееся без ног"),
            new Word("лягушка", "Зелёное земноводное"),
            new Word("волк", "Предок домашней собаки"),
            new Word("обезьяна", "Животное, напоминающее человека"),
            new Word("крокодил", "Хищное пресмыкающееся с мощными челюстями"),
            new Word("коала", "Милое сумчатое животное, живущее на деревьях")
        ));
    }

    public Word getRandomWordByCategory(String category) {
        var words = wordCategories.get(category);
        if (!words.isEmpty()) {
            return words.get(random.nextInt(words.size()));
        }
        return null;
    }
}
