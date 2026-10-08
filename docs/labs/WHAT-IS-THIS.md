# About this library and curriculum

LoudounCodes JKarel extends the familiar Karel classroom environment into a setting
for student-designed games, simulations, and visual projects. It is a modernization
of the original Fairfax County Public Schools Karel library, with clearer API
names, support for newer Java versions, and additional ways to construct and
control the world. This edition targets Java 18 and retains the Ant build and
jGRASP workflow used in the classroom.

Karel's small world gives students a useful foundation: actions have visible
consequences, sensors support decisions, and a short program can express a complete
solution. After students master those ideas, their questions often grow beyond the
original assignments. Can a door open? Can two teams have different colors? Can the
program keep score when a robot collects a beeper? This library gives those
questions a practical place in the next stage of learning Java.

## A clearer interface and a richer world

The API uses names that express the model students are working with. An `Arena`
is the shared world; a `Location` identifies a grid position; a `Direction`
represents a robot's facing. Calls such as `Arena.addNorthWall` and
`Arena.setPace` read naturally as operations on that world. This more fluent
vocabulary helps students distinguish scene construction from the actions of an
individual robot, while typed directions and named pacing values make choices
explicit in their code.

The added capabilities support projects whose worlds change during play. Students
can build walls and beeper supplies programmatically, add or remove a door,
assign colors to robots and beeper trails, and load another map while retaining a
player. Listeners let a scorekeeper or another observer respond to movement and
beeper activity. Custom items and robot drawing provide a way to give a project
its own visual vocabulary. Students still write the rules, choose the sequence of
actions, and decide what each event means in their game.

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

Work through the sequence as a progression, or choose lessons to support an
existing project. A maze may need changing walls; a team game may need colors and
event scoring; a visual project may need custom rendering. The final map-interface
lesson is an advanced design exercise that asks students to connect a declared
contract to their own implementation. Throughout, the aim is to help students
move from solving a supplied Karel problem to designing a program whose behavior
they can explain, test, and extend.
