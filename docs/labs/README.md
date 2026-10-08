# Creative-project extensions

These two original labs are for students who have finished their Karel sequence
and are building a game, maze, simulation, or artwork. Start with a working small
program, predict its behavior, then choose a change that fits your own project.
Allow one class period per lab, with extra time for the project extensions.

1. [Team trails: colors and walls](01-team-trails.md)
2. [Event scoreboard: interfaces and observers](02-event-scoreboard.md)

## Set up in jGRASP

Use JDK 18 or later. The library targets Java 18; jGRASP's own minimum Java version
is a separate requirement. The teacher builds once with `ant` and gives students
`out/jkarel-1.0.0.jar`. Students do not need Ant or JUnit to run these labs.

In jGRASP, open **Settings → PATH/CLASSPATH → Workspace**, select **CLASSPATH**,
then **New** and browse to that JAR. Apply the setting. If your classroom uses
jGRASP projects, choose the project scope instead of Workspace. Remove an older
JKarel JAR entry so the compiler and runner see the same library. Keep the original
FCPS library available for assignments that still import `edu.fcps.karel2`;
these labs import `org.loudouncodes.jkarel`.

Copy the example `.java` file into your own working folder, open it in jGRASP,
compile, and run. The file name must match its public class name. No map files or
extra dependency JARs are needed for these two examples.

- `package org.loudouncodes.jkarel does not exist`: check the CLASSPATH entry.
- `UnsupportedClassVersionError`: check the Java used to run in jGRASP; it needs 18+.
- A wall exception: read the failing line and use the appropriate `...IsClear()`
  sensor before that move. The robot remains where it was.
- STEP pacing waits for Enter in the run console. FAST is used in the examples.

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

Before giving the JAR to students, run both examples in the actual classroom
jGRASP installation. Check red and blue robots/trails in the first lab; check
pickup scores and the final eight-move count in the second. Java installation and
visual display are classroom checks, separate from the automated Ant tests.

## Sources and reuse

The exercises and example programs were written for this repository; they do not
adapt the restricted Fairfax worksheets. The library's Fairfax/Loudoun ancestry
remains documented in the root README. These files use the repository's existing
[GPL version 3 license](../../LICENSE.TXT); no separate curriculum license is
introduced by these labs. A broader curriculum publication can make that choice
explicitly later.

The jGRASP setup instructions are independently written from the official
[jGRASP installation tutorial, chapter 1, PATH/CLASSPATH section](https://jgrasp.org/tutorials187/01_Installing.pdf)
and [project tutorial, chapter 7](https://www.jgrasp.org/tutorials187/07_Projects.pdf),
version 1.8.7 (2009), accessed 2026-10-08. These are citations for setup facts;
no tutorial text or figures are reproduced. Menus may vary with classroom version.
