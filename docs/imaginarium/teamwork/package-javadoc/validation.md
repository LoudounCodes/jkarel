# Package Javadoc validation

The package-javadoc process-flow.yml governs this local documentation change.
It validated before editing and at closeout. The checkout was clean at the start.

Replaced placeholders in the core, demo, and XML package descriptions. The core
page leads from a working robot program to maps, world objects, events, and the
implementation's teaching abstractions. The demo explains lettering and how to
run the standard jar. The XML page includes a map and explains parsing/reflection.
Library APIs, Ant structure, locked curriculum prose, and published assets remain
unchanged.

Checks passed using the available Homebrew JDK and the existing Java 18 target:

- `ant build-docs`: generated all three package summaries successfully.
- `ant build-src`: compilation passed.
- Generated HTML inspection: all 109 local links and fragments on the three
  package pages resolve; placeholder text is gone.
- The FirstRobot example was extracted from Javadoc, compiled with `--release 18`,
  and executed headlessly.
- The XML map was extracted from Javadoc and loaded headlessly. Its 6-by-4 size,
  beeper placement, and east-side vertical wall were verified through the API.
- `git diff --check`: passed.

Javadoc reports 100 warnings in existing member documentation, including missing
comments and parameter descriptions. No package-info warning or Javadoc error
appears in the output. This change does not claim complete member-level Javadoc
quality or GUI acceptance. No behavior regression suite was needed for comment-only
source edits. The smoke examples and generated pages are ignored local build output.

Proof level: single-author local automated validation and code inspection.
The initial local pass did not include publication or independent review.
Publication was subsequently authorized and verified as recorded below. See out/docs/org/loudouncodes/jkarel/package-summary.html
for the local core-package preview.

## Authorized publication

Bock subsequently authorized publication. The process flow was updated and
validated before pushing and again after deployment. Published source revision:
`9f2d831c7706cbb5aa224b835f1d5bbfe25c676f`.

GitHub Actions run https://github.com/LoudounCodes/jkarel/actions/runs/37881083709
completed successfully: Java 18 build and tests, relocated starter verification,
site assembly, upload, and Pages deployment.

Live HTTP verification passed for core, demo, and XML package summaries under
both `/jkarel/javadoc/` and the historical `/jkarel/out/docs/` address. Each page
contains its new section heading and no placeholder. The downloaded starter ZIP
passes integrity checking and contains byte-identical copies of those three live
Javadoc pages. The published PDF remains byte-identical to the locked local PDF,
SHA-256 `c4ec178cad083d9ddab630f7a96033806052516682b06cbbb5795ee717760aec`.

Proof is single-author automated validation plus live HTTP verification.
Independent review and classroom acceptance remain unclaimed. The scope boundary
permits this JKarel publication and excludes unrelated changes.
