package io.rcw.bowling;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Game represents a played game.
 */
public final class Game {

    /**
     * The max number of frames a game could have
     */
    private static final int MAX_FRAMES = 10;

    // These are the list of frames for the game
    private final List<Frame> frames;

    public Game(List<Frame> frames) {
        this.frames = frames;
    }


    /**
     * Returns if the game is valid or not. For sake of simplicity, I consider 10 FRAMES to be valid.
     *
     * @return if the game is valid
     */
    public boolean isValid() {
        return frames.size() == MAX_FRAMES;
    }

    /**
     * Gets the list of frames within the game
     *
     * @return the frames from the game
     */
    public List<Frame> getFrames() {
        return frames;
    }


    /**
     * Calculates the score of this game according to the rules of Ten Pin bowling
     *
     * @return The calculated score
     */
    public int calculateScore() {
        int score = 0;

        for (int i = 0; i < MAX_FRAMES; i++) {
            Frame frame = frames.get(i);
            // Start with a local score that we add to, this would be the frames total score
            final AtomicInteger localScore = new AtomicInteger(frame.score());

            // Check if this frame is a strike
            if (frame.isStrike()) {
                // Get the next frame, if available
                frame.next().ifPresent(next -> {
                    // Add to the score the next two scores.
                    // If it is a strike, it will only have 1 turn, but it will still work
                    // since 2 is the max limit
                    localScore.addAndGet(next.limitScore(2));

                    // If the next frame is also a strike, we must calculate it accordingly
                    if (next.isStrike()) {
                        // Again, check if next's next frame is present
                        next.next().ifPresent(nextTwo -> {
                            // If this is a spare, calculate as a spare, add only the next
                            if (nextTwo.isSpare()) {
                                localScore.addAndGet(
                                        nextTwo.turns().get(0).getPinsHit());
                            } else {
                                // Add the score normally, without any bonus frames though.
                                localScore.addAndGet(nextTwo.score(Frame.NO_BONUS));
                            }
                        });

                    }
                });
            } else if (frame.isSpare()) {
                // Fhe frame was a spare, so add the next's frames first turn/throw
                frame.next().ifPresent((next) -> localScore.addAndGet(next.turns().get(0).getPinsHit()));
            }

            // Finally, compound the current score, with our local
            score += localScore.get();
        }
        return score;
    }

    @Override
    public String toString() {
        return "Game{" +
                "frames=" + frames +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(frames, game.frames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frames);
    }
}
