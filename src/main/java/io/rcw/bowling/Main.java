package io.rcw.bowling;

public class Main {
    public static void main(String[] args) {
        final String test = "X|11|36|45|X|71|4/|36|7/|45||"; // expect 103

        Game game = GameParser.parse(test);

        System.out.println(game.calculateScore(-1));
    }
}
