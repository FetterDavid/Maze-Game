package mazegame.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import mazegame.model.MazeGameModel;
import mazegame.model.Square;
import mazegame.model.Turn;
import mazegame.utils.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code MazeGameController} class handles user interactions in the maze game.
 * It processes player input, updates game state and reflects changes in the view.
 */
public class MazeGameController {

    private MazeGameModel mazeGameModel;
    @FXML
    private GridPane mazeGridPane;
    @FXML
    private Label stepsLabel;

    @FXML
    private TextField playerName;

    private static final Logger logger = LogManager.getLogger();

    @FXML
    private void initialize() {
        mazeGameModel = new MazeGameModel();
        buildMazeGridPane();
        resetMaze();
    }

    /**
     * Build the maze ui.
     **/
    private void buildMazeGridPane() {
        for (int col = 0; col < MazeGameModel.MAZE_SIZE; col++) {
            for (int row = 0; row < MazeGameModel.MAZE_SIZE; row++) {
                mazeGridPane.add(createMazeTile(mazeGameModel.getMazeSquare(new Position(row, col))), col, row);
            }
        }
        logger.info("The maze has been build for the UI.");
    }

    /**
     * Create maze tiles for the ui (use StackPane).
     *
     * @param square a {@code Square} object representing a tile of the maze
     * @return a {@code StackPane} representing a tile of the maze for the UI
     **/
    private StackPane createMazeTile(Square square) {
        StackPane mazeTile = new StackPane();
        mazeTile.setStyle(getMazeTileBorderStyle(square));
        mazeTile.setOnMouseClicked(this::handleMouseClick);
        return mazeTile;
    }

    /**
     * Get the appropriate border style for a Square. Representing the walls
     *
     * @param square a {@code Square} object representing a tile of the maze
     * @return the appropriate border style string
     **/
    private String getMazeTileBorderStyle(Square square) {
        String borderStyle = "-fx-border-color: black; -fx-border-width:";
        if (square.isUpWall()) borderStyle += " 4";
        else borderStyle += " 0";
        if (square.isRightWall()) borderStyle += " 4";
        else borderStyle += " 0";
        if (square.isDownWall()) borderStyle += " 4";
        else borderStyle += " 0";
        if (square.isLeftWall()) borderStyle += " 4;";
        else borderStyle += " 0;";
        return borderStyle;
    }

    private void movePlayer(Position pos) {
        ((StackPane) mazeGridPane.getChildren().get((mazeGameModel.getPlayerPos().getRow() + mazeGameModel.getPlayerPos().getCol() * MazeGameModel.MAZE_SIZE) + 1)).getChildren().clear();
        ((StackPane) mazeGridPane.getChildren().get((pos.getRow() + pos.getCol() * MazeGameModel.MAZE_SIZE) + 1)).getChildren().add(getPlayerCircle());
        mazeGameModel.movePlayer(pos.getRow(), pos.getCol());
        stepsLabel.setText(Integer.toString(mazeGameModel.getSteps()));
    }

    private void moveMonster(Position pos) {
        ((StackPane) mazeGridPane.getChildren().get((mazeGameModel.getMonsterPos().getRow() + mazeGameModel.getMonsterPos().getCol() * MazeGameModel.MAZE_SIZE) + 1)).getChildren().clear();
        ((StackPane) mazeGridPane.getChildren().get((pos.getRow() + pos.getCol() * MazeGameModel.MAZE_SIZE) + 1)).getChildren().add(getMonsterCircle());
        mazeGameModel.moveMonster(pos.getRow(), pos.getCol());
    }

    private boolean canMonsterMove() {
        Position nextPos = mazeGameModel.getNextMonsterMove();
        return !mazeGameModel.getMonsterPos().equals(nextPos);
    }

