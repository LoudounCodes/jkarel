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
from reportlab.platypus import BaseDocTemplate, PageTemplate, Frame, NextPageTemplate, Flowable, ActionFlowable, Paragraph, Spacer, PageBreak, Preformatted, Image, Table, TableStyle, KeepTogether
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

from illustrations import FIGURES
from page_art import draw_vignette

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

body = ParagraphStyle('Body', fontName=FONT, fontSize=10, leading=13.5, textColor=INK, spaceAfter=7)
h1 = ParagraphStyle('Title', parent=body, fontName=BOLD, fontSize=20, leading=24, textColor=INK, spaceAfter=12, keepWithNext=True)
h2 = ParagraphStyle('Heading', parent=body, fontName=BOLD, fontSize=13, leading=16, textColor=GREEN, spaceBefore=7, spaceAfter=4, keepWithNext=True)
code = ParagraphStyle('Code', fontName='Courier', fontSize=8.0, leading=9.5, spaceBefore=5, spaceAfter=10)
small = ParagraphStyle('Small', parent=body, fontSize=9, leading=13)
capability = ParagraphStyle('Capability', parent=body, fontName=BOLD, leading=14, spaceAfter=10, keepWithNext=True)
sidebar_title = ParagraphStyle('SidebarTitle', parent=small, fontName=BOLD, leading=11, spaceAfter=4)
sidebar_body = ParagraphStyle('SidebarBody', parent=small, leading=11, spaceAfter=0)
caption = ParagraphStyle('Caption', parent=small, fontSize=8.5, leading=11, spaceAfter=9)
bullet = ParagraphStyle('Bullet', parent=body, leftIndent=17, firstLineIndent=-12, spaceAfter=4)
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
    consumed = set()
    for index, node in enumerate(nodes):
        if index in consumed:
            continue
        kind, value = node['t'], node.get('c')
        if kind in ('Para', 'Plain'):
            text = inline(value)
            result.append(Paragraph(text, capability if text.startswith('<b>New ') else body))
        elif kind == 'BlockQuote':
            explanation = []
            for quote_index, part in enumerate(value):
                if part['t'] not in ('Para', 'Plain'):
                    raise ValueError('Sidebars must contain a title and explanatory paragraphs')
                explanation.append(Paragraph(inline(part['c']), sidebar_title if quote_index == 0 else sidebar_body))
            opening = []
            next_index = index + 1
            while next_index < len(nodes) and nodes[next_index]['t'] in ('Para', 'Plain'):
                opening.append(nodes[next_index])
                consumed.add(next_index)
                next_index += 1
            callout = Table([[explanation]], colWidths=[184], hAlign='LEFT')
            callout.setStyle(TableStyle([
                ('BOX',(0,0),(-1,-1),0.6,colors.HexColor('#aeb8ae')),
                ('LINEBEFORE',(0,0),(0,0),1.5,GREEN),
                ('LEFTPADDING',(0,0),(-1,-1),10), ('RIGHTPADDING',(0,0),(-1,-1),10),
                ('TOPPADDING',(0,0),(-1,-1),6), ('BOTTOMPADDING',(0,0),(-1,-1),6),
            ]))
            sidebar = Table([[blocks(opening), [callout]]], colWidths=[332, 184], hAlign='LEFT')
            sidebar.setStyle(TableStyle([
                ('VALIGN',(0,0),(-1,-1),'TOP'),
                ('LEFTPADDING',(0,0),(0,0),0), ('RIGHTPADDING',(0,0),(0,0),16),
                ('LEFTPADDING',(1,0),(1,0),0), ('RIGHTPADDING',(1,0),(1,0),0),
                ('TOPPADDING',(0,0),(-1,-1),0), ('BOTTOMPADDING',(0,0),(-1,-1),4),
            ]))
            result.append(sidebar)
            if anchor and anchor.startswith('lab'):
                drawing, explanation = FIGURES[int(anchor[3:])]()
                result.append(KeepTogether([Spacer(1, 7), drawing, Spacer(1, 4),
                    Paragraph(explanation, caption)]))
        elif kind == 'Header':
            heading = inline(value[2])
            if value[0] == 1 and anchor:
                heading = '<a name="' + anchor + '"/>' + heading
            continuation = {
                'lab1': 'Exercises', 'lab3': 'Exercises',
                'lab5': 'Exercises', 'lab6': 'Experiments',
                'lab8': 'Investigate and extend',
            }
            if anchor in continuation and heading == continuation[anchor]:
                result.append(PageBreak())
                number = int(anchor[3:])
                result.append(Paragraph('Lesson ' + str(number) + ': ' + heading, h1))
            elif anchor == 'lab2' and heading == 'Callback rules':
                heading = 'Event scoreboard: callback rules'
                result.append(Paragraph(heading, h2))
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
    if anchor and anchor.startswith('lab'):
        for i, flow in enumerate(result):
            if isinstance(flow, Paragraph) and flow.getPlainText() == 'Show what you learned':
                result[i:] = [KeepTogether(result[i:])]
                break
    return result


