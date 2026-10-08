# Branded classroom handout

[LoudounCodes Karel Extension Labs](LoudounCodes-Karel-Extension-Labs.pdf) combines
an opening API lesson and nine feature lessons with jGRASP setup, complete example listings, teacher notes,
and source citations. It is formatted on US Letter paper with internal navigation
links, external source links, page numbers, and the selected LoudounCodes logo once at
small size. Body text is black; green headings and links provide limited spot color.
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

Print only the lesson needed: orientation is page 3; colors 4; events 5–6;
walls 7; pacing 8; directions/retreat 9; map transitions 10; custom rendering 11;
lettering 12; map-source design 13. The opening and setup are pages 1–2;
complete code/maps are 14–24; teacher notes and feature coverage are 25–27.

Validation: 27 pages; body text and example listings inspected after
PDF rendering; jGRASP and license citations retained as clickable links. The PDF
was opened locally with macOS `open` for Bock's review.
