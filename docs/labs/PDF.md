# Branded classroom handout

[LoudounCodes Karel Extension Labs](LoudounCodes-Karel-Extension-Labs.pdf) combines
an opening API lesson and nine feature lessons with jGRASP setup, complete example listings, teacher notes,
and source citations. It is formatted on US Letter paper with internal navigation
links, external source links, page numbers, and a dedicated cover with the selected
LoudounCodes logo, followed by the lesson plan and a full-page introduction to the library and curriculum. Every lesson begins with a capability, concept, or terminology
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

Print two-sided on US Letter, flipping on the **long edge**, at actual size.
Keep the marked blank pages; disabling a printer's blank-page skipping preserves
the intended page order. The cover is page 1, lesson plan 3, introduction 5, and setup 6.
Pages 2 and 4 are intentional blank backs. Every lesson starts on a right-hand
page and occupies two pages: orientation 7–8; colors 9–10; events 11–12;
walls 13–14; pacing 15–16; directions/retreat 17–18; map transitions 19–20;
custom rendering 21–22; lettering 23–24; map-source design 25–26.
Complete code/maps are 27–38; teacher notes are 39; page 40 is intentionally
blank; feature coverage is 41–42. These ranges also support printing a single lab.

Validation: 42 pages, including three intentional blanks. All ten lesson starts
are odd-numbered; each lesson is exactly two pages. Original small robot line
illustrations appear on every instructional and reference page after the
introduction, with subjects drawn from the adjacent lesson or code. The cover
uses the logo. Wider line spacing and a reserved white illustration area reduce
visual density without filled panels. Representative introduction, lesson,
continuation, code, and blank pages were inspected after rendering. Internal
navigation and external citations are retained. Both Ant distributions contain
the current PDF. Classroom acceptance remains pending.

## Illustration sources

`page_art.py` draws the small subject-specific robot vignettes directly into the
PDF; `render_pdf.py` tracks page subjects and inserts blank versos automatically.
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

## Licensing

The cover and teacher references identify CC BY 4.0 for original lesson text and
illustrations, with credit to Bock / LoudounCodes. Java code and arena screenshots
remain GPLv3; the logo is excluded from the CC BY grant. The full scope and
suggested attribution are in [LICENSING.md](../../LICENSING.md). Both Ant
distributions include the applicable license texts and non-endorsement notice.
