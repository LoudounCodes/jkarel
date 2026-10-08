#!/usr/bin/env python3
"""Check the classroom ZIP after relocation, using only its local JAR and maps.

Run `ant build-starters`, then `python3 scripts/verify_starters.py`.
Requires a Java 18+ JDK. GUI and STEP console interaction are separate checks.
"""
from pathlib import Path
import re
import os
import subprocess
import tempfile
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
    assert 'jkarel.jar' in path.read_text(), path
    assert '/Users/' not in path.read_text() and ':\\' not in path.read_text(), path


def main():
    scratch = ROOT / 'build'
    scratch.mkdir(exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='relocated classroom ', dir=scratch) as temp:
        with ZipFile(ROOT / 'out/LoudounCodes-Karel-Starter-Labs.zip') as archive:
            archive.extractall(temp)
        distribution = Path(temp) / 'starter-labs'
        folders = sorted(distribution.glob('[0-9][0-9]-*'))
        assert len(folders) == 10
        for folder in folders:
            source, = folder.glob('*.java')
            project = source.with_suffix('.gpj')
            check_project(project)
            assert 'TODO' in source.read_text()
            assert (folder / 'LICENSE.TXT').exists()
            jar = folder / 'jkarel.jar'
            assert jar.read_bytes() == (ROOT / 'out/classroom/jkarel.jar').read_bytes()
            with ZipFile(jar) as library:
                names = library.namelist()
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
    main()
