#!/usr/bin/env python3
# GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
"""Check the classroom ZIP after relocation, using only its local JAR and maps.

Run `ant build-starters`, then `python3 scripts/verify_starters.py`.
Requires a Java 18+ JDK. GUI and STEP console interaction are separate checks.
"""
from pathlib import Path, PurePosixPath
import re
import argparse
import os
import subprocess
import tempfile
from html.parser import HTMLParser
from urllib.parse import urlsplit, unquote
from zipfile import ZipFile

ROOT = Path(__file__).resolve().parent.parent
EXPECTED = {
    'WelcomeArena': ('Inventory: 2', 'Inventory: 1'),
    'TeamTrails': ('Red inventory: 5, Blue inventory: 5', 'Both teams left five beepers'),
    'EventScoreboard': ('Final: Red 0, Blue 0, moves 0', 'Final: Red 2, Blue 2, moves 8'),
    'RoomBuilder': ('Player [4, 3], inventory 0', 'Player [5, 3], inventory 1'),
    'PaceProbe': ('[4, 4] NORTH', '[4, 4] NORTH'),
    'ScoutMoves': ('Scout [3, 4] NORTH', 'Scout [3, 3] EAST'),
    'MapStages': ('New wall blocks front: false', 'New wall blocks front: true'),
    'CustomItems': ('Reached the goal at [4, 2]', 'Reached the goal at [4, 2]'),
    'RobotLettering': ('Infinite inventory: true', 'Infinite inventory: true'),
    'DescribeAMap': ('Player [4, 2], inventory 0', 'Player [4, 2], inventory 2'),
}


def run(command, cwd):
    result = subprocess.run(command, cwd=cwd, capture_output=True, text=True, timeout=30)
    if result.returncode:
        raise RuntimeError(f'{command[0]} failed in {cwd}:\n{result.stdout}{result.stderr}')
    return result.stdout


def check_project(path):
    """Check native section lengths and relative source references."""
    assert b'\r' not in path.read_bytes(), f'Project serialization must retain LF: {path}'
    text = path.read_text()
    while text:
        header = re.match(r'!(\d+);([a-z])\n', text)
        assert header, f'Invalid project section: {path}'
        size = int(header[1])
        text = text[header.end():]
        section, text = text[:size], text[size:]
        assert len(section) == size, path
        if header[2] == 'f':
            assert section == f'%\\011{path.stem}.java\n', path
    assert 's#k0jkarel.jar\\000\n' in path.read_text(), path
    assert 's#j0jkarel.jar\n' in path.read_text(), path
    assert '/Users/' not in path.read_text() and ':\\' not in path.read_text(), path


def check_windows_archive(archive):
    """Reject ZIP names that cannot safely round-trip through Windows extraction."""
    reserved = {'CON', 'PRN', 'AUX', 'NUL'} | {
        f'{prefix}{number}' for prefix in ('COM', 'LPT') for number in range(1, 10)
    }
    seen = set()
    for entry in archive.infolist():
        name = entry.filename.rstrip('/')
        path = PurePosixPath(name)
        assert not path.is_absolute() and '..' not in path.parts, name
        assert name.casefold() not in seen, f'Case-insensitive ZIP collision: {name}'
        seen.add(name.casefold())
        for part in path.parts:
            assert not re.search(r'[<>:"\\|?*\x00-\x1f]', part), name
            assert not part.endswith((' ', '.')), name
            assert part.split('.')[0].upper() not in reserved, name
        # A representative Windows Downloads location, not a claim about every path.
        assert len(r'C:\Users\Student\Downloads\Karel Labs' + '\\' + name) < 260, name
    print('Windows ZIP checks passed: legal names, no case collisions, relative paths, LF project files.')


class DocumentationLinks(HTMLParser):
    def __init__(self):
        super().__init__()
        self.links = []

    def handle_starttag(self, tag, attrs):
        self.links.extend(value for key, value in attrs if key in ('href', 'src') and value)


def check_license_files(folder, names):
    for name in names:
        assert (folder / name).read_bytes() == (ROOT / name).read_bytes(), (folder, name)


