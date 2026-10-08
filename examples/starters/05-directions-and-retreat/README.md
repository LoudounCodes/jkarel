# Lesson 5: Directions, sensors, and a retreating scout — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `ScoutMoves.gpj` in jGRASP, then open `ScoutMoves.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The west wall blocks the left sensor. The unfinished right turn and retreat do nothing, so the scout walks north to [3, 4].

## Your work

1. Complete turnRight using three left turns.
2. Complete retreat using the rear sensor and protected backUp.
3. End at [3, 3], facing EAST, and explain why retreat does not turn the scout.

The corresponding lesson is `05-directions-and-retreat.md` in the curriculum packet.
The complete `ScoutMoves.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

This lesson text is © 2026 Bock / LoudounCodes, licensed under CC BY 4.0
(see CURRICULUM-LICENSE.TXT). Give credit and identify changes; credit does not
imply endorsement. The library and Java code remain GPLv3 (LICENSE.TXT), with
NON-ENDORSEMENT.TXT applying to Bock's software contributions.
Library source: https://github.com/LoudounCodes/jkarel
