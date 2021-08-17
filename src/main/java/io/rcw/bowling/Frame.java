package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private List<Turn> turns;
    private boolean strike = false;
    private boolean spare = false;

    public Frame() {
        this.turns = new ArrayList<>();
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
        return turns.stream().mapToInt(Turn::getPinsHit).sum();
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
