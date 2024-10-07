package backend.academy.hangman;

public record Word(String word, String hint, int difficulty) {
    public Word {
        if (word == null || hint == null) {
            throw new IllegalArgumentException("Слово и подсказка не могут быть null.");
        }
    }

    @Override
    public String toString() {
        return "Word{"
            + "word='" + word + '\''
            + ", hint='" + hint + '\''
            + ", difficulty=" + difficulty
            + '}';
    }
}
