package io.rcw.bowling;

public class Main {
    public static void main(String[] args) {
//        final String test = "X|11|36|45|X|71|4/|36|7/|45||"; // expect 103
//        final String test = "X|7/|9-|X|-8|8/|-6|X|X|X||81"; // expect 167
        final String test = "X|7/|9-|X|-8|8/|-6|X|X|9/||8"; // expect 167
        Game game = GameParser.parse(test);

        System.out.println(game.calculateScore());
    }
}
