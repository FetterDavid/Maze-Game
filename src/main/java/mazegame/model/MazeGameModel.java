package mazegame.model;

import mazegame.utils.MazeReader;
import mazegame.utils.Position;
import mazegame.utils.ScoreReaderWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Model class for the MazeGame.
 */
public class MazeGameModel {
    /**
     * Size of the maze.
     **/
    public static int MAZE_SIZE = 6;
    /**
     * The goal tile row position.
     */
    public static int EXIT_TILE_ROW = 0;
    /**
     * The goal tile col position.
     */
    public static int EXIT_TILE_COL = 4;
    /**
     * Matrix representing the maze. Contains {@code Square} objects.
     */
    private Square[][] maze = new Square[MAZE_SIZE][MAZE_SIZE];
    /**
     * {@code Position} of the player.
     */
    private Position playerPos = new Position(0, 0);
    /**
     * {@code Position} of the monster.
     */
    private Position monsterPos = new Position(2, 4);
    /**
     * Representing the current {@code Turn} of the game.
     */
    private Turn currentTurn;
    /**
     * Count the steps of the Player.
     */
    private int steps;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Constructor, build the maze logically from json file.
     */
    public MazeGameModel() {
        MazeReader mazeReader = new MazeReader();
        buildMaze(mazeReader.readMazeTiles());
    }

    /**
     * Move Player to the given row and col.
     *
     * @param moveToRow row to move the Player.
     * @param moveToCol column to move the Player.
     **/
    public void movePlayer(int moveToRow, int moveToCol) {
        maze[playerPos.getRow()][playerPos.getCol()].setPlayer(false);
        maze[moveToRow][moveToCol].setPlayer(true);
        playerPos.setPosition(moveToRow, moveToCol);
        steps += 1;
        logger.info("Player moved to row {}; col {}", moveToRow, moveToCol);
    }

    /**
     * Move Monster to the given row and col.
     *
     * @param moveToRow row to move the Monster.
     * @param moveToCol column to move the Monster.
     **/
    public void moveMonster(int moveToRow, int moveToCol) {
        maze[monsterPos.getRow()][monsterPos.getCol()].setMonster(false);
        maze[moveToRow][moveToCol].setMonster(true);
        monsterPos.setPosition(moveToRow, moveToCol);
        logger.info("Monster moved to row {}; col {}", moveToRow, moveToCol);
    }

    /**
     * @return the next move of the monster for the current positions.
     **/
    public Position getNextMonsterMove() {
        int rowDistanceFomPlayer = playerPos.getRow() - monsterPos.getRow();
        int colDistanceFomPlayer = playerPos.getCol() - monsterPos.getCol();
        if (colDistanceFomPlayer < 0 && isValidMove(monsterPos, new Position(monsterPos.getRow(), monsterPos.getCol() - 1)))
            return new Position(monsterPos.getRow(), monsterPos.getCol() - 1);
        if (colDistanceFomPlayer > 0 && isValidMove(monsterPos, new Position(monsterPos.getRow(), monsterPos.getCol() + 1)))
            return new Position(monsterPos.getRow(), monsterPos.getCol() + 1);
        if (rowDistanceFomPlayer < 0 && isValidMove(monsterPos, new Position(monsterPos.getRow() - 1, monsterPos.getCol())))
            return new Position(monsterPos.getRow() - 1, monsterPos.getCol());
        if (rowDistanceFomPlayer > 0 && isValidMove(monsterPos, new Position(monsterPos.getRow() + 1, monsterPos.getCol())))
            return new Position(monsterPos.getRow() + 1, monsterPos.getCol());
        logger.info("Monster cannot move");
        return monsterPos;
    }

    /**
     * @param currentPos the current position of the entity.
     * @param moveToPos  the position where the entity try to go.
     * @return a move is valid or not.
     **/
    public boolean isValidMove(Position currentPos, Position moveToPos) {
        int rowMoveDirection = moveToPos.getRow() - currentPos.getRow();
        int colMoveDirection = moveToPos.getCol() - currentPos.getCol();
        if (Math.abs(rowMoveDirection) > 1 || Math.abs(colMoveDirection) > 1) return false;// 1 tile away check
        if (Math.abs(rowMoveDirection) == 1 && Math.abs(colMoveDirection) == 1) return false;// vertical or horizontal
        // wall checks
        if (rowMoveDirection == -1 && getMazeSquare(currentPos).isUpWall()) return false;
        if (rowMoveDirection == 1 && getMazeSquare(currentPos).isDownWall()) return false;
        if (colMoveDirection == 1 && getMazeSquare(currentPos).isRightWall()) return false;
        if (colMoveDirection == -1 && getMazeSquare(currentPos).isLeftWall()) return false;
        return true;
    }

