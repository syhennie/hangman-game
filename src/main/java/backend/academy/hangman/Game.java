package backend.academy.hangman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {
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
    protected int errorCount;
    protected int errorMax;
    protected final List<Character> errorChar;
    protected String secretWordView;
    protected Scaffold scaffold;
    private final UserInterface ui;
    protected final WordRepository wordRepository;
    protected String[] scaffoldStates;
    private boolean hintShown;
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

    public Word chooseRandomWord(String category, String difficulty) {
        Word randomWord = wordRepository.getRandomWordByCategoryAndDifficulty(category, difficulty);
        if (randomWord == null) {
            ui.displayMessage("Категория или уровень сложности не найдены. Попробуйте снова.");
            return null;
        }
        return randomWord;
    }

    public String chooseOption(Set<String> availableOptions, String message) {
        String optionsList = String.join(", ", availableOptions);
        String input = ui.getInput(message + " (Enter для случайного выбора): " + optionsList);
        if (input.isBlank()) {
            String randomOption = getRandomOption(availableOptions);
            ui.displayMessage("Выбрано по умолчанию: " + randomOption);
            return randomOption;
        } else {
            while (!availableOptions.contains(input.toLowerCase())) {
                input = ui.getInput("Некорректный выбор. " + message + ": " + optionsList);
            }
        }

        return input.toLowerCase();
    }

    private String getRandomOption(Set<String> options) {
        List<String> optionList = new ArrayList<>(options);
        return optionList.get(new Random().nextInt(optionList.size()));
    }

    public String chooseCategory() {
        Set<String> availableCategories = wordRepository.getAvailableCategories();
        return chooseOption(availableCategories, "Выберите категорию");
    }

    public String chooseDifficulty() {
        return chooseOption(new HashSet<>(ALL_DIFFICULTY), "Выберите уровень сложности");
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

    protected void updateScaffold() {
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
        Word secretWord = chooseRandomWord(category, difficulty);
        secretWordView = "_".repeat(secretWord.word().length());
        updateScaffold();

        while (true) {
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
                    break;
                }
                if (errorCount >= errorMax) {
                    break;
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

        // добавлена проверка на русскую букву
        if (input.length() == 1 && Character.isLetter(input.charAt(0)) && input.matches("[а-яА-Я]")) {
            return processLetter(input.charAt(0), secretWord);
        } else {
            ui.displayMessage("Пожалуйста, введите одну букву русского языка.");
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
                ui.displayMessage("Эта буква уже была использована."); // =)
            }
        }
        return true;
    }

    protected void displayEndMessage(Word secretWord) {
        ui.displayMessage("\nЗагаданное слово: " + secretWord.word());
        if (errorCount < errorMax) {
            ui.displayMessage("Поздравляем! Вы выиграли!\n");
        } else {
            ui.displayMessage(Scaffold.valueOf(scaffoldStates[scaffoldStates.length - 1]).toString());
            ui.displayMessage("Вы проиграли, допустив много ошибок!\n");
        }
    }
}