def check_documentation(distribution, runtime_jar):
    readme = (distribution / 'README.md').read_text()
    assert 'https://github.com/LoudounCodes/jkarel' in readme
    for link in re.findall(r'\[[^\]]+\]\(([^)]+)\)', readme):
        url = urlsplit(link)
        if not url.scheme and url.path:
            assert (distribution / unquote(url.path)).exists(), link
    assert (distribution / 'jkarel.jar').read_bytes() == runtime_jar.read_bytes()
    check_license_files(distribution, ['LICENSE.TXT', 'NON-ENDORSEMENT.TXT', 'CURRICULUM-LICENSE.TXT', 'LICENSING.md'])
    check_license_files(distribution / 'library-source', ['LICENSE.TXT', 'NON-ENDORSEMENT.TXT', 'CURRICULUM-LICENSE.TXT', 'LICENSING.md'])
    assert 'CC BY 4.0' in readme and 'GPLv3' in readme
    assert (distribution / 'LoudounCodes-Karel-Extension-Labs.pdf').read_bytes() == (ROOT / 'docs/labs/LoudounCodes-Karel-Extension-Labs.pdf').read_bytes()
    docs = distribution / 'javadoc'
    assert (docs / 'index.html').exists()
    check_license_files(docs, ['LICENSE.TXT', 'NON-ENDORSEMENT.TXT'])
    pages = list(docs.rglob('*.html'))
    for page in pages:
        parser = DocumentationLinks()
        parser.feed(page.read_text())
        for link in parser.links:
            url = urlsplit(link)
            if not url.scheme and url.path:
                target = (page.parent / unquote(url.path)).resolve()
                assert target.is_relative_to(docs.resolve()), (page, link)
                assert target.exists(), (page, link)
    types = 0
    for source in (ROOT / 'src/main/org/loudouncodes').rglob('*.java'):
        content = source.read_text()
        if re.search(r'public\s+(?:(?:abstract|final)\s+)?(?:class|enum|interface)\s+' + re.escape(source.stem) + r'\b', content):
            package = re.search(r'package\s+([\w.]+)\s*;', content)[1]
            assert (docs / package.replace('.', '/') / (source.stem + '.html')).exists(), source
            types += 1
    print(f'Root README/JAR/PDF and Javadoc verified: {len(pages)} HTML pages, {types} public API types, all local page/resource links resolve.')


def main(output_dir):
    scratch = ROOT / 'build'
    scratch.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='relocated classroom ', dir=scratch) as temp:
        with ZipFile(output_dir / 'LoudounCodes-Karel-Starter-Labs.zip') as archive:
            check_windows_archive(archive)
            archive.extractall(temp)
        distribution = Path(temp) / 'starter-labs'
        check_documentation(distribution, output_dir / 'classroom/jkarel.jar')
        folders = sorted(distribution.glob('[0-9][0-9]-*'))
        assert len(folders) == 10
        for folder in folders:
            source, = folder.glob('*.java')
            project = source.with_suffix('.gpj')
            check_project(project)
            assert 'TODO' in source.read_text()
            check_license_files(folder, ['LICENSE.TXT', 'NON-ENDORSEMENT.TXT', 'CURRICULUM-LICENSE.TXT'])
            assert 'CC BY 4.0' in (folder / 'README.md').read_text()
            jar = folder / 'jkarel.jar'
            assert jar.read_bytes() == (output_dir / 'classroom/jkarel.jar').read_bytes()
            with ZipFile(jar) as library:
                names = library.namelist()
                for notice in ['LICENSE.TXT', 'NON-ENDORSEMENT.TXT']:
                    assert library.read(notice) == (ROOT / notice).read_bytes(), notice
                for name in names:
                    if name.endswith('.class'):
                        assert int.from_bytes(library.read(name)[6:8], 'big') <= 62, name
                assert not any('Tests' in n or n.endswith('.java') for n in names)
                assert not any(n.startswith(('docs/', 'examples/')) for n in names)
            classes = folder / 'classes'
            classes.mkdir()
            for index, program in enumerate([source, ROOT / 'examples/java' / source.name]):
                run(['javac', '--release', '18', '-cp', str(jar), '-d', str(classes), str(program)], folder)
                arguments = ['--auto'] if source.stem == 'PaceProbe' else []
                output = run(['java', '-Djava.awt.headless=true', '-cp',
                              str(classes) + os.pathsep + str(jar), source.stem, *arguments], folder)
                assert EXPECTED[source.stem][index] in output, (source, output)
                if source.stem == 'MapStages' and index == 1:
                    assert 'Loaded: ./stage-one.map' in output and 'Loaded: ./stage-two.map' in output
            print(f'{folder.name}: starter and completed reference passed')
        assert (distribution / 'LoudounCodes-Karel-Extension-Labs.pdf').exists()
        assert (distribution / 'library-source/src/main/org/loudouncodes/jkarel/Robot.java').exists()
        print('10 independent folders verified after ZIP extraction to a path containing spaces.')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output-dir', type=Path, default=ROOT / 'out')
    main(parser.parse_args().output_dir.resolve())