class Topic(Flowable):
    """Carry subject information through automatic page and code-listing breaks."""
    def __init__(self, name):
        super().__init__()
        self.name = name
        self.keepWithNext = True
    def draw(self):
        pass

class Recto(ActionFlowable):
    """After a section break, leave an even page blank before the next section."""
    def apply(self, doc):
        if doc.page % 2 == 0:
            doc.blank_pages.add(doc.page)
            doc.handle_pageBreak()

class Packet(BaseDocTemplate):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.topic = 'cover'
        self.blank_pages = set()
        self.page_topics = []
    def afterFlowable(self, flowable):
        if isinstance(flowable, Topic):
            self.topic = flowable.name
    def afterPage(self):
        if self.page in self.blank_pages:
            self.canv.saveState()
            self.canv.setFillGray(.5)
            self.canv.setFont(FONT, 9)
            self.canv.drawCentredString(306, 396, 'This page intentionally left blank.')
            self.canv.restoreState()
            self.page_topics.append((self.page, 'blank'))
        else:
            if self.topic not in ('cover', 'introduction'):
                draw_vignette(self.canv, self.topic, self.page)
            page(self.canv, self)
            self.page_topics.append((self.page, self.topic))

cover_title = ParagraphStyle('CoverTitle', parent=h1, fontSize=28, leading=34, spaceAfter=18)
story = [Topic('cover'), Spacer(1, 45),
         Image(str(HERE/'assets/loudouncodes-logo.png'), 180, 180, hAlign='LEFT'),
         Spacer(1, 32),
         Paragraph('Karel<br/>Creative Project Lessons', cover_title),
         Paragraph('LoudounCodes', h2),
         Paragraph('Java 18 · jGRASP', body),
         Paragraph('Classroom review edition · October 2026', small),
         NextPageTemplate('opening'), PageBreak(), Recto(), Topic('introduction')]
introduction = blocks(parse(HERE/'WHAT-IS-THIS.md'))
intro_body = ParagraphStyle('IntroductionBody', parent=body, leading=12.5, spaceAfter=5)
for flow in introduction:
    if isinstance(flow, Paragraph) and flow.style is body:
        flow.style = intro_body
story += introduction
story += [NextPageTemplate('body'), PageBreak(), Recto(), Topic('guide'),
         Paragraph('Lesson guide', h1),
         Paragraph('The classroom archive includes an independent starter folder for each lesson. '
                   'Open its jGRASP project and complete the TODOs. The appendix contains complete reference programs.', body),
         Paragraph('Start with the API orientation, then choose the features your own project needs. '
                   'Print the selected lesson pages; complete program listings and teacher notes follow separately.', body),
         Spacer(1, 8)]
for number, (_, _, title) in enumerate(LESSONS):
    story.append(Paragraph(str(number) + '. <link href="#lab' + str(number) + '" color="#27733b">' + title + '</link>', body))
story.append(PageBreak())

