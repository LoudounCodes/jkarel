# Lesson 7: Draw a goal and a different player

Your game might need a target, a treasure marker, or a different-looking player.
Give an object responsibility for drawing itself while keeping game decisions
in the main loop.

## Predict and run

Compile [CustomItems.java](../../examples/java/CustomItems.java). A goal at `[4, 2]`
is drawn as an outlined green target. A Robot subclass draws itself as a small
blue square. After two eastward moves from `[2, 2]`, the player reaches the goal;
the main loop removes it and prints `Reached the goal at [4, 2]`.
Use slow or STEP pacing to watch the target before it disappears.

```java
private static class Goal extends Item {
    Goal(int x, int y) { super(x, y); }
    public void render(Graphics g, int x, int y) {
        g.drawOval(x - 12, y - 12, 24, 24);
    }
}
```

`Item` is abstract. Its `render` method is the drawing contract, and your subclass
must supply an implementation. Register a completed goal with
`Arena.getModel().addUserItem(goal)` and remove it with `removeUserItem(goal)`.
A custom item is decorative until you write its behavior: a Goal does not become
a wall, beeper, or collision automatically.

The `x` and `y` passed to `render` are pixel coordinates chosen by the arena.
The Item's `getX()` and `getY()` are grid coordinates. Draw relative to the passed
pixels. Rendering may happen many times on Swing's painting thread: do not move
robots, change scores, pause, or modify the arena inside `render`.

Compare grid positions with `player.getLocation().equals(goal.getLocation())`.
Using `==` would ask whether two references identify the same Location object.
Read locations for decisions; use a subclass's protected `updatePosition` method
when designing an item that can relocate, rather than changing another object's
returned Location.

## Make it yours

1. Change the outline or marker size. Show that appearance does not change location.
2. Keep the goal after the player arrives and make the main loop announce it once.
3. Override the player's drawing with a circle, triangle, or shield. Keep robot
   movement and sensors working. A custom render override must decide how to show
   direction; it does not receive the default robot icon automatically.
4. Track additions and removals in a listener. The existing `userItemDropped`
   callback name means removal of a custom item.

## Show what you learned

Explain abstract Item versus concrete Goal, pixel versus grid coordinates, and
rendering versus a game rule. Demonstrate one custom item and one custom robot
appearance without introducing side effects in rendering.

Teacher extension: ask what a treasure interface would promise to the game.
Start with a small behavior such as an integer point value; do not make every
visual item responsible for the entire game loop.
