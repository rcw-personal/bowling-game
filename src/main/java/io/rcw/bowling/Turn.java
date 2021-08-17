package io.rcw.bowling;

import java.util.Objects;

public class Turn {
    // The result from this turn
    private final Result result;
    private final int pinsHit;
    private boolean bonus; // is this a bonus turn?

    public Turn(Result result, int pinsHit) {
        this.result = result;
        this.pinsHit = pinsHit;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public Result getResult() {
        return result;
    }

    public int getPinsHit() {
        return pinsHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return pinsHit == turn.pinsHit && bonus == turn.bonus && result == turn.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, pinsHit, bonus);
    }


    @Override
    public String toString() {
        return "Turn{" +
                "result=" + result +
                ", pinsHit=" + pinsHit +
                ", bonus=" + bonus +
                '}';
    }
}