    /**
     * Start the Monster turn (take to step).
     **/
    private void startMonsterTurn() {
        logger.info("Start Monster Turn");
        clearHighlights();
        mazeGameModel.setCurrentTurn(Turn.MONSTER);
        if (canMonsterMove()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(e -> {
                moveMonster(mazeGameModel.getNextMonsterMove());
                if (canMonsterMove()) {
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));
                    pause2.setOnFinished(e2 -> {
                        moveMonster(mazeGameModel.getNextMonsterMove());
                        if (mazeGameModel.isPlayerGotCaught()) playerLost();
                        else startPlayerTurn();
                    });
                    pause2.play();
                } else if (mazeGameModel.isPlayerGotCaught()) playerLost();
                else startPlayerTurn();
            });
            pause.play();
        } else startPlayerTurn();
    }

    /**
     * Set the maze to the start state.
     **/
    private void resetMaze() {
        moveMonster(new Position(2, 4));
        movePlayer(new Position(0, 0));
        mazeGameModel.setSteps(0);
        stepsLabel.setText("0");
        clearHighlights();
        startPlayerTurn();
        logger.info("The maze has been reset.");
    }

    private void startPlayerTurn() {
        logger.info("Start Player Turn");
        mazeGameModel.setCurrentTurn(Turn.PLAYER);
        highlightPlayerValidMoves();
    }

    /**
     * Highlight all the valid move of the player.
     **/
    private void highlightPlayerValidMoves() {
        highlightValidMove(mazeGameModel.getPlayerPos(), 1, 0);
        highlightValidMove(mazeGameModel.getPlayerPos(), -1, 0);
        highlightValidMove(mazeGameModel.getPlayerPos(), 0, 1);
        highlightValidMove(mazeGameModel.getPlayerPos(), 0, -1);
        logger.info("Start valid Player moves");
    }

    /**
     * Highlight a move if its valid.
     *
     * @param pos       the base position
     * @param colOffset the column offset from the base position
     * @param rowOffset the row offset from the base position
     **/
    private void highlightValidMove(Position pos, int rowOffset, int colOffset) {
        if (mazeGameModel.isValidMove(pos, new Position(pos.getRow() + rowOffset, pos.getCol() + colOffset))) {
            StackPane pane = (StackPane) mazeGridPane.getChildren().get(((pos.getRow() + rowOffset) + (pos.getCol() + colOffset) * MazeGameModel.MAZE_SIZE) + 1);
            String style = pane.getStyle();
            pane.setStyle(style + "; -fx-background-color: #8A9A5B");
        }
    }

    /**
     * Deletes all the tile highlighting on the table.
     **/
    private void clearHighlights() {
        for (Node node : mazeGridPane.getChildren()) {
            String style = node.getStyle();
            node.setStyle(style + "; -fx-background-color: transparent");
        }
    }

    /**
     * @return a {@code Circle} representing the player.
     **/
    private Circle getPlayerCircle() {
        Circle player = new Circle(25);
        player.setStyle("-fx-fill: blue");
        return player;
    }

    /**
     * @return a {@code Circle} representing the monster.
     **/
    private Circle getMonsterCircle() {
        Circle player = new Circle(25);
        player.setStyle("-fx-fill: black");
        return player;
    }

    /**
     * Switch the game to the score board stage.
     */
    private void goToScores() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/highScoreBoard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mazeGridPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Stage switched to the score board");
    }

    private void playerWon() {
        mazeGameModel.saveGameResult(playerName.textProperty().getValue(), "Nyert");
        goToScores();
        logger.info("The Player won");
    }

    private void playerLost() {
        mazeGameModel.saveGameResult(playerName.textProperty().getValue(), "Vesztett");
        goToScores();
        logger.info("The Player lost");
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        logger.info("The Player clicked to  row {}; col {}", row, col);
        if (mazeGameModel.isValidMove(mazeGameModel.getPlayerPos(), new Position(row, col)) && mazeGameModel.getCurrentTurn() == Turn.PLAYER) {
            movePlayer(new Position(row, col));
            if (mazeGameModel.isPlayerEscaped()) playerWon();
            else startMonsterTurn();
        }
    }
}