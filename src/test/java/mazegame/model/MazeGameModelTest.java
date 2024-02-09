package mazegame.model;

import mazegame.utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeGameModelTest {

    private MazeGameModel model;

    @BeforeEach
    void setUp() {
        model = new MazeGameModel();
    }

    @Test
    void movePlayer() {
        model.movePlayer(1, 1);
        assertTrue(model.getPlayerPos().equals(new Position(1, 1)));
    }

    @Test
    void moveMonster() {
        model.moveMonster(1, 1);
        assertTrue(model.getMonsterPos().equals(new Position(1, 1)));
    }

    @Test
    void getNextMonsterMove() {
        // Scenario 1: Monster is to the right of the player
        model.setPlayerPos(new Position(0, 0));
        model.setMonsterPos(new Position(0, 3));
        Position nextMove = model.getNextMonsterMove();
        assertTrue(nextMove.equals(new Position(0, 2))); // Expected monster to move left

        // Scenario 2: Monster is to the left of the player
        model.setPlayerPos(new Position(0, 3));
        model.setMonsterPos(new Position(0, 0));
        nextMove = model.getNextMonsterMove();
        assertTrue(nextMove.equals(new Position(0, 1))); // Expected monster to move right

        // Scenario 3: Monster is below of the player and cannot move horizontal
        model.setPlayerPos(new Position(0, 0));
        model.setMonsterPos(new Position(1, 2));
        nextMove = model.getNextMonsterMove();
        assertTrue(nextMove.equals(new Position(0, 2))); // Expected monster to move up

        // Scenario 4: Monster is above of the player and cannot move horizontal
        model.setPlayerPos(new Position(5, 1));
        model.setMonsterPos(new Position(4, 0));
        nextMove = model.getNextMonsterMove();
        assertTrue(nextMove.equals(new Position(5, 0))); // Expected monster to move down

        // Scenario 5: There is no valid move for the Monster
        model.setPlayerPos(new Position(3, 2));
        model.setMonsterPos(new Position(2, 2));
        nextMove = model.getNextMonsterMove();
        assertTrue(nextMove.equals(new Position(2, 2))); // Expected monster to stay the same position
    }

    @Test
    void isValidMove() {
        assertTrue(model.isValidMove(new Position(0, 1), new Position(0, 2))); // Horizontal move (Right)
        assertTrue(model.isValidMove(new Position(0, 1), new Position(1, 1))); // Vertical move (Down)
        assertFalse(model.isValidMove(new Position(0, 1), new Position(1, 0))); // Diagonal move
        assertFalse(model.isValidMove(new Position(0, 1), new Position(0, 3))); // Move more than 1 tile
        assertFalse(model.isValidMove(new Position(0, 1), new Position(1, 0))); // Move through wall
    }

    @Test
    void isPlayerEscaped() {
        model.setPlayerPos(new Position(MazeGameModel.EXIT_TILE_ROW, MazeGameModel.EXIT_TILE_COL));
        assertTrue(model.isPlayerEscaped());
        model.setPlayerPos(new Position(0, 0));
        assertFalse(model.isPlayerEscaped());

    }

    @Test
    void isPlayerGotCaught() {
        model.setPlayerPos(new Position(3, 3));
        model.setMonsterPos(new Position(3, 3));
        assertTrue(model.isPlayerGotCaught());
        model.setMonsterPos(new Position(0, 0));
        assertFalse(model.isPlayerGotCaught());

    }
}