package backend.academy.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScaffoldTest {
    private Scaffold scaffold;

    @BeforeEach
    void setUp() {
        scaffold = new Scaffold();  // Инициализация Scaffold перед каждым тестом
    }

    @Test
    void testInitialScaffoldState() {
        String expected =
            "  ________\n" +
                "  |/      \n" +
                "  |       \n" +
                "  |        \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(0));
    }

    @Test
    void testFirstErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |       \n" +
                "  |        \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(1));
    }

    @Test
    void testSecondErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |        \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(2));
    }

    @Test
    void testThirdErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |      | \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(3));
    }

    @Test
    void testFourthErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |     /| \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(4));
    }

    @Test
    void testFifthErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |     /|\\ \n" +
                "  |         \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(5));
    }

    @Test
    void testSixthErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |     /|\\ \n" +
                "  |     / \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(6));
    }

    @Test
    void testSeventhErrorScaffoldState() {
        String expected =
            "  ________\n" +
                "  |      |\n" +
                "  |      O\n" +
                "  |     /|\\ \n" +
                "  |     / \\ \n" +
                "  |          \n" +
                "__|__      \n";
        assertEquals(expected, scaffold.getScaffold(7));
    }
}
