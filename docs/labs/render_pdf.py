#!/usr/bin/env python3
"""Render the original Markdown labs and Java examples as a branded handout.

Run from any directory: uv run --with reportlab==4.4.10 docs/labs/render_pdf.py
Requires pandoc for Markdown parsing. No changes to the library's Ant build.
"""
from pathlib import Path
from html import escape
import json
import subprocess
import textwrap

from reportlab.lib import colors
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import ParagraphStyle
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, PageBreak, Preformatted, Image, Table, TableStyle
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

HERE = Path(__file__).resolve().parent
ROOT = HERE.parent.parent
OUTPUT = HERE / 'LoudounCodes-Karel-Extension-Labs.pdf'
GREEN = colors.HexColor('#27733b')
INK = colors.black
FONT = 'Helvetica'
BOLD = 'Helvetica-Bold'
ITALIC = 'Helvetica-Oblique'
font_dir = Path('/System/Library/Fonts/Supplemental')
if (font_dir / 'Arial.ttf').exists():
    for name, filename in [('Handout', 'Arial.ttf'), ('HandoutBold', 'Arial Bold.ttf'), ('HandoutItalic', 'Arial Italic.ttf')]:
        pdfmetrics.registerFont(TTFont(name, str(font_dir / filename)))
    pdfmetrics.registerFontFamily('Handout', normal='Handout', bold='HandoutBold', italic='HandoutItalic', boldItalic='HandoutBold')
    FONT, BOLD, ITALIC = 'Handout', 'HandoutBold', 'HandoutItalic'

body = ParagraphStyle('Body', fontName=FONT, fontSize=10, leading=13, textColor=INK, spaceAfter=6)
h1 = ParagraphStyle('Title', parent=body, fontName=BOLD, fontSize=22, leading=27, textColor=INK, spaceAfter=16, keepWithNext=True)
h2 = ParagraphStyle('Heading', parent=body, fontName=BOLD, fontSize=13, leading=16, textColor=GREEN, spaceBefore=8, spaceAfter=5, keepWithNext=True)
code = ParagraphStyle('Code', fontName='Courier', fontSize=8.0, leading=8.8, spaceBefore=5, spaceAfter=13)
small = ParagraphStyle('Small', parent=body, fontSize=9, leading=13)
bullet = ParagraphStyle('Bullet', parent=body, leftIndent=17, firstLineIndent=-12, spaceAfter=6)
LESSONS = [
    ('00-meet-jkarel.md', 'WelcomeArena.java', 'Names, types, and the Arena API'),
    ('01-team-trails.md', 'TeamTrails.java', 'Robot and beeper colors'),
    ('02-event-scoreboard.md', 'EventScoreboard.java', 'Interfaces and event scoring'),
    ('03-build-a-room.md', 'RoomBuilder.java', 'Build walls, supplies, and doors'),
    ('04-predict-with-pacing.md', 'PaceProbe.java', 'Pacing and STEP mode'),
    ('05-directions-and-retreat.md', 'ScoutMoves.java', 'Directions and protected behavior'),
    ('06-change-levels.md', 'MapStages.java', 'Load maps and change levels'),
    ('07-draw-your-own-items.md', 'CustomItems.java', 'Custom items and robot rendering'),
    ('08-robot-lettering.md', 'RobotLettering.java', 'AlphaBot lettering and infinite supplies'),
    ('09-describe-a-map.md', 'DescribeAMap.java', 'MapDataSource design extension'),
]
links = {'README.md': '#setup', 'FEATURE-MAP.md': '#featuremap',
         '../../LICENSE.TXT': 'https://github.com/LoudounCodes/jkarel/blob/master/LICENSE.TXT'}
for number, (markdown, source, _) in enumerate(LESSONS):
    links[markdown] = '#lab' + str(number)
    links['../../examples/java/' + source] = '#source' + str(number)

def inline(nodes):
    result = []
    for node in nodes:
        kind, value = node['t'], node.get('c')
        if kind == 'Str': result.append(escape(value))
        elif kind in ('Space', 'SoftBreak', 'LineBreak'): result.append(' ')
        elif kind == 'Code': result.append('<font name="Courier" size="9">' + escape(value[1]) + '</font>')
        elif kind in ('Strong', 'Emph'): result.append(('<b>' if kind == 'Strong' else '<i>') + inline(value) + ('</b>' if kind == 'Strong' else '</i>'))
        elif kind == 'Link':
            url = links.get(value[2][0], value[2][0])
            result.append('<link href="' + escape(url, quote=True) + '" color="#27733b">' + inline(value[1]) + '</link>')
        elif kind == 'Quoted': result.append('“' + inline(value[1]) + '”')
        else: raise ValueError('Unsupported inline: ' + kind)
    return ''.join(result)

def parse(path):
    return json.loads(subprocess.check_output(['pandoc', str(path), '-f', 'markdown', '-t', 'json']))['blocks']

