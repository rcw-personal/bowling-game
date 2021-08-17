package io.rcw.bowling.tests;

import io.rcw.bowling.Game;
import io.rcw.bowling.GameParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tests {

    private static final int ALL_STRIKE_SCORE = 300;
    private static final int ALL_FIVE_SPARES = 150;
    private static final int ALL_NINES = 90;

    private static final int PROVIDED_EXAMPLE_GAME = 167;

    // These are known games mapped to their expected scores
    private static final Map<String, Integer> GAMES = new HashMap<>() {{
        put("X|11|36|45|X|71|4/|36|7/|45||", 103);
        put("X|7/|9-|X|-8|8/|-6|X|X|9/||8", 157);
        put("8/|54|9-|X|X|5/|53|63|9/|9/||X", 149);
    }};


    /**
     * Test entry for games I have generated, verifying the scores with online bowling calculators
     * (which I guess assumes those were also correct)
     */
    @Test
    public void myGames() {
        for (Map.Entry<String, Integer> test : GAMES.entrySet()) {
            final Game game = GameParser.parse(test.getKey());
            final int score = game.calculateScore();

            Assertions.assertEquals(score, test.getValue());
        }
    }

    /**
     * Test entry for all strike game
     */
    @Test
    public void allStrikeTest() {
        final Game game = GameParser.parse("X|X|X|X|X|X|X|X|X|X||XX");
        Assertions.assertEquals(game.calculateScore(), ALL_STRIKE_SCORE);
    }

    /**
     * Test entry for all five spares game.
     */
    @Test
    public void fiveSpares() {
        final Game game = GameParser.parse("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5");
        Assertions.assertEquals(game.calculateScore(), ALL_FIVE_SPARES);
    }

    /**
     * Test entry for all nines
     */
    @Test
    public void allNines() {
        final Game game = GameParser.parse("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-|");
        Assertions.assertEquals(game.calculateScore(), ALL_NINES);
    }

    /**
     * Test entry for the game provided in the word document sheet
     */
    @Test
    public void providedGame() {
        final Game game = GameParser.parse("X|7/|9-|X|-8|8/|-6|X|X|X||81");
        Assertions.assertEquals(game.calculateScore(), PROVIDED_EXAMPLE_GAME);
    }

}
