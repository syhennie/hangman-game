package backend.academy.hangman;

import java.io.PrintStream;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final PrintStream output;

    public UserInterface(PrintStream output) {
        this.output = output;
        this.scanner = new Scanner(System.in);
    }

    public String getInput(String prompt) {
        output.println(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        output.println(message);
    }
}
