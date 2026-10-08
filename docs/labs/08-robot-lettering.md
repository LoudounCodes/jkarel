# Lesson 8: A robot that draws letters

Use an existing Robot subclass to turn a short message into beeper art. Then
inspect how a larger behavior is composed from familiar robot actions.

## Predict and run

Compile [RobotLettering.java](../../examples/java/RobotLettering.java). It imports
`AlphaBot` from `org.loudouncodes.jkarel.demo`, opens a blank 24-by-12 arena,
sets a fast pace, and writes `HI` in blue beepers.

```java
AlphaBot sign = new AlphaBot();
sign.setColor(Color.BLUE);
sign.say("HI");
```

AlphaBot starts at `[2, 1]`, facing north, with `BeeperStack.INFINITY` beepers.
Its inventory stays infinite after drawing. This sentinel represents an
unlimited supply; it is not an ordinary negative inventory. A robot can use
an infinite starting inventory, and a map can contain an infinite stack.

Each character is drawn using an 8-by-8 bitmap. Start with uppercase ASCII letters
and a short message on an empty arena. The current font table accepts characters
with codes 0 through 127; other characters are not supported. Walls can obstruct
the drawing route, and the library does not expand the visible grid for a longer
message automatically. Increase its width before drawing more letters.

## Investigate and extend

1. Write a two-letter initial or short ASCII word. Predict the space it needs.
2. Change color between calls to `say`. Existing letters keep their stack colors.
3. Read `say`, `drawLetter`, and `scanline` in AlphaBot. Explain how one public
   operation delegates to several small private methods and ordinary robot verbs.
4. Sketch an 8-by-8 symbol on paper, then write your own small drawing robot for
   a trophy or team emblem. Begin with loops and beepers before bit operations.

## Show what you learned

Show your message, explain why the inventory does not run out, and trace one
part of the drawing into moves and drops. Identify a limitation of AlphaBot and
a change you would need before using it as a general message renderer.

Teacher prompt: distinguish an unlimited inventory from a scoreboard's numeric
count. This is a worked example of inheritance and decomposition, not a requirement
that students use the font-table implementation in their own game.
