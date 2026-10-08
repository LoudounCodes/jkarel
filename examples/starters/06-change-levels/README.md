# Lesson 6: Change levels while keeping the player — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `MapStages.gpj` in jGRASP, then open `MapStages.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The first map loads and the player collects one beeper. No load message is printed, and the second map is not loaded yet.

## Your work

1. Print the map name in mapLoaded.
2. Load the second supplied map after the pickup, keeping the same player.
3. Show that the player stays at [3, 2] with inventory 1, and the new wall blocks its front.

The corresponding lesson is `06-change-levels.md` in the curriculum packet.
The complete `MapStages.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

Keep both .map files beside this Java file and run with no arguments.

This lesson text is © 2026 Bock / LoudounCodes, licensed under CC BY 4.0
(see CURRICULUM-LICENSE.TXT). Give credit and identify changes; credit does not
imply endorsement. The library and Java code remain GPLv3 (LICENSE.TXT), with
NON-ENDORSEMENT.TXT applying to Bock's software contributions.
Library source: https://github.com/LoudounCodes/jkarel
