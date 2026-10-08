# Lesson 3: Build a room with a door — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `RoomBuilder.gpj` in jGRASP, then open `RoomBuilder.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The outer boundary and closed door are supplied. The player stays at [4, 3] because the door still blocks it; the rest of the divider is unfinished.

## Your work

1. Build the interior divider while leaving row 3 as the door opening.
2. Remove the stored door wall before the player tries to cross.
3. End at [5, 3] with inventory 1, then design another removable door.

The corresponding lesson is `03-build-a-room.md` in the curriculum packet.
The complete `RoomBuilder.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

This lesson text is © 2026 Bock / LoudounCodes, licensed under CC BY 4.0
(see CURRICULUM-LICENSE.TXT). Give credit and identify changes; credit does not
imply endorsement. The library and Java code remain GPLv3 (LICENSE.TXT), with
NON-ENDORSEMENT.TXT applying to Bock's software contributions.
Library source: https://github.com/LoudounCodes/jkarel
