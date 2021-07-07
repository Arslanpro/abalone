package abalone.protocol.enums;

/**
 * Defines all the commands, and the used delimiter.
 *
 * @author Tom Pothof
 */
@SuppressWarnings("unused")
public enum Command {
    JOIN,
    PLAYER_JOINED,
    READY,
    DISCONNECT,
    UPDATE_COLOR,
    PLAYER_LEFT,
    MAKE_MOVE,
    END_TURN,
    SYNC_BOARD,
    GAME_OVER,
    CHAT,
    LEADERBOARD;

    public static final char DELIMITER = ';';

    /**
     * Get a command from its string representation.
     *
     * @param command The name of the command to get
     * @return The command represented by {@code command}
     * @throws IllegalArgumentException if no command can be recognized in {@code command}
     * @ensures fromString(command.toString ()) doesn't throw an exception
     */
    public static Command fromString(String command) {
        for (Command cmd : values()) {
            if (cmd.toString().equalsIgnoreCase(command)) {
                return cmd;
            }
        }

        throw new IllegalArgumentException("No command recognized with name '" + command + '\'');
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
