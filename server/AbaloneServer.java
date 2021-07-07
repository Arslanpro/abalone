package abalone.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import abalone.element.Board;
import abalone.element.Field;
import abalone.element.Game;
import abalone.player.HumanPlayer;
import abalone.protocol.ServerProtocol;
import abalone.exceptions.ExitProgram;
import abalone.protocol.enums.Color;
import abalone.protocol.enums.Command;
import abalone.protocol.enums.Direction;

/**
 * Server for Networked Abalone Application
 */
public class AbaloneServer implements Runnable, ServerProtocol {

    // TODO use public
    public Game game;

    /**
     * The ServerSocket of this AbaloneServer
     */
    private ServerSocket ssock;

    /**
     * List of AbaloneClientHandlers, one for each connected client
     */
    private List<AbaloneClientHandler> clients;

    /**
     * The view of this AbaloneServer
     */
    private AbaloneServerTUI serverTUI;

    /**
     * Joined users
     */
    private Vector<String> allUsers;

    /**
     * Joined waiting users
     */
    private Vector<String> waitingUsers;

    /**
     * Game player number
     */
    private int playerNumber;

    /**
     * Current turn to control among threads
     */
    private int currentTurn;

    /**
     * Game set
     */
    private Hashtable<String, Game> games;

    /**
     * name - turn
     */
    private Hashtable<String, Integer> names;

    /**
     * Constructs a new AbaloneServer.
     */
    public AbaloneServer() {
        clients = new ArrayList<>();
        serverTUI = new AbaloneServerTUI();
        allUsers = new Vector<>();
        waitingUsers = new Vector<>();
        games = new Hashtable<>();
        names = new Hashtable<>();
        game = new Game();
        playerNumber = 0;
        currentTurn = -1;
//        resetGameEnv();
        // TODO any other settings?
    }

    /**
     * Opens a new socket by calling {@link #setup()} and starts a new
     * AbaloneClientHandler for every connecting client.
     * <p>
     * If {@link #setup()} throws a ExitProgram exception, stop the program.
     * In case of any other errors, ask the user whether the setup should be
     * ran again to open a new socket.
     */
    public void run() {
        while (true) {
            try {
                // Setup port number and create ServerSocket
                setup();
                while (true) {
                    Socket sock = ssock.accept();
                    AbaloneClientHandler handler = new AbaloneClientHandler(sock, this, serverTUI);
                    new Thread(handler).start();
                    clients.add(handler);
                }
            } catch (ExitProgram e) {
                // If setup() throws an ExitProgram exception, stop the program.
                break;
            } catch (IOException e) {
                System.out.println("A server IO error occurred: " + e.getMessage());
                if (!serverTUI.getBoolean("Do you want to open a new socket?")) {
                    break;
                }
            }
        }
        serverTUI.showMessage("Exiting...");
    }

    /**
     * Opens a new ServerSocket at localhost on a user-defined port.
     * <p>
     * The user is asked to input a port, after which a socket is attempted
     * to be opened. If the attempt succeeds, the method ends, If the
     * attempt fails, the user decides to try again, after which an
     * ExitProgram exception is thrown or a new port is entered.
     *
     * @throws ExitProgram if a connection can not be created on the given
     *                     port and the user decides to exit the program.
     * @ensures a serverSocket is opened.
     */
    public void setup() throws ExitProgram {
        // set player number
        playerNumber = serverTUI.getInt("Set player number (2-4):");
        // set port number
        ssock = null;
        while (ssock == null) {
            int port = serverTUI.getInt("Please enter the server port:");
            // Try to open a new ServerSocket
            try {
                serverTUI.showMessage("Attempting to open a socket on port " + port + "...");
                ssock = new ServerSocket(port);
                serverTUI.showMessage("Listening on port " + port);
            } catch (IOException e) {
                serverTUI.showMessage("ERROR: could not create a socket on port " + port + ".");
                if (!serverTUI.getBoolean("Do you want to try again?")) {
                    throw new ExitProgram("User indicated to exit the program.");
                }
            }
        }
    }

    /**
     * Removes a clientHandler from the client list.
     *
     * @requires client != null
     */
    public void removeClient(AbaloneClientHandler client) {
        this.clients.remove(client);
    }

    // ------------------ Server Methods --------------------------

