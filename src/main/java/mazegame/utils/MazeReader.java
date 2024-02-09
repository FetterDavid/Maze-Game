package mazegame.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import mazegame.model.Square;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Class for reading maze from json.
 */
public class MazeReader {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();

    /**
     * Read maze tiles from a json file and return an array of {@code Square}.
     *
     * @return array of {@code Square} representing the maze logically.
     **/
    public Square[] readMazeTiles() {
        try {
            return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("data/maze.json"), Square[].class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new Square[0];
    }
}
