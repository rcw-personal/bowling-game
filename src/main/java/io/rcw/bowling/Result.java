package io.rcw.bowling;

public enum Result {
    /**
     * A strike is when one hits all pins down. Represented by the character 'X'
     */
    STRIKE,
    /**
     * A spare is when the pins left from the prior turn are knocked over. Represented by the character '/'
     */
    SPARE,
    /**
     * A miss is when no pins are hit. Represented by the character '-'
     */
    MISS,
    /**
     * A score is any other number of pins hit.
     */
    SCORE
}
