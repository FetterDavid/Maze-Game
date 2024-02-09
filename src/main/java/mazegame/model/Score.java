package mazegame.model;

/**
 * Class representing previous game results.
 */
public class Score {
    /**
     * Name of the player.
     **/
    private String name;
    /**
     * Number of steps of the player.
     **/
    private int steps;
    /**
     * The result of the game.
     **/
    private String win;
    /**
     * The date of the end of the game in 'dd-MM-yyyy hh:mm' format.
     **/
    private String date;

    //-------------------------- Setter and Getters -----------------------------------------------
    /**
     * Returns the name of the player.
     *
     * @return A String representing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the player.
     *
     * @param name A String representing the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number of steps.
     *
     * @return The number of steps as an integer.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Sets the number of steps.
     *
     * @param steps The new number of steps as an integer.
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * Returns the win status.
     *
     * @return A String representing the win status. Can be "win" or "lose".
     */
    public String getWin() {
        return win;
    }

    /**
     * Sets the win status .
     *
     * @param win A String representing the win status. Can be "nyert" or "vesztett".
     */
    public void setWin(String win) {
        this.win = win;
    }

    /**
     * Returns the date.
     *
     * @return A String representing the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date A String representing the date.
     */
    public void setDate(String date) {
        this.date = date;
    }
}
