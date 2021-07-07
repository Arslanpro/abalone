package abalone.element;

import abalone.protocol.enums.Color;
import abalone.protocol.enums.Direction;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Board for the Abalone game.
 */
public class Board {
    public static final int DIMENSION = 9 + 2;

    /**
     * The DIMENSION by DIMENSION fields of the Abalone board.
     */
    public Field[][] fields;

    private int playerNumber;

    public int deadBlackCount = 0;
    private int deadWhiteCount = 0;
    private int deadRedCount = 0;
    private int deadBlueCount = 0;

    // -- Constructors -----------------------------------------------

    /**
     * Creates an empty board. Note that the left-top corner and right-bottom corner are out of board and always None.
     * Two additional rows/columns applies to board in order to handle out-of-board cases and avoid exceptions.
     * <p>
     * ....*****
     * ...******
     * ..*******
     * .********
     * *********
     * ********.
     * *******..
     * ******...
     * *****....
     */
    public Board() {
        playerNumber = 2;
        deadBlackCount = 0;
        deadWhiteCount = 0;
        deadRedCount = 0;
        deadBlueCount = 0;

        fields = new Field[DIMENSION][DIMENSION];
        // init fields
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                fields[i][j] = new Field();
                fields[i][j].setX(i);
                fields[i][j].setY(j);
            }
        }
    }
