# Lesson 9: An interface that describes a map

**New concept: MapDataSource Interfaces**

> **Data-source interfaces**
>
> An interface specifies what information an object supplies. Different implementations can describe maps. `Arena.loadMap` reads that description and installs it in the arena.

Advanced design extension. You know how to build an arena and observe its events.
Now separate a description of a map from the code that installs it.

## Read the contract

`MapDataSource` declares four methods: width, height, beeper stacks, and walls.
An implementation could describe a fixed room or a generated maze. A caller
can use the interface without needing to know how that description was produced.

`Arena.loadMap(MapDataSource)` reads the description and installs its walls
and beepers. It validates the source before replacing the current map, copies
beeper data, and retains wall references for doors and custom drawing.
The String overload continues to load XML maps.

## Predict and run

Compile [DescribeAMap.java](../../examples/java/DescribeAMap.java). `TrainingMap`
implements the interface, and `Arena.loadMap` accepts its description.

```java
Arena.loadMap(new TrainingMap());
```

Expected result: an 8-by-6 map with one north wall at `[3, 3]` and two beepers at
`[4, 2]`. The player walks there and collects both, ending with inventory `2`.
Setup finishes before the player is constructed. Loading preserves existing robots and listeners, as in the map-loading lesson.
Addition events precede `mapLoaded`, which reports the source class name.

## Make a second implementation

1. Write `SupplyRoom implements MapDataSource` with a different size and layout.
   Load it with `Arena.loadMap(new SupplyRoom())` and compare the two layouts.
2. Explain the `Map<Location, BeeperStack>` and `List<Wall>` return types. The Map
   is a Java collection indexed by locations; it is not the whole game map.
3. Choose a policy for returned collections: newly built descriptions, snapshots,
   or read-only views. Explain whether callers can change your source accidentally.
4. Reload a description after the player collects supplies. Predict which
   player state survives and which beeper stacks return; check a listener's
   `mapLoaded` notification.

## Show what you learned

Demonstrate two map-source implementations using `Arena.loadMap`. Explain an
interface promise, an implementation choice, and a loader responsibility.
Identify the data your source supplies and the operations the library performs.

Teacher prompt: compare this interface, which is queried for data, with
ArenaListener, whose methods receive notifications. Keep this example small;
leave maze generation and refactoring for later work.
