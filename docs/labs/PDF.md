# Branded classroom handout

[LoudounCodes Karel Extension Labs](LoudounCodes-Karel-Extension-Labs.pdf) combines
an opening API lesson and nine feature lessons with jGRASP setup, complete example listings, teacher notes,
and source citations. It is formatted on US Letter paper with internal navigation
links, external source links, page numbers, and a dedicated cover with the selected
LoudounCodes logo, followed by a “What is this?” introduction. Every lesson begins with a capability, concept, or terminology
label and an explanatory sidebar. Body text is black; green headings, links, and
thin sidebar rules provide limited spot color. The appendix examples include
comments explaining the relevant behavior.
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

Print only the lesson needed: orientation is page 5; colors 6; events 7–8;
walls 9; pacing 10; directions/retreat 11; map transitions 12; custom rendering 13;
lettering 14; map-source design 15. The cover, introduction, guide, and setup are
pages 1–4; complete code/maps are 16–26; teacher notes and feature coverage are 27–29.

Validation: 29 pages; capability labels checked in extracted text, and the cover,
sidebars, and commented example layout inspected after
PDF rendering; jGRASP and license citations retained as clickable links. The PDF
was opened locally with macOS `open` for Bock's review. All ten commented
Java examples compile with `javac --release 18` against the packaged library.
