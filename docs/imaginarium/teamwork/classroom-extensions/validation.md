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


## Expanded lessons and economical print layout

2026-10-08: added Lesson 0 API orientation and seven further feature lessons,
keeping the original color/event lessons. Ten lesson programs compile with
`javac --release 18` against the JAR. All ten run headlessly with expected-output
checks, including map-transition state, retreat facing, door sensing, infinite
inventory, and the descriptor adapter. MapStages was also run from the folder
containing its maps with no arguments to verify the student file-lookup setup.
CustomItems and RobotLettering ran with real Swing windows; their final renders
were inspected. STEP interaction still needs the actual classroom run console.

The renderer uses the selected logo once, black body text, limited green accents,
and no page header or filled panel. Lesson text is on pages 3–13 of the 27-page
packet; complete code/maps and teacher notes can be printed separately. Feature
coverage is checked against both current source and repository history. The
MapDataSource exercise is explicitly a student adapter, not a claim of an
integrated library feature. Source references and existing license notes remain.

18:23 UTC direction check — Are we doing the right thing right now? Yes: the next
usable outcome is the revised printable packet. Example behavior and visible
rendering have been checked; finish PDF inspection and packaging without expanding
into library refactoring or additional tooling.

Ant also packages the new example map files alongside their Java sources so the
level-change examples can be extracted together. The Ant command and Java target
remain unchanged.

19:09 UTC direction check — Are we doing the right thing right now? Yes: ten
independent starters and their student archive have compiled and run after
relocation. Verify native jGRASP project settings, then finish the distributable;
illustrations remain the next step rather than expanding this task into artwork.

## Student starter distribution

Ten original starter projects now live under `examples/starters`, each with
compilable Java code, focused TODOs, a native jGRASP project, baseline/target
instructions, and maps where needed. Complete reference programs remain separate.
The original curriculum shell archive was inspected for its scaffold pattern;
none of its shell code, demonstration JARs, worksheets, or maps was copied.

`ant build-starters` builds a 65 KB runtime JAR and supplies it to every independent
folder. The ZIP includes the curriculum PDF once, license copies, and corresponding
library source with the Ant build. The target does not change build-all or Java 18.
The ZIP excludes compiled student classes and temporary jGRASP project files.

`python3 scripts/verify_starters.py` passed all ten folders after extracting the
ZIP into a path containing spaces. Each starter and its complete reference compiled
with `--release 18` against the folder's own JAR and ran headlessly with expected
output. Local map lookup and project section lengths/relative paths were checked.
The runtime JAR excludes tests, source listings, and reference examples.
`ant test` also passed: 14 tests, zero failures/errors/skips.

The installed jGRASP 2.0.6_18 opened the TeamTrails project, recognized the source
as a project member, and compiled it using its local JAR without manual classpath
setup. The same project also compiled after relocation to a path containing
spaces and ran to the expected starter result: Red inventory 5, Blue inventory 5.
jGRASP itself ran on the installed Java 17; compilation/runtime tools used
the local newer JDK. Library and automated compilation still target Java 18.
Actual classroom Java 18, all ten graphical lessons, and STEP Enter interaction
remain human acceptance checks. No publication or push is claimed.

19:21 UTC direction check — Are we doing the right thing right now? Yes: the
archive, relocation checks, native editor compile/run, and regression checks are
finished. Commit the bounded starter work and hand over the classroom archive.

## Illustrated packet

The PDF is now 37 pages, with a captioned figure integrated into each of the ten
lessons. Original vector diagrams cover Arena/Display, stack recoloring, callback
order, doors and sensors, pacing, facing versus movement, retained/replaced map
state, and the map-source adapter. Actual Swing captures show the completed team
trails, AlphaBot lettering, and the custom player/goal before and after removal.
The custom close-ups are captured directly from the example's actual classes.
`scripts/CaptureLabViews.java` and `docs/labs/illustrations.py` reproduce the art.

