package backend.academy.hangman;

import java.util.Scanner;

/**
 * Класс UserInterface предоставляет методы для взаимодействия с пользователем.
 * Он позволяет выводить сообщения и получать ввод от пользователя с помощью консоли.
 */
public class UserInterface {
    private final Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
