package io.rcw.bowling;

public class Main {
    public static void main(String[] args) {
//        final String test = "X|11|36|45|X|71|4/|36|7/|45||"; // expect 103, PASSES
        final String test = "X|7/|9-|X|-8|8/|-6|X|X|X||81"; // expect 167, PASSES
//        final String test = "X|7/|9-|X|-8|8/|-6|X|X|9/||8"; // expect 157, FAILED
//        final String test = "X|X|X|X|X|X|X|X|X|X||XX"; // expect 300, FAILED,
//        final String test = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5"; // expect 150, PASSES,
//        final String test ="8/|54|9-|X|X|5/|53|63|9/|9/||X"; // expect 149, PASSES
        Game game = GameParser.parse(test);

        System.out.println(game.calculateScore());
    }
}
