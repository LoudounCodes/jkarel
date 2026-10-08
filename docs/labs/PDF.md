# Branded classroom handout

[LoudounCodes Karel Extension Labs](LoudounCodes-Karel-Extension-Labs.pdf) combines
both original labs with jGRASP setup, complete example listings, teacher notes,
and source citations. It is formatted on US Letter paper with internal navigation
links, external source links, page numbers, and the existing LoudounCodes logo.

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

Validation: eight pages; body text and both complete Java listings inspected after
PDF rendering; jGRASP and license citations retained as clickable links. The PDF
was opened locally with macOS `open` for Bock's review.