All ten figure captions and lesson page boundaries were checked in extracted PDF
text. The figures were visually inspected in rendered pages for legibility and
layout. Longer lessons break at exercise boundaries; assessments and teacher
prompts stay together. Black text, white diagram backgrounds, and limited spot
color remain. Screenshot panels are small and retain the library's appearance.
The PDF print guide is updated to its current page ranges.

`ant build-jar build-starters` passed. Both the full JAR and classroom ZIP contain
the exact current PDF. Class inclusion in the full JAR is constrained to the actual
library package so generated capture/verification classes are not packaged;
Python caches are also excluded. Java target, default Ant workflow, and classroom
starter layout are preserved. Classroom human acceptance and publication remain
outside this local artifact check.

19:36 UTC direction check — Are we doing the right thing right now? Yes: the
illustrated PDF is open and all ten lesson figures have been inspected. A fresh
full JAR build removed a stale Python cache entry retained by Ant's up-to-date
check. Both distribution copies now match the PDF exactly, with caches and
capture/verification classes excluded. Finish the local commit and hand over.

## Duplex layout and small illustrations

19:55 UTC direction check — Are we doing the right thing right now? Yes: the next
usable outcome is the revised printable packet. Topic-specific original vector
robot vignettes and additional white space now run throughout the instructional
and reference pages. Each of the ten lessons occupies two pages beginning
on an odd page. The 42-page packet includes marked blank backs at 2, 4, and 40.
The full-page introduction is preserved; code line spacing is increased, and the
scoreboard listing splits between the main program and listener implementation.
Representative rendered introduction, blank, lesson, continuation, and code pages
were inspected. Page-topic output checks the full pagination and illustration
coverage. Finish distribution checks and open the PDF for Bock.

PDF checks passed: 42 pages; exact blank-page text on pages 2, 4, and 40;
vector drawing coverage on all 37 instructional/reference pages; every lesson
start odd-numbered; all ten lessons two pages; navigation links retained.
`ant build-jar build-starters` passed, and byte comparisons confirm both the
full JAR and classroom ZIP contain the current PDF. Only document/rendering
sources changed; Java code, Java 18 target, and Ant workflow are unchanged.
Proof remains single-author automated checks and local visual inspection;
classroom human acceptance is pending. No push or publication occurred.

## Reader-facing production notes

Removed source-verification narrative, feature-history audit anchors, and
branding/illustration production notes from the reader packet. Useful teacher
citations remain; internal verification belongs in project documentation.

## All ten native jGRASP projects and Windows portability

2026-10-08: Extracted the classroom ZIP into a fresh repository-local folder with
spaces in its path. Launched jGRASP 2.0.6_18 separately for each lesson with its
own isolated settings directory. Used Project → Open, File → Open, Build → Compile,
and Build → Run in the actual Swing editor. Each native project tree recognized
its own source file; each editor compilation created its class file without
manually changing the classpath; each Run I/O console showed the expected starter
result. Inspected all ten captured editor views. Programs were stopped with
Build → End after capture. The starters retain their TODOs.

| Project | Native open / compile / run | Observed starter result |
| --- | --- | --- |
| 00-meet-jkarel | Passed | Inventory: 2 |
| 01-team-trails | Passed | Red inventory: 5, Blue inventory: 5 |
| 02-event-scoreboard | Passed | Final: Red 0, Blue 0, moves 0 |
| 03-build-a-room | Passed | Player [4, 3], inventory 0 |
| 04-predict-with-pacing | Passed | [4, 4] NORTH |
| 05-directions-and-retreat | Passed | Scout [3, 4] NORTH |
| 06-change-levels | Passed | Same player: [3, 2], inventory 1; new wall blocks front: false |
| 07-draw-your-own-items | Passed | Reached the goal at [4, 2] |
| 08-robot-lettering | Passed | Infinite inventory: true |
| 09-describe-a-map | Passed | Player [4, 2], inventory 0 |

