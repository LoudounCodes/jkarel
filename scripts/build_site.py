#!/usr/bin/env python3
# GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
"""Assemble the Pages artifact after `ant build-all build-starters`."""
from pathlib import Path
import shutil
import subprocess
from html.parser import HTMLParser
from urllib.parse import unquote, urlsplit

ROOT = Path(__file__).resolve().parent.parent
DEST = ROOT / 'out/site'


class Links(HTMLParser):
    def __init__(self):
        super().__init__()
        self.targets = []

    def handle_starttag(self, tag, attrs):
        self.targets.extend(value for key, value in attrs
                            if key in ('href', 'src') and value)


def main():
    package = ROOT / 'out/LoudounCodes-Karel-Starter-Labs.zip'
    pdf = ROOT / 'docs/labs/LoudounCodes-Karel-Extension-Labs.pdf'
    if not package.exists() or not (ROOT / 'out/docs/index.html').exists():
        raise SystemExit('Run ant build-all build-starters first.')
    if DEST.exists():
        shutil.rmtree(DEST)
    shutil.copytree(ROOT / 'site', DEST)
    for folder in ('downloads', 'assets', 'licenses'):
        (DEST / folder).mkdir(exist_ok=True)
    for source in (package, pdf):
        shutil.copy2(source, DEST / 'downloads' / source.name)
    for name in ('loudouncodes-logo.png', 'team-trails.png'):
        shutil.copy2(ROOT / 'docs/labs/assets' / name, DEST / 'assets' / name)
    shutil.copytree(ROOT / 'out/docs', DEST / 'javadoc')
    # Preserve the historical API reference address when replacing the old site.
    shutil.copytree(ROOT / 'out/docs', DEST / 'out/docs')
    shutil.copy2(ROOT / 'out/jkarel-1.0.0.jar', DEST / 'out/jkarel-1.0.0.jar')
    for name in ('LICENSE.TXT', 'NON-ENDORSEMENT.TXT', 'CURRICULUM-LICENSE.TXT', 'LICENSING.md'):
        shutil.copy2(ROOT / name, DEST / 'licenses' / name)
    revision = subprocess.check_output(['git', 'rev-parse', '--short', 'HEAD'], cwd=ROOT, text=True).strip()
    page = DEST / 'index.html'
    text = page.read_text().replace('@ZIP_SIZE@', f'{package.stat().st_size / 1_000_000:.1f} MB')
    text = text.replace('@PDF_SIZE@', f'{pdf.stat().st_size / 1_000_000:.1f} MB').replace('@REVISION@', revision)
    page.write_text(text)
    (DEST / '.nojekyll').touch()
    for page in DEST.rglob('*.html'):
        parser = Links()
        parser.feed(page.read_text())
        for target in parser.targets:
            url = urlsplit(target)
            if not url.scheme and url.path:
                local = (page.parent / unquote(url.path)).resolve()
                if not local.is_relative_to(DEST.resolve()) or not local.exists():
                    raise SystemExit(f'Broken local link: {page.relative_to(DEST)} -> {target}')
    print(f'Pages artifact ready: {DEST}; all local HTML resource links resolve.')


if __name__ == '__main__':
    main()
