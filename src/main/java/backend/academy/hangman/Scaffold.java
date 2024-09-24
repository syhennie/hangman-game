package backend.academy.hangman;

public enum Scaffold {
    ZERO {
        @Override
        public String toString() {
            return """
                     _______
                     |/
                     |
                     |
                     |
                     |
                    _|_""";
        }
    },
    ONE {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |
                     |
                     |
                     |
                    _|_""";
        }
    },
    TWO {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |
                     |
                     |
                    _|_""";
        }
    },
    THREE {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |     |
                     |
                     |
                    _|_""";
        }
    },
    FOUR {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |    /|
                     |
                     |
                    _|_""";
        }
    },
    FIVE {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |
                     |
                    _|_""";
        }
    },
    SIX {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |    /
                     |
                    _|_""";
        }
    },
    SEVEN {
        @Override
        public String toString() {
            return """
                     _______
                     |/    |
                     |     O
                     |    /|\\
                     |    / \\
                     |
                    _|_""";
        }
    }
}
