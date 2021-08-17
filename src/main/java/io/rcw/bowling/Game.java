package io.rcw.bowling;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        int score = 0;

        for (int i = 0; i < MAX_FRAMES; i++) {
            Frame frame = frames.get(i);
            final AtomicInteger localScore = new AtomicInteger(0);

            // The last frame is a simple sum of the pins knocked down.
            if (i == MAX_FRAMES - 1) {
                localScore.addAndGet(frame.score());
            } else {
                localScore.addAndGet(frame.score());

                if (frame.isStrike()) {
                    frame.next().ifPresent(next -> {
                        localScore.addAndGet(next.limitScore(2));

                        if (next.isStrike()) {
                            next.next().ifPresent(nextTwo -> {
                                if (nextTwo.isSpare()) {
                                    localScore.addAndGet(
                                            nextTwo.turns().get(0).getPinsHit());
                                } else {
                                    localScore.addAndGet(nextTwo.score(turn -> !turn.isBonus()));
                                }
                            });

                        }
                    });
                } else if (frame.isSpare()) {
                    frame.next().ifPresent((next) -> localScore.addAndGet(next.turns().get(0).getPinsHit()));
                }
            }

            score += localScore.get();
        }
        return score;
    }
}
