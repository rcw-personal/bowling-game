package io.rcw.bowling;

import java.util.ArrayList;
import java.util.List;

public final class GameParser {

    public static Game parse(String line) {
        List<Frame> frames = new ArrayList<>();

        Frame currentFrame = new Frame();
        Turn lastTurn = null;

        boolean bonus = false;

        for (int i = 0; i < line.length(); i++) {
            char at = line.charAt(i); // get the current character at the index i

            Result result;
            int pinsHit = 0;

            switch(at) {
                case 'X' -> {
                    result = Result.STRIKE;
                    pinsHit = 10;
                }
                case '/' -> {
                    result = Result.SPARE;
                    if (lastTurn != null) {
                        pinsHit = 10 - lastTurn.getPinsHit();
                    }
                }
                case '|' -> {
                    if (i+1<line.length() && line.charAt(i + 1) == '|') {
                        // bonus, stay in frame
                        bonus = true;
                        frames.add(currentFrame);
                    }

                    if (!bonus) {
                        // add the last frame
                        frames.add(currentFrame);
                        // create a new frame
                        currentFrame.setNext(currentFrame = new Frame());
                        lastTurn = null;
                    }
                    continue;
                }
                case '-' -> result = Result.MISS;
                default -> {
                    pinsHit = Integer.parseInt(String.valueOf(at));
                    result = Result.SCORE;
                }
            }


            currentFrame.addTurn(lastTurn = new Turn(result, pinsHit));

            System.out.println("frame size " +  currentFrame.turns().size());

        }

        return new Game(frames);
    }
}
