package abalone.element;

import abalone.player.Player;
import abalone.protocol.enums.Color;

import java.util.ArrayList;

public class Game {
    public Board board;

    private ArrayList<Player> players;
    private int playerNumber;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Game object.
     */
    public Game() {
        // default player number
        playerNumber = 0;
        players = new ArrayList<>();
        board = new Board();
    }

    // -- Commands ---------------------------------------------------

    public void join(Player player) {
        players.add(player);
        playerNumber++;
        setMarble(player);
    }

    /**
     * After all players joint, game initializes the environment and ready for playing
     */
    public void init() {
        board.setPlayerNumber(playerNumber);
        board.initPlayerFields();
    }

    /**
     * Resets the game.
     * The board is emptied and black player becomes the current player.
     */
    private void reset() {
        board.reset();
    }

    /**
     * Prints the game situation.
     */
    public String update() {
        return "\ncurrent game situation: \n\n" + board.toString() + "\n";
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        // try to handle?
        return null;
    }

    /**
     * Set color for each player when game starts
     */
    public void setMarble(Player player) {
        switch (players.indexOf(player)) {
            case 0:
                player.setMarble(Color.BLACK);
                break;
            case 1:
                player.setMarble(Color.WHITE);
                break;
            case 2:
                player.setMarble(Color.RED);
                break;
            case 3:
                player.setMarble(Color.BLUE);
                break;
        }
    }

    /**
     * Prints the result of the last game.
     */
    public boolean isGameOver() {
        return !board.getWinner().contains(Color.NONE);
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
