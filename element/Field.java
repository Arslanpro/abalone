package abalone.element;

import abalone.protocol.enums.Color;
import abalone.protocol.enums.Direction;
import javafx.util.Pair;

public class Field {
    public Marble marble;
    public int x;
    public int y;

    public Field() {
        marble = new Marble();
    }
    /**
     * @require field != null
     * @ensure result == true || result == false
     * @param field
     * @return
     */
    public boolean isSamePosition(Field field) {
        return (field.getX() == x) && (field.getY() == y);
    }
    /**
     * @require getMarble().getColor() != null
     * @ensure result == true || result == false
     * @return
     */

    public boolean isEmpty() {
        return getMarble().getColor().equals(Color.NONE);
    }
    /**
     * @ensure marble.getColor == null
     */
    public void reset() {
        marble = new Marble();
    }
    
    public Pair<Integer, Integer> getNeighbourFieldCoordinate(Direction direction) {
        switch (direction) {
            case STRAIGHT_LEFT:
                return new Pair<>(x, y - 1);
            case DOWN_LEFT:
                return new Pair<>(x + 1, y - 1);
            case DOWN_RIGHT:
                return new Pair<>(x + 1, y);
            case STRAIGHT_RIGHT:
                return new Pair<>(x, y + 1);
            case UP_RIGHT:
                return new Pair<>(x - 1, y + 1);
            case UP_LEFT:
                return new Pair<>(x - 1, y);
        }
        return new Pair<>(x, y);
    }
    /**
     * @ensure getMarble().getColor() != null
     * @return
     */
    public Color getMarbleColor() {
        return getMarble().getColor();
    }
    /**
     * @ensure result >= 0
     * @return
     */
    public int getX() {
        return x;
    }
    /**
     * @ensure result >= 0
     * @return
     */
    public int getY() {
        return y;
    }
    /**
     * @require x >= 0
     * @ensure getX() == x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * @require y >= 0
     * @ensure getY() == y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * @ensure result != null
     * @return
     */
    public Marble getMarble() {
        return marble;
    }
    /**
     * @require marble != null
     * @ensure getMarble == marble
     * @param marble
     */
    public void setMarble(Marble marble) {
        this.marble = marble;
    }
}
