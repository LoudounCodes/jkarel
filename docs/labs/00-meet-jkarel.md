# Lesson 0: Meet LoudounCodes JKarel

**New terminology: Arenas, Locations, and Directions**

> **Arena, Location, Direction**
>
> Arena is the shared world; Location is a grid position; Direction is a facing value. Arena methods operate on the scene. Robot methods act on one robot.

You already know Karel. Learn these names to read the new API and build
your own world and game rules.

## From a display to an arena

An arena names the place where robots, walls, and beepers interact.
`Arena.addNorthWall(4, 3)` reads as an action in that place;
`Arena.setPace(Pacing.SLOW)` names how its activity unfolds. These names express the model and make the API more fluent to read.
These methods do not support chained calls.

Compare a familiar opening with the new version:

```java
// Familiar FCPS-style opening:
// Display.openWorld("my-map.map");
// Display.setSpeed(5);
// Robot karel = new Robot(2, 2, Display.EAST, 2);

// JKarel opening:
Arena.openDefaultMap();
Arena.setPace(Pacing.FAST);
Robot karel = new Robot(2, 2, Direction.EAST, 2);
```

The changes to carry into your own code:

- Import `org.loudouncodes.jkarel` for these projects. Existing assignments that
  import `edu.fcps.karel2` still use their original library.
- Use `Arena.loadMap(name)` or `Arena.openDefaultMap()` to prepare a scene;
  `Arena.setSize(width, height)` sets the displayed grid.
- Direction is a `Direction`, not an integer. An arbitrary number cannot be
  passed as one. Choose a named `Pacing` value; there is no numeric speed
  conversion rule.
- `getLocation()`, `getX()`, and `getY()` describe a robot's grid position;
  `getDirection()` describes its facing.
- Infinite beepers use `BeeperStack.INFINITY`. Robots, walls, and stacks are
  kinds of `Item`; each describes how it is drawn.
- The normal robot verbs and sensors still work. Teach a subclass `turnRight`
  by calling `turnLeft` three times.
- `Arena` is the front door; `ArenaModel` holds the scene's state.
  `Arena.getModel()` exposes custom items and removable walls.

## Predict and run

Compile [WelcomeArena.java](../../examples/java/WelcomeArena.java) with the
[jGRASP setup](README.md). Trace its two moves on paper before you run it.
Expected output is location `[3, 3]`, direction `NORTH`, and inventory `1`.
The dropped beeper stays at `[2, 2]`.

Change the initial direction to `NORTH`. Predict the new endpoint before running.
Then change the pace to `Pacing.SLOW`. Explain why timing changes but the route
and inventory do not. Read the console if an action fails; this version reports
an exception at the failing line instead of quietly ending the program.

## Show what you learned

Write a five-line opening for your own project. Explain which calls affect the
arena and which affect one robot. Translate an opening from your own earlier
work into the new names, and explain why a direction and a location are different
kinds of information. Leave unrelated starter classes in their original package.

Teacher prompt: read an Arena call aloud as a sentence, then distinguish the
front door from the stored scene. Leave Swing internals for a later course.
