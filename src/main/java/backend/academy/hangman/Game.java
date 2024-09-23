package backend.academy.hangman;

import java.util.*;

/**
 * Класс Game реализует основную логику игры "Виселица".
 * Он управляет процессом игры, включая выбор категорий, уровня сложности, обработку ошибок
 * и отображение подсказок.
 */
public class Game {
    static int errorCount;              // Текущее количество ошибок
    static int errorMax;                // Максимальное количество допустимых ошибок
    static List<Character> errorChar;   // Список неверно указанных букв
    static String secretWordView;       // Текущее отображение загаданного слова (с угаданными буквами)
    static Scaffold scaffold;           // Текущее состояние виселицы

    protected UserInterface ui;     // Объект для взаимодействия с пользователем
    private final WordRepository wordRepository; // Хранилище слов для выбора случайного слова
    private String[] scaffoldStates;    // Массив состояний виселицы для выбранного уровня сложности
    private boolean hintShown;          // Флаг, указывающий, была ли показана подсказка

    public Game() {
        ui = new UserInterface();
        wordRepository = new WordRepository();
        hintShown = false;
    }

    public void printWelcomeMessage() {
        ui.displayMessage("Добро пожаловать в игру Виселица!");
    }

    public String chooseCategory() {
        String category = ui.getInput("Выберите категорию: технологии, страны, животные");
        if (category == null || category.isEmpty()) {
            ui.displayMessage("Выбрана категория по умолчанию.");
            category = "технологии"; // Категория по умолчанию
        }
        category = category.toLowerCase();

        if (!category.equals("технологии") && !category.equals("страны") && !category.equals("животные")) {
            throw new IllegalArgumentException("Недопустимая категория: " + category);
        }
        return category;
    }

    public Word chooseRandomWord(String category) {
        Word randomWord = wordRepository.getRandomWordByCategory(category);
        if (randomWord == null) {
            ui.displayMessage("Категория не найдена. Попробуйте снова.");
            return null;
        }
        return randomWord;
    }

    public String chooseDifficulty() {
        String difficulty = ui.getInput("Выберите уровень сложности: легкий, средний, сложный");
        if (difficulty == null || difficulty.isEmpty()) {
            ui.displayMessage("Выбрана сложность по умолчанию.");
            difficulty = "средний"; // Сложность по умолчанию
        }
        difficulty = difficulty.toLowerCase();

        if (!difficulty.equals("легкий") && !difficulty.equals("средний") && !difficulty.equals("сложный")) {
            throw new IllegalArgumentException("Недопустимый уровень сложности: " + difficulty);
        }
        return difficulty;
    }

    public int getAttempt(String difficulty) {
        return switch (difficulty) {
            case "легкий" -> 6;
            case "средний" -> 4;
            case "сложный" -> 3;
            default -> throw new IllegalArgumentException("Недопустимый уровень сложности: " + difficulty);
        };
    }

    private void setScaffoldStates(String difficulty) {
        switch (difficulty) {
            case "легкий":
                scaffoldStates = new String[] {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX"};
                break;
            case "средний":
                scaffoldStates = new String[] {"ZERO", "ONE", "TWO", "THREE", "SIX"};
                break;
            case "сложный":
                scaffoldStates = new String[] {"ZERO", "THREE", "SIX"};
                break;
            default:
                throw new IllegalArgumentException("Недопустимый уровень сложности: " + difficulty);
        }
    }

    /**
     * Основной метод, запускающий процесс игры.
     * Обрабатывает выбор категории, сложности и выполнение игровых действий,
     * включая ввод букв и показ подсказок.
     */
    public void start() {
        errorCount = 0; // Обнуление счётчика ошибок
        errorChar = new ArrayList<>(); // Инициализация списка ошибок
        hintShown = false; // Сброс флага для новой игры

        printWelcomeMessage(); // Приветственное сообщение
        String category;
        try {
            category = chooseCategory(); // Выбор категории
        } catch (IllegalArgumentException e) {
            ui.displayMessage(e.getMessage());
            return;
        }

        String difficulty;
        try {
            difficulty = chooseDifficulty(); // Выбор сложности
        } catch (IllegalArgumentException e) {
            ui.displayMessage(e.getMessage());
            return;
        }

        errorMax = getAttempt(difficulty); // Получение допустимого количества ошибок
        setScaffoldStates(difficulty); // Настройка состояний виселицы
        Word secretWord = chooseRandomWord(category); // Выбор случайного слова

        if (secretWord == null) {
            return; // Прекращаем игру, если слово не найдено
        }

        scaffold = Scaffold.valueOf(scaffoldStates[Math.min(errorCount, scaffoldStates.length - 1)]);
        secretWordView = "_".repeat(secretWord.word().length()); // Отображение скрытого слова
        boolean isNotWin = true;

        // Основной цикл игры
        while (isNotWin) {
            System.out.println(scaffold);
            System.out.println("Загаданное слово:  " + secretWordView);
            System.out.println("Оставшиеся попытки: " + (errorMax - errorCount));
            System.out.println("Ошибки  (" + errorCount + "): " + errorChar);
            System.out.println("Введите букву или ! для показа подсказки:");

            String input = ui.getInput("").toLowerCase();

            if (input.isEmpty()) {
                ui.displayMessage("Ввод не может быть пустым.");
                continue; // Пропускаем итерацию, ждем следующего ввода
            }

            // Проверка на запрос подсказки
            if ("!".equals(input)) {
                if (!hintShown) {
                    ui.displayMessage("Подсказка: " + secretWord.hint());
                    hintShown = true;
                } else {
                    ui.displayMessage("Подсказка уже была показана.");
                }
                continue; // Пропускаем итерацию, ждем следующего ввода
            }

            // Проверка на ввод буквы
            if (input.length() == 1) {
                Character inputCharacter = input.charAt(0);

                if (!Character.isLetter(inputCharacter)) {
                    ui.displayMessage("Пожалуйста, введите только букву.");
                    continue;
                }

                if (secretWord.word().contains(String.valueOf(inputCharacter))) {
                    char[] tempView = secretWordView.toCharArray();

                    for (int i = 0; i < secretWord.word().length(); i++) {
                        if (secretWord.word().charAt(i) == inputCharacter) {
                            tempView[i] = inputCharacter;
                        }
                    }

                    secretWordView = new String(tempView);
                    if (!secretWordView.contains("_")) {
                        isNotWin = false;
                    }
                } else {
                    if (errorChar.contains(inputCharacter)) {
                        ui.displayMessage("Вы уже вводили эту букву. Неверно.");
                    } else {
                        errorChar.add(inputCharacter);
                        errorCount++;
                    }

                    if (errorCount < scaffoldStates.length) {
                        scaffold = Scaffold.valueOf(scaffoldStates[Math.min(errorCount, scaffoldStates.length - 1)]);
                    }

                    if (errorCount >= errorMax) {
                        isNotWin = false;
                    }
                }
            } else {
                ui.displayMessage("Пожалуйста, введите только одну букву.");
            }
        }

        ui.displayMessage("\nЗагаданное слово: " + secretWord.word());
        // Вывод результата игры
        if (errorCount < errorMax) {
            ui.displayMessage("Поздравляем! Вы выиграли!\n");
        } else {
            ui.displayMessage(Scaffold.valueOf(scaffoldStates[scaffoldStates.length - 1]).toString());
            ui.displayMessage("Вы проиграли, допустив много ошибок!\n");
        }
    }
}
