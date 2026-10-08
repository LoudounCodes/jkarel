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
from reportlab.lib.enums import TA_CENTER
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import ParagraphStyle
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, PageBreak, Preformatted, Image
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

HERE = Path(__file__).resolve().parent
ROOT = HERE.parent.parent
OUTPUT = HERE / 'LoudounCodes-Karel-Extension-Labs.pdf'
GREEN = colors.HexColor('#27733b')
GOLD = colors.HexColor('#d4a93d')
INK = colors.HexColor('#202824')
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
h1 = ParagraphStyle('Title', parent=body, fontName=BOLD, fontSize=22, leading=27, textColor=GREEN, spaceAfter=16, keepWithNext=True)
h2 = ParagraphStyle('Heading', parent=body, fontName=BOLD, fontSize=13, leading=16, textColor=GREEN, spaceBefore=8, spaceAfter=5, keepWithNext=True)
code = ParagraphStyle('Code', fontName='Courier', fontSize=8.0, leading=8.8, backColor=colors.HexColor('#f0f4f0'), borderPadding=9, spaceBefore=5, spaceAfter=13)
small = ParagraphStyle('Small', parent=body, fontSize=9, leading=13)
bullet = ParagraphStyle('Bullet', parent=body, leftIndent=17, firstLineIndent=-12, spaceAfter=6)
links = {'01-team-trails.md': '#lab1', '02-event-scoreboard.md': '#lab2', 'README.md': '#setup', '../../examples/java/TeamTrails.java': '#team-source', '../../examples/java/EventScoreboard.java': '#event-source', '../../LICENSE.TXT': 'https://github.com/LoudounCodes/jkarel/blob/master/LICENSE.TXT'}

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
            for i, item in enumerate(items, 1):
                prefix = '•' if kind == 'BulletList' else str(i) + '.'
                text = ' '.join(inline(part['c']) for part in item if part['t'] in ('Para', 'Plain'))
                result.append(Paragraph(prefix + '  ' + text, bullet))
        else: raise ValueError('Unsupported block: ' + kind)
    return result

story = []
center = ParagraphStyle('Cover', parent=body, alignment=TA_CENTER)
cover_title = ParagraphStyle('CoverTitle', parent=center, fontName=BOLD, fontSize=34, leading=40, textColor=GREEN, spaceAfter=18)
cover_subtitle = ParagraphStyle('CoverSubtitle', parent=center, fontSize=15, leading=22, spaceAfter=16)
story += [Spacer(1, 30), Image(str(HERE/'assets/loudouncodes-logo.png'), 120, 120), Spacer(1, 28),
          Paragraph('Karel<br/>Creative Project Labs', cover_title),
          Paragraph('Colors, walls, and events<br/>with LoudounCodes JKarel', cover_subtitle),
          Spacer(1, 14), Paragraph('LAB 01  ·  TEAM TRAILS<br/>LAB 02  ·  EVENT SCOREBOARD', center),
          Spacer(1, 28), Paragraph('For students ready to make their own maze,<br/>game, simulation, or artwork.', center),
          Spacer(1, 35), Paragraph('Java 18  ·  jGRASP  ·  JKarel 1.0.0', center),
          Paragraph('Classroom review edition · October 2026', center), PageBreak()]

setup = parse(HERE/'README.md')
split = next(i for i,b in enumerate(setup) if b['t']=='Header' and inline(b['c'][2])=='Teacher acceptance')
setup[0]['c'][2] = [{'t':'Str','c':'Start here: jGRASP setup'}]
story += blocks(setup[:split], 'setup')
story += [PageBreak()]
story += blocks(parse(HERE/'01-team-trails.md'), 'lab1')
story += [PageBreak()]
story += blocks(parse(HERE/'02-event-scoreboard.md'), 'lab2')
story += [PageBreak(), Paragraph('Complete example programs', h1), Paragraph('Copy these programs into files with the names shown. Both examples are also in the repository’s examples/java folder. Compile and run each separately in jGRASP.', body)]
for index, (filename, anchor) in enumerate([('TeamTrails.java','team-source'), ('EventScoreboard.java','event-source')]):
    if index: story.append(PageBreak())
    story.append(Paragraph('<a name="'+anchor+'"/>'+filename, h2))
    source = (ROOT/'examples/java'/filename).read_text()
    story.append(Preformatted(source.rstrip(), code))
story += [PageBreak(), Paragraph('Teacher notes and sources', h1)]
story += blocks(setup[split:])
story.append(Paragraph('Brand asset: the existing LoudounCodes logo supplied from the personal Dropbox Marketing/logos collection. PDF layout and examples are created for this handout.', small))

def page(canvas, document):
    width, height = letter
    canvas.saveState()
    canvas.setFillColor(INK); canvas.rect(0, height-64, width, 64, fill=1, stroke=0)
    canvas.drawImage(str(HERE/'assets/loudouncodes-logo.png'), 43, height-55, width=46, height=46, mask='auto')
    canvas.setFillColor(colors.white); canvas.setFont(BOLD, 16)
    canvas.drawString(100, height-38, 'LoudounCodes.org')
    canvas.setFillColor(GOLD); canvas.rect(0, height-66, width, 2, fill=1, stroke=0)
    canvas.setFont(FONT, 8); canvas.setFillColor(colors.white)
    canvas.drawRightString(width-43, height-35, 'KAREL  /  CREATIVE PROJECT LABS')
    canvas.setStrokeColor(colors.HexColor('#d5ddd5')); canvas.line(43, 37, width-43, 37)
    canvas.setFillColor(GREEN); canvas.setFont(FONT, 8)
    canvas.drawString(43, 24, 'LoudounCodes.org  •  Classroom review edition')
    canvas.drawRightString(width-43, 24, str(document.page))
    canvas.restoreState()

doc = SimpleDocTemplate(str(OUTPUT), pagesize=letter, leftMargin=48, rightMargin=48, topMargin=83, bottomMargin=53,
    title='LoudounCodes — Karel Creative Project Labs', author='LoudounCodes', subject='Team trails and event scoreboard extension labs for JKarel in jGRASP')
doc.build(story, onFirstPage=page, onLaterPages=page)
print(OUTPUT)
