package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Frame {

    private static final Predicate<Turn> ANY = (turn) -> true;


    private Frame next;
    private List<Turn> turns;
    private boolean strike = false;
    private boolean spare = false;

    public Frame() {
        this.turns = new ArrayList<>();
    }


    public void setNext(Frame frame) {
        this.next = frame;
    }

    public Optional<Frame> next() {
        return Optional.ofNullable(next);
    }

    public void addTurn(Turn turn) {
        this.turns.add(turn);

        switch (turn.getResult()) {
            case STRIKE -> strike = true;
            case SPARE ->  spare = true;
        }
    }

    public boolean isSpare() {
        return spare;
    }

    public boolean isStrike() {
        return strike;
    }

    // Score returns the frames score
    public int score() {
        return this.score(ANY);
    }

    public int limitScore(int turnCount) {
        return turns.stream().limit(turnCount).mapToInt(Turn::getPinsHit).sum();
    }

    public int score(Predicate<Turn> filter) {
        return turns.stream().filter(filter).mapToInt(Turn::getPinsHit).sum();
    }

    /**
     * This returns the turns in this frame
     *
     * @return The turns in this frame
     */
    public List<Turn> turns() {
        return  turns;
    }
}
