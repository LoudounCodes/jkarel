# Lesson 1: Team trails

How could a player tell which team owns a trail? Build a small arena with two
colored robots, then use the colors and walls in a project of your choice.

You should already be comfortable constructing a Robot, calling its methods,
using loops, and checking a sensor. After this lab you should be able to create
walls in code and explain how a robot's color affects its dropped beepers.

## Predict, run, inspect

Open [TeamTrails.java](../../examples/java/TeamTrails.java) using the
[jGRASP setup](README.md). Before running, sketch the grid. Predict the robots'
final locations, their remaining inventories, and the colors of the beepers.
Run it and compare with your sketch.

Expected result: a red trail on row 2 and a blue trail on row 4, each covering
columns 2 through 6. Both robots end in column 6, with no beepers left. A wall
separates the lanes. The program builds its own outer boundary.

`Color` comes from `java.awt`. Use `Color.RED`, `Color.BLUE`, or
`new Color(80, 160, 220)`; each red/green/blue component is an integer from 0 to 255.

```java
robot.setColor(Color.BLUE);
robot.putBeeper();
```

The drop colors the **whole stack** at that location. If a red robot later drops
onto a blue stack, the stack becomes red. Picking up a beeper preserves the color
of the remaining stack. Picking it up does not change the robot's color.

`Arena.addNorthWall(x, y)` places a unit wall immediately north of that cell.
The East, South, and West methods work the same way. Adjacent calls can build a
longer wall. `Arena.setSize` changes the displayed grid; it does not enclose it
with a full boundary. Build boundaries explicitly, as this example does.

## Make it yours

1. Change both team colors. Explain why you need an import for `Color`.
2. Change a robot's color halfway through its trail. Predict which beepers change.
3. Add a second divider with a gap. Write a robot route through the gap using
   turns and sensors; keep the route inside the boundary.
4. Have the robots meet and drop on the same cell. Test the whole-stack color rule.

Choose one extension for your project: colored territory, breadcrumbs through a
maze, team bases, or a drawing made by several robots. Color is appearance, not a
team identifier: keep a separate variable or robot reference for game rules.

## Show what you learned

Show your arena and the code that builds it. Explain a north-wall coordinate,
why a move is safe, and the result of two different colored drops on one cell.
Give a prediction and observation for one change you made. Your program should
finish its planned route without a wall exception or an empty-inventory drop.

Teacher prompts: ask why the loop avoids moving after the final drop; have a
student point to the wall that a left-facing sensor would detect. Extra challenge:
extract a reusable `buildRoom` method that takes a location and dimensions.
