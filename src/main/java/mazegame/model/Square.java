package mazegame.model;

/**
 * Class for representing a tile of the game board.
 */
public class Square {
    /**
     * The row of the tile on the board.
     */
    private int row;
    /**
     * The column of the tile on the board.
     */
    private int col;
    /**
     * is there an 'up' wall of the tile?.
     */
    private boolean upWall;
    /**
     * is there an 'right' wall of the tile?
     */
    private boolean rightWall;
    /**
     * is there an 'down' wall of the tile?
     */
    private boolean downWall;
    /**
     * is there an 'left' wall of the tile?
     */
    private boolean leftWall;
    /**
     * is the Player standing on the tile?
     */
    private boolean player;
    /**
     * is the Monster standing on the tile?
     */
    private boolean monster;


    //-------------------------- Setter and Getters -----------------------------------------------
    /**
     * Returns the column of this square in the maze.
     *
     * @return An integer representing the column.
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the row of this square in the maze.
     *
     * @return An integer representing the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Checks if there is a wall on the upside of this square.
     *
     * @return A boolean indicating if there is an up wall.
     */
    public boolean isUpWall() {
        return upWall;
    }

    /**
     * Checks if there is a wall on the right side of this square.
     *
     * @return A boolean indicating if there is a right wall.
     */
    public boolean isRightWall() {
        return rightWall;
    }

    /**
     * Checks if there is a wall on the downside of this square.
     *
     * @return A boolean indicating if there is a down wall.
     */
    public boolean isDownWall() {
        return downWall;
    }

    /**
     * Checks if there is a wall on the left side of this square.
     *
     * @return A boolean indicating if there is a left wall.
     */
    public boolean isLeftWall() {
        return leftWall;
    }

    /**
     * Checks if the player is on this square.
     *
     * @return A boolean indicating if the player is on this square.
     */
    public boolean isPlayer() {
        return player;
    }

    /**
     * Sets whether the player is on this square or not.
     *
     * @param player A boolean representing the player's presence on this square.
     */
    public void setPlayer(boolean player) {
        this.player = player;
    }

    /**
     * Checks if the monster is on this square.
     *
     * @return A boolean indicating if the monster is on this square.
     */
    public boolean isMonster() {
        return monster;
    }

    /**
     * Sets whether the monster is on this square or not.
     *
     * @param monster A boolean representing the monster's presence on this square.
     */
    public void setMonster(boolean monster) {
        this.monster = monster;
    }
}
