package backend.academy.hangman;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public int errorCount;
    private int errorMax;
    public final List<Character> errorChar;
    public String secretWordView;
    private Scaffold scaffold;
    private final UserInterface ui;
    private final WordRepository wordRepository;
    private String[] scaffoldStates;
    private boolean hintShown;
    private static final int EASY_ATTEMPTS = 6;
    private static final int MEDIUM_ATTEMPTS = 4;
    private static final int HARD_ATTEMPTS = 3;
    private static final String ZERO = "ZERO";
    private static final String ONE = "ONE";
    private static final String TWO = "TWO";
    private static final String THREE = "THREE";
    private static final String FOUR = "FOUR";
    private static final String FIVE = "FIVE";
    private static final String SIX = "SIX";
    private static final String SEVEN = "SEVEN";
    private static final List<String> ALL_DIFFICULTY = List.of("легкий", "средний", "сложный");

    public Game(UserInterface ui) {
        this.ui = ui;
        this.wordRepository = new WordRepository();
        this.hintShown = false;
        this.errorChar = new ArrayList<>();
        this.errorCount = 0;
    }

    public void printWelcomeMessage() {
        ui.displayMessage("Добро пожаловать в игру Виселица!");
    }

    public String chooseCategory() {
        String category = ui.getInput("Выберите категорию: технологии, страны, животные");
        List<String> allCategory = List.of("технологии", "страны", "животные");
        if (category == null || category.isEmpty()) {
            ui.displayMessage("Выбрана категория по умолчанию.");
            category = allCategory.getFirst(); // Категория по умолчанию
        }
        category = category.toLowerCase();
        if (!allCategory.contains(category)) {
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
            difficulty = ALL_DIFFICULTY.getFirst(); // Сложность по умолчанию
        }
        difficulty = difficulty.toLowerCase();
        if (!ALL_DIFFICULTY.contains(difficulty)) {
            throw new IllegalArgumentException("Недопустимый уровень сложности: " + difficulty);
        }
        return difficulty;
    }

    public int getAttemptsForDifficulty(String difficulty) {
        int index = ALL_DIFFICULTY.indexOf(difficulty);
        return switch (index) {
            case 1 -> MEDIUM_ATTEMPTS;
            case 2 -> HARD_ATTEMPTS;
            default -> EASY_ATTEMPTS;
        };
    }

    void setScaffoldStates(String difficulty) {
        if (difficulty.equals(ALL_DIFFICULTY.getFirst())) {
            scaffoldStates = new String[] {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN};
        } else if (difficulty.equals(ALL_DIFFICULTY.get(1))) {
            scaffoldStates = new String[] {TWO, FOUR, FIVE, SIX, SEVEN};
        } else {
            scaffoldStates = new String[] {TWO, FIVE, SEVEN};
        }
    }

    private void updateScaffold() {
        scaffold = Scaffold.valueOf(scaffoldStates[Math.min(errorCount, scaffoldStates.length - 1)]);
    }

    public void start() {
        printWelcomeMessage();
        String category;
        try {
            category = chooseCategory();
        } catch (IllegalArgumentException e) {
            ui.displayMessage(e.getMessage());
            return;
        }
        String difficulty = chooseDifficulty();
        errorMax = getAttemptsForDifficulty(difficulty);
        setScaffoldStates(difficulty);
        Word secretWord = chooseRandomWord(category);
        secretWordView = "_".repeat(secretWord.word().length());
        updateScaffold();

        boolean isGameActive = true;
        while (isGameActive) {
            ui.displayMessage(scaffold.toString());
            ui.displayMessage("Загаданное слово:  " + secretWordView);
            ui.displayMessage("Оставшиеся попытки: " + (errorMax - errorCount));
            ui.displayMessage("Ошибки  (" + errorCount + "): " + errorChar);
            ui.displayMessage("Введите букву или ! для показа подсказки:");
            String input = ui.getInput("").toLowerCase();
            if (input.isEmpty()) {
                ui.displayMessage("Ввод не может быть пустым.");
                continue;
            }
            if (processInput(input, secretWord)) {
                if (!secretWordView.contains("_")) {
                    isGameActive = false;
                }
                if (errorCount >= errorMax) {
                    isGameActive = false;
                }
            }
        }

        displayEndMessage(secretWord);
    }

    protected boolean processInput(String input, Word secretWord) {
        if ("!".equals(input)) {
            if (!hintShown) {
                ui.displayMessage("Подсказка: " + secretWord.hint());
                hintShown = true;
            } else {
                ui.displayMessage("Подсказка уже была показана.");
            }
            return false;
        }

        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            return processLetter(input.charAt(0), secretWord);
        } else {
            ui.displayMessage("Пожалуйста, введите одну букву.");
            return false;
        }
    }

    protected boolean processLetter(char letter, Word secretWord) {
        if (secretWord.word().contains(String.valueOf(letter))) {
            char[] tempView = secretWordView.toCharArray();
            for (int i = 0; i < secretWord.word().length(); i++) {
                if (secretWord.word().charAt(i) == letter) {
                    tempView[i] = letter;
                }
            }
            secretWordView = new String(tempView);
        } else {
            if (!errorChar.contains(letter)) {
                errorChar.add(letter);
                errorCount++;
                updateScaffold();
            } else {
                ui.displayMessage("Вы уже вводили эту букву. Неверно.");
            }
        }
        return true;
    }

    private void displayEndMessage(Word secretWord) {
        ui.displayMessage("\nЗагаданное слово: " + secretWord.word());
        if (errorCount < errorMax) {
            ui.displayMessage("Поздравляем! Вы выиграли!\n");
        } else {
            ui.displayMessage(Scaffold.valueOf(scaffoldStates[scaffoldStates.length - 1]).toString());
            ui.displayMessage("Вы проиграли, допустив много ошибок!\n");
        }
    }
}