Native GUI evidence is local under `build/jgrasp-check`: numbered screenshots and
logs, `all-projects.json`, and isolated settings/extracted folders. The reproducible
Swing driver is `scripts/VerifyJGraspProjects.java`. Its arguments are settings
directory, project, source, screenshot, and the JDK bin directory. Compile it with
Java 17+; run it with the installed jGRASP JAR on the classpath. It captures evidence
rather than declaring a pass solely from launch success; inspect project membership
and the Run I/O result.

jGRASP itself ran on Oracle Java 17.0.1; the compiler/runtime were the local
Homebrew JDK 27. Automated starter/reference compilation separately targets
Java 18. The packaged library classes were checked for class versions no higher
than Java 18 (62); older package metadata classes are compatible too.

Windows portability checks now run in `scripts/verify_starters.py`: ZIP paths are
relative, names are legal on Windows, reserved device names and case-insensitive
collisions are rejected, project files retain LF serialization, and compile/run
classpath entries point exactly to the sibling `jkarel.jar`. The longest archived
name is 83 characters (121 under a representative Windows Downloads directory).
All ten starters and all ten complete references compile and run after extraction
to a path containing spaces; local map lookup is checked. Windows Extract All /
Project Open / Compile / Run instructions are included in the starter README.

This is native macOS editor proof plus automated portability checks. The available
local VMs are Linux; no Windows VM/device execution is claimed. Actual Windows
classroom Java/jGRASP execution and STEP Enter interaction remain pending.
No project serialization changes were necessary; the original Ant build, Java 18
target, directory layout, and teaching API are preserved. No push or publication.

## Complete classroom distribution contents

`ant build-starters` now depends on the existing Javadoc target, includes the
generated documentation at `starter-labs/javadoc`, and places the runtime JAR
and license at the distribution root as well as beside each lab project. The
root README introduces the library/curriculum, lists the package contents, links
to GitHub, and links locally to the PDF, Javadoc index, JAR, and license. Ten
independent shells, maps, Windows instructions, and corresponding source remain.
The default Ant target and Java 18 class target are preserved. Javadoc uses source
18 and fails the build on errors; existing missing-description warnings remain.

`ant build-starters` and a fresh build using
`ant -Doutput.dir=build/distribution-check build-starters` both passed. The
archive verifier passed against both output directories after ZIP extraction to
paths containing spaces. It checks root README links/GitHub URL, exact PDF and
JAR copies, all 21 public API type pages, and local page/resource links across
38 Javadoc HTML pages, in addition to Windows archive checks and compilation/run
of every starter and complete reference. The optional `--output-dir` argument
supports checking a fresh distribution without moving the canonical download.

The unchanged default `ant` workflow also passed with 14 JUnit tests and zero
failures/errors/skips. Student project files and Java source were unchanged, so
the prior native jGRASP checks still apply. Single-author local build/packaging
proof; actual Windows classroom execution and interactive STEP input remain
pending. No push or publication occurred.

## Licensing direction check

2026-10-08T21:44:34.260466+00:00: Are we doing the right thing right now?
Yes: finish the authorized CC BY curriculum/GPL code split and rebuild the existing
classroom artifacts. Use established license texts and Ant packaging; no new
build structure or publication. Only final packaging verification remains.

## Curriculum attribution and non-endorsement

Bock authorized CC BY 4.0 for original lesson prose and illustrations, keeping
GPLv3 for the library and Java code. LICENSING.md defines this split, excludes
arena screenshot components and the logo from the CC grant, and supplies a
Bock / LoudounCodes attribution example. NON-ENDORSEMENT.TXT adds the GPLv3
section 7(d) publicity term only for Bock-authored software contributions;
upstream notices and the existing GPL text are unchanged. Source comments refer
to the scoped notice without changing executable Java behavior.

