# LoudounCodes JKarel

## Introduction:
This code was born from the Loudoun County Public School System's use of Fairfax County's FCPSKarel computer science curriculum.

The primary person forking this code (@bokmann) has been using this code for 7 years as a Teacher's Assistant at Loudoun Valley High School.

In the intervening decade+ since the code was originally written, Java has evolved.  This is first and foremost an effort to evolve the Karel code into the present.

This version of the framework is making the code more idomatic with current Java conventions, as well as revisiting some design decisions that should make this code more suitable both for a beginner programming course, as well as a post-AP course learning about software architecture.

## Building:
### Prerequisites:
* JDK 18 or later, available from a bash-like shell.
* Ant 1.10.4 or later, available from a bash-like shell.
### Build Steps:
* download this code either by cloneing via git+ssh or downloading an uncompressing the zip file, above.
* from a command line at the root of the project, type 'ant'.
  * The JAR and Javadoc appear in `out`.
  * Intermediate compiled classes appear in `build`.
* typing 'ant clean' will remove the build and out directories, returning your directory to a pristine state (except for modifications you may have made to the source).
## Creative projects in jGRASP

The Ant build and package layout remain unchanged. Use JDK 18+ and Ant 1.10.4+.
`ant` runs the regression tests and produces `out/jkarel-1.0.0.jar` and `out/docs`.
`ant clean` removes `build` and `out`.

Start with the [classroom setup and extension lesson sequence](docs/labs/README.md):
[Team trails](docs/labs/01-team-trails.md) teaches colors and programmatic walls;
[Event scoreboard](docs/labs/02-event-scoreboard.md) teaches event listeners.
The sequence starts with API orientation and also covers walls, pacing, directions,
map transitions, custom rendering, lettering, and the MapDataSource design seam.
Runnable examples live alongside the existing Java examples.

This classroom pass repairs directional sensors, stationary beeper storage,
stack colors, map wall lengths, and existing event notifications. Invalid robot
actions now throw an exception so jGRASP can identify the failing line. Register
observers through `Arena.addListener` and remove them through `Arena.removeListener`.
Callbacks are synchronous; keep game control in your main loop.

## Changes made to this version:
* idioms based on C++ (like enums based on integer values) have been replaced with modern Java semantics (like Java's Enum, introduced in Java 1.5)
* XML Parsing was moved to use the built-in Java classes rather than the external Xerces library from Apache (makes the setup easier for the begining level class)
* Fixed the build system to be inline with larger organizational software development practices
  * Classes are built in an independent location; not next to java files.
  * There is an appropriately implemented 'clean' command, which removes all non-version-controlled build artefacts.
  * The jar file is built entirely by the build process, not reliant on fragments of text files.
  * the version number of the library is semantic, and included in the jar file name.
* Documentation is now entirely built with Javadoc
  * Doxyfile is no longer used in this project.
  * (effort to redocument and include diagrams is in-progress)
  * rewrite documentation from scratch (in progress. As forked, had over 50 documentation warnings or errors)
* Names were changed to a more semantic metaphor, making it easier to learn
  * As an example, Robots no longer have 'coordinates' on a 'display', they have 'locations' in an 'arena'.
  * the concept of Display Speed has been changed to the Arena Pace.  There is a Pacing enumeration that also includes a step ability.
    * lots of integer mod checking math and edge case code evaporated with this change.
* Package hierarchy was collapsed.  A 'util' package that depends on and is depended by the parent provides no value, just a layer of confusion.
* XML Package was not collapsed, because a future mod is going to introduce an interface and a runtime dependency as an example for an architecure class.
* Unused files were removed.
* Unit Tests are in progress.
* Package hierarchy was renamed to clearly delineate this from the past non-semantically compatible library, and to remove the version number from the hierarchy name. This will make package evolution and upgrade easier in the future.
* New jar file is compatible with JRuby.
* Check the git commit comments for a more detailed changelog.

## Regarding Contributions:
Contributions are welcome!  As this is intended for a class on software architecture, the impact on pull requests will be considered, so contrbutions will not be blinfly accepted.

To contribute:
* fork this project on github.
* make your change, ideally tracking it to an issue you have added to the repo.
* Commits should follow the Chris Beam's "How to Write a Commit Message"
  * https://chris.beams.io/posts/git-commit/#imperative
  * with an overriding opinion on Rule 5.  While Git commit messages that are imperative will be accepted, this project prefers commit subject lines written by humans start with a *present tense action verb*, and answer the question, "What would this commit do if I cherry-picked it into my current code?"
* make sure you add your name to the list of contributors.  While github tracks that, I hope to eventually embed attribution into the jar file manifest.
* submit a pull request.

## Unit Tests:
With the commit that includes this comment, this build has now integrated JUnit4. There are many unit tests to write.

JUnit 5 was available at the time this was added to the project, but getting it to integrate with ant was troublesome. JUnit 5 introduces an entirely new way to write unit tests, and the JUnit 4 was more in-style with the way Java is taught with this library.  These two facts made it an easy choice to use JUnit 4 at this time.

* https://github.com/junit-team/junit4

## ToDo:
* redesign curriculum to use new classes and features.
  * While this code was licensed under the GPL, Fairfax County's original curriculum was not available under a permissible license. Therefore, we are going to go back to the source and create new curriculum aligned with the following resources:
    * Karel the Robot: A Gentle Introduction to the Art of Programming, by Richard E. Pattis
    * Big Java, Early Objects, 6th edition, by Cay Horstmann
    * AP Computer Science A Course and Exam Description: (https://apcentral.collegeboard.org/pdf/ap-computer-science-a-course-and-exam-description.pdf)
    * Oracle's Java Technology Certification Path: https://education.oracle.com/oracle-certification-path/pFamily_48
* Add a maze generation package based on the book "Mazes For Programmers", so that maze generating algorithms can be studied in an algorithms course.
* Design curriculum for a software architecture course.
  * example: Strategy Pattern in the Pacing Enum
  * Inversion/Refactoring of Dependency in the XML package
  * Interface for ArenaData, with a the xml library and a maze generation library as data producers.
  * more advanced robots, ala RoboCode or RTanque?
  * Curriculum using JRuby?
* Make pluggable tile packs, similar to Minecraft, so that the arena, walls, beepers, and robots can have different looks.
* Provide a gateway to things like IBM's RoboCode
* Finish refactorings that are in-flight
* Write unit tests
* Add back javadoc worthy of beginning programmers
* In-class, students seem to have issues with the relative directory path when opening map files. I'd like to redo this so that a bunch of maps for the new curriculum are stored as part of thejar file and defined as constants, so students can open maps without having to reference the file system at all.  It would certainly simplify the setup on systems like Nutanix Frame.

### Contributors to this version, maintained by the nonprofit LoudounCodes:
* David Bock(@bokmann)

### Previous Contributors to FCPSKarel, on which this work is based:

* Andy Street <alstreet@vt.edu> 2007 Original author of FCPS Karel. Thank you for the foundational work.
* Daniel Johnson <2010djohnson@tjhsst.edu> Summer 2009 Major contributions before we got our grubby little hands on it.
* Craig Saperstein <cmsaperstein@gmail.com>
