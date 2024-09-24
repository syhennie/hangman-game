package backend.academy.hangman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScaffoldTest {

    @Test
    public void testScaffoldZeroToString() {
        // Arrange
        Scaffold scaffold = Scaffold.ZERO;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/
                     |
                     |
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldOneToString() {
        // Arrange
        Scaffold scaffold = Scaffold.ONE;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |
                     |
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldTwoToString() {
        // Arrange
        Scaffold scaffold = Scaffold.TWO;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldThreeToString() {
        // Arrange
        Scaffold scaffold = Scaffold.THREE;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |     |
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldFourToString() {
        // Arrange
        Scaffold scaffold = Scaffold.FOUR;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |    /|
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldFiveToString() {
        // Arrange
        Scaffold scaffold = Scaffold.FIVE;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldSixToString() {
        // Arrange
        Scaffold scaffold = Scaffold.SIX;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |    /
                     |
                    _|_""", result);
    }

    @Test
    public void testScaffoldSevenToString() {
        // Arrange
        Scaffold scaffold = Scaffold.SEVEN;

        // Act
        String result = scaffold.toString();

        // Assert
        assertEquals("""
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |    / \\
                     |
                    _|_""", result);
    }
}

