# Feature coverage and teaching boundaries

Use this guide to choose a lesson for a project or connect a feature to the Java
concepts it introduces.

| Feature or change | Lesson | Actual teaching surface |
| --- | --- | --- |
| Display → Arena; Coordinate → Location; simpler package | 0 | Arena facade, Robot location accessors, `org.loudouncodes.jkarel` imports |
| Typed direction rather than numeric constants | 0, 5 | Direction enum; left/right/behind compute values |
| Robot and whole-stack beeper colors | 1 | setColor/getColor; colored drops; existing stack color survives pickup |
| ArenaListener behavior interface and default methods | 2 | addListener/removeListener; synchronous notifications; score outside Robot |
| Programmatic walls and beepers, removable doors | 3 | directional wall calls; addBeeper/addBeepers; stored Wall reference |
| Named pacing and STEP | 4 | Pacing enum; Enter in run console; setup and action pause semantics |
| Protected backup and reusable subclasses | 5 | backUp inside Scout; backIsClear; position versus facing |
| Runtime map reload; bundled and local maps | 6 | loadMap(String); existing player/listener persistence; XML wall length |
| Self-rendering objects and generic user items | 7 | abstract Item; add/removeUserItem; Robot.render override; grid versus pixels |
| Letter-drawing robot and infinite supply ownership | 8 | demo.AlphaBot.say; BeeperStack.INFINITY; ASCII and layout limits |
| A data-source interface for maps | 9 | MapDataSource descriptor; Arena.loadMap(MapDataSource) |

## Teaching notes

Begin with Lesson 0, then choose the features a student's project needs. Familiar robot actions, sensors, subclassing, and game loops remain
part of the working vocabulary.

The event lesson introduces movement and pickup scoring, then explores drops
and unregistering. Later lessons use map-load events and custom-item removal.
Callbacks run synchronously; students should keep game loops outside listeners.
The library does not provide timer or keyboard-input events.

Lesson 9 uses MapDataSource to describe a map and `Arena.loadMap` to install it.
Students write two implementations and load each through the same interface.
The existing String overload continues to load XML maps.
