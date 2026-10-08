"""Original outline illustrations for the packet's page subjects (vector artwork)."""
from reportlab.lib import colors

GREEN = colors.HexColor('#27733b')
GRAY = colors.HexColor('#899389')
BLUE = colors.HexColor('#285aa5')
RED = colors.HexColor('#ad3434')


def draw_vignette(c, topic, variant=0):
    """Draw a small robot interacting with the current lesson's subject."""
    c.saveState()
    c.translate(438, 53)
    c.setLineWidth(.85)
    c.setStrokeColor(GRAY)
    c.setFillColor(GREEN)

    def line(x1, y1, x2, y2):
        c.line(x1, y1, x2, y2)

    def token(x, y, color=GREEN):
        c.setStrokeColor(color)
        c.circle(x, y, 3, fill=0)
        c.setStrokeColor(GRAY)

    def bot(x=12, y=6, color=GREEN):
        c.setStrokeColor(color)
        c.roundRect(x, y+13, 25, 22, 4, fill=0)
        c.roundRect(x+4, y+37, 17, 14, 3, fill=0)
        line(x+12, y+51, x+12, y+57)
        c.circle(x+12, y+59, 1.8)
        c.setFillColor(color)
        c.circle(x+9, y+44, 1.1, fill=1)
        c.circle(x+16, y+44, 1.1, fill=1)
        line(x+8, y+40, x+17, y+40)
        line(x+8, y+37, x+8, y+35)
        line(x+17, y+37, x+17, y+35)
        for dx in (5, 17):
            line(x+dx, y+13, x+dx, y+6)
            c.roundRect(x+dx-3, y+2, 9, 4, 1, fill=0)
        line(x, y+29, x-7, y+23)
        line(x-7, y+23, x-9, y+16)
        line(x+25, y+29, x+34, y+35)
        line(x+34, y+35, x+40, y+35)
        c.circle(x+42, y+35, 2)
        c.setStrokeColor(GRAY)

    if topic.startswith(('lab', 'source')):
        n = int(''.join(ch for ch in topic if ch.isdigit()))
    else:
        n = {'guide': 6, 'setup': 0, 'maps': 6, 'teacher': 9,
             'featuremap': 2}.get(topic, 0)
    bot(color=BLUE if n == 1 and variant % 2 else GREEN)
    line(3, 6, 126, 6)
    if n == 0:  # An arena contains the robot; the monitor presents the view.
        c.roundRect(72, 23, 48, 35, 2, fill=0)
        c.rect(78, 30, 36, 22)
        for x in range(84, 114, 8): line(x, 30, x, 52)
        for y in range(36, 52, 8): line(78, y, 114, y)
        line(94, 23, 94, 14); line(83, 14, 105, 14)
        token(91, 41)
    elif n == 1:
        for x, y, color in [(65, 13, BLUE), (77, 20, BLUE), (88, 27, RED),
                            (99, 34, RED), (111, 41, RED)]: token(x, y, color)
        c.setStrokeColor(RED)
        c.roundRect(90, 51, 29, 13, 3)
        c.setStrokeColor(GRAY)
    elif n == 2:  # Notification rings and a scoreboard.
        for radius in (6, 11, 16): c.circle(61, 42, radius)
        c.roundRect(85, 15, 38, 45, 2)
        c.setFont('Helvetica', 9); c.setFillColor(GREEN)
        c.drawCentredString(104, 45, 'SCORE')
        c.setFont('Helvetica', 17); c.drawCentredString(104, 25, str(3+variant % 5))
    elif n == 3:
        for x in (68, 105):
            c.rect(x, 9, 18, 51)
            for y in (22, 35, 48): line(x, y, x+18, y)
        c.setStrokeColor(GREEN)
        line(86, 9, 86, 57); line(86, 57, 102, 64); line(102, 64, 102, 16)
        token(97, 35)
    elif n == 4:
        c.circle(95, 39, 23)
        line(95, 39, 95, 55); line(95, 39, 106, 33)
        for dx, dy in [(0,19),(19,0),(0,-19),(-19,0)]:
            c.circle(95+dx, 39+dy, .8)
        c.roundRect(82, 5, 26, 8, 2)
    elif n == 5:
        c.circle(95, 37, 25)
        c.setStrokeColor(GREEN)
        p=c.beginPath(); p.moveTo(95, 59); p.lineTo(88, 27); p.lineTo(95, 33); p.lineTo(102, 27); p.close()
        c.drawPath(p)
        line(73, 37, 117, 37)
        c.setFont('Helvetica', 8); c.setFillColor(GREEN); c.drawCentredString(95, 66, 'N')
    elif n == 6:
        for offset in (8, 4, 0):
            c.rect(70+offset, 14+offset, 45, 39)
        line(78, 22, 91, 22); line(91, 22, 91, 39)
        line(91, 39, 106, 39); line(106, 39, 106, 28)
        token(104, 23)
    elif n == 7:
        c.setStrokeColor(GREEN)
        c.circle(96, 39, 24); c.circle(96, 39, 16); c.circle(96, 39, 7)
        line(63, 39, 93, 39); line(87, 44, 93, 39); line(87, 34, 93, 39)
    elif n == 8:
        for col, row in [(0,0),(0,1),(0,2),(0,3),(0,4), (2,0),(2,1),(2,2),(2,3),(2,4),(1,2),
                         (4,0),(4,1),(4,2),(4,3),(4,4)]:
            token(71+col*10, 15+row*10)
    else:
        c.roundRect(68, 17, 23, 34, 2); c.roundRect(100, 17, 23, 34, 2)
        c.setFont('Courier', 19); c.setFillColor(GREEN)
        c.drawString(72, 29, '{'); c.drawString(104, 29, '}')
        line(91, 34, 100, 34)
    c.restoreState()
