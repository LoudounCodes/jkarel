# Public curriculum site validation

Bock requested a public GitHub Pages curriculum site with a download link and
professional polish. The existing site serves a Jekyll rendering of an older
repository README from gh-pages. The replacement uses a responsive static
landing page, the selected LoudounCodes logo, and the actual TeamTrails capture.
It links all ten lessons into their existing PDF pages, provides the complete
classroom ZIP and API reference, and preserves the historical out/docs API
reference and out/jkarel-1.0.0.jar addresses. Locked PDF prose and Ant build are
unchanged. There are no external font, script, or analytics dependencies.

The assembler uses the committed PDF and freshly built ZIP/Javadoc. A pinned
GitHub Actions workflow builds with Java 18 and Ant, runs regression tests and
relocated starter verification, checks local site links, then deploys the Pages
artifact. Subsequent master pushes affecting publishable content trigger updates.

Local evidence: Ant passes 17 tests. All ten independent starters and completed
references compile for Java 18 and run after ZIP relocation. Package, license,
Javadoc and Windows-path checks pass. Chromium desktop (1440 px) and mobile
(390 px) checks verify navigation, all ten lesson links, and no horizontal
overflow. Both layouts were visually inspected. Hosting under a subdirectory
and the actual browser ZIP download were checked; the download matches the
built archive. Every local HTML resource link resolves.

Single-author automated and browser proof. Live publication verification follows
a successful remote workflow run. Actual Windows classroom execution and
interactive STEP acceptance remain pending.
