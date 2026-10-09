# Public curriculum site

The static landing page serves the committed curriculum PDF, a freshly built
classroom ZIP, and generated Javadoc. It uses the selected LoudounCodes artwork
and an actual TeamTrails arena capture. Curriculum prose and the Ant build stay
in their existing locations.

Build locally from the repository root:

```sh
ant build-all build-starters
python3 scripts/verify_starters.py
python3 scripts/build_site.py
python3 -m http.server 8000 --directory out/site
```

Open http://localhost:8000. The assembler verifies local HTML resource links.
All landing-page paths are relative so the same artifact works at `/jkarel/`.
The historical `/out/docs/` API paths remain available alongside `/javadoc/`.

`.github/workflows/pages.yml` builds, tests, verifies, and deploys on pushes to
master. GitHub Pages must use GitHub Actions as its publishing source, and the
github-pages environment must allow master. The workflow deploys the committed
PDF; regeneration and editorial review remain deliberate local steps.

Original site prose: © 2026 Bock / LoudounCodes, CC BY 4.0. Arena images follow
the library's GPLv3 license. The LoudounCodes logo is excluded from CC BY.
