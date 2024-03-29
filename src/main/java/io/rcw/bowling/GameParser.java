package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;

public final class GameParser {

    /**
     * This function parses the specified game format provided into a Game object we can interact with.
     *
     * The format specified follows these rules:
     *
     * | = a frame boundary
     * || = a bonus frame boundaries
     * / = a spare
     * X = a strike
     * - = a miss
     * 1-9 = regular scores, parsed as integers.
     *
     * It is parsed in a linear manner
     *
     * @param line The string to parse
     * @return A Game that we may use to calculate the score, check validity of a game or frame within the game.
     *
     * @throws GameParseException if we come across an invalid character, like 'A', which cannot be parsed
     */
    public static Game parse(String line) throws GameParseException {
        List<Frame> frames = new ArrayList<>();

        Frame currentFrame = new Frame();
        Turn lastTurn = null;

        boolean bonus = false;

        for (int i = 0; i < line.length(); i++) {
            char at = line.charAt(i); // Get the current character at the index i

            Result result;
            int pinsHit = 0;

            switch(at) {
                case 'X' -> {
                    result = Result.STRIKE;
                    pinsHit = 10;
                }
                case '/' -> {
                    result = Result.SPARE;
                    if (lastTurn != null) {
                        pinsHit = 10 - lastTurn.getPinsHit();
                    }
                }
                case '|' -> {
                    if (i+1<line.length() && line.charAt(i + 1) == '|') {
                        // bonus, stay in frame
                        bonus = true;
                        frames.add(currentFrame);
                    }

                    if (!bonus) {
                        // add the last frame
                        frames.add(currentFrame);
                        // create a new frame
                        currentFrame.setNext(currentFrame = new Frame());
                        lastTurn = null;
                    }
                    continue;
                }
                case '-' -> result = Result.MISS;
                default -> {
                    try {
                        pinsHit = Integer.parseInt(String.valueOf(at));
                        result = Result.SCORE;
                    } catch (NumberFormatException exception) {
                        throw new GameParseException("Invalid character while parsing.");
                    }
                }
            }


            lastTurn = new Turn(result, pinsHit);
            lastTurn.setBonus(bonus);
            currentFrame.addTurn(lastTurn);
        }

        return new Game(frames);
    }
}
