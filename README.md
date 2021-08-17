Bowling Game
--

This is my repository for the bowling game coding assessment at High5Games.

### About 
This repository contains two components. A Gradle plugin which contains a task 
to generate test cases, according to the specification.

You can execute this gradle task through:
```bash
./gradlew generateTests --count=[number]
```

The count flag is optional and defaults to `10,000` test cases.

The second is the main code & unit tests which implement the necessary code.
###Objective
`Write a program to score a game of Ten-Pin Bowling.`
```
Input: string (described below) representing a bowling game

Ouput: integer score
```

### Rules

```
Each game, or "line" of bowling, includes ten turns,
or "frames" for the bowler.
In each frame, the bowler gets up to two tries to
knock down all ten pins.

If the first ball in a frame knocks down all ten pins,
this is called a "strike". The frame is over. The score
for the frame is ten plus the total of the pins knocked
down in the next two balls.

If the second ball in a frame knocks down all ten pins,
this is called a "spare". The frame is over. The score
for the frame is ten plus the number of pins knocked
down in the next ball.

If, after both balls, there is still at least one of the
ten pins standing the score for that frame is simply
the total number of pins knocked down in those two balls.

If you get a spare in the last (10th) frame you get one
more bonus ball. If you get a strike in the last (10th)
frame you get two more bonus balls.

These bonus throws are taken as part of the same turn.
If a bonus ball knocks down all the pins, the process
does not repeat. The bonus balls are only used to
calculate the score of the final frame.
```

### Syntax
```
X indicates a strike

/ indicates a spare

- indicates a miss

| indicates a frame boundary

The characters after the || indicate bonus balls
```