    @Override
    public synchronized String handleJoin(InetAddress clientIP, String playerName) {
        StringBuilder command = new StringBuilder(String.valueOf(Command.JOIN) + Command.DELIMITER);
        if (allUsers.contains(playerName)) {
            command.append(String.valueOf(false)).append(Command.DELIMITER).append("Name already in use");
        } else {
            command.append(String.valueOf(true));
            allUsers.add(playerName);
            for (String user : waitingUsers) {
                command.append(Command.DELIMITER).append(user);
            }
            waitingUsers.add(playerName);
        }

        // TODO assume start game here, and is a human player (computer player to be asked and set later in client controller)
        HumanPlayer player = new HumanPlayer(playerName);
        // to avoid same name in other game (bug, will update and overwrite V - game)
        // update: one game supported currently
        games.put(playerName, game);
        names.put(playerName, waitingUsers.size() - 1);
        game.join(player);
        if (waitingUsers.size() == playerNumber) {
            serverTUI.showMessage("game starts...");
            game.init();
//            resetGameEnv();
            // start game
            currentTurn = 0;
        }
        return command.toString();
    }

    @Override
    public void sendPlayerJoined(InetAddress clientIP) {
//        String command = String.valueOf(Command.PLAYER_JOINED) + Command.DELIMITER + userIP.get(clientIP);
//        return command;
    }

    @Override
    public void handleReady(InetAddress clientIP) {
    }

    @Override
    public String sendPlayerLeft(InetAddress clientIP) {
        return "";
    }

    @Override
    public void handleDisconnect(InetAddress clientIP) {
    }

    @Override
    public String sendColorUpdate(InetAddress clientIP, String playerName) {
        String command = String.valueOf(Command.UPDATE_COLOR) + Command.DELIMITER;
        command += game.getPlayerByName(playerName).getMarble().getColor().toString();
        return command;
    }

    @Override
    public String sendMove(InetAddress clientIP, String playerName) {
        String command = String.valueOf(Command.MAKE_MOVE);
        return command;
    }

    @Override
    public void handleMove(String tail, String head, String direction) {
        // using Player class
        Color turn;

        Field tailField =
                game.board.getFeild(tail.toLowerCase().charAt(0) - 'a' + 1, Character.getNumericValue(tail.charAt(1)));
        Field headField =
                game.board.getFeild(head.toLowerCase().charAt(0) - 'a' + 1, Character.getNumericValue(head.charAt(1)));

        switch (currentTurn) {
            case 0:
                turn = Color.BLACK;
                break;
            case 1:
                turn = Color.WHITE;
                break;
            case 2:
                turn = Color.RED;
                break;
            case 3:
                turn = Color.BLUE;
                break;
            default:
                turn = Color.NONE;
        }
        if (game.board.isValidMove(tailField, headField, Direction.fromString(direction), turn)) {
            game.board.makeMove(tailField, headField, Direction.fromString(direction), turn);
        }
    }

    @Override
    public String sendEndTurn(InetAddress clientIP) {
        return "";
    }

    @Override
    public String syncBoard(String playerName, boolean isNext) throws IOException {
        String command = String.valueOf(Command.SYNC_BOARD) + Command.DELIMITER;
        // in current turn
        while (true) {
            int thisTurn = currentTurn;
//            if (isNext) {
//                thisTurn -= 1;
//                if (thisTurn == -1) {
//                    thisTurn = allUsers.size();
//                }
//            }
            if (thisTurn == allUsers.indexOf(playerName)) {
                for (int i = 0; i < Board.DIMENSION; i++) {
                    for (int j = 0; j < Board.DIMENSION; j++) {
                        if (!game.board.isOutOfBoard(game.board.getFeild(i, j))) {
                            command += String.valueOf((char) ((char) i + 'A' - 1)) + j + ":";
                            command += game.board.getFeild(i, j).getMarbleColor().toString() + Command.DELIMITER;
                        }
                    }
                }
                break;
            }
        }
        return command;
    }

    @Override
    public String sendGameOver() throws IOException {
        if (game.isGameOver()) {
            System.out.println("Game over!");
        } else {
            // go to next turn (in next thread)
            currentTurn += 1;
            if (currentTurn == allUsers.size()) {
                currentTurn = 0;
            }
        }
        return "";
    }

    @Override
    public String handleChat(InetAddress senderIP, String message) {
        return "";
    }

    @Override
    public String handleLeaderboard(String timeframe, String order, String type, String query, String queryParameter) {
        return "";
    }

    @Override
    public String handleLeaderboard(String timeframe, String order, String type, String query, String queryParameter, int maxResults) {
        return "";
    }

    /**
     * Start a new AbaloneServer
     */
    public static void main(String[] args) {
        AbaloneServer server = new AbaloneServer();
        System.out.println("Welcome to the Abalone Server! Starting...");
        (new Thread(server)).start();
    }

    private void resetGameEnv() {
        // reset game environment
        clients.clear();
        allUsers.clear();
        waitingUsers.clear();
        // TODO set a new game and add into the Game pool
        game = new Game();
        // TODO put into a group
        // TODO shift game instance to next group
    }
}
