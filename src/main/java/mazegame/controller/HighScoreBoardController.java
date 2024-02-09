package mazegame.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mazegame.model.HighScoreBoardModel;
import mazegame.model.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The {@code HighScoreBoardController} class handles user interactions in the score board.
 * It reflects changes in the view.
 */
public class HighScoreBoardController {
    @FXML
    private TableView<Score> table;

    @FXML
    private TableColumn<Score, String> name;

    @FXML
    private TableColumn<Score, Integer> steps;

    @FXML
    private TableColumn<Score, String> win;

    @FXML
    private TableColumn<Score, String> date;

    @FXML
    private Label gameResultLabel;


    private HighScoreBoardModel model;
    private static final Logger logger = LogManager.getLogger();


    @FXML
    private void initialize() {
        model = new HighScoreBoardModel();
        name.setCellValueFactory(new PropertyValueFactory<Score, String>("name"));
        steps.setCellValueFactory(new PropertyValueFactory<Score, Integer>("steps"));
        win.setCellValueFactory(new PropertyValueFactory<Score, String>("win"));
        date.setCellValueFactory(new PropertyValueFactory<Score, String>("date"));
        table.setItems(FXCollections.observableArrayList(model.getScores()));
        setGameResultLabel();
    }

    @FXML
    private void goBackToMaze(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/maze.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        logger.info("Stage switched back to the maze");
    }

    /**
     * Set the game result text according the result of the previous game result.
     */
    private void setGameResultLabel() {
        if (model.getScores()[model.getScores().length - 1].getWin().equals("Nyert"))
            gameResultLabel.setText("Gratulák! Sikerült kijutni a labirintusból.");
        else gameResultLabel.setText("Vesztettél! Elkapott a szörny.");
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        logger.info("App has been closed.");
    }
}
