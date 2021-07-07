package abalone.player;

import abalone.client.AbaloneClientTUI;
import abalone.element.Board;
import abalone.element.Field;
import abalone.protocol.enums.Color;
import abalone.protocol.enums.Direction;
import javafx.util.Pair;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ComputerPlayer extends Player {
    final String indexAX = "ABCDEFGHI";
    final String indexBX = "ABCDEFGHI";
    final String indexAY = "123456789";
    final String indexBY = "123456789";
    final int indexAXLen = indexAX.length();
    final int indexAYLen = indexAY.length();
    final int indexBXLen = indexBX.length();
    final int indexBYLen = indexBY.length();
    final String dir = "012345";
    final int dirLen = dir.length();
    Random r = new Random();

    /**
     * Creates a new Player object.
     *
     * @param name
     * @requires name is not null
     * @ensures the Name of this player will be name
     */
    public ComputerPlayer(String name) {

        super(name);
    }

    @Override
    public Pair<String, Pair<String, String>> getMovement(AbaloneClientTUI UserInterface, Board board, Color turn) {
        Field fieldA;
        Field fieldB;
        Direction dirRandom = null;
        String marbleBegin;
        String marbleEnd;
        char tmpAX;
        char tmpAY;
        char tmpBX;
        char tmpBY;
        // decision making
        do {
            tmpAX = indexAX.toLowerCase().charAt(r.nextInt(indexAXLen));
            tmpAY = indexAY.toLowerCase().charAt(r.nextInt(indexAYLen));
            tmpBX = indexBX.toLowerCase().charAt(r.nextInt(indexBXLen));
            tmpBY = indexBY.toLowerCase().charAt(r.nextInt(indexBYLen));
            fieldA = board.findFieldByPosition(tmpAX, Character.getNumericValue(tmpAY));
            fieldB = board.findFieldByPosition(tmpBX, Character.getNumericValue(tmpBY));
            switch (dir.charAt(r.nextInt(dirLen))) {
                case '0':
                    dirRandom = Direction.DOWN_LEFT;
                    break;
                case '1':
                    dirRandom = Direction.DOWN_RIGHT;
                    break;
                case '2':
                    dirRandom = Direction.STRAIGHT_LEFT;
                    break;
                case '3':
                    dirRandom = Direction.STRAIGHT_RIGHT;
                    break;
                case '4':
                    dirRandom = Direction.UP_LEFT;
                    break;
                case '5':
                    dirRandom = Direction.UP_RIGHT;
                    break;
                default:
                    break;
            }
            // try
        } while (!board.isValidMove(fieldA, fieldB, dirRandom, turn));
        // sleep 1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // show on screen
        marbleBegin = String.valueOf(tmpAX).toUpperCase() + tmpAY;
        marbleEnd = String.valueOf(tmpBX).toUpperCase() + tmpBY;
        UserInterface.showMessage("Computer: Marble A: " + marbleBegin);
        UserInterface.showMessage("Computer: Marble B: " + marbleEnd);
        UserInterface.showMessage("Computer: Direction: " + dirRandom.toString());
        return new Pair(marbleBegin, new Pair(marbleEnd, dirRandom.toString()));
    }
}
