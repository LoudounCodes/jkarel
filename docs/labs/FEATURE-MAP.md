# Feature coverage and teaching boundaries

The sequence is written from the implementation and repository history, rather
than inferring capabilities from unfinished Javadoc. These are original exercises.
The program sources form the reproducible examples; no Fairfax worksheet is adapted.

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
| A data-source interface for future maps | 9 | MapDataSource descriptor; explicit student-written installation adapter |

Orientation teaches the vocabulary without claiming the old and new APIs are
source-compatible or that fluent naming means method chaining. Robot meeting
sensors, ordinary subclassing, and game loops are supporting concepts; not every
inherited operation is labeled as a new feature.

The event lesson covers movement and pickup scoring. Its experiments cover drops
and unregistering; other lessons exercise map-load events and custom-item removal.
ArenaListener documents its remaining callbacks, constructor timing, and lack of
turn/color notifications. Do not invent a timer or keyboard event API for these labs.

The map-data interface is declared but not integrated into the XML loader. Lesson 9
is explicitly an advanced design exercise; its adapter lives in the example code.
The existing JRuby experiments, Ant/JUnit packaging, XML internals, and Swing frame
access are teacher/developer tools outside this Java/jGRASP student sequence.
Digit is a legacy class, not a new feature; the lettering lesson uses AlphaBot.

## Implementation and history anchors

The root README records the Fairfax/Loudoun lineage. API facts were checked in
`src/main/org/loudouncodes/jkarel`, including Arena, Robot, Direction, Pacing,
ArenaModel, ArenaListener, Item, MapDataSource, and `demo/AlphaBot.java`.
The early API comparison was checked against commit `12e7e0f`'s Display and Robot.
Useful provenance points in this repository's history:

- `a0df5a8`: Direction enum; `369f0cf`: Location naming; `8247185`: Arena naming.
- `5feb72b`: pacing; `ed94517`: robot rendering; `8a8dc3e`: generated walls/beepers.
- `524f07d`: lettering robot; `71bde07`: infinity moves to BeeperStack.
- `8a81d21`, `ea2f68f`, `f041915`: listener introduction, events, default methods.
- `40e9092`, `a784563`: custom items and protected repositioning.
- `be5152e`: MapDataSource declaration; `7de0d9f`: robot/beeper colors.
- `3f6969e`: protected backup; `f2e09d8`: runtime map reload.
- `5e07f3e`: classroom correctness repairs and completed callbacks.

These anchors establish provenance within the supplied code repository. They are
not claims that a change originated nowhere else. The jGRASP setup references,
license, and reuse notes appear in the main lesson README and PDF teacher notes.
