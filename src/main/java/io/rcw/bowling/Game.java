package io.rcw.bowling;

import java.util.List;

/**
 * Game represents a played game.
 */
public final class Game {
    private static final int TEN_PINS = 10;
    private static final int MAX_SCORE = 300;

    private static final int MAX_FRAMES = 10;


    // These are the list
    private final List<Frame> frames;

    public Game(List<Frame> frames) {
        this.frames = frames;
    }

    public int calculateScore() {
        if (frames.stream().allMatch(Frame::isStrike)) {
            return  MAX_SCORE;
        }

        int score = 0;

        for (int i = 0; i < MAX_FRAMES; i++) {
            Frame frame = frames.get(i);

            int localScore = frame.score();

            if (frame.isStrike() || frame.isSpare() && i == MAX_FRAMES-1) {
                if (frame.isStrike()) {
                    localScore += frame.score() - TEN_PINS;
                }
            }
            if (i > 0) {
                Frame previous = this.frames.get(i - 1);
                if (previous.isSpare()) {
                    localScore += frame.turns().get(0).getPinsHit();
                } else if (previous.isStrike()) {
                    localScore += frame.score();
                }
            }

            score += localScore;

            System.out.println("Frame: " + (i + 1) + ", frame score: " + localScore + ", current score: " + score);
        }
        return score;
    }
}
