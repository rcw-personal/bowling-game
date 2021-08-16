package io.rcw.bowling.gradle.tasks;


import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class GenerateTests extends DefaultTask {

    private static class Turn {
        int pinsHit;
        char result;
    }

    private static class Frame {
        public List<Turn> turns = new ArrayList<>();

        public String simulate(int maxTurnCount, int pinCount, boolean bonus) {
            StringBuilder stringBuilder = new StringBuilder();
            int pins = pinCount;
            boolean open = true;
            for (int turns = 0; turns < maxTurnCount && (open || bonus); turns++) {
                Turn turn = new Turn();
                // nextInt is inclusive, exclusive. We wish to include
                // the numbers between 0 through 10. 0 being a miss,
                // 10 being a strike
                turn.pinsHit = RANDOM.nextInt(pins + 1);
                // subtract from pins
                pins -= turn.pinsHit;

                if (pins == 0) {
                    // the pins remaining is zero which means we either got a strike or spare
                    turn.result = turns == 0 ? STRIKE : SPARE;
                    open = bonus;
                    stringBuilder.append(turn.result);

                    if (bonus && turn.result == STRIKE) {
                        // reset the pins
                        pins = pinCount;
                    }
                } else if (turn.pinsHit == 0) {
                    // we hit zero pins which results in a miss
                    turn.result = MISS;
                    stringBuilder.append(MISS);
                } else {
                    // rather hacky/crude way of representing result
                    turn.result = Integer.valueOf(turn.pinsHit).toString().charAt(0);
                    stringBuilder.append(turn.pinsHit);
                }

                this.turns.add(turn); // add this turn
            }
            return stringBuilder.toString();
        }
    }

    private int count = 10_000; // default to 10,000


    @Option(option = "count", description = "Specifies the number of tests to generate. Default is 10,000")
    public void setCount(String count) {
        this.count = Integer.parseInt(count);
    }

    // strike, spare, miss characters

    private static final char STRIKE = 'X';
    private static final char MISS = '-';
    private static final char SPARE = '/';
    private static final char FRAME_BOUNDARY = '|';
    private static final Random RANDOM = new Random();

    @TaskAction
    public void generateTest() {
        System.out.printf("Generating %d test cases\n", count);
        File file = getProject().file("tests.txt");

        try (FileWriter writer = new FileWriter(file)) {

            IntStream.range(0, count).forEach((i) -> {
                final StringBuilder stringBuilder = new StringBuilder();
                // generate a random game
                int totalFrames = 10;

                for (int frameCount = 0; frameCount < totalFrames; frameCount++) {
                    Frame frame = new Frame();

                    // we could do like a frame.simulate(turnCount, pinCOunt);
                    stringBuilder.append(frame.simulate(2, 10, false));

                    stringBuilder.append(FRAME_BOUNDARY);

                    if (frameCount == totalFrames - 1) {
                        stringBuilder.append(FRAME_BOUNDARY); // append the last frame boundary

                        Turn lastTurn = frame.turns.get(frame.turns.size() - 1); // get the last turn for this final frame.

                        if (lastTurn.result == STRIKE) {
                            // the last result was a strike, two extra turns in the frame
                            stringBuilder.append(frame.simulate(2, 10, true));
                        } else if (lastTurn.result == SPARE) {
                            // they will get one turn
                            stringBuilder.append(frame.simulate(1, 10, true));
                        }

                    }
                }

                try {
                    writer.write(stringBuilder + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