/**
 * @require playerNumber > 0
 * @ensure 
 */
    public void initPlayerFields() {
        // TODO check by printing initialized board

        // put player marbles on board (2, 3, 4 players)
        switch (playerNumber) {
            case 2:
                int[][] fieldsReferenceTwo =
                        {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0},
                                {0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0},
                                {0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

                setInitialFeildsByReference(fieldsReferenceTwo);
//                for (int i = 0; i < DIMENSION; i++) {
//                    for (int j = 0; j < DIMENSION; j++) {
//                        if (((i == 1) && (j > 4)) ||
//                                ((i == 3) && (j > 3)) ||
//                                ((i == 4) && (j > 4) && (j < 8))) {
//                            fields[i][j].getMarble().setColor(Color.BLACK);
//                        } else if (((i == 9) && (j < 6)) ||
//                                ((i == 8) && (j < 7)) ||
//                                ((i == 7) && (j > 2) && (j < 6))) {
//                            fields[i][j].getMarble().setColor(Color.WHITE);
//                        }
//                    }
//                }
                break;

            case 3:
                int[][] fieldsReferenceThree =
                        {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 2, 2, 0, 1, 1, 0},
                                {0, 0, 0, 0, 2, 2, 0, 0, 1, 1, 0},
                                {0, 0, 0, 2, 2, 0, 0, 0, 1, 1, 0},
                                {0, 0, 2, 2, 0, 0, 0, 0, 1, 1, 0},
                                {0, 2, 2, 0, 0, 0, 0, 0, 1, 1, 0},
                                {0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0},
                                {0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

                setInitialFeildsByReference(fieldsReferenceThree);
//                for (int i = 0; i < DIMENSION; i++) {
//                    for (int j = 0; j < DIMENSION; j++) {
//                        if (((i < 7) && (j == 8)) ||
//                                ((i < 6) && (j == 9))) {
//                            fields[i][j].getMarble().setColor(Color.BLACK);
//                        } else if ((i < 6) || ((i == 6) && (j == 1))) {
//                            if ((j > 4 - i) && (j < 7 - i)) {
//                                fields[i][j].getMarble().setColor(Color.WHITE);
//                            }
//                        } else if ((i == 9 && j < 6) ||
//                                (i == 8 && j < 7) ||
//                                (i == 7 && j > 2 && j < 6)) {
//                            fields[i][j].getMarble().setColor(Color.RED);
//                        }
//                    }
//                }
                break;

            case 4:
                int[][] fieldsReferenceFour =
                        {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0},
                                {0, 0, 0, 0, 0, 4, 4, 4, 0, 1, 0},
                                {0, 0, 0, 0, 0, 4, 4, 0, 1, 1, 0},
                                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                                {0, 2, 2, 2, 0, 0, 0, 1, 1, 1, 0},
                                {0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0},
                                {0, 2, 2, 0, 3, 3, 0, 0, 0, 0, 0},
                                {0, 2, 0, 3, 3, 3, 0, 0, 0, 0, 0},
                                {0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

                setInitialFeildsByReference(fieldsReferenceFour);
//                for (int i = 0; i < DIMENSION; i++) {
//                    for (int j = 0; j < DIMENSION; j++) {
//                        if (((j == 7) && (i > 3) && (i < 6)) ||
//                                ((j == 8) && (i > 2) && (i < 6)) ||
//                                ((j == 9) && (i > 1) && (i < 6))) {
//                            fields[i][j].getMarble().setColor(Color.BLACK);
//                        } else if (((i > 4) && (i < 9) && (j == 1)) ||
//                                ((i > 4) && (i < 8) && (j == 2)) ||
//                                ((i > 4) && (i < 7) && (j == 3))) {
//                            fields[i][j].getMarble().setColor(Color.WHITE);
//                        } else if (((j > 3) && (j < 6) && (i == 7)) ||
//                                ((j > 2) && (j < 6) && (i == 8)) ||
//                                ((j > 1) && (j < 6) && (i == 9))) {
//                            fields[i][j].getMarble().setColor(Color.RED);
//                        } else if (((j > 4) && (j < 9) && (i == 1)) ||
//                                ((j > 4) && (j < 8) && (i == 2)) ||
//                                ((j > 4) && (j < 7) && (i == 3))) {
//                            fields[i][j].getMarble().setColor(Color.BLUE);
//                        }
//                    }
//                }
                break;
        }
    }
    /**
     * 
     * @param fieldsReference
     * @require fieldsReference != null
     * @ensure fields.getMarblecolor() != null
     */

    public void setInitialFeildsByReference(int[][] fieldsReference) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                switch (fieldsReference[i][j]) {
                    case 1:
                        fields[i][j].getMarble().setColor(Color.BLACK);
                        break;
                    case 2:
                        fields[i][j].getMarble().setColor(Color.WHITE);
                        break;
                    case 3:
                        fields[i][j].getMarble().setColor(Color.RED);
                        break;
                    case 4:
                        fields[i][j].getMarble().setColor(Color.BLUE);
                        break;
                    default:
                        fields[i][j].getMarble().setColor(Color.NONE);
                        break;
                }
            }
        }
    }

    /**
     * Creates a deep copy of this board.
     *
     * @ensures the result is a new object
     * @ensures the values of all fields of the copy match the ones of this board
     */
    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < DIMENSION; i++) {
            System.arraycopy(this.fields[i], 0, copy.fields[i], 0, DIMENSION);
        }
        return copy;
    }
    /**
     * 
     * @param fieldBegin
     * @param fieldEnd
     * @param direction
     * @param turn
     * @return
     * @require fieldBegin != null && fieldEnd != null && direction = Direction.UP_LEFT || Direction.UP_RIGHT
     * Direction.DOWN_LEFT || Direction.DOWN_RIGHT || Direction.STRAIGHT_LEFT || Direction.STRAIGHT_RIGHT
     */

    public boolean isValidMove(String fieldBegin, String fieldEnd, Direction direction, Color turn) {
    	
        Field fieldBegin1 = findFieldByPosition(fieldBegin.toLowerCase().charAt(0), Character.getNumericValue(fieldBegin.charAt(1)));
        Field fieldEnd1 = findFieldByPosition(fieldEnd.toLowerCase().charAt(0), Character.getNumericValue(fieldEnd.charAt(1)));
//        Direction direction1 = Direction.fromString(direction);
        return isValidMove(fieldBegin1, fieldEnd1, direction, turn);
    }

    public boolean isValidMove(Field fieldBegin, Field fieldEnd, Direction direction, Color turn) {
        int xBegin = fieldBegin.getX();
        int yBegin = fieldBegin.getY();
        int xEnd = fieldEnd.getX();
        int yEnd = fieldEnd.getY();

        // check input marble(s) are on the board
        if (isOutOfBoard(fieldBegin) || isOutOfBoard(fieldEnd)) {
            return false;
        }

        // single marble
        if ((xBegin - xEnd == 0) && (yBegin - yEnd == 0)) {
            if (fieldBegin.getMarbleColor() == turn) {
                Field neighbourField = getNeighbourField(fieldBegin, direction);
                return neighbourField.isEmpty() && !isOutOfBoard(neighbourField);
            }
        }

        // two marbles
        if ((abs(xBegin - xEnd) <= 1) && (abs(yBegin - yEnd) <= 1) &&
                !((xBegin - xEnd == 0) && (yBegin - yEnd == 0))) {
            if ((fieldBegin.getMarbleColor() == turn) && (fieldEnd.getMarbleColor() == turn)) {
                // swap begin and end if in reverse order to direction
                if (getNeighbourField(fieldBegin, direction.opposite()) == fieldEnd) {
                    Field fieldTmp = fieldBegin;
                    fieldBegin = fieldEnd;
                    fieldEnd = fieldTmp;
                }
                // if there is a move in direction to marble line
                if (getNeighbourField(fieldBegin, direction) == fieldEnd) {
                    // if next neighbour is on the board
                    Field nextNeighbour = getNeighbourField(fieldEnd, direction);
                    if (!isOutOfBoard(nextNeighbour)) {
                        // if there is an enemy marble in next neighbour
                        if (fieldEnd.getMarble().enemies().contains(nextNeighbour.getMarbleColor())) {
                            Field nextNextNeighbourField = getNeighbourField(nextNeighbour, direction);
                            return nextNextNeighbourField.isEmpty() || isOutOfBoard(nextNextNeighbourField);
                        } else {
                            return nextNeighbour.isEmpty();
                        }
                    }
                } else {
                    // if direction and marbles are of different line
                    return (getNeighbourField(fieldBegin, direction).isEmpty() && !isOutOfBoard(getNeighbourField(fieldBegin, direction))) &&
                            (getNeighbourField(fieldEnd, direction).isEmpty() && !isOutOfBoard(getNeighbourField(fieldEnd, direction)));
                }
            }
        }

        // three marbles
        if (((abs(xBegin - xEnd) == 3) && (abs(yBegin - yEnd) == 3)) ||
                ((abs(xBegin - xEnd) == 0) && (abs(yBegin - yEnd) == 3)) ||
                ((abs(xBegin - xEnd) == 3) && (abs(yBegin - yEnd) == 0))) {
            // set fieldMiddle
            int xMiddle = (abs(xBegin - xEnd) == 3) ? ((xBegin + xEnd) / 2) : xBegin;
            int yMiddle = (abs(yBegin - yEnd) == 3) ? ((yBegin + yEnd) / 2) : yBegin;
            Field fieldMiddle = fields[xMiddle][yMiddle];
            // swap begin and end if in reverse order to direction
            if (getNeighbourField(fieldBegin, direction.opposite()) == fieldMiddle) {
                Field fieldTmp = fieldBegin;
                fieldBegin = fieldEnd;
                fieldEnd = fieldTmp;
            }
            // check marbles are legal
            if (fieldBegin.getMarbleColor() == turn) {
                // if there is a move in direction to marble line
                if (getNeighbourField(fieldBegin, direction) == fieldMiddle) {
                    if ((fieldMiddle.getMarble().isFriend(fieldEnd.getMarble()) &&
                            ((fieldMiddle.getMarbleColor() == turn) || (fieldEnd.getMarbleColor() == turn))) ||
                            (fieldMiddle.getMarbleColor() == fieldEnd.getMarbleColor())) {
                        Field nextNeighbour = getNeighbourField(fieldEnd, direction);
                        if (!isOutOfBoard(nextNeighbour)) {
                            // if there is an enemy marble in next neighbour
                            if (fieldEnd.getMarble().enemies().contains(nextNeighbour.getMarbleColor())) {
                                Field nextNextNeighbour = getNeighbourField(nextNeighbour, direction);
                                Field nextNextNextNeighbour = getNeighbourField(nextNextNeighbour, direction);
                                // the next neighbour can move
                                if (nextNextNeighbour.isEmpty() || isOutOfBoard(nextNextNeighbour)) {
                                    return true;
                                } else if (fieldEnd.getMarble().enemies().contains(nextNextNeighbour.getMarbleColor())) {
                                    // if there is an enemy marble in next next neighbour
                                    // the next next neighbour can move
                                    return nextNextNextNeighbour.isEmpty() || isOutOfBoard(nextNextNextNeighbour);
                                }
                            } else {
                                return nextNeighbour.isEmpty();
                            }
                        }
                    }
                    // if direction and marbles are of different line
                } else if (((fieldMiddle.getMarbleColor() == turn) || (fieldMiddle.getMarble().isFriend(fieldBegin.getMarble()))) ||
                        ((fieldEnd.getMarbleColor() == turn) || (fieldEnd.getMarble().isFriend(fieldBegin.getMarble())))) {
                    return (getNeighbourField(fieldBegin, direction).isEmpty() && !isOutOfBoard(getNeighbourField(fieldBegin, direction))) &&
                            (getNeighbourField(fieldMiddle, direction).isEmpty() && !isOutOfBoard(getNeighbourField(fieldMiddle, direction))) &&
                            (getNeighbourField(fieldEnd, direction).isEmpty() && !isOutOfBoard(getNeighbourField(fieldEnd, direction)));
                }
            }
        }

        return false;
    }
