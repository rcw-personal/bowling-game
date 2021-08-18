package io.rcw.bowling;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    /**
     * Entry point for this application.
     *
     * This is a really simple command line application that takes input from STDIN
     * (through commands like `cat` or `echo` on Unix for example)
     * expecting the game format specified.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        // Read from STDIN
        final Scanner scanner = new Scanner(new InputStreamReader(System.in));

        // While scanner has lines
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();

            try {
                // Create and parse a game from the line
                final Game game = GameParser.parse(line);

                // check if the game is valid/full
                if (game.isValid()) {
                    // Don't bother calculating if we find any frames we deem invalid.
                    if (game.getFrames().stream().anyMatch(frame -> !frame.check())) {
                        // Ignore games with invalid frames
                        continue;
                    }
                    // Print to standard out the score of this line
                    System.out.println(game.calculateScore());
                }
            } catch (GameParseException ignored) {
                // Ignore with invalid line
            }
        }
    }

}
