package abalone.server;

import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Abalone Server TUI for user input and user messages
 */
public class AbaloneServerTUI implements AbaloneServerView {
    /**
     * The PrintWriter to write messages to
     */
    private PrintWriter console;

    /**
     * Constructs a new AbaloneServerTUI. Initializes the console.
     */
    public AbaloneServerTUI() {
        console = new PrintWriter(System.out, true);
    }

    @Override
    public void showMessage(String message) {
        console.println(message);
    }

    @Override
    public String getString(String question) {
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public int getInt(String question) {
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    @Override
    public boolean getBoolean(String question) {
        System.out.println(question);
        System.out.println("true/false");
        Scanner sc = new Scanner(System.in);
        return sc.nextBoolean();
    }
}