    /**
     * @return player is escaped from the maze.
     **/
    public boolean isPlayerEscaped() {
        boolean escaped = (playerPos.getRow() == EXIT_TILE_ROW && playerPos.getCol() == EXIT_TILE_COL);
        if (escaped) logger.info("Player escaped from the maze");
        return escaped;
    }

    /**
     * @return player is got caught by the monster.
     **/
    public boolean isPlayerGotCaught() {
        boolean gotCaught = (playerPos.getRow() == monsterPos.getRow() && playerPos.getCol() == monsterPos.getCol());
        if (gotCaught) logger.info("Player got caught.");
        return gotCaught;
    }

    /**
     * Build the maze from a {@code Square} array.
     *
     * @param squares an array of {@code Square} representing the maze logically.
     **/
    private void buildMaze(Square[] squares) {
        int i = 0;
        for (int col = 0; col < MAZE_SIZE; col++) {
            for (int row = 0; row < MAZE_SIZE; row++) {
                maze[row][col] = squares[i];
                i++;
            }
        }
        logger.info("The maze has been build logically.");
    }

    /**
     * Saving a {@code Score} representing the finished game result.
     *
     * @param name name of the player.
     * @param win  result of the game.
     **/
    public void saveGameResult(String name, String win) {
        ScoreReaderWriter readerWriter = new ScoreReaderWriter();
        Score score = new Score();
        List<Score> scoreList = new ArrayList<Score>(Arrays.asList(readerWriter.readScores()));
        score.setName(name);
        score.setSteps(steps);
        score.setWin(win);
        score.setDate(new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date()));
        scoreList.add(score);
        readerWriter.writeScores(scoreList.toArray(Score[]::new));
        logger.info("{} game result has been saved", name);
    }

    //---------------------------- Getters and Setters ----------------------------------------

    /**
     * Returns the current maze.
     *
     * @return A 2D array of {@code Square} objects representing the maze.
     */
    public Square[][] getMaze() {
        return maze;
    }

    /**
     * Sets the current maze.
     *
     * @param maze A 2D array of {@code Square} objects representing the maze.
     */
    public void setMaze(Square[][] maze) {
        this.maze = maze;
    }

    /**
     * Returns the Square object at a given position in the maze.
     *
     * @param pos A {@code Position} object representing the row and column index in the maze.
     * @return The {@code Square} object at the specified position.
     */
    public Square getMazeSquare(Position pos) {
        return maze[pos.getRow()][pos.getCol()];
    }

    /**
     * Returns the current position of the player in the maze.
     *
     * @return A {@code Position} object representing the player's position.
     */
    public Position getPlayerPos() {
        return playerPos;
    }

    /**
     * Sets the current position of the player in the maze.
     *
     * @param playerPos A {@code Position} object representing the player's new position.
     */
    public void setPlayerPos(Position playerPos) {
        this.playerPos = playerPos;
    }

    /**
     * Returns the current position of the monster in the maze.
     *
     * @return A {@code Position} object representing the monster's position.
     */
    public Position getMonsterPos() {
        return monsterPos;
    }

    /**
     * Sets the current position of the monster in the maze.
     *
     * @param monsterPos A {@code Position} object representing the monster's new position.
     */
    public void setMonsterPos(Position monsterPos) {
        this.monsterPos = monsterPos;
    }

    /**
     * Returns the current turn in the game.
     *
     * @return A {@code Turn} object representing the current turn.
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Sets the current turn in the game.
     *
     * @param currentTurn A {@code Turn} object representing the new turn.
     */
    public void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * Returns the current number of steps taken in the game.
     *
     * @return The number of steps as an integer.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Sets the number of steps taken in the game.
     *
     * @param steps The new number of steps as an integer.
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }
}
