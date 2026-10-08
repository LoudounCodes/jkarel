# About this library and curriculum

LoudounCodes JKarel extends the familiar Karel classroom environment into a setting
for student-designed games, simulations, and visual projects. It is a modernization
of the original Fairfax County Public Schools Karel library, with clearer API
names, support for newer Java versions, and additional ways to construct and
control the world. This edition targets Java 18 and retains the Ant build and
jGRASP workflow used in the classroom.

Karel gives students a foundation in actions, decisions, and visible consequences.
Once they master the original assignments, their questions grow: Can a door open?
Can teams have different colors? Can a robot's pickup change the score? This library
makes those questions part of the next stage of learning Java.

## A clearer interface and a richer world

The older API organized the world around a `Display`. That name suggests a
screen: something that shows the action. An `Arena` names the place where the
action happens. A robot belongs in an arena alongside walls, supplies, and other
robots; the display presents that world to the viewer. The distinction helps
students reason about the simulation separately from its presentation. Calls such
as `Arena.addNorthWall` express an operation in the world they are constructing.

`Pacing` makes a similar distinction. A display's “speed” leaves the reader to
interpret what is moving and what the number means. An arena's pace describes
how its activity unfolds. Like a pace car governing a race, pacing regulates the
rhythm of the action. Named values such as `Pacing.SLOW` and `Pacing.STEP` make
that intent visible: students control when actions proceed while the rules of
movement stay the same.

These choices establish a mental model. Object-oriented programming lets us
represent a real or imagined world through objects with recognizable roles,
responsibilities, and relationships. A coherent vocabulary helps programmers
reason about the system and communicate their intentions. In a small classroom
program, that clarity makes the next line easier to write. In a project maintained
by a dozen programmers over several years, the same clarity supports shared
understanding as people and requirements change. Small API decisions become
part of the structure on which larger programs depend.

Students can build walls and supplies programmatically, open doors by removing
walls, color robots and beeper trails, and change maps while retaining a player.
Listeners let a scorekeeper respond to moves and pickups. Custom rendering gives
items and robots their own appearance. Students write the rules and decide what
each event means in their game.

## Java concepts with a visible purpose

The lessons introduce the concepts needed to use these capabilities. Enums describe
directions and pacing. Subclasses give robots additional behavior. Interfaces let
one object observe another, and rendering methods connect an object's role in the
world with its appearance. These ideas arise from concrete project needs, so
students can examine both the Java code and its visible result.

The library's internals also provide material for more advanced Java courses.
Teachers can trace an action from the public API into the model, examine how
listeners receive notifications, or discuss the responsibilities of an abstract
item and its subclasses. The introductory lessons stay close to the public
teaching interface; later work can use the implementation to investigate design
choices, boundaries, and opportunities for improvement.

## Using the curriculum

This sequence is intended for teachers and students who already know Karel or an
earlier version of JKarel. The opening lesson explains the changes in terminology
and API. The remaining lessons introduce individual capabilities, with a brief
concept explanation, exercises, and complete commented programs in the appendix.
The jGRASP setup instructions and teacher notes support classroom use.

Follow the sequence or choose lessons for a particular project. The final
map-interface lesson is an advanced design exercise connecting a declared contract
to a student's implementation. The aim throughout is to move from solving a
supplied Karel problem to designing a program students can explain, test, and extend.
