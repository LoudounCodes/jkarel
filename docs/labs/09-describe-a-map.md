# Lesson 9: An interface that describes a map

**New concept: MapDataSource Interfaces (Design Exercise)**

> **Data-source interfaces**
>
> An interface specifies what information an object supplies. Different implementations can describe maps. Here a student-written adapter installs that description; the library does not yet load MapDataSource objects directly.

Advanced design extension. You know how to build an arena and observe its events.
Now separate a description of a map from the code that installs it.

## Read the contract

`MapDataSource` declares four methods: width, height, beeper stacks, and walls.
An implementation could describe a fixed room or a generated maze. A caller
can use the interface without needing to know how that description was produced.

This is a design seam in the current library. `Arena.loadMap` accepts a String;
there is no `Arena.loadMap(MapDataSource)` overload, and the XML loader is not
wired through this interface. This exercise supplies its own small adapter,
using operations you have already practiced. It does not change the library.

## Predict and run

Compile [DescribeAMap.java](../../examples/java/DescribeAMap.java). `TrainingMap`
implements the interface, while `install` accepts any MapDataSource.

```java
private static void install(MapDataSource source) {
    Arena.openDefaultMap();
    Arena.setSize(source.getWidth(), source.getHeight());
    for (Wall wall : source.getWalls())
        Arena.getModel().addWall(wall);
    for (BeeperStack stack : source.getBeepers().values())
        Arena.getModel().putBeepers(stack.getLocation(),
            stack.getBeepers(), stack.getColor());
}
```

Expected result: an 8-by-6 map with one north wall at `[3, 3]` and two beepers at
`[4, 2]`. The player walks there and collects both, ending with inventory `2`.
Setup finishes before the player is constructed. The adapter's map reset has
the same robot-preservation semantics as the map-loading lesson.

## Make a second implementation

1. Write `SupplyRoom implements MapDataSource` with a different size and layout.
   Install it using the same method; do not add a second installation method.
2. Explain the `Map<Location, BeeperStack>` and `List<Wall>` return types. The Map
   is a Java collection indexed by locations; it is not the whole game map.
3. Choose a policy for returned collections: newly built descriptions, snapshots,
   or read-only views. Explain whether callers can change your source accidentally.
4. Sketch a future library overload and its tests: colored stacks, long walls,
   existing robots, and listener notifications. Discuss before implementing it.

## Show what you learned

Demonstrate two map-source implementations using one adapter. Explain an interface
promise, an implementation choice, and an adapter responsibility. Identify exactly
which part is supported by the current library and which part you wrote.

Teacher prompt: compare this interface, which is queried for data, with
ArenaListener, whose methods receive notifications. Keep the initial example
small; random-maze generation and dependency refactoring belong to a later project.
