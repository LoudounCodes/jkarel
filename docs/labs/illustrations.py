"""Original vector lesson figures and unmodified captures of the Java examples."""
from pathlib import Path
from reportlab.graphics.shapes import Drawing, Rect, Line, String, Polygon, Circle, Image
from reportlab.lib import colors

GREEN = colors.HexColor('#27733b')
GRAY = colors.HexColor('#bbc3bb')
INK = colors.HexColor('#222222')
RED = colors.HexColor('#ad3434')
BLUE = colors.HexColor('#285aa5')
ASSETS = Path(__file__).resolve().parent / 'assets'


def text(d, x, y, value, size=9, bold=False, color=INK, anchor='start'):
    d.add(String(x, y, value, fontName='Helvetica-Bold' if bold else 'Helvetica',
                 fontSize=size, fillColor=color, textAnchor=anchor))


def box(d, x, y, w, h, title, lines=()):
    d.add(Rect(x, y, w, h, strokeColor=GRAY, fillColor=None, strokeWidth=.7))
    text(d, x+9, y+h-17, title, bold=True)
    for i, line in enumerate(lines):
        text(d, x+9, y+h-34-i*13, line, size=8.5)


def arrow(d, x1, y1, x2, y2, color=GREEN):
    d.add(Line(x1, y1, x2, y2, strokeColor=color, strokeWidth=1.2))
    import math
    a = math.atan2(y2-y1, x2-x1)
    d.add(Polygon([x2, y2, x2-6*math.cos(a-.45), y2-6*math.sin(a-.45),
                   x2-6*math.cos(a+.45), y2-6*math.sin(a+.45)],
                  strokeColor=color, fillColor=color))


def robot(d, x, y, facing='E', color=GREEN):
    points = {'E':[8,0,-6,6,-6,-6], 'N':[0,8,-6,-6,6,-6],
              'W':[-8,0,6,6,6,-6], 'S':[0,-8,6,6,-6,6]}[facing]
    d.add(Polygon([v+(x if i%2==0 else y) for i,v in enumerate(points)],
                  strokeColor=color, fillColor=None, strokeWidth=1.4))


def grid(d, x, y, columns=8, rows=6, cell=14):
    for c in range(columns+1):
        d.add(Line(x+c*cell,y,x+c*cell,y+rows*cell,strokeColor=GRAY,strokeWidth=.35))
    for r in range(rows+1):
        d.add(Line(x,y+r*cell,x+columns*cell,y+r*cell,strokeColor=GRAY,strokeWidth=.35))


def view(d, filename, x, y, height):
    from PIL import Image as PILImage
    path = ASSETS/filename
    with PILImage.open(path) as source:
        width = height*source.width/source.height
    d.add(Image(x,y,width,height,str(path)))
    return width


def orientation():
    d=Drawing(516,116)
    box(d,8,21,226,83,'Arena: the place where actions happen',
        ['Robots, walls, beepers, and game state', 'Construct the world; perform an action'])
    box(d,296,21,212,83,'Display: the view of that world',
        ['Draw positions, colors, and shapes', 'Present the result to the viewer'])
    arrow(d,242,62,288,62)
    text(d,265,76,'render',8,anchor='middle')
    text(d,8,5,'Pacing controls the rhythm of arena actions; it does not change movement rules.',8.5)
    return d, 'Names guide the model: a robot inhabits an arena, and a display shows that arena.'


def trails():
    d=Drawing(516,150)
    view(d,'team-trails.png',8,7,136)
    text(d,190,129,'One color belongs to the whole stack',bold=True)
    for x,count,color in [(212,'2',BLUE),(394,'3',RED)]:
        d.add(Circle(x,77,17,fillColor=None,strokeColor=color,strokeWidth=1.8))
        text(d,x,73,count,13,bold=True,color=color,anchor='middle')
    arrow(d,242,77,354,77,RED)
    text(d,298,98,'red robot drops one',8.5,anchor='middle')
    text(d,212,45,'before: blue',8.5,anchor='middle')
    text(d,394,45,'after: red',8.5,anchor='middle')
    text(d,190,18,'Robot identity stays separate from its appearance.',8.5)
    return d, 'Completed TeamTrails at left. At right, a red drop recolors an existing blue stack.'


def listeners():
    d=Drawing(516,102)
    for x,title,lines in [
        (0,'1. Action',['robot.pickBeeper()']),
        (133,'2. State update',['inventory: 0 -> 1','beeper removed']),
        (266,'3. Notification',['beeperPickedUp','receives the robot']),
        (399,'4. Score update',['identify the player','redScore++'])]:
        box(d,x,19,117,72,title,lines)
    for x in [117,250,383]: arrow(d,x+2,56,x+14,56)
    text(d,0,3,'Main loop owns the turn. The listener observes a completed pickup.',8.5)
    return d, 'Callback order matters: the listener sees the updated inventory, then updates its own score.'