/**
 * 
 * @param field
 * @param direction
 * @return
 * @require field ! = null && direction instanceof Direction
 */
    public Field getNeighbourField(Field field, Direction direction) {
        int neighbourX = field.getNeighbourFieldCoordinate(direction).getKey();
        int neighbourY = field.getNeighbourFieldCoordinate(direction).getValue();
        return fields[neighbourX][neighbourY];
    }
/**
 * 
 * @param fieldBegin
 * @param fieldEnd
 * @param direction
 * @param turn
 * @require fieldBegin != null && fieldEnd != null && direction instanceof Direction && turn instanceof Color
 */
    public void makeMove(Field fieldBegin, Field fieldEnd, Direction direction, Color turn) {
        if (isValidMove(fieldBegin, fieldEnd, direction, turn)) {
            int xBegin = fieldBegin.getX();
            int yBegin = fieldBegin.getY();
            int xEnd = fieldEnd.getX();
            int yEnd = fieldEnd.getY();

            // single marble
            if ((xBegin - xEnd == 0) && (yBegin - yEnd == 0)) {
                Field nextNeighbour = getNeighbourField(fieldEnd, direction);
                nextNeighbour.setMarble(fieldBegin.getMarble());
                fieldBegin.setMarble(new Marble());
            }

            // two marbles
            if ((abs(xBegin - xEnd) <= 1) && (abs(yBegin - yEnd) <= 1) &&
                    !((xBegin - xEnd == 0) && (yBegin - yEnd == 0))) {
                if ((fieldBegin.getMarbleColor() == turn) && (fieldEnd.getMarbleColor() == turn)) {
                    // swap begin and end if in reverse order to direction
                    if (getNeighbourField(fieldBegin, direction.opposite()) == fieldEnd) {
                        Field fieldTmp = fieldBegin;
                        fieldBegin = fieldEnd;
                        fieldEnd = fieldTmp;
                    }
                    // if there is a move in direction to marble line
                    if (getNeighbourField(fieldBegin, direction) == fieldEnd) {
                        Field nextNeighbour = getNeighbourField(fieldEnd, direction);
                        Field nextNextNeighbour = getNeighbourField(nextNeighbour, direction);
                        // sumito
                        if (!nextNeighbour.isEmpty() && isOutOfBoard(nextNextNeighbour)) {
                            switch (nextNeighbour.getMarbleColor()) {
                                case BLACK:
                                    deadBlackCount++;
                                    break;
                                case WHITE:
                                    deadWhiteCount++;
                                    break;
                                case RED:
                                    deadRedCount++;
                                    break;
                                case BLUE:
                                    deadBlueCount++;
                                    break;
                            }
                        }
                        // update color
                        nextNextNeighbour.setMarble(nextNeighbour.getMarble());
                        nextNeighbour.setMarble(fieldEnd.getMarble());
                        fieldEnd.setMarble(fieldBegin.getMarble());
                        fieldBegin.setMarble(new Marble());
                    } else {
                        // if direction and marbles are of different line
                        getNeighbourField(fieldBegin, direction).setMarble(fieldBegin.getMarble());
                        getNeighbourField(fieldEnd, direction).setMarble(fieldEnd.getMarble());
                        fieldBegin.setMarble(new Marble());
                        fieldEnd.setMarble(new Marble());
                    }
                }
            }

            // three marbles
            if (((abs(xBegin - xEnd) == 3) && (abs(yBegin - yEnd) == 3)) ||
                    ((abs(xBegin - xEnd) == 0) && (abs(yBegin - yEnd) == 3)) ||
                    ((abs(xBegin - xEnd) == 3) && (abs(yBegin - yEnd) == 0))) {
                // set fieldMiddle
                int xMiddle = (abs(xBegin - xEnd) == 3) ? ((xBegin + xEnd) / 2) : xBegin;
                int yMiddle = (abs(yBegin - yEnd) == 3) ? ((yBegin + yEnd) / 2) : yBegin;
                Field fieldMiddle = fields[xMiddle][yMiddle];
                // swap begin and end if in reverse order to direction
                if (getNeighbourField(fieldBegin, direction.opposite()) == fieldMiddle) {
                    Field fieldTmp = fieldBegin;
                    fieldBegin = fieldEnd;
                    fieldEnd = fieldTmp;
                }
                // if there is a move in direction to marble line
                if (getNeighbourField(fieldBegin, direction) == fieldMiddle) {
                    Field nextNeighbour = getNeighbourField(fieldEnd, direction);
                    Field nextNextNeighbour = getNeighbourField(nextNeighbour, direction);
                    Field nextNextNextNeighbour = getNeighbourField(nextNextNeighbour, direction);
                    // sumito
                    if (!nextNextNeighbour.isEmpty() && isOutOfBoard(nextNextNextNeighbour)) {
                        switch (nextNextNeighbour.getMarbleColor()) {
                            case BLACK:
                                deadBlackCount++;
                                break;
                            case WHITE:
                                deadWhiteCount++;
                                break;
                            case RED:
                                deadRedCount++;
                                break;
                            case BLUE:
                                deadBlueCount++;
                                break;
                        }
                    }
                    // update color
                    nextNextNextNeighbour.setMarble(nextNextNeighbour.getMarble());
                    nextNextNeighbour.setMarble(nextNeighbour.getMarble());
                    nextNeighbour.setMarble(fieldEnd.getMarble());
                    fieldEnd.setMarble(fieldBegin.getMarble());
                    fieldBegin.setMarble(new Marble());
                } else {
                    // if direction and marbles are of different line
                    getNeighbourField(fieldBegin, direction).setMarble(fieldBegin.getMarble());
                    getNeighbourField(fieldMiddle, direction).setMarble(fieldMiddle.getMarble());
                    getNeighbourField(fieldEnd, direction).setMarble(fieldEnd.getMarble());
                    fieldBegin.setMarble(new Marble());
                    fieldMiddle.setMarble(new Marble());
                    fieldEnd.setMarble(new Marble());
                }
            }
        }
    }

    public void setFeildsFromString(String feildsString) {
        // client-side method for both human and computer players by SYNC_BOARD command sending from server
    }

    /**
     * Returns a String representation of this board.
     *
     * @return the game situation as String
     */
    public String toString() {
        // show marbles on board with indexes
        String s = "";
        for (int i = 0; i < DIMENSION; i++) {
            if ((i > 4) && (i != 10)) {
                s += " ".repeat(i - 2);
                // char index bottom board
                s += "\u001b[32m" + String.valueOf((char) ((char) i + 'A' - 1)) + "\u001b[0m ";
            } else {
                s += " ".repeat(i);
            }
            for (int j = 0; j < DIMENSION; j++) {
                if (i + j == 4) {
                    if (i == 0) {
                        s += " ";
                    } else {
                        // char index top board
                        s += "\u001b[32m" + String.valueOf((char) ((char) i + 'A' - 1)) + "\u001b[0m";
                    }
                } else if ((i + j < 4) || i + j > 15) {
                    s += "\u001b[32m";
                    if ((i == 7 && j == 9)) {
                        s += "9";
                    } else if ((i == 8 && j == 8)) {
                        s += "8";
                    } else if ((i == 9 && j == 7)) {
                        s += "7";
                    } else if ((i == 10 && j == 6)) {
                        s += "6";
                    } else {
                        s += " ";
                    }
                    s += "\u001b[0m";
                } else if (isOutOfBoard(fields[i][j])) {
                    
                    s += fields[i][j].getMarbleColor().toString().charAt(0);
                } else {
                    // show marbles with color
                    switch (fields[i][j].getMarbleColor()) {
                        case BLACK:
                            s += "\u001b[30mo";
                            break;
                        case WHITE:
                            s += "\u001b[37mo";
                            break;
                        case RED:
                            s += "\u001b[31mo";
                            break;
                        case BLUE:
                            s += "\u001b[34mo";
                            break;
                        case NONE:
                            s += " ";
                            break;
                    }
                }
                s += "\u001b[0m ";
            }
            
            s += "\n";
        }
       
        s += " ".repeat(13) + "\u001b[32m1 2 3 4 5\u001b[0m";
        return s;


    }

    /**
     * Empties all fields of this board (i.e., let them refer to the value
     * Mark.EMPTY).
     *
     * @ensures all fields are EMPTY field.getMarbleColor() == Color.NONE
     */
    public void reset() {
        deadBlackCount = 0;
        deadWhiteCount = 0;
        deadRedCount = 0;
        deadBlueCount = 0;

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                fields[i][j].reset();
            }
        }
    }
    /**
     * @require playerNumber >= 2
     * @ensure rhis.getPlayerNumber = playerNumber
     * @param playerNumber
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    /**
     * @require field != null
     * @param field
     * @return
     */
    public boolean isOutOfBoard(Field field) {
//        int field_x = field.getX() - 'a';
//        int field_y = field.getY();
        int field_x = field.getX();
        int field_y = field.getY();

        return ((field_x < 5) && (field_y < 6 - field_x)) ||
                ((field_x > 5) && (field_y > 14 - field_x)) ||
                (field_x == 0) || (field_x == DIMENSION - 1) ||
                (field_y == 0) || (field_y == DIMENSION - 1);
    }
    

    public HashSet<Color> getWinner() {
        HashSet<Color> winner = new HashSet<>();
        if (deadBlackCount >= 6 || deadRedCount >= 6) {
            winner.add(Color.WHITE);
            winner.add(Color.BLUE);
        } else if (deadWhiteCount >= 6 || deadBlueCount >= 6) {
            winner.add(Color.BLACK);
            winner.add(Color.RED);
        } else {
            winner.add(Color.NONE);
        }
        return winner;
    }

    public Field getFeild(int x, int y) {
        return fields[x][y];
    }

    /**
     * Translate x and y to 2-D array offset and return that field.
     */
    public Field findFieldByPosition(char x, int y) {
        int field_x = x - 'a' + 1;
        return fields[field_x][y];
    }
}
