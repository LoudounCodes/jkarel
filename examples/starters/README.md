# Karel lesson starters

Each numbered folder is an independent student project. The source here contains
original starter code, jGRASP project settings, instructions, and any required
maps. Complete examples remain in `examples/java` and in the packet appendix.

## Build the classroom download

From the repository root, run:

```sh
ant build-starters
```

This optional target leaves the existing Ant build and Java 18 target in place.
It produces `out/LoudounCodes-Karel-Starter-Labs.zip` and the unpacked
`out/starter-labs` folder. Each lesson folder includes its own `jkarel.jar`,
project file, Java starter, instructions, and license. The archive also includes
the curriculum PDF and corresponding library source with its Ant build.
The classroom JAR includes the library classes and map/graphics resources;
it excludes test classes and complete example solutions.

Students should extract the archive, copy their chosen lesson folder to a
writable working location, open its `.gpj` file, and complete the TODOs. They
need Java 18 or later and jGRASP. They do not need Ant, Python, or JUnit.
The numbered folders have no dependencies on one another.

| Lesson | Starter | Work left for the student |
| --- | --- | --- |
| 0: API orientation | WelcomeArena.java | Route, facing, and inventory using the new names |
| 1: Colors | TeamTrails.java | Team colors and colored drops |
| 2: Listeners | EventScoreboard.java | Registration, callbacks, and removal |
| 3: Construction | RoomBuilder.java | Divider and opening the stored door |
| 4: Pacing | PaceProbe.java | STEP selection and action-state tracing |
| 5: Subclasses | ScoutMoves.java | Right turn and safe protected retreat |
| 6: Levels | MapStages.java | Map notification and loading the second level |
| 7: Rendering | CustomItems.java | Goal/player appearance and goal removal |
| 8: Lettering | RobotLettering.java | Color and drawing the message |
| 9: Map interfaces | DescribeAMap.java | Map description and a second implementation |

All starters compile and run before completion. Their initial states intentionally
differ from the completed lesson examples. Each folder's README states the work
and the intended result. A completed core task is followed by the exercises in
the corresponding lesson; a TODO is not the entire lab.

These shells are original work. The original curriculum was consulted for its
teaching pattern of supplied setup and unfinished student methods; its worksheets,
code, maps, and demonstration JARs are not copied into this distribution.

jGRASP project usage follows the official
[Projects tutorial](https://www.jgrasp.org/tutorials187/07_Projects.pdf).
Project files use a local JAR path so a folder can move between machines.
Their settings follow jGRASP's native serialization; the source-file sections and
classpath were checked by opening and compiling a supplied project in jGRASP.

Teacher verification after packaging:

```sh
python3 scripts/verify_starters.py
```

This extracts the archive into a path containing spaces, checks the ten projects,
and compiles/runs each starter and its completed reference against the local JAR.
Headless checks do not exercise drawing or Enter input in STEP mode; those need
jGRASP and the classroom run console.
