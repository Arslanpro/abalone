package abalone.protocol;

import abalone.player.Player;
import abalone.protocol.enums.Color;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Defines all the methods a server should support.
 *
 * @author Tom Pothof
 */
public interface ServerProtocol {

    /**
     * Handle a client's join request.
     *
     * @param clientIP   The IP address of the connecting client
     * @param playerName The player's chosen name
     * @return The server's response to the client's request
     */
    String handleJoin(InetAddress clientIP, String playerName);

    /**
     * Handle a client's ready-up requests.
     * <p>
     * Players can disconnect freely before a game starts
     * Once all connected clients have sent a ready command, the game starts.
     * Disconnects will now cause a {@link #sendGameOver(InetAddress)} to be sent out
     *
     * @param clientIP The IP address of the client
     */
    void handleReady(InetAddress clientIP);

    /**
     * Tell all connected clients (except the one that just joined) that another client connected.
     *
     * @param clientIP The IP of the newly connected client
     */
    void sendPlayerJoined(InetAddress clientIP);

    /**
     * Tell all connected clients (except the one that just left) that a client left.
     *
     * @param clientIP The IP of the disconnected client
     * @return A formatted string telling client which player left
     */
    String sendPlayerLeft(InetAddress clientIP);

    /**
     * Handle a client's request to disconnect.
     * If this gets called before the game has started, nothing special happens.
     * If this gets called after the game has started,
     * a {@link #sendGameOver(InetAddress)} should take place, with reason {@code disconnect}
     *
     * @param clientIP The IP address of the disconnecting client
     */
    void handleDisconnect(InetAddress clientIP);

    /**
     * Tell each connected client which color they'll be playing as.
     *
     * @param clientIP The client to send this message to
     * @param playerName    The playerName the client is
     * @return A formatted string telling the client what color they'll be playing as
     */
    String sendColorUpdate(InetAddress clientIP, String playerName);

    /**
     * Tell a client to make a move.
     *
     * @param clientIP The IP address of the client
     */
    String sendMove(InetAddress clientIP, String playerName);

    /**
     * Handle a client's response to {@link #sendMove(InetAddress)}.
     *
     * @param tail      The last marble in the move's column
     * @param head      The front marble in the move's column
     * @param direction The direction in which the column should move
     */
    void handleMove(String tail, String head, String direction);

    /**
     * Inform a client their turn is over.
     *
     * @param clientIP The client whose turn is now over
     */
    String sendEndTurn(InetAddress clientIP);

    /**
     * Synchronize the board with all players.
     *
     * @return A formatted string containing the new state of the board
     * @throws IOException if an IO error occurs
     */
    String syncBoard(String playerName, boolean isNext) throws IOException;

    /**
     * Inform a client the game is over, telling them if they won, lost, played a draw, or that someone disconnected.
     * Has to be called for each of the clients individually, due to potentially different responses
     *
     * @return A formatted string informing the client of the game-over
     * @throws IOException if an IO error occurs
     */
    String sendGameOver() throws IOException;

    /**
     * Handle a chat message from a player.
     * If you don't want to implement a chat, return {@code null} or {@code ""}
     *
     * @param senderIP The IP address of the message's sender
     * @param message  The message the client wants to send
     * @return A formatted string, which should be forwarded to the other client(s)
     */
    String handleChat(InetAddress senderIP, String message);

    /**
     * Handle a client's leaderboard request.
     *
     * @param timeframe      The timeframe of scores to send
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
     * @return A formatted string containing the requested leaderboard entries
     */
    String handleLeaderboard(String timeframe, String order, String type, String query, String queryParameter);

    /**
     * Handle a client's leaderboard request.
     *
     * @param timeframe      The timeframe of scores to send
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
     * @param maxResults     The maximum amount of results to send back
     * @return A formatted string containing the requested leaderboard entries
     */
    String handleLeaderboard(
            String timeframe, String order, String type, String query, String queryParameter, int maxResults
    );
}
