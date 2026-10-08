# Branded classroom handout

[LoudounCodes Karel Extension Labs](LoudounCodes-Karel-Extension-Labs.pdf) combines
an opening API lesson and nine feature lessons with jGRASP setup, complete example listings, teacher notes,
and source citations. It is formatted on US Letter paper with internal navigation
links, external source links, page numbers, and a dedicated cover with the selected
LoudounCodes logo, followed by a full-page introduction to the library and curriculum. Every lesson begins with a capability, concept, or terminology
label and an explanatory sidebar. Body text is black; green headings, links, and
sidebar accent edges provide limited spot color. Sidebars have inset text and
a light outline on a white background. The appendix examples include
comments explaining the relevant behavior. Each lesson also includes a captioned
figure: original vector diagrams, actual arena views, or both.
There are no page headers or filled panels.

Regenerate from the Markdown and Java sources without changing the Ant build:

```sh
uv run --with reportlab==4.4.10 docs/labs/render_pdf.py
```

Requires Python, uv, and pandoc. The renderer embeds Arial when available on
macOS and uses PDF standard Helvetica otherwise. The rendered PDF is committed
alongside its source material for review and printing.

The logo is copied unchanged from Bock's personal Dropbox:
`2-areas/loudouncodes/2-areas/Marketing/logos/master_logo.png`.
It is the selected master artwork, copied unchanged. The
Dropbox original remains untouched.

Print only the lesson needed: orientation is pages 5–6; colors 7–8; events 9–10;
walls 11–12; pacing 13; directions/retreat 14–15; map transitions 16–17; custom
rendering 18–19; lettering 20–21; map-source design 22–23. The cover, introduction,
guide, and setup are pages 1–4; complete code/maps are 24–34; teacher notes and
feature coverage are 35–37.

Validation: 37 pages; capability labels checked in extracted text, and the cover,
sidebars, lesson figures, and commented example layout inspected after
PDF rendering; jGRASP and license citations retained as clickable links. The PDF
was opened locally with macOS `open` for Bock's review. All ten commented
Java examples compile with `javac --release 18` against the packaged library.

## Illustration sources

`illustrations.py` draws the diagrams directly into the PDF as vector graphics.
The timing and layout diagrams are schematic explanations of the current API.
The screenshot assets are captured from the actual Swing arena. TeamTrails and
RobotLettering run the complete examples; the custom-item pair instantiates the
example's own Goal and Player classes to capture before and after removal.
The custom views are details captured directly from Swing for print legibility;
no pixels are altered after capture.

Regenerate the arena views from the repository root:

```sh
ant build-starters
mkdir -p build/illustration-capture
javac --release 18 -cp out/classroom/jkarel.jar -d build/illustration-capture \
    scripts/CaptureLabViews.java examples/java/TeamTrails.java \
    examples/java/CustomItems.java examples/java/RobotLettering.java
java -cp build/illustration-capture:out/classroom/jkarel.jar \
    org.loudouncodes.jkarel.CaptureLabViews TeamTrails
java -cp build/illustration-capture:out/classroom/jkarel.jar \
    org.loudouncodes.jkarel.CaptureLabViews CustomItems
java -cp build/illustration-capture:out/classroom/jkarel.jar \
    org.loudouncodes.jkarel.CaptureLabViews RobotLettering
```

Capture requires a graphical Java session. These commands use the macOS/Linux
classpath separator; use `;` on Windows. Re-render the PDF after capture, then
run `ant build-starters` again to update the classroom archive.
