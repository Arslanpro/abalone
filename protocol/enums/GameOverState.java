package abalone.protocol.enums;

/**
 * Enum representing the reason a game has ended.
 *
 * @author Tom Pothof
 */
public enum GameOverState {
    WIN, LOSS, DRAW, DISCONNECT;

    /**
     * Get a game over state from its string representation.
     *
     * @param state The name of the state to get
     * @return The game over state represented by {@code state}
     * @throws IllegalArgumentException if no state can be recognized in {@code state}
     * @ensures fromString(state.toString ()) doesn't throw an exception
     */
    public static GameOverState fromString(String state) {
        for (GameOverState gameOverState : values()) {
            if (gameOverState.toString().equalsIgnoreCase(state)) {
                return gameOverState;
            }
        }

        throw new IllegalArgumentException("No state recognized with name '" + state + '\'');
    }
}
