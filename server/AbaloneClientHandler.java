package abalone.server;

import abalone.exceptions.ServerUnavailableException;
import abalone.protocol.enums.Color;
import abalone.protocol.enums.Command;
import abalone.protocol.enums.Direction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


/**
 * AbaloneClientHandler for the Abalone Server application.
 * This class can handle the communication with one
 * client.
 */
public class AbaloneClientHandler implements Runnable {

    /**
     * The socket and In- and OutputStreams
     */
    private BufferedReader in;
    private BufferedWriter out;
    private Socket sock;
    private AbaloneServerTUI serverTUI;

    /**
     * The connected AbaloneServer
     */
    private AbaloneServer server;

    /**
     * Name of this ClientHandler
     */
    private String name;

    /**
     * Constructs a new AbaloneClientHandler. Opens the In- and OutputStreams.
     *
     * @param sock      The client socket
     * @param server    The connected server
     * @param serverTUI The global server TUI
     */
    public AbaloneClientHandler(Socket sock, AbaloneServer server, AbaloneServerTUI serverTUI) {
        try {
            this.sock = sock;
            in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.server = server;
            this.serverTUI = serverTUI;
            this.name = sock.getInetAddress().toString() + ":" + sock.getPort();
            // TODO set name
            serverTUI.showMessage("New client [" + name + "] (" + sock.getInetAddress().toString() + ") connected!");
        } catch (IOException e) {
            shutdown();
        }
    }

    /**
     * Continuously listens to client input and forwards the input to the
     * {@link #handleCommand(String)} method.
     */
    public void run() {
        String command;
        try {
            command = in.readLine();
            while (command != null) {
                System.out.println("> [" + name + "] Incoming: " + command);
                handleCommand(command);
                command = readLineFromClient();
            }
            shutdown();
        } catch (IOException e) {
            shutdown();
        }
    }

    /**
     * Handles commands received from the client by calling the according
     * methods at the AbaloneServer.
     *
     * @param commands command from client
     * @throws IOException if an IO errors occur.
     */
    private void handleCommand(String commands) throws IOException {
        String[] command = commands.split(String.valueOf(Command.DELIMITER));
        String cmdResult;
        if (command[0].equalsIgnoreCase(String.valueOf(Command.JOIN))) {
            // for output logging  use
            name = command[1];
            // handleJoin
            cmdResult = server.handleJoin(sock.getInetAddress(), command[1]);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);

            // TODO set a global variable in AbaloneServer.java to ensure synchronized ready status
//            // set joined player name
//            server.playerJoined = name;
//            // sendPlayerJoined
//            cmdResult = server.sendPlayerJoined(sock.getInetAddress());
//            out.write(cmdResult);
//            serverTUI.showMessage("message sent: " + cmdResult);
//            // wait for other users
//            while (true) {
//                if (server.playerJoined.equalsIgnoreCase(String.valueOf(Command.READY))) {
//                    break;
//                }
//            }

            // send color update
            cmdResult = server.sendColorUpdate(sock.getInetAddress(), command[1]);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);

            // sync board to client before making move
            cmdResult = server.syncBoard(command[1], false);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);

            // start game and let players send move
            cmdResult = server.sendMove(sock.getInetAddress(), command[1]);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);


        } else if (command[0].equalsIgnoreCase(String.valueOf(Command.MAKE_MOVE))) {

            // handle move
            server.handleMove(command[1], command[2], command[3]);

            // check win
            // if not game over move to next turn start move again
            server.sendGameOver();

            // --------------

            // sync board to client before making move
            cmdResult = server.syncBoard(name, true);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);

            // start game and let players send move
            cmdResult = server.sendMove(sock.getInetAddress(), name);
            out.write(cmdResult);
            out.newLine();
            out.flush();
            serverTUI.showMessage("[" + name + "] Message sent: " + cmdResult);

//        } else if (command.equals(Character.toString(ProtocolMessages.EXIT))) {
//            in.close();
//            out.close();
//            sock.close();
        } else {
            serverTUI.showMessage("Check your parameters");
            out.write("Unknown Command");
            out.newLine();
            out.flush();
        }
    }

    /**
     * Reads and returns one line from the client.
     *
     * @return the line sent by the client.
     * @throws IOException if IO errors occur.
     */
    public String readLineFromClient() throws IOException {
        if (in != null) {
            try {
                // Read and return answer from Server
                String answer = in.readLine();
                if (answer == null) {
                    throw new IOException("Could not read from client.");
                }
                return answer;
            } catch (IOException e) {
                throw new IOException("Could not read from client.");
            }
        } else {
            throw new IOException("Could not read from client.");
        }
    }

    /**
     * Shut down the connection to this client by closing the socket and the In- and OutputStreams.
     */
    private void shutdown() {
        System.out.println("> [" + name + "] Shutting down.");
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO notify this game group and ...
        server.removeClient(this);
    }
}
