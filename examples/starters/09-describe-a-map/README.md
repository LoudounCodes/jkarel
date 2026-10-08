# Lesson 9: An interface that describes a map — starter

Use Java 18 or later. This program compiles before the TODOs are completed;
its initial output is a starting state, not the finished result described below.

## Open and run in jGRASP

1. Extract the entire lab folder to a writable location.
2. Open `DescribeAMap.gpj` in jGRASP, then open `DescribeAMap.java` from the project.
3. Compile and run the Java file. The project uses the local `jkarel.jar`.
   If a classroom jGRASP version does not pick up that setting, select
   **Settings → PATH/CLASSPATH → Project → CLASSPATH**, and add this folder's
   `jkarel.jar`. Remove any conflicting older JKarel entry.
4. Complete the TODOs, rerun, and compare the result with the lesson.

## Before you edit

The adapter creates an empty 8-by-6 scene. The player walks to [4, 2] and has inventory zero because the map description has no supplies yet.

## Your work

1. Add the requested beeper stack and wall to TrainingMap.
2. Use the supplied adapter to reach [4, 2] with inventory 2.
3. Write a second MapDataSource implementation and install it through the same adapter.

The corresponding lesson is `09-describe-a-map.md` in the curriculum packet.
The complete `DescribeAMap.java` listing in the packet appendix is a reference.
Keep your edits in this starter folder; the complete example is separate.

The included GPL-3.0 license covers the library and original lesson materials.
Library source: https://github.com/LoudounCodes/jkarel
