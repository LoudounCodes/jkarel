# Lesson 6: Change levels while keeping the player

**New capability: Map Reloading**

> **Map reloading**
>
> Loading replaces the map contents while keeping existing robots and listeners. Player position, facing, inventory, and your score variables survive. A level transition must decide which state to reset.

Move from one map to another during a program. First decide which state belongs
to the map and which belongs to the player.

## Set up and run

The starter folder already contains `MapStages.java`, `stage-one.map`, and
`stage-two.map`. To run the complete reference separately, copy
[MapStages.java](../../examples/java/MapStages.java) from `examples/java` and both
maps from `examples/maps` into the same working folder. In jGRASP, compile and
run the Java file from that folder. These maps were written
for this lab; they are small XML files you can read and edit.

```java
Arena.loadMap("stage-one.map");
// Take a turn using the current map.
Arena.loadMap("stage-two.map");
```

The example uses the current working folder by default. From the repository root,
run it with the argument `examples/maps` to select the supplied map folder.
If a map is missing, the library warns in the console and uses its default map;
check the warning rather than assuming that an empty scene is your intended level.

Expected results: the player collects a beeper in level one. After level two
loads, that same player remains at `[3, 2]`, facing east, holding `1`.
A wall in the new map blocks its front. Loading replaces walls, beeper stacks,
and custom items, but preserves existing robots and listeners. It does not reset
player position, facing, inventory, or your score variables.

For a map already packaged in the JAR, `Arena.loadMap(Arena.DEFAULT_MAP)`
selects the built-in default by its resource name. Local maps use file paths;
this example keeps them beside the Java file for a predictable student setup.

The outer XML tag is still `world` for file compatibility. A wall's `length`
expands into that many unit segments. Horizontal segments advance along x;
vertical segments advance along y. You do not need a second XML library to load
these files.

## Experiments

1. Change level one's wall length. Predict the affected cells and check sensors.
2. Change a beeper count. Verify what is collected before the level transition.
3. Add a robot tag and explain why the new robot appears alongside the player.
4. Design a level-transition method that chooses explicitly whether to keep the
   player or remove it with `explode()` and construct a replacement.

## Show what you learned

List the state that survives a load and the state that is replaced. Explain
where your two files live and how the program finds them. Show that a new wall
can affect an existing robot; avoid spawning it inside a wall layout you have
not checked.

Teacher prompt: distinguish the map name requested by `mapLoaded` from proof
that a missing-file fallback did not occur. Do not teach an automatic reset rule
that the current implementation does not provide.
