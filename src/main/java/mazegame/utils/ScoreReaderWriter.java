package mazegame.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import mazegame.model.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Class for reading and writing {@code Score} objects from json file.
 */
public class ScoreReaderWriter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();

    /**
     * Read {@code Score} objects from a json file and return an array.
     *
     * @return an array of {@code Score} representing previous game results.
     **/
    public Score[] readScores() {
        try {
            return objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("data/score.json"), Score[].class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new Score[0];
    }

    /**
     * Write {@code Score} objects to a json file.
     *
     * @param scores array of {@code Score} representing game results.
     **/
    public void writeScores(Score[] scores) {
        try {
            ;
            URL resourceUrl = getClass().getClassLoader().getResource("data/score.json");
            File file = new File(resourceUrl.toURI());
            objectMapper.writeValue(file, scores);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
