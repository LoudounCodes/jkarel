# LoudounCodes Karel creative-project labs

This package contains ten starter projects and a curriculum for students who
already know Karel and want to build games, mazes, simulations, or artwork.
LoudounCodes JKarel modernizes the FCPS Karel library with clearer Java types,
robot and beeper colors, removable walls, event listeners, and custom rendering.

Each numbered folder is an independent jGRASP project with starter code and TODOs,
its own copy of the library, instructions, and any required maps. The PDF explains
the API and lessons; its appendix contains complete reference programs.

The project lives on GitHub at [LoudounCodes/jkarel](https://github.com/LoudounCodes/jkarel).
Visit the repository for source code, updates, and issue reporting.

## What's included

- `00-meet-jkarel` through `09-describe-a-map`: the ten lab shells.
- [Curriculum PDF](LoudounCodes-Karel-Extension-Labs.pdf): lessons, examples, and teacher notes.
- [Java API reference](javadoc/index.html): the generated Javadoc; open it in a browser.
- [jkarel.jar](jkarel.jar): the Java 18 library, also included beside every lab project.
- [Licensing guide](LICENSING.md), license texts, and `library-source`: attribution, reuse terms, and library source with its Ant build.

Students need a JDK, version 18 or later, and jGRASP. They do not need Ant, Python,
or JUnit. The numbered folders have no dependencies on one another. Extract the
archive, open a lesson project, and complete its TODOs.

## Build the classroom download

From the repository root, run:

```sh
ant build-starters
```

This optional target leaves the existing Ant build and Java 18 target in place.
It produces `out/LoudounCodes-Karel-Starter-Labs.zip` and the unpacked
`out/starter-labs` folder. Each lesson folder includes its own `jkarel.jar`,
project file, Java starter, instructions, and license. The archive also includes
the curriculum PDF, generated Javadoc, a root copy of the JAR, and corresponding
library source with its Ant build.
The classroom JAR includes the library classes and map/graphics resources;
it excludes test classes and complete example solutions.

Students should extract the archive, copy their chosen lesson folder to a
writable working location, open its `.gpj` file, and complete the TODOs. They
need Java 18 or later and jGRASP. They do not need Ant, Python, or JUnit.
The numbered folders have no dependencies on one another.

## Licensing

Lesson text and original illustrations: © 2026 Bock / LoudounCodes,
[CC BY 4.0](https://creativecommons.org/licenses/by/4.0/). Share and adapt them
with credit, a license link, and identification of changes; credit does not imply
endorsement. The [full curriculum license](CURRICULUM-LICENSE.TXT) is included.

The library and Java code remain [GPLv3](LICENSE.TXT), with
[non-endorsement terms](NON-ENDORSEMENT.TXT) for Bock's software contributions.
The [licensing guide](LICENSING.md) explains the code, screenshots, and logo.

## Open a lesson on Windows

1. Right-click the downloaded ZIP and choose **Extract All**. Choose a writable
   folder, such as a folder under Documents. Finish extraction before opening a lab.
2. Start jGRASP, choose **Project → Open**, and select the lesson's `.gpj` file
   inside the extracted `starter-labs` folder.
3. In the project panel, open the `.java` file under **Source Files**.
4. Choose **Build → Compile**, then **Build → Run**.

The project already refers to `jkarel.jar` in its own folder. Keep that JAR,
the project file, source file, and any `.map` files together when copying a lab.
No workspace classpath change is needed for these starter projects. Spaces in
folder names are supported. Use a JDK with `javac`, version 18 or later.

For the pacing exercise, type Enter in jGRASP's **Run I/O** console when STEP
is selected. Close the arena window or use **Build → End** before another run.

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

For more on using projects, see the official
[jGRASP Projects tutorial](https://www.jgrasp.org/tutorials187/07_Projects.pdf).
The `.gpj` files refer to the JAR beside them, so each lesson folder can move
between machines.

Teacher verification after packaging:

```sh
python3 scripts/verify_starters.py
```

This extracts the archive into a path containing spaces, checks the ten projects,
and compiles/runs each starter and its completed reference against the local JAR.
Headless checks do not exercise drawing or Enter input in STEP mode; those need
jGRASP and the classroom run console.
