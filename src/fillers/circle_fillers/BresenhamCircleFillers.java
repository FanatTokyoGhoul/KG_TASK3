package fillers.circle_fillers;

import Utils.*;
import drawers.circle_drawers.BresenhamCircleDrawer;
import drawers.line_drawers.BresenhamLineDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class BresenhamCircleFillers implements CircleFiller {

    private PixelDrawer pd;
    private PixelManager pm;
    private Color c;
    private CircleDrawer cd;

    public BresenhamCircleFillers(PixelDrawer pd, PixelManager pm) {
        this.pd = pd;
        this.pm = pm;
        this.c = Color.BLACK;
        this.cd = new BresenhamCircleDrawer(pd);
    }

    @Override
    public void fillCircle(int x, int y, int r) {
//        cd.drawCircle(x, y, r);
//        fill(x, y, r, 0, 0);
        LineDrawer ld = new BresenhamLineDrawer(pd);
        ld.setColor(c);
        int x0 = 0;
        int y0 = r;
        int delta = 1 - 2 * r;
        int error = 0;
        while (y0 >= 0) {
            pd.drawPixel(x + x0, y + y0, c);
            pd.drawPixel(x + x0, y - y0, c);
            pd.drawPixel(x - x0, y + y0, c);
            pd.drawPixel(x - x0, y - y0, c);
            ld.drawLine(new ScreenPoint(x + x0, y + y0), new ScreenPoint(x - x0, y + y0));
            ld.drawLine(new ScreenPoint(x + x0, y - y0), new ScreenPoint(x - x0, y - y0));
            error = 2 * (delta + y0) - 1;
            if (delta < 0 && error <= 0) {
                ++x0;
                delta += 2 * x0 + 1;
                continue;
            }
            error = 2 * (delta - x0) - 1;
            if (delta > 0 && error > 0) {
                --y0;
                delta += 1 - 2 * y0;
                continue;
            }
            ++x0;
            delta += 2 * (x0 - y0);
            --y0;
        }
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
        this.cd.setC(c);
    }
}