Official references: https://creativecommons.org/licenses/by/4.0/legalcode.txt
and https://opensource.org/license/gpl-3.0 (section 7). The CC legal text is
included unchanged as CURRICULUM-LICENSE.TXT. SHA-256: 9ba9550ad48438d0836ddab3da480b3b69ffa0aac7b7878b5a0039e7ab429411

The PDF cover and teacher references, repository README, and all ten standalone
lab READMEs identify the applicable licenses. Ant includes full notices at the
ZIP root and in library-source, both notices in runtime JARs and offline Javadoc,
and both license texts plus the scoped additional term in every lab folder.
The full development JAR includes all four licensing documents and the exact PDF.

Final default Ant build passed all 14 JUnit tests with no failures, errors, or
skips. Canonical and fresh-output starter builds passed. The archive verifier
passed against both ZIPs after extraction to paths with spaces, including all
ten starter and completed-reference compilations/runs, Java 18 bytecode limits,
Windows filename/path checks, exact notice copies, and all local links across
38 Javadoc pages documenting 21 public API types. The PDF remains 42 pages with
three intentional blanks and ten two-page lessons starting recto; cover, teacher
references, and a continued source listing inspected. Updated PDF opened locally.

Proof remains single-author local build/packaging verification; the earlier native
macOS jGRASP results still apply to unchanged project files and executable code.
Actual Windows classroom execution and interactive STEP input remain pending.
No push or publication occurred.

## Authorized prose and history rollback

Bock requested a rollback because rejected rewrites could mislead future agents
browsing history. Restored the introduction and every lesson verbatim from the
last accepted illustrated/duplex packet. The Arena, Pacing, and mental-model
argument is again together in the introduction. Removed the separate comparison
section and its navigation/rendering support. Later reader-packet production-note
removals, licenses, native jGRASP proof, Windows instructions, Javadoc, README,
JAR packaging, and source notices are preserved.

Byte comparisons confirm the introduction and ten lessons match the earlier
accepted tree. Code, scripts, examples, starter projects, build.xml, repository
README, license files, and dependencies match the later licensed distribution.
Restored prose is reused rather than rewritten. Obsolete comparison/rewrite
validation claims are removed from the current progress record.

The rendered PDF is 42 pages with three intentional blanks, ten two-page lessons
starting recto, and the original illustrations. Inspected the restored full-page
introduction and teacher/license page. Default Ant build passed all 14 tests;
starter packaging and relocated-ZIP checks passed for all ten starter and completed
reference programs. Both JAR and ZIP contain the exact restored PDF, and the
removed comparison is absent from the development JAR. Notice copies and all
local Javadoc links passed the existing verifier.

The unpublished follow-up work is consolidated onto the accepted packet commit,
excluding the rejected prose rewrites from the normal branch history. No remote
history is changed and no push/publication occurs. Proof remains single-author
local build, packaging, and visual verification, supplemented by the preserved
native macOS jGRASP results. Actual Windows classroom execution and interactive
STEP acceptance remain pending.

## Front lesson plan with locked content

Bock locked reader content except changes necessary to put the lesson plan at
the front and include terminology as a lesson. Moved the plan from page 5 to
page 3 and the unchanged introduction from page 3 to page 5. The plan heading
is now Lesson plan; its Lesson 0 label is Terminology — Arenas, Locations, and
Directions. That entry links to the existing terminology lesson on pages 7–8.
The Markdown sequence label and production page guide match. No new lesson or
prose rewrite was introduced.

All introduction, lesson, and feature-guide Markdown bytes match the locked
commit. Rendered text comparison covers all 42 pages, excluding footer page
numbers: the introduction matches its moved counterpart, the plan matches after
the authorized heading/label changes, and every other page matches exactly.
All ten plan links resolve to the existing lessons. The three blank versos,
lesson starts, code listings, and illustrations remain. Inspected the two moved
pages and opened the current PDF locally.

