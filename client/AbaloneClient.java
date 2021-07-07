package abalone.client;

import abalone.element.Game;
import abalone.exceptions.ExitProgram;
import abalone.exceptions.ServerUnavailableException;
import abalone.player.ComputerPlayer;
import abalone.player.HumanPlayer;
import abalone.player.Player;
import abalone.protocol.ClientProtocol;
import abalone.protocol.enums.Color;
import abalone.protocol.enums.Command;
import abalone.protocol.enums.Direction;
import abalone.protocol.enums.GameOverState;
import abalone.server.AbaloneServer;
import javafx.util.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class AbaloneClient implements ClientProtocol {
    private Socket serverSock;
    private BufferedReader in;
    private BufferedWriter out;
    private AbaloneServer server;
    private AbaloneClientTUI clientTUI;
    private String host;
    private int port;
    private String clientName;
    private boolean handshake;
    private Game game;
    private Player player;
    private int playerNumber;
    private Color turn;

    /**
     * Constructs a new AbaloneClient. Initialises the view.
     */
    public AbaloneClient() {
        server = new AbaloneServer();
        clientTUI = new AbaloneClientTUI();
    }

    /**
     * Starts a new AbaloneClient by creating a connection.
     * <p>
     * When errors occur, or when the user terminates a server connection, the
     * user is asked whether a new connection should be made.
     */
    public void start() {
        // set player number
        playerNumber = clientTUI.getInt("set player number (2-4):");
        while (true) {
            try {
                game = new Game();
                game.setPlayerNumber(playerNumber - 1);
                host = this.clientTUI.getString("server IP address:");
                port = this.clientTUI.getInt("server port number:");
                clientName = this.clientTUI.getString("your name:");
                player = clientTUI.getBoolean("are you human (or AI): (true/false)") ?
                        new HumanPlayer(clientName) : new ComputerPlayer(clientName);
                game.join(player);
                // initialize the game
                game.init();
                while (clientName.indexOf(' ') >= 0) {
                    clientName = this.clientTUI.getString("re-input your name without space:");
                }
                this.createConnection();
                this.sendJoin(clientName);
                this.handleJoin();
                if (handshake) {
                    run();
                } else {
                    if (!this.clientTUI.getBoolean("Do you want to open a new connection?")) {
                        shutdown();
                        break;
                    }
                }
            } catch (ServerUnavailableException e) {
                clientTUI.showMessage("A ServerUnavailableException error occurred: " + e.getMessage());
                if (!clientTUI.getBoolean("Do you want to open a new connection?")) {
                    shutdown();
                    break;
                }
            }
        }
        clientTUI.showMessage("Exiting...");
    }

    /**
     * Creates a connection to the server.
     *
     * @throws ExitProgram if a connection is not established and the user indicates to
     *                     want to exit the program.
     * @ensures serverSock contains a valid socket connection to a server
     */
    public void createConnection() throws ServerUnavailableException {
        clearConnection();
        while (serverSock == null) {
            try {
                // try to open a Socket to the server
                InetAddress addr = InetAddress.getByName(host);
                System.out.println("attempting to connect to " + addr + ":" + port + "...");
                serverSock = new Socket(addr, port);
                in = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(serverSock.getOutputStream()));
            } catch (IOException e) {
                throw new ServerUnavailableException("Unable to connect to the server");
            }
        }
    }

    /**
     * Try to close In- and OutputStreams and reset the serverSocket and In- and OutputStreams to null.
     */
    public void clearConnection() {
        serverSock = null;
        in = null;
        out = null;
    }

    /**
     * Sends a message to the connected server, followed by a new line. The
     * stream is then flushed.
     *
     * @param msg the message to write to the OutputStream.
     * @throws ServerUnavailableException if IO errors occur.
     */
    public synchronized void sendMessage(String msg) throws ServerUnavailableException {
        if (out != null) {
            try {
                out.write(msg);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new ServerUnavailableException("Could not write \"" + msg + "\" to server.");
            }
        } else {
            throw new ServerUnavailableException("Could not write \"" + msg + "\" to server.");
        }
    }

    /**
     * Reads and returns one line from the server.
     *
     * @return the line sent by the server.
     * @throws ServerUnavailableException if IO errors occur.
     */
    public String readLineFromServer() throws ServerUnavailableException {
        if (in != null) {
            try {
                // Read and return answer from Server
                String answer = in.readLine();
                if (answer == null) {
                    throw new ServerUnavailableException("Could not read from server.");
                }
                return answer;
            } catch (IOException e) {
                throw new ServerUnavailableException("Could not read from server.");
            }
        } else {
            throw new ServerUnavailableException("Could not read from server.");
        }
    }

    @Override
    public void sendJoin(String name) throws ServerUnavailableException {
        String command = String.valueOf(Command.JOIN) + Command.DELIMITER + name;
        sendMessage(command);
    }

    @Override
    public void handleJoin() throws ServerUnavailableException {
        String[] command = readLineFromServer().split(String.valueOf(Command.DELIMITER));
        if (command[0].equalsIgnoreCase(String.valueOf(Command.JOIN))) {
            if (command[1].equalsIgnoreCase(String.valueOf(true))) {
                this.clientTUI.showMessage("connected to server");
                if (command.length > 2) {
                    this.clientTUI.showMessage("current users: ");
                    for (int otherPlayer = 2; otherPlayer < command.length; otherPlayer++) {
                        this.clientTUI.showMessage(command[otherPlayer]);
                    }
                }
                handshake = true;
            } else {
                this.clientTUI.showMessage("failed to connect to server: " + command[2]);
                handshake = false;
            }
        }
    }

    @Override
    public void sendReady() throws IOException {
    }

    public void handlePlayerJoined(String playerName) {
        clientTUI.showMessage(playerName + " joined in game");
    }

    @Override
    public void handlePlayerLeft(String playerName) {
    }

    @Override
    public void sendDisconnect() throws IOException {
    }

    @Override
    public void handleColorUpdate(Color color) {
        turn = color;
        clientTUI.showMessage("using color " + color.toString());
    }

    @Override
    public void handleMove() {
        String begin;
        String end;
        Direction direction;
        // show current board
        clientTUI.showMessage(game.update());
        // ask begin; ask end; ask direction
        Pair<String, Pair<String, String>> movementDecision = player.getMovement(clientTUI, game.board, turn);
        begin = movementDecision.getKey();
        end = movementDecision.getValue().getKey();
        direction = Direction.fromString(movementDecision.getValue().getValue());
        while (!game.board.isValidMove(begin, end, direction, turn)) {
            movementDecision = player.getMovement(clientTUI, game.board, turn);
            begin = movementDecision.getKey();
            end = movementDecision.getValue().getKey();
            direction = Direction.fromString(movementDecision.getValue().getValue());
        }
        try {
            sendMove(begin, end, direction);
        } catch (IOException e) {
            shutdown();
        }
        clientTUI.showMessage("waiting for my next turn...");
    }

    @Override
    public void sendMove(String tail, String head, Direction direction) throws IOException {
        String sendCommand;
        sendCommand = String.valueOf(Command.MAKE_MOVE) + Command.DELIMITER;
        sendCommand += tail + Command.DELIMITER;
        sendCommand += head + Command.DELIMITER;
        sendCommand += direction.toString();
        try {
            sendMessage(sendCommand);
        } catch (ServerUnavailableException e) {
            clientTUI.showMessage("A ServerUnavailableException error occurred: " + e.getMessage());
            if (!clientTUI.getBoolean("Do you want to open a new connection?")) {
                shutdown();
            }
        }
    }

    @Override
    public void handleEndTurn() {
    }

    @Override
    public void updateBoard(String[] fieldsFromServer) {
        for (String fieldFromServer : fieldsFromServer) {
            if (!fieldFromServer.equalsIgnoreCase(Command.SYNC_BOARD.toString())) {
                String[] singleField = fieldFromServer.split(String.valueOf(':'));
                int x = singleField[0].toLowerCase().charAt(0) - 'a' + 1;
                int y = Character.getNumericValue(singleField[0].charAt(1));
                game.board.getFeild(x, y).getMarble().setColor(Color.fromString(singleField[1]));
            }
        }
    }

    @Override
    public void handleGameOver(GameOverState gameOverState) {
    }

    @Override
    public void sendChat(String message) throws IOException {
    }

    @Override
    public void handleChat(String playerName, String message) {
    }

    @Override
    public void sendLeaderboard(
            String timeframe, String order, String type, String query, String queryParameter, int maxResults
    ) throws IOException {
    }

    @Override
    public void handleLeaderboard(String[] scores) {
    }


    /**
     * This method starts a new AbaloneClient.
     *
     * @param args
     */
    public static void main(String[] args) {
        (new AbaloneClient()).start();
    }

    /**
     * Continuously listens to client input and forwards the input to the
     * {@link #handleCommand(String)} method.
     */
    private void run() {
        String command;
        try {
            while (true) {
                command = readLineFromServer();
                System.out.println("> Server Incoming: " + command);
                handleCommand(command);
//                command = readLineFromServer();
            }
        } catch (IOException | ServerUnavailableException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    /**
     * Handles commands received from the client by calling the according methods at the AbaloneServer.
     *
     * @param commands command from client
     * @throws IOException if an IO errors occur.
     */
    private void handleCommand(String commands) throws IOException {
        String[] command = commands.split(String.valueOf(Command.DELIMITER));
        String cmdResult;
        if (command[0].equalsIgnoreCase(String.valueOf(Command.PLAYER_JOINED))) {
            // handlePlayerJoined
            handlePlayerJoined(command[1]);
        } else if (command[0].equalsIgnoreCase(String.valueOf(Command.UPDATE_COLOR))) {
            // handleUpdateColor
            handleColorUpdate(Color.fromString(command[1]));
        } else if (command[0].equalsIgnoreCase(String.valueOf(Command.SYNC_BOARD))) {
            // handleSyncBoard
            updateBoard(command);
        } else if (command[0].equalsIgnoreCase(String.valueOf(Command.MAKE_MOVE))) {
            // handleMakeMove
            handleMove();


//        } else if (command.equals(Character.toString(ProtocolMessages.EXIT))) {
//            in.close();
//            out.close();
//            sock.close();
        } else {
            clientTUI.showMessage("Check your parameters");
            try {
                sendMessage("Unknown Command");
            } catch (ServerUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shut down the connection by closing the socket and the In- and OutputStreams.
     */
    private void shutdown() {
        clientTUI.showMessage("Closing the connection...");
        try {
            in.close();
            out.close();
            serverSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

