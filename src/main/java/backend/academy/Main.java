package backend.academy;

import backend.academy.hangman.Game;
import backend.academy.hangman.UserInterface;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    static UserInterface ui = new UserInterface();

    public static void main(String[] args){
        while (true) {
            System.out.println("Начать новую игру [0] или выйти [1] из приложения?");
            char select = readInput();
            if (select == '0') {
                Game game = new Game(); // Создаем новый экземпляр игры
                game.start(); // Запускаем игру
            } else if (select == '1') {
                ui.displayMessage("Выход из приложения.");
                System.exit(0);
            } else {
                ui.displayMessage("Сделайте выбор ещё раз.");
            }
        }
    }

    static char readInput() {
        String input = ui.getInput("").trim();
        return input.isEmpty() ? ' ' : input.charAt(0); // Возвращаем первый символ строки или пробел, если строка пуста
    }
}
