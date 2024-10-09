package backend.academy.hangman;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static UserInterface ui = new UserInterface(System.out);

    public static void main(String[] args) {
        while (true) {
            ui.displayMessage("Начать новую игру [0] или выйти [1] из приложения?");
            char select = readInput();
            if (select == '0') {
                Game game = new Game(ui);
                game.start();
            } else if (select == '1') {
                ui.displayMessage("Выход из приложения.");
                break;
            } else {
                ui.displayMessage("Сделайте выбор ещё раз.");
            }
        }
    }

    public static char readInput() {
        String input = ui.getInput("").trim();
        return input.isEmpty() ? ' ' : input.charAt(0);
    }
}
