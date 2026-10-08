# Lesson 2: Let the arena tell the scoreboard

**New concept: Listeners**

> **Listeners**
>
> A listener is an object registered to receive notifications. The arena calls its methods when actions happen. The listener observes those actions without taking over the game loop.

A game needs to count pickups and moves. Should every robot have to know about
the scoreboard? Use an interface to let a separate observer react to arena events.

Complete Team trails first. You should know fields, methods, conditionals, and
object references. This lab introduces an interface with default methods and the
observer pattern: one object performs an action; another receives a notification.

## Predict and run

Open [EventScoreboard.java](../../examples/java/EventScoreboard.java) using the
[jGRASP setup](README.md). Read `takeTurn` and `Scoreboard`. Predict the number
of pickup callbacks and move callbacks before running.

Expected console results progress through red/blue scores of 1/0, 1/1, 2/1, and
2/2. The final line is `Final: Red 2, Blue 2, moves 8`. Both robots finish at column
6; each holds two beepers. The main loop drives the turns. The scoreboard never
moves a robot.

```java
Arena.addListener(scores);
```

This registers an object implementing `ArenaListener`. Its default methods do
nothing, so the class overrides only the events it wants. `@Override` lets the
compiler check the method name and parameter type. For a pickup the library calls
`beeperPickedUp(Robot robot)` after updating the stack and inventory. Comparing
`robot == red` tests whether this is the exact red robot object; its appearance
could change while its identity stays the same.

## Experiments

1. Move one beeper farther down its lane. Predict which turn produces its pickup.
2. Count drops separately by overriding `beeperDropped`. Add a drop in `takeTurn`
   after a pickup. Watch the scores: decide whether a pickup of a previously
   dropped beeper should award another point in your game.
3. Add a third robot and track it explicitly. Color alone must not determine score.
4. Remove the listener before the final turn. Explain why the robot still moves
   but that action no longer changes the observed counters.

Choose a project use: count maze steps, track food pickups, record a move log, or
announce when a player has collected enough items. Write the win check in the
main game loop after taking a turn; the callback can update a flag or counter.

## Callback rules

- Callbacks run immediately on the thread that performs the action. Keep them
  short. Do not start another movement or a game loop from a callback.
- `robotMoved` reports completed forward/backward movement; turns have no event.
- A robot drop produces `beeperAdded` followed by `beeperDropped`. Use the latter
  to count player drops; counting both would count one action twice.
- `robotAdded` runs before the constructor returns. Register scoring after player
  creation, as this example does; do not depend on subclass fields in that event.
- `wallCollision` observes an attempted blocked move before an exception is thrown.
  Sensors are still the right way to plan safe movement.
- `mapLoaded` fires after loading. Map loading preserves existing robots and
  listeners while replacing walls, beepers, and custom items.
- There are no keyboard-input, timer, turn, or color-change callbacks yet.

The `finally` block removes this listener even if a robot action fails. It does
not reset the scoreboard, arena, or robots; those are separate responsibilities.

## Show what you learned

Demonstrate one new counter or announcement. Explain the difference between
calling a robot method and receiving a callback, why pickup sees the new inventory,
and why the game loop belongs outside the listener. Include the predicted and
observed callback counts for your change.

Teacher prompts: ask the student to trace one `move()` and one `pickBeeper()` into
their callbacks. Extra challenge: add a second listener that records a text log;
explain how it can coexist with scoring without modifying Robot.
