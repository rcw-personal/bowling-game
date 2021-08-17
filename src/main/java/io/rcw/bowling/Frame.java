package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private List<Turn> turns;

    public Frame() {
        this.turns = new ArrayList<>();
    }


    public void addTurn(Turn turn) {
        this.turns.add(turn);
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
