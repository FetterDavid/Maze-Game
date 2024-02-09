package mazegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the JavaFX Maze Game.
 */
public class MazeGameApplication extends Application {

    /**
     * Begins the JavaFX application, sets up the initial scene and shows the stage.
     *
     * @param stage Primary stage for the application.
     * @throws IOException If there's an error loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/maze.fxml"));
        stage.setTitle("JavaFX Maze Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
