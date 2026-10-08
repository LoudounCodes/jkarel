# Lesson 2: Let the arena tell the scoreboard — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `EventScoreboard.gpj` in jGRASP, then open `EventScoreboard.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The two players move and collect supplies. The scoreboard stays at zero because registration and counting are unfinished.

## Your work

1. Register and remove the Scoreboard listener in the indicated places.
2. Complete its movement and pickup callbacks using player object identity.
3. Reach Final: Red 2, Blue 2, moves 8, then add one new counter.

The corresponding lesson is `02-event-scoreboard.md` in the curriculum packet.
The complete `EventScoreboard.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

The included GPL-3.0 license covers the library and original lesson materials.
Library source: https://github.com/LoudounCodes/jkarel
