package io.rcw.bowling;

public enum Result {
    /**
     * A strike is when one hits all pins down. Represented by the character 'X'
     */
    STRIKE('X'),
    /**
     * A spare is when the pins left from the prior turn are knocked over. Represented by the character '/'
     */
    SPARE('/'),
    /**
     * A miss is when no pins are hit. Represented by the character '-'
     */
    MISS('-'),
    /**
     * A score is any other number of pins hit. In the enum constant, the character is presented by '0', but will
     * simply be the number of pins knocked down
     */
    SCORE('0');

    private char representation;

    Result(char c) {
        this.representation = c;
    }

    public char getRepresentation() {
        return representation;
    }
}
