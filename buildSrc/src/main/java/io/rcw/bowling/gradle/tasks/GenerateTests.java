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
        private int maxTurnCount;
        public boolean bonus;

        public Frame(int turnCount, boolean bonus) {
            this.maxTurnCount = turnCount;
            this.bonus = bonus;
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
                int turnCount = 2;
                boolean bonus = false;
                int totalFrames = 10;

               for (int frameCount = 0; frameCount < totalFrames; frameCount++) {
                    int pins = 10;
                    boolean open = true;

                    Frame frame = new Frame(turnCount, bonus);

                    for (int turns = 0; turns < frame.maxTurnCount; turns++) {
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
                            open = false;
                            stringBuilder.append(turn.result);
                        } else if (turn.pinsHit == 0) {
                            // we hit zero pins which results in a miss
                            turn.result = MISS;
                            stringBuilder.append(MISS);
                        } else {
                            // rather hacky/crude way of representing result
                            turn.result = Integer.valueOf(turn.pinsHit).toString().charAt(0);
                            stringBuilder.append(turn.pinsHit);
                        }

                        frame.turns.add(turn); // add this turn


                        if (frameCount==totalFrames-1 && turns==frame.maxTurnCount-1 && !bonus) {
                            System.out.println(turns + ", " + (frame.maxTurnCount-1) + ", " + turn.result);
                            if (turn.result == SPARE) {
                                frame.maxTurnCount += 1;
                                bonus = true; // we are now entering a bonus turn
                                open = true;
                                pins = 10;
                            } else if (turn.result == STRIKE) {
                                frame.maxTurnCount += 2;
                                open = true;
                                bonus = true; // we are now entering a bonus turn
                                pins = 10;
                            }

                            System.out.println(frame.maxTurnCount);
                            stringBuilder.append("||");
                        } else if (!open) {
                            break;
                        }
                    }

                    if (frameCount<totalFrames-1) {
                        stringBuilder.append(FRAME_BOUNDARY);
                    }

//                    if (frameCount == totalFrames && !frame.bonus) {
//                        // it seems like you append this last one, regardless of
//                        // a bonus turn or not
//                        stringBuilder.append(FRAME_BOUNDARY);
//                        Frame peeked = lastResults.peek();
//                        if (peeked != null) {
//                            System.out.println(peeked.turns.get(peeked.turns.size() - 1).result);
//                            switch (peeked.turns.get(peeked.turns.size() - 1).result) {
//                                case SPARE:
//                                    totalFrames += 1;
//                                    turnCount = 1;
//                                    bonus = true;
//                                    break;
//                                case STRIKE:
//                                    totalFrames+=1;
//                                    System.out.println("strike");
//                                    turnCount+=1;
//                                    bonus = true;
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                    }
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
