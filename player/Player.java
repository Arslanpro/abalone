package abalone.player;

import abalone.client.AbaloneClientTUI;
import abalone.element.Board;
import abalone.element.Marble;
import abalone.protocol.enums.Color;
import javafx.util.Pair;

/**
 * Abstract class for keeping a player.
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Marble marble;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Player object.
     */
    public Player(String name) {
        this.name = name;
        this.marble = new Marble();
    }

    // -- Queries ----------------------------------------------------

    /**
     * Returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the marble of the player.
     */
    public Marble getMarble() {
        return marble;
    }

    /**
     * Set the marble of the player.
     */
    public void setMarble(Color color) {
        marble.setColor(color);
    }

    // -- Commands ---------------------------------------------------

    public abstract Pair<String, Pair<String, String>> getMovement(AbaloneClientTUI UserInterface, Board board, Color turn);
}
