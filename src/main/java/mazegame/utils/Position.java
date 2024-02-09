package mazegame.utils;

/**
 * Class for help managing row and column positions.
 */
public class Position {
    private int row;
    private int col;

    /**
     * Constructs a new Position object with the specified row and column.
     *
     * @param row An integer representing the initial row.
     * @param col An integer representing the initial column.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Sets the row and column of this position.
     *
     * @param row An integer representing the row.
     * @param col An integer representing the column.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of this position.
     *
     * @return An integer representing the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row of this position.
     *
     * @param row An integer representing the row.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column of this position.
     *
     * @return An integer representing the column.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column of this position.
     *
     * @param col An integer representing the column.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Determines whether this position is equal to another object.
     *
     * @param o The object to compare this position to.
     * @return A boolean indicating whether the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        return col == position.col;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return An integer representing the hash code value.
     */
    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
