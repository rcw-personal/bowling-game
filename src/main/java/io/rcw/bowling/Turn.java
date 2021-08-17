package io.rcw.bowling;

public class Turn {
    // The result from this turn
    private final Result result;
    private final int pinsHit;

    public Turn(Result result, int pinsHit) {
        this.result = result;
        this.pinsHit = pinsHit;
    }

    public Result getResult() {
        return result;
    }

    public int getPinsHit() {
        return pinsHit;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "result=" + result +
                ", pinsHit=" + pinsHit +
                '}';
    }
}
