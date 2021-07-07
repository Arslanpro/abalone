package abalone.protocol;

import abalone.exceptions.ServerUnavailableException;
import abalone.protocol.enums.Color;
import abalone.protocol.enums.Direction;
import abalone.protocol.enums.GameOverState;

import java.io.IOException;

/**
 * Defines all the methods a client should support.
 *
 * @author Tom Pothof
 */
public interface ClientProtocol {

    /**
     * Sends a join request to the server.
     *
     * @param name The name of the player
     * @throws IOException if an IO error occurs
     * @requires name doesn't contain spaces
     */
    void sendJoin(String name) throws ServerUnavailableException;

    /**
     * Indicates to the server you're ready to start the game.
     *
     * @throws IOException if an IO error occurs
     */
    void sendReady() throws IOException;

    /**
     * Handle the server's response to a join request.
     */
    void handleJoin() throws ServerUnavailableException;

    /**
     * Handle the server's message stating a player has joined.
     *
     * @param playerName The name of the player who joined
     */
    void handlePlayerJoined(String playerName);

    /**
     * Handle the server's message stating a player has left.
     *
     * @param playerName The name of the player who left
     */
    void handlePlayerLeft(String playerName);

    /**
     * Informs the server this client is disconnecting.
     *
     * @throws IOException if an IO error occurs
     */
    void sendDisconnect() throws IOException;

    /**
     * Handle a server's message assigning this client a specific color.
     *
     * @param color The color the server tells you to play as
     */
    void handleColorUpdate(Color color);

    /**
     * Handle a server's request to make a move.
     */
    void handleMove();

    /**
     * Send the player's move to the server.
     *
     * @param tail      The index of the back marble in the column
     * @param head      The index of the front marble in the column
     * @param direction The direction to move the column in
     * @throws IOException if an IO error occurs
     */
    void sendMove(String tail, String head, Direction direction) throws IOException;

    /**
     * Handle the server's {@code end_turn} command.
     */
    void handleEndTurn();

    /**
     * Update the board based on a server's request.
     *
     * @param fields The "index:color" pairs sent by the server's {@code sync_board} command
     */
    void updateBoard(String[] fieldsFromServer);

    /**
     * Handle a server's game over message.
     *
     * @param gameOverState The reason the game ended. one of:
     *                      win, loss, draw, or disconnect
     */
    void handleGameOver(GameOverState gameOverState);

    /**
     * Send a chat message to the server.
     * If you don't want to implement chat, just leave this empty
     *
     * @param message The chat message to send
     */
    void sendChat(String message) throws IOException;

    /**
     * Handle a chat message from the server.
     * If you don't want to implement chat, just leave this empty
     *
     * @param playerName The name of the player who sent the message
     * @param message    The player's chat message
     */
    void handleChat(String playerName, String message);

    /**
     * Request the server's leaderboard.
     * If you don't want to implement a leaderboard, just leave this empty
     *
     * @param timeframe      The timeframe of scores to get
     *                       one of: day, week, month, year, all-time
     * @param order          The order to sort the scores in
     *                       one of: highest, lowest
     * @param type           The type of scores to get
     *                       one of: score, ratio
     * @param query          The subset of scores to query
     *                       one of: all, player_averages, team_averages, player, team
     * @param queryParameter Additional parameter for certain queries.
     *                       Can be {@code null}, or {@code ""} if no arguments are expected
     *                       See documentation on gitlab for more details
     * @param maxResults     The maximum amount of results the server should send back
     *                       A value less than 1 should be interpreted as "all values",
     *                       meaning the message sent to the server shouldn't have a {@code max_amount} parameter
     */
    void sendLeaderboard(
            String timeframe, String order, String type, String query, String queryParameter, int maxResults
    ) throws IOException;

    /**
     * Handle the server's response to a leaderboard command.
     * If you don't want to implement a leaderboard, just leave this empty
     *
     * @param scores An array with the requested scores or ratios,
     *               formatted like this: "[date-time] player/team - score/ratio"
     */
    void handleLeaderboard(String[] scores);
}