def blocks(nodes, anchor=None):
    result = []
    for node in nodes:
        kind, value = node['t'], node.get('c')
        if kind in ('Para', 'Plain'):
            result.append(Paragraph(inline(value), body))
        elif kind == 'Header':
            heading = inline(value[2])
            if value[0] == 1 and anchor:
                heading = '<a name="' + anchor + '"/>' + heading
            if anchor == 'lab2' and heading == 'Callback rules':
                result.append(PageBreak())
                heading = 'Event scoreboard: callback rules'
                result.append(Paragraph(heading, h1))
            else:
                result.append(Paragraph(heading, h1 if value[0] == 1 else h2))
        elif kind == 'CodeBlock':
            lines = []
            for line in value[1].splitlines():
                # Long shell commands wrap with continuations that remain runnable.
                if value[0][1] == ['sh'] and len(line) > 95:
                    wrapped = textwrap.wrap(line, width=90, subsequent_indent='    ', break_long_words=False, break_on_hyphens=False)
                    lines.extend(part + (' \\' if i < len(wrapped)-1 else '') for i, part in enumerate(wrapped))
                else: lines.append(line)
            result.append(Preformatted('\n'.join(lines), code))
        elif kind in ('BulletList', 'OrderedList'):
            items = value if kind == 'BulletList' else value[1]
            for i, item in enumerate(items, 1 if kind == 'BulletList' else value[0][0]):
                prefix = '•' if kind == 'BulletList' else str(i) + '.'
                text = ' '.join(inline(part['c']) for part in item if part['t'] in ('Para', 'Plain'))
                result.append(Paragraph(prefix + '  ' + text, bullet))
        elif kind == 'Table':
            rows = list(value[3][1])
            for table_body in value[4]:
                rows += table_body[2] + table_body[3]
            data = []
            for row in rows:
                cells = []
                for cell in row[1]:
                    text = ' '.join(inline(part['c']) for part in cell[4] if part['t'] in ('Para', 'Plain'))
                    cells.append(Paragraph(text, small))
                data.append(cells)
            table = Table(data, colWidths=[516 / len(data[0])] * len(data[0]), repeatRows=1, hAlign='LEFT')
            table.setStyle(TableStyle([('VALIGN',(0,0),(-1,-1),'TOP'), ('LINEBELOW',(0,0),(-1,0),0.5,colors.grey),
                ('BOTTOMPADDING',(0,0),(-1,-1),6), ('TOPPADDING',(0,0),(-1,-1),6)]))
            result.append(table)
        else: raise ValueError('Unsupported block: ' + kind)
    return result

story = [Image(str(HERE/'assets/loudouncodes-logo.png'), 48, 48, hAlign='LEFT'), Spacer(1, 10),
         Paragraph('LoudounCodes Karel<br/>Creative Project Lessons', h1),
         Paragraph('Java 18 · jGRASP · Classroom review edition · October 2026', small),
         Paragraph('Start with the API orientation, then choose the features your own project needs. '
                   'Print the selected lesson pages; complete program listings and teacher notes follow separately.', body),
         Spacer(1, 8)]
for number, (_, _, title) in enumerate(LESSONS):
    story.append(Paragraph(str(number) + '. <link href="#lab' + str(number) + '" color="#27733b">' + title + '</link>', body))
story += [Spacer(1, 12), Paragraph('Predict. Run. Explain. Make it yours.', h2), PageBreak()]

setup = parse(HERE/'README.md')
split = next(i for i,b in enumerate(setup) if b['t']=='Header' and inline(b['c'][2])=='Teacher acceptance')
setup_start = next(i for i,b in enumerate(setup) if b['t']=='Header' and inline(b['c'][2])=='Set up in jGRASP')
setup[setup_start]['c'][0] = 1
setup[setup_start]['c'][2] = [{'t':'Str','c':'Set up in jGRASP'}]
story += blocks(setup[setup_start:split], 'setup')
for number, (markdown, _, _) in enumerate(LESSONS):
    story += [PageBreak()]
    story += blocks(parse(HERE/markdown), 'lab' + str(number))

story += [PageBreak(), Paragraph('Complete example programs', h1),
          Paragraph('Copy each listing into a file with the name shown and run it separately in jGRASP. '
                    'For MapStages, also copy both map files from the listings below into that working folder. '
                    'The same sources are in examples/java and examples/maps.', body)]
for number, (_, filename, _) in enumerate(LESSONS):
    if number: story.append(PageBreak())
    story.append(Paragraph('<a name="source'+str(number)+'"/>'+filename, h2))
    story.append(Preformatted((ROOT/'examples/java'/filename).read_text().rstrip(), code))
story.append(PageBreak())
story.append(Paragraph('Map files for the level-changing lesson', h1))
for filename in ['stage-one.map', 'stage-two.map']:
    story.append(Paragraph(filename, h2))
    story.append(Preformatted((ROOT/'examples/maps'/filename).read_text().rstrip(), code))
story += [PageBreak(), Paragraph('Teacher notes and sources', h1)]
story += blocks(setup[split:])
story.append(Paragraph('Brand asset: the selected master_logo.png from the personal Dropbox '
                       'Marketing/logos collection, copied unchanged. The artwork is retained in the repository; '
                       'the PDF uses one small placement for economical printing.', small))
story += [PageBreak()]
story += blocks(parse(HERE/'FEATURE-MAP.md'), 'featuremap')

def page(canvas, document):
    width, height = letter
    canvas.saveState()
    canvas.setStrokeColor(colors.HexColor('#d5ddd5')); canvas.line(43, 37, width-43, 37)
    canvas.setFillGray(0.35); canvas.setFont(FONT, 8)
    canvas.drawString(43, 24, 'LoudounCodes.org  •  Classroom review edition')
    canvas.drawRightString(width-43, 24, str(document.page))
    canvas.restoreState()

doc = SimpleDocTemplate(str(OUTPUT), pagesize=letter, leftMargin=48, rightMargin=48, topMargin=45, bottomMargin=53,
    title='LoudounCodes — Karel Creative Project Labs', author='LoudounCodes', subject='API orientation and feature lessons for JKarel in jGRASP')
doc.build(story, onFirstPage=page, onLaterPages=page)
print(OUTPUT)
