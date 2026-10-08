# Lesson 3: Build a room with a door

**New capability: Programmatic Walls and Beepers**

> **Programmatic construction**
>
> The program can place walls and beeper stacks while it runs. Loops describe repeated layout. Keep a wall object reference when the game needs to remove that specific wall.

Maps need not be fixed before a game starts. Construct walls and beepers with
loops, then let the program open a door. Use coordinates and conditions you
already know to describe the world you want.

## Predict and run

Compile [RoomBuilder.java](../../examples/java/RoomBuilder.java). Sketch its 8-by-6
room, divider, and door. Predict what `frontIsClear()` reports before and after
the door is removed. Run the example: the first result is `false`; the player
then moves from `[4, 3]` to `[5, 3]`, picks up one beeper, and holds `1`.
Two beepers remain on that cell.

```java
Arena.addEastWall(4, 2);
Arena.addBeepers(5, 3, 3);
Wall door = new Wall(4, 3, Arena.VERTICAL);
Arena.getModel().addWall(door);
Arena.getModel().removeWall(door);
```

North/South/East/West wall calls describe a wall relative to a cell. Each adds
one segment. A loop builds a longer barrier. An east wall at `[4, 3]` separates
columns 4 and 5. `Arena.setSize` changes the visible grid; construct enclosing
walls explicitly.

Keep the `Wall` reference when you plan to remove it. Constructing another wall
with the same coordinates does not give you the same object. Model-level wall
changes appear on the next arena repaint; the following robot action supplies
one here. Do not implement a game loop inside a wall event callback.

## Exercises

1. Move the gap to another row. Change the player's starting location to match.
2. Keep the door closed until the player holds a key represented by a beeper.
3. Write a `buildRoom` method taking width and height, then build two room sizes.
4. Place food with `Arena.addBeeper` or supplies with `Arena.addBeepers`. Decide
   when a game should replenish them and express that rule in the main loop.

## Show what you learned

Demonstrate a door changing a sensor result. Explain the difference between a
wall coordinate and a robot location, and why removing the stored door object
works. Your route must check the correct sensor before moving.

Teacher prompt: ask which cell lies immediately west of an east wall. Project
extension: represent a maze with rows of characters and translate them into
unit wall segments; begin with a small hand-written maze before random generation.
