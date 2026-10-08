# Lesson 5: Directions, sensors, and a retreating scout

A robot can face one direction while checking another. Extend Robot with a
retreat method and use typed directions to reason about its options.

## Predict and run

Compile [ScoutMoves.java](../../examples/java/ScoutMoves.java). Its `Scout` subclass
starts at `[3, 3]`, facing north. A wall is immediately west of it. Predict its
left sensor, planned facing, and final position.

```java
public void turnRight() {
    turnLeft(); turnLeft(); turnLeft();
}
public void retreat() {
    if (backIsClear()) backUp();
}
```

Expected results: left clear is `false`; the planned direction is `EAST`.
After turning right, advancing, and retreating, the scout returns to `[3, 3]`
and still faces east. Backing up changes position without turning.

`backUp()` is protected: a Robot subclass can call it, but the main program
cannot call `robot.backUp()` on an ordinary Robot. The subclass exposes a
meaningful operation, `retreat`, and checks its precondition. Public side sensors
are available on any Robot. Each tests its own side relative to the current facing.

```java
Direction planned = scout.getDirection().right();
```

This computes a direction value. It does not turn the scout. `left()`, `right()`,
and `behind()` describe relations between enum values. `turnLeft()` is an action
on the robot. Avoid integer arithmetic on directions or assumptions about their
numeric order.

## Make it yours

1. Block the cell behind the scout and show that `retreat` leaves it in place.
2. Give the scout a `turnAround` method using two left turns.
3. Write `chooseExit` to inspect sensors and return a Direction. Keep choosing
   separate from the method that performs the actual turns and movement.
4. Use `nextToRobot(other)` for a meeting in a cooperative game. It means sharing
   a cell, not occupying neighboring cells; robots can share a location.

## Show what you learned

Explain public versus protected with your own subclass. Demonstrate one method
that computes an answer and another that changes the robot. Show safe movement
when the front and back have different wall conditions.

Teacher prompt: rotate the paper sketch without changing the world walls and
ask which relative sensor now detects the same wall.
