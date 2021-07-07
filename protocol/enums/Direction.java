package abalone.protocol.enums;

/**
 * Defines all directions marbles can move in.
 *
 * @author Tom Pothof
 */
public enum Direction {
    UP_LEFT, UP_RIGHT,
    DOWN_LEFT, DOWN_RIGHT,
    STRAIGHT_LEFT, STRAIGHT_RIGHT;

    /**
     * Get a direction from a string representation.
     * Parsing is as lenient as it could be,
     * by using {@code String.contains(String)} and case-insensitive comparison
     *
     * @param direction The string representation of the direction to get
     * @return The direction represented by {@code direction}
     * @throws IllegalArgumentException if no direction can be identified in {@code direction}
     * @ensures fromString(direction.toString ()) doesn't throw an exception
     */
    public static Direction fromString(String direction) {
        var lower = direction.toLowerCase();
        if (lower.contains("up")) {
            if (lower.contains("left")) {
                return UP_LEFT;
            }
            if (lower.contains("right")) {
                return UP_RIGHT;
            }
        } else if (lower.contains("down")) {
            if (lower.contains("left")) {
                return DOWN_LEFT;
            }
            if (lower.contains("right")) {
                return DOWN_RIGHT;
            }
        } else if (lower.contains("straight")) {
            if (lower.contains("left")) {
                return STRAIGHT_LEFT;
            }
            if (lower.contains("right")) {
                return STRAIGHT_RIGHT;
            }
        }

        throw new IllegalArgumentException("No direction could be identified in '" + direction + '\'');
    }

    /**
     * Get the direction opposite to {@code this}.
     *
     * @return The direction opposite to {@code this}
     */
    public Direction opposite() {
        switch (this) {
            case UP_LEFT:
                return DOWN_RIGHT;
            case UP_RIGHT:
                return DOWN_LEFT;
            case DOWN_LEFT:
                return UP_RIGHT;
            case DOWN_RIGHT:
                return UP_LEFT;
            case STRAIGHT_LEFT:
                return STRAIGHT_RIGHT;
            case STRAIGHT_RIGHT:
                return STRAIGHT_LEFT;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
