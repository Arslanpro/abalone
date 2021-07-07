package abalone.protocol.enums;

/**
 * Defines all the colors recognized on the game board.
 *
 * @author Tom Pothof
 */
public enum Color {
    NONE, BLACK, WHITE, RED, BLUE;

    /**
     * Get a color based on its string representation.
     *
     * @param color The name of the color to get
     * @return The color represented by {@code color}
     * @throws IllegalArgumentException if no color can be recognized in {@code color}
     * @ensures fromString(color.toString ()) doesn't throw an exception
     */
    public static Color fromString(String color) {
        for (Color col : values()) {
            if (col.toString().equalsIgnoreCase(color)) {
                return col;
            }
        }

        throw new IllegalArgumentException("No color recognized with name '" + color + '\'');
    }
}