def doors():
    d=Drawing(516,130)
    for base,closed,label in [(18,True,'Door closed'),(286,False,'Door open')]:
        text(d,base,116,label,bold=True)
        grid(d,base,18)
        d.add(Rect(base,18,112,84,fillColor=None,strokeColor=INK,strokeWidth=1.4))
        for row in [2,4,5]:
            d.add(Line(base+56,18+(row-1)*14,base+56,18+row*14,strokeColor=INK,strokeWidth=2))
        if closed:d.add(Line(base+56,46,base+56,60,strokeColor=RED,strokeWidth=2.7))
        robot(d,base+(3.5 if closed else 4.5)*14,53)
        text(d,base+123,78,'frontIsClear()',8.5)
        text(d,base+123,62,'false' if closed else 'true before crossing',8.5,bold=True)
        text(d,base+123,42,'player [4, 3]' if closed else 'player [5, 3]',8.5)
    arrow(d,237,60,270,60)
    text(d,18,3,'Colored segment: the stored Wall object removed to open the door.',8.5)
    return d, 'The boundary and divider remain. Removing one wall changes what the front sensor can see.'


def pacing():
    d=Drawing(516,126)
    for y,label,times,positions in [(103,'FAST',[0,200,400],[92,138,184]),
                                    (60,'SLOW',[0,600,1200],[92,230,368])]:
        text(d,0,y-3,label,bold=True)
        d.add(Line(80,y,458,y,strokeColor=GRAY,strokeWidth=.6))
        for ms,x in zip(times,positions):
            d.add(Circle(x,y,3,fillColor=GREEN,strokeColor=GREEN))
            text(d,x,y-15,str(ms)+' ms',8,anchor='middle')
    text(d,0,13,'STEP',bold=True)
    for x,w,label in [(80,82,'action'),(190,128,'wait for Enter'),(345,120,'next action')]:
        box(d,x,0,w,32,label)
    arrow(d,166,17,186,17);arrow(d,322,17,341,17)
    return d, 'Schematic timing after setup: FAST waits 200 ms, SLOW 600 ms, and STEP waits for Enter.'


def retreat():
    d=Drawing(516,114)
    states=[('[3, 3]','N'),('[3, 3]','E'),('[4, 3]','E'),('[3, 3]','E')]
    for i,(location,facing) in enumerate(states):
        x=i*133
        box(d,x,17,117,83,location+'  '+facing)
        robot(d,x+58,49,facing)
    for i,label in enumerate(['turnRight()','move()','backUp()']):
        arrow(d,i*133+119,52,i*133+131,52)
        text(d,i*133+125,105,label,8,anchor='middle')
    text(d,0,2,'Retreat checks backIsClear() first. Backward movement preserves facing.',8.5)
    return d, 'Position and facing are different state: a turn changes one, and a retreat changes the other.'


def levels():
    d=Drawing(516,104)
    box(d,0,18,157,75,'Before the second load',['player [3, 2], EAST','inventory 1'])
    box(d,190,18,157,75,'After the second load',['same player and listener','[3, 2], EAST; inventory 1'])
    arrow(d,161,56,185,56)
    box(d,370,18,146,75,'Replaced by map',['walls and beeper stacks','custom items'])
    text(d,0,2,'Load changes the scene. The program decides whether to reset player and game state.',8.5)
    return d, 'MapStages retains the player while level two introduces a wall immediately in front of it.'


def custom():
    d=Drawing(516,155)
    text(d,8,140,'Before reaching the goal',bold=True)
    text(d,328,140,'After reaching the goal',bold=True)
    view(d,'custom-goal-start.png',8,9,112)
    view(d,'custom-goal-finish.png',328,9,112)
    arrow(d,272,70,312,70)
    text(d,178,97,'render draws',8.5)
    text(d,178,82,'the shapes',8.5)
    text(d,178,45,'game loop',8.5)
    text(d,178,30,'removes goal',8.5)
    return d, 'Details from the actual example classes: the blue square is the player; the green target disappears when reached.'


def lettering():
    d=Drawing(516,144)
    view(d,'robot-lettering.png',8,5,132)
    box(d,192,29,316,93,'One instruction, many ordinary actions',
        ['sign.say("HI")', 'AlphaBot composes movement and beeper drops.',
         'BeeperStack.INFINITY supplies unlimited beepers.', 'The lettering route needs room and clear cells.'])
    return d, 'AlphaBot writes HI with blue beepers. The named constant identifies an unlimited supply.'


def sources():
    d=Drawing(516,122)
    box(d,0,64,128,53,'TrainingMap',['fixed description'])
    box(d,0,3,128,53,'SupplyRoom',['student implementation'])
    box(d,176,27,162,72,'MapDataSource contract',['width, height','walls, beeper stacks'])
    arrow(d,132,89,172,75);arrow(d,132,29,172,51)
    box(d,384,27,132,72,'install(source)',['student adapter','Arena/model operations'])
    arrow(d,342,63,380,63)
    return d, 'Two implementations, one contract, one adapter. The library does not yet load this interface directly.'


FIGURES = [orientation, trails, listeners, doors, pacing, retreat, levels, custom, lettering, sources]
