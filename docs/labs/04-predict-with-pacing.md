# Lesson 4: Predict an action with STEP pacing

Slow a program down to explain it. Pacing is a named choice that changes how long
the program waits between actions, while preserving the rules of the robot.

## Predict and run

Compile [PaceProbe.java](../../examples/java/PaceProbe.java). The robot is created
at `[3, 3]`, facing east, before the example switches to `Pacing.STEP`.

```java
Arena.setPace(Pacing.STEP);
robot.move();
robot.turnLeft();
robot.move();
```

For each action, predict location and direction. Focus the jGRASP run console
and press Enter when the program waits. STEP pauses after the action changes
state and requests a repaint, before that call returns. It does not wait before
the state change. The console traces are `[4, 3] EAST`, `[4, 3] NORTH`, and
`[4, 4] NORTH`.

Creation, color changes, and several arena operations also use pacing. Setting
STEP before constructing a robot can therefore pause in its constructor. For a
clear movement trace, finish setup first as this example does. STEP uses console
input, so do not combine it with another Scanner reading the same input stream.

## Experiments

1. Compare `SLOW`, `MEDIUM`, `FAST`, and `LUDICRUS` using the same three actions.
   `LUDICRUS` is the exact spelling in this library. Confirm that the endpoint is
   identical even when you cannot watch every frame.
2. Insert a beeper drop with an appropriate starting inventory. Predict the
   inventory at each pause.
3. Pause a short section of your own project, then restore `Pacing.FAST`.
   Use `Arena.getPace()` to report the current choice.

## Show what you learned

Submit a location/direction/inventory trace and demonstrate it in STEP mode.
Explain why reducing delay cannot repair a wrong algorithm. For unattended
graphical runs, this example accepts `--auto`; actual STEP behavior must be
checked interactively in the classroom.

Teacher extension: read `Pacing.java`. Each enum value provides the same `tick`
behavior with a different implementation. Name the strategy pattern only after
students can explain the observable difference.
