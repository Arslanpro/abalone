package abalone.element;

import abalone.protocol.enums.Color;

import java.util.HashSet;
import java.util.Set;

public class Marble {
    private Color color;

    public Marble() {
        color = Color.NONE;
    }

    // for 4-player game
    /**
     * 
     * @param marble
     * @return
     * @require marble != null
     * @ensure colorNew != Color.NONE
     */
    public boolean isFriend(Marble marble) {
        Color colorNew = marble.getColor();
        
        switch (colorNew) {
            case BLACK:
                return color == Color.RED;
            case RED:
                return color == Color.BLACK;
            case WHITE:
                return color == Color.BLUE;
            case BLUE:
                return color == Color.WHITE;
        }
        return false;
    }

    // for 4-player game
    /**
     * @require color = Color.NONE
     * @ensure enemies.size() == 2
     * @return
     */
    public Set<Color> enemies() {
        Set<Color> enemies = new HashSet<>();
        switch (color) {
            case BLACK:
                enemies.add(Color.WHITE);
                enemies.add(Color.BLUE);
            case RED:
                enemies.add(Color.WHITE);
                enemies.add(Color.BLUE);
            case WHITE:
                enemies.add(Color.BLACK);
                enemies.add(Color.RED);
            case BLUE:
                enemies.add(Color.BLACK);
                enemies.add(Color.RED);
        }
        return enemies;
        
    }
    /**
     * @ensure color != null
     * @return
     */
    public Color getColor() {
        return color;
    }
    /**
     * @require color != null
     * @ensure color == Color.RED|| Color.BLUE || Color.BLACK || Color.WHITE
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
