package io.rcw.bowling;

import java.util.List;
import java.util.Stack;

/**
 * Game represents a played game.
 */
public final class Game {
    // These are the list
    private final List<Frame> frames;

    public Game(List<Frame> frames) {
        this.frames = frames;
    }

    public int calculateScore(int numberOfFrames) {
        if (numberOfFrames == -1) {
            numberOfFrames = frames.size();
        }

        int score = 0;
        int lastStrike = 0;
        Turn lastTurn = null;
        for (int i = 0; i < numberOfFrames; i++) {
            Frame frame = frames.get(i);
            int localScore = 0;

            for (Turn turn : frame.turns()) {
                if (turn.getResult() == Result.STRIKE) {

                } else if (turn.getResult() == Result.SPARE) {
                    localScore = 10;
                } else if (turn.getResult() == Result.SCORE) {
                    localScore += turn.getPinsHit();
                }
            }

            score += localScore;

            System.out.println("Frame: " + (i + 1) + ", frame score: " + localScore + ", current score: " + score);
            lastTurn = frame.turns().get(frame.turns().size() - 1);
        }
        return score;
    }
}
