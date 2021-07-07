package abalone.client;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import abalone.exceptions.ExitProgram;
import abalone.exceptions.ServerUnavailableException;
import abalone.protocol.enums.Command;

public class AbaloneClientTUI implements AbaloneClientView {
    /**
     * The PrintWriter to write messages to
     */
    private PrintWriter console;

    /**
     * Constructs a new AbaloneServerTUI. Initializes the console.
     */
    public AbaloneClientTUI() {
        console = new PrintWriter(System.out, true);
    }

//    /**
//     * Asks for user input continuously and handles communication accordingly using
//     * the {@link #handleUserInput(String input)} method.
//     * <p>
//     * If an ExitProgram exception is thrown, stop asking for input, send an exit
//     * message to the server according to the protocol and close the connection.
//     *
//     * @throws ServerUnavailableException in case of IO exceptions.
//     */
//    @Override
//    public void start() throws ServerUnavailableException {
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("your input is");
//            try {
//                this.handleUserInput(sc.nextLine());
//            } catch (ExitProgram e) {
////                TODO Client.sendExit();
//                System.out.println("now stopped due to errors!");
//                break;
//            }
//        }
//    }
//
//
//    /**
//     * Split the user input on a space and handle it accordingly.
//     *
//     * @param input The user input.
//     * @throws ExitProgram                When the user has indicated to exit the program.
//     * @throws ServerUnavailableException if an IO error occurs in taking the corresponding actions.
//     */
//    @Override
//    public void handleUserInput(String input) throws ExitProgram, ServerUnavailableException {
//        String[] inputed = input.split(String.valueOf(Command.DELIMITER));
//        if (false) {
////        if (inputed[0].equals(Character.toString(ProtocolMessages.HELLO))) {
////            try {
////                Client.handleHello();
////            } catch (ProtocolException e) {
////                e.printStackTrace();
////            }
////        } else if (inputed[0].equals(String.valueOf(ProtocolMessages.IN))) {
////            Client.doIn(inputed[1]);
////        } else if (inputed[0].equals(String.valueOf(ProtocolMessages.OUT))) {
////            Client.doOut(inputed[1]);
////        } else if (inputed[0].equals(String.valueOf(ProtocolMessages.ROOM))) {
////            Client.doRoom(inputed[1]);
////        } else if ((inputed[0].equals(String.valueOf(ProtocolMessages.ACT)))) {
////            Client.doAct(inputed[1], inputed[2]);
////        } else if ((inputed[0].equals(String.valueOf(ProtocolMessages.BILL)))) {
////            Client.doBill(inputed[1], inputed[2]);
////        } else if ((inputed[0]
////                .equals(String.valueOf(ProtocolMessages.PRINT)))) {
////            Client.doPrint();
////        } else if ((inputed[0]
////                .equals((String.valueOf(ProtocolMessages.EXIT))))) {
////            Client.sendExit();
////        } else if (inputed[0].equals(String.valueOf(ProtocolMessages.HELP))) {
////            this.printHelpMenu();
//        } else {
//            System.out.println("Check your parameters");
//        }
//    }

    /**
     * Writes the given message to standard output.
     *
     * @param message the message to write to the standard output.
     */
    @Override
    public void showMessage(String message) {
        console.println(message);
    }

    /**
     * Ask the user to input a valid IP. If it is not valid, show a message and
     * ask again.
     *
     * @return a valid IP
     */
    @Override
    public InetAddress getIp() {
        boolean check = true;
        InetAddress ip = null;
        Scanner sc = new Scanner(System.in);
        while (check) {
            System.out.println("input a valid ip");
            try {
                ip = InetAddress.getByName(sc.nextLine());
            } catch (UnknownHostException e) {
                System.out.println("not valid");
            }
        }
        sc.close();
        return ip;
    }

    /**
     * Prints the question and asks the user to input a String.
     *
     * @param question The question to show to the user
     * @return The user input as a String
     */
    @Override
    public String getString(String question) {
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Prints the question and asks the user to input an Integer.
     *
     * @param question The question to show to the user
     * @return The written Integer.
     */
    @Override
    public int getInt(String question) {
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    /**
     * Prints the question and asks the user for a yes/no answer.
     *
     * @param question The question to show to the user
     * @return The user input as boolean.
     */
    @Override
    public boolean getBoolean(String question) {
        System.out.println(question);
        System.out.println("true/false");
        Scanner sc = new Scanner(System.in);
        return sc.nextBoolean();
    }

    /**
     * Prints the help menu with available input options.
     */
    @Override
    public void printHelpMenu() {
        System.out.println("");
    }
}
