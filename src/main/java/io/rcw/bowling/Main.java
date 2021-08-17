package io.rcw.bowling;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger("Bowling Game");

    public static void main(String[] args) {
        //Read from STDIN
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            try {
                Game game = GameParser.parse(line);
                if (game.isValid()) {
                    System.out.println(game.calculateScore());
                }
            } catch (GameParseException e) {
                // ignore with invalid line
                if (line.isBlank() || line.isEmpty() || line.charAt(0) == 0x00) {
                    break;
                }
                System.out.println("Invalid game.");
            }
        }
    }

}