Ant build-jar/build-starters passed. Both distributions contain the exact current
PDF. Archive portability, root README links, license copies, and all local
Javadoc resources passed the existing packaging checks. Executable code and
jGRASP projects are unchanged, so earlier compilation/run evidence remains
applicable. Local layout and packaging proof; actual Windows classroom and STEP
acceptance remain pending. No push or publication.

## MapDataSource loading overload

Bock authorized a quick addition of the missing map-interface connection.
Added Arena.loadMap(MapDataSource) and a package-level model installer. The XML
String loader is unchanged. Descriptions are read and validated before clearing
the current level; dimensions must be positive and beeper keys must agree with
stack locations. Null components and invalid counts are rejected. Collections
and beeper data are copied. Wall identity is retained for removable doors and
custom rendering.

Loading replaces walls, beepers, and custom items while preserving robot state
and registered listeners. Wall/beeper additions precede one mapLoaded callback;
its String identifier is the source class's Class.getName() value. The facade
then repaints and applies the selected pacing. This uses existing model operations
and callbacks, preserving the Ant/Java 18 workflow and teaching abstractions.
Javadoc describes the new overload and its contract; the original explanation
of future maze-generator implementations is retained.

Default Ant build passed all 17 tests, including new tests for player preservation,
complete-state map notification and event ordering, colored/infinite supplies,
removable custom walls, reusable descriptions, two implementations, and invalid
or failing descriptions retaining the prior level. All ten starters and completed
references compiled and ran against the updated classroom JAR after relocated
ZIP extraction. A separate Java 18 program calling Arena.loadMap(new GeneratedRoom())
compiled against the classroom JAR and ran to [4, 2] with inventory 2. Offline
Javadoc includes the overload. Both distributions rebuilt; final PDF/JAR copies,
notices, archive portability, and local documentation resources checked.

The locked curriculum Markdown and PDF are byte-for-byte unchanged. Lesson 9
still describes the missing overload and uses its student adapter; its factual
statements and matching appendix comment need an explicitly authorized update.
The XML parser still installs maps directly, as before. Single-author local build
and packaging proof; actual Windows classroom and interactive STEP acceptance
remain pending. No push or publication.

2026-10-08T23:58:40Z: Are we doing the right thing right now? Yes: the focused
loader works through the existing API, checks pass, and the classroom package
is rebuilt. Stop here; XML-parser restructuring and locked lesson changes
are outside this addition.

## Authorized MapDataSource curriculum correction

Bock authorized a surgical PDF correction for the implemented loader. Lesson 9
now uses Arena.loadMap(new TrainingMap()), explains validation and level-loading
behavior, and asks students to load a second implementation and check reloading.
The diagram, lesson-plan label, feature references, commented appendix example,
and matching starter use the same implemented API. The obsolete student adapter
is removed. The introduction and Lessons 0–8 remain byte-for-byte unchanged.

Both PDFs have 42 pages. Normalized rendered text on every unrelated page matches
the locked PDF exactly, including footers. Changes are confined to pages 3, 25,
26, 37, 41, and 42. All lessons retain two pages and odd-numbered starts, and
the three intentionally blank versos remain. Visually inspected the corrected
lesson, appendix listing, and feature references.

Ant build-jar/build-starters passed. The updated starter and completed reference
compile for Java 18 and run with a folder-local classroom JAR after extraction
into a path containing spaces: both finish at [4, 2], with inventory 0 and 2
respectively. ZIP filename/path/project checks passed. The root README, JAR,
PDF, and 38 offline Javadoc HTML pages (21 public API types) pass package checks;
all local documentation links resolve. Both distributions contain the exact
revised PDF, and packaged Lesson 9 sources match the repository. Library source
is unchanged since the preceding 17-test build.

Single-author local visual, build, and packaging proof. Actual Windows classroom
and interactive STEP acceptance remain pending. No push or publication.
