# Lesson 7: Draw a goal and a different player — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `CustomItems.gpj` in jGRASP, then open `CustomItems.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The player uses the ordinary robot drawing and reaches [4, 2]. The goal exists in the model but is invisible until you implement its drawing; removal is also unfinished.

## Your work

1. Draw the goal using the supplied pixel center.
2. Replace the default Player drawing with a shape using getColor().
3. Remove the goal when reached; keep game decisions outside render.

The corresponding lesson is `07-draw-your-own-items.md` in the curriculum packet.
The complete `CustomItems.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

This lesson text is © 2026 Bock / LoudounCodes, licensed under CC BY 4.0
(see CURRICULUM-LICENSE.TXT). Give credit and identify changes; credit does not
imply endorsement. The library and Java code remain GPLv3 (LICENSE.TXT), with
NON-ENDORSEMENT.TXT applying to Bock's software contributions.
Library source: https://github.com/LoudounCodes/jkarel
