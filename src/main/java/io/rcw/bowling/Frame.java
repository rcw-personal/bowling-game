package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Frame represents a frame in a bowling game. It contains a set of turns made during this frame,
 * and handles the summation of the frames through the {@link Frame#score()} methods
 */
public class Frame {
    /**
     * Filter for turns. This serves as a catch-all, want all the turns
     */
    public static final Predicate<Turn> ANY = (turn) -> true;

    /**
     * This filters out any bonus turns.
     */
    public static final Predicate<Turn> NO_BONUS = (turn) -> !turn.isBonus();

    private Frame next;
    private final List<Turn> turns;
    private boolean strike = false;
    private boolean spare = false;

    public Frame() {
        this.turns = new ArrayList<>();
    }

    /**
     * Provides a series of checks to check if this frame is valid.
     * Realistically anything could be passed in and result in an invalid frame.
     * Such as, `-X`. A human or programming error, could mismatch a spare with a strike.
     *
     * Or add more scores, outside the bonus.
     *
     * @return True if this frame is valid.
     */
    public boolean check() {
        // Only the final frame should have a bonus frame.
        if (this.next != null && this.turns.size() > 2) {
            return false;
        }

        // If it is a strike and not the last frame, it is invalid, because a strike frame should only have a single turn
        if (this.next != null && this.isStrike() && this.turns.size() > 1) {
            return false;
        }

        return true;
    }

    /**
     * Sets the next frame for ease of use. Almost like a linked list/chain. Frame->Frame.
     * No previous references.
     *
     * @param frame The next frame in the game
     */
    public void setNext(Frame frame) {
        this.next = frame;
    }

    /**
     * Gets an optional of the next frame. The next frame can be optional if this is the last
     * frame in the game.
     *
     * @return Optional of the next frame
     */
    public Optional<Frame> next() {
        return Optional.ofNullable(next);
    }

    /**
     * Adds a turn to this frame. Upon adding the turn, it checks if the turn resulted in a strike
     * or a spare.
     *
     * If so, it will mark this frame as a strike or spare frame.
     *
     * @param turn The turn to add to this frame
     */
    public void addTurn(Turn turn) {
        this.turns.add(turn);

        switch (turn.getResult()) {
            case STRIKE -> strike = true;
            case SPARE ->  spare = true;
        }
    }

    /**
     * @return True if this frame ended with a spare
     */
    public boolean isSpare() {
        return spare;
    }

    /**
     * @return True if this frame was a strike
     */
    public boolean isStrike() {
        return strike;
    }

    /**
     * This calculates the frames score which is to be added to the total.
     *
     * @return The frames score
     */
    public int score() {
        return this.score(ANY);
    }

    /**
     * Only take the sum of {@param turnCount} number of turns.
     *
     * @param turnCount The number of turns to sum
     *
     * @return The sum for the turns
     */
    public int limitScore(int turnCount) {
        return turns.stream().limit(turnCount).mapToInt(Turn::getPinsHit).sum();
    }

    /**
     * Takes the sums of all turns that match the applied filter.
     *
     * @param filter The filter to apply
     *
     * @return The sum of filtered turns
     */
    public int score(Predicate<Turn> filter) {
        return turns.stream().filter(filter).mapToInt(Turn::getPinsHit).sum();
    }

    /**
     * This returns the turns in this frame
     *
     * @return The turns in this frame
     */
    public List<Turn> turns() {
        return turns;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "next=" + next +
                ", turns=" + turns +
                ", strike=" + strike +
                ", spare=" + spare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return strike == frame.strike && spare == frame.spare && Objects.equals(next, frame.next) && Objects.equals(turns, frame.turns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, turns, strike, spare);
    }
}
