# Classroom extensions validation

2026-10-08. Single-author implementation and self-review; no independent review
or classroom acceptance is claimed. Bock authorized inclusion of the three
pre-existing beeper-event edits and chose to retain Java 18.

## Scope delivered

Ant, the source/example layout, the `org.loudouncodes.jkarel` package, and the
student Robot/Arena approach remain. Corrected all four directional sensors,
mutable beeper map keys, pickup color loss, pickup/drop notifications, and XML wall
lengths. Added the Arena listener registration facade and completed the declared
collision, map-load, and user-item callbacks. Listener iteration permits an
observer to unregister during notification. Rendering uses wall/item snapshots
and contrasting beeper labels. `openDefaultMap` loads the map instead of closing
the window. Invalid robot actions throw a diagnostic exception instead of exiting
successfully. Map-loading stream ownership is closed after parsing.

Two original labs and runnable examples cover colored team trails with generated
walls, then event-based scoring. Setup references cite official jGRASP tutorials.
All additions remain under the existing repository license.

## Evidence

- `ant clean build-all` succeeded from a clean output directory. After the final
  label-contrast change, `ant build-all` succeeded again: **14 tests, zero failures,
  zero errors**. Tests exercise every facing/sensor combination, movement after
  drops, remaining-stack colors, infinite beepers, event ordering/unregistration,
  collisions, invalid inventory actions, map wall lengths, map loading, custom
  item notifications, snapshots, and color/label rendering.
- Both examples compiled with `javac --release 18` against only the packaged JAR.
  Both ran headlessly against that JAR. TeamTrails completed its two five-beeper
  trails; EventScoreboard reported `Final: Red 2, Blue 2, moves 8`.
- Both examples also ran with real Swing windows. An ignored build-directory smoke
  wrapper invoked their main methods and checked robot positions/inventories and
  remaining beeper stacks, rendered the panels, and disposed the windows. TeamTrails
  was rerun after the contrast fix. Its inspected image shows red/blue trails,
  readable counts, the divider, and the outer boundary.
- JAR inspection confirmed both lab documents and example sources are included;
  test classes are excluded. `javap` reports classfile major version 62 (Java 18).
- Process-flow validation passes; `git diff --check` passes.

The local compiler/runtime is OpenJDK 27 using `--release 18`. Actual Java 18
runtime execution and the classroom's jGRASP compiler/runtime selection remain
human acceptance checks. No claim is made that jGRASP itself was exercised here.

## Remaining scope

Run both labs on the classroom machines before distribution. The XML parser still
has legacy reflection/error handling, and Javadoc still emits 100 warnings. Those
are deferred broader cleanup; no parser replacement was introduced. Grid size
still does not construct full boundary walls automatically; the first lab teaches
explicit enclosure. Keyboard input, timers, turn/color events, and general game
engine features are outside this pass. Neither code nor curriculum was pushed or
published.
