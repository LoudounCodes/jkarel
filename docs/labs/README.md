# Creative-project extensions

[About this library and curriculum](WHAT-IS-THIS.md) introduces the library and this curriculum.

Student projects are available in [the starter folders](../../examples/starters/README.md).
Build the classroom archive with `ant build-starters`; each lab includes a local
library JAR, a jGRASP project, and focused TODOs. The appendix listings are the
complete reference examples.

These lessons are for students who have finished their Karel sequence
and are building a game, maze, simulation, or artwork. Start with the orientation,
then choose the features your project needs. Each lesson includes a working example,
predictions, experiments, and a demonstration of what you learned. Most fit one
class period; the interface-design lesson can take longer.

0. [Terminology: Arenas, Locations, and Directions](00-meet-jkarel.md)
1. [Team trails: robot and beeper colors](01-team-trails.md)
2. [Event scoreboard: interfaces and observers](02-event-scoreboard.md)
3. [Build a room: walls, beepers, and doors](03-build-a-room.md)
4. [Predict with pacing: enums and STEP](04-predict-with-pacing.md)
5. [Directions and retreat: sensors and protected behavior](05-directions-and-retreat.md)
6. [Change levels: map loading and persistent players](06-change-levels.md)
7. [Draw your own items: abstract classes and rendering](07-draw-your-own-items.md)
8. [Robot lettering: AlphaBot and infinite supplies](08-robot-lettering.md)
9. [Describe a map: the MapDataSource interface](09-describe-a-map.md)

[Feature coverage](FEATURE-MAP.md) connects each
lesson to its actual API and distinguishes a working feature from a design exercise.
Each lesson includes a captioned diagram or arena view. The PDF uses black body text, sparse green accents, no page header or filled panels,
and the selected LoudounCodes logo on a separate cover page.

## Set up in jGRASP

Use JDK 18 or later. The library targets Java 18; jGRASP's own minimum Java version
is a separate requirement. The teacher builds once with `ant build-starters` and
gives students `out/LoudounCodes-Karel-Starter-Labs.zip`. Students do not need
Ant or JUnit to run these labs.

Extract the ZIP to a writable folder. In jGRASP, choose **Project → Open**, select
the lesson's `.gpj` file, then open its `.java` file under **Source Files**.
Compile and run. Each project uses `jkarel.jar` in its own folder; keep the JAR,
project, Java file, and any maps together when copying a lab. Complete its TODOs;
the appendix contains the complete reference examples.

If the local JAR is not picked up, open **Settings → PATH/CLASSPATH → Project**,
select **CLASSPATH**, then **New** and browse to that folder's `jkarel.jar`.
Apply the setting. Remove a conflicting older JKarel entry. Keep the original
FCPS library available for assignments that still import `edu.fcps.karel2`;
these labs import `org.loudouncodes.jkarel`. Extra dependency JARs are not needed.
The level-changing starter includes both map files beside its Java file;
the other lessons build their own scenes.

- `package org.loudouncodes.jkarel does not exist`: check the CLASSPATH entry.
- `UnsupportedClassVersionError`: check the Java used to run in jGRASP; it needs 18+.
- A wall exception: read the failing line and use the appropriate `...IsClear()`
  sensor before that move. The robot remains where it was.
- STEP pacing waits for Enter in the run console. Most examples use FAST;
  RobotLettering uses LUDICRUS, and PaceProbe normally selects STEP.

Teacher command-line equivalent, from the repository root:

```sh
ant
javac --release 18 -cp out/jkarel-1.0.0.jar -d build/labs examples/java/TeamTrails.java examples/java/EventScoreboard.java
java -cp out/jkarel-1.0.0.jar:build/labs TeamTrails
java -cp out/jkarel-1.0.0.jar:build/labs EventScoreboard
```

On Windows, replace the runtime classpath's `:` separator with `;`.
Close each arena window before running the next example.

## Teacher acceptance

Before giving the JAR to students, run the examples in the actual classroom
jGRASP installation. Check red and blue robots/trails in the first lab; check
pickup scores and the final eight-move count in the second. Check STEP interactively in the run
console and check the map files from the student working folder. Java installation and
visual display are classroom checks, separate from the automated Ant tests.

## References and license

Lesson text and original illustrations: © 2026 Bock / LoudounCodes,
[CC BY 4.0](https://creativecommons.org/licenses/by/4.0/). You may share and adapt
them with credit, a license link, and an indication of changes. Credit does not
imply endorsement.

The library and Java examples remain [GPLv3](../../LICENSE.TXT). Arena screenshots
follow the library's license; the LoudounCodes logo is excluded from the CC BY
grant. See the [licensing guide](../../LICENSING.md) for details.

For jGRASP setup, see the official
[installation tutorial, chapter 1: PATH/CLASSPATH](https://jgrasp.org/tutorials187/01_Installing.pdf)
and [project tutorial, chapter 7](https://www.jgrasp.org/tutorials187/07_Projects.pdf).
These references describe version 1.8.7 (2009); menus may vary with the version
installed in your classroom.