setup = parse(HERE/'README.md')
split = next(i for i,b in enumerate(setup) if b['t']=='Header' and inline(b['c'][2])=='Teacher acceptance')
setup_start = next(i for i,b in enumerate(setup) if b['t']=='Header' and inline(b['c'][2])=='Set up in jGRASP')
setup[setup_start]['c'][0] = 1
setup[setup_start]['c'][2] = [{'t':'Str','c':'Set up in jGRASP'}]
story += [Topic('setup')]
story += blocks(setup[setup_start:split], 'setup')
for number, (markdown, _, _) in enumerate(LESSONS):
    story += [PageBreak(), Recto(), Topic('lab'+str(number))]
    story += blocks(parse(HERE/markdown), 'lab' + str(number))

story += [PageBreak(), Recto(), Topic('source0'), Paragraph('Complete example programs', h1),
          Paragraph('Copy each listing into a file with the name shown and run it separately in jGRASP. '
                    'For MapStages, also copy both map files from the listings below into that working folder. '
                    'Student starter folders contain focused TODOs; these appendix listings are complete references. '
                    'The same sources are in examples/java and examples/maps.', body)]
for number, (_, filename, _) in enumerate(LESSONS):
    if number: story.append(PageBreak())
    story.append(Topic('source'+str(number)))
    story.append(Paragraph('<a name="source'+str(number)+'"/>'+filename, h2))
    source = (ROOT/'examples/java'/filename).read_text().rstrip()
    if filename == 'EventScoreboard.java':
        # Keep the main program and listener implementation on separate pages.
        main, listener = source.split('    private static class Scoreboard', 1)
        story.append(Preformatted(main.rstrip(), code))
        story += [PageBreak(), Paragraph('EventScoreboard.java: listener implementation (continued)', h2)]
        story.append(Preformatted('    private static class Scoreboard'+listener, code))
    else:
        story.append(Preformatted(source, code))
story.append(PageBreak())
story.append(Topic('maps'))
story.append(Paragraph('Map files for the level-changing lesson', h1))
for filename in ['stage-one.map', 'stage-two.map']:
    story.append(Paragraph(filename, h2))
    story.append(Preformatted((ROOT/'examples/maps'/filename).read_text().rstrip(), code))
story += [PageBreak(), Recto(), Topic('teacher'), Paragraph('Teacher notes and sources', h1)]
story += blocks(setup[split:])
story.append(Paragraph('Brand asset: the selected master_logo.png from the personal Dropbox '
                       'Marketing/logos collection, copied unchanged. The artwork is retained in the repository; '
                       'the PDF places it on the cover; lesson pages use only limited spot color.', small))
story.append(Paragraph('Illustrations: original vector diagrams and topic-specific robot line art; arena views captured from the included Java examples. '
                       'Screenshots preserve the actual appearance. Diagram timing and layouts are schematic.', small))
story += [PageBreak()]
story += [Recto(), Topic('featuremap')]
story += blocks(parse(HERE/'FEATURE-MAP.md'), 'featuremap')

def page(canvas, document):
    width, height = letter
    canvas.saveState()
    canvas.setStrokeColor(colors.HexColor('#d5ddd5')); canvas.line(43, 37, width-43, 37)
    canvas.setFillGray(0.35); canvas.setFont(FONT, 8)
    canvas.drawString(43, 24, 'LoudounCodes.org  •  Classroom review edition')
    canvas.drawRightString(width-43, 24, str(document.page))
    canvas.restoreState()

doc = Packet(str(OUTPUT), pagesize=letter, leftMargin=48, rightMargin=48, topMargin=40, bottomMargin=130,
    title='LoudounCodes — Karel Creative Project Labs', author='LoudounCodes', subject='API orientation and feature lessons for JKarel in jGRASP')
doc.addPageTemplates([
    PageTemplate(id='opening', frames=[Frame(48, 48, 516, 704, leftPadding=6, rightPadding=6)]),
    PageTemplate(id='body', frames=[Frame(48, 130, 516, 622, leftPadding=6, rightPadding=6)]),
])
doc.build(story)
review = ROOT/'build/pdf-review'
review.mkdir(parents=True, exist_ok=True)
(review/'page-topics.json').write_text(json.dumps(doc.page_topics, indent=2)+'\n')
print(OUTPUT)
