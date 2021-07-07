package abalone.player;

import abalone.client.AbaloneClientTUI;
import abalone.element.Board;
import abalone.protocol.enums.Color;
import javafx.util.Pair;

public class HumanPlayer extends Player {

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new human player object.
     */
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Pair<String, Pair<String, String>> getMovement(AbaloneClientTUI UserInterface, Board board, Color turn) {
        String marbleBegin = getMarbleBegin(UserInterface);
        String marbleEnd = getMarbleEnd(UserInterface);
        String direction = getDirection(UserInterface);
        return new Pair(marbleBegin, new Pair(marbleEnd, direction));
    }

    private String getMarbleBegin(AbaloneClientTUI UserInterface) {
        return UserInterface.getString("\nMarble Begin:");
    }

    private String getMarbleEnd(AbaloneClientTUI UserInterface) {
        return UserInterface.getString("Marble End:");
    }

    private String getDirection(AbaloneClientTUI UserInterface) {
        return UserInterface.getString("Direction(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, STRAIGHT_LEFT, STRAIGHT_RIGHT):");
    }

    // -- Commands ---------------------------------------------------

}
