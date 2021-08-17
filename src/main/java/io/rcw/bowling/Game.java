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
        if (frames.stream().allMatch(Frame::isStrike)) {
            return MAX_SCORE;
        }

        int score = 0;

        for (int i = 0; i < MAX_FRAMES; i++) {
            Frame frame = frames.get(i);
            final AtomicInteger localScore = new AtomicInteger(0);

            // The last frame is a simple sum of the pins knocked down.
            if (i == MAX_FRAMES-1) {
                localScore.addAndGet(frame.score());
            } else {
                localScore.addAndGet(frame.score());

                if (frame.isStrike()) {
                    frame.next().ifPresent(next -> {
                            localScore.addAndGet(next.limitScore(2));


                            // TODO: OK. so basically, we need to be able to know if a turn
                            // is a bonus turn or not and filter them out. That should solve the rest of the issues


                    });
                } else if (frame.isSpare()) {
                    frame.next().ifPresent((next) -> localScore.addAndGet(next.turns().get(0).getPinsHit()));
                }
            }

            System.out.println("Current frame: " + localScore.get());
//            if (frame.next() != null) {
//                if (frame.isStrike()) {
//                    Frame next = frame.next();
//                    localScore += next.score();
//
//                    if (next.next() != null) {
//                        localScore += next.next().score();
//                    }
//
//                } else if (frame.isSpare()) {
//                    localScore += frame.next().turns().get(0).getPinsHit();
//                }
//            }

            score += localScore.get();

            System.out.println("Frame: " + (i + 1) + ", frame score: " + localScore + ", current score: " + score);
        }
        return score;
    }
}
