package backend.academy.hangman;

public class Scaffold {
    private static final int ACCESS_ERROR = 8;
    private final String[] scaffoldState = new String[ACCESS_ERROR];

    // CHECKSTYLE:OFF
    public Scaffold() {
        int i = 0;
        scaffoldState[i] = "  ________" + '\n'
            + "  |/      " + '\n'
            + "  |       " + '\n'
            + "  |        " + '\n'
            + "  |         " + '\n'
            + "  |          " + '\n'
            + "__|__      " + '\n';

        scaffoldState[++i] = scaffoldState[i - 1].replace("  |/      " + '\n', "  |      |" + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |       " + '\n', "  |      O" + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |        " + '\n', "  |      | " + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |      | " + '\n', "  |     /| " + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |     /| " + '\n', "  |     /|\\ " + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |         " + '\n', "  |     / " + '\n');
        scaffoldState[++i] = scaffoldState[i - 1].replace("  |     / " + '\n', "  |     / \\ " + '\n');
    }

    // CHECKSTYLE:ON
    public String getScaffold(int errorCount) {
        return scaffoldState[errorCount];
    }
}
