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

## Publication and live verification

Published master through 8cba0f516384586705be1f51a412302dd355d549. GitHub Pages
uses Actions as its publishing source; its existing github-pages environment
now allows master alongside gh-pages. The existing gh-pages branch is retained.
Workflow run https://github.com/LoudounCodes/jkarel/actions/runs/37875522813
completed successfully: Java 18 build/tests, all relocated starter/reference
checks, static-site assembly, upload, and deployment.

Live site: https://loudouncodes.github.io/jkarel/
Live ZIP: https://loudouncodes.github.io/jkarel/downloads/LoudounCodes-Karel-Starter-Labs.zip
Live PDF: https://loudouncodes.github.io/jkarel/downloads/LoudounCodes-Karel-Extension-Labs.pdf

The public landing page reports deployed revision 8cba0f5. Live Chromium desktop
and mobile checks pass: all ten lesson links are present, assets return successful
responses, navigation works, and there is no horizontal overflow. Screenshot
evidence is local under ignored build/site-review. The public PDF is byte-for-byte
identical to the reviewed committed PDF. The downloaded ZIP passes its integrity
check, includes ten GPJ projects, the same reviewed PDF, exact current library
Java sources, and corrected STEP Javadoc. New and historical API-reference URLs
and the historical library JAR URL return HTTP 200.

Live PDF SHA256: c4ec178cad083d9ddab630f7a96033806052516682b06cbbb5795ee717760aec
Live ZIP SHA256: 8acb289a7a8016afbc595ae4225c3ba135678ff7b59e20140ae983324d224b5e

2026-10-09T02:40:54Z: Are we doing the right thing right now? Yes: the site and
public downloads are verified. Close out the publication; further infrastructure
changes would not improve the requested classroom download.

Single-author local and live automated/browser proof, plus successful remote
GitHub Actions evidence. Windows classroom execution and interactive STEP input
remain human acceptance checks. No custom domain, paid service, or analytics
configuration was introduced.

Both live PDF and ZIP hashes also match the github-pages deployment artifact
downloaded from the successful workflow run.
