package mazegame.model;

import mazegame.utils.ScoreReaderWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model class for the high score board.
 */
public class HighScoreBoardModel {

    /**
     * The array containing the previous game scores.
     **/
    Score[] scores;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Constructor, read the saved score from json.
     */
    public HighScoreBoardModel() {
        ScoreReaderWriter readerWriter = new ScoreReaderWriter();
        scores = readerWriter.readScores();
        logger.info("Scores has been loaded.");
    }

    /**
     * @return the array containing the previous game scores reverse (new first).
     **/
    public Score[] getScores() {
        List<Score> list = Arrays.asList(scores);
        Collections.reverse(list);
        return list.toArray(new Score[0]);
    }
}
