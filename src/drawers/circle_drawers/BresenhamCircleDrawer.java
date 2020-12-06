package drawers.circle_drawers;

import Utils.CircleDrawer;
import Utils.PixelDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class BresenhamCircleDrawer implements CircleDrawer {

    private PixelDrawer pd;
    private Color c;

    public BresenhamCircleDrawer(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    @Override
    public void drawCircle(ScreenPoint p, int r) {
        int x = p.getX();
        int y = p.getY();
        int x0 = 0;
        int y0 = r;
        int delta = 1 - 2 * r;
        int error = 0;
        while(y0 >= 0) {
            pd.drawPixel(x + x0, y + y0, c);
            pd.drawPixel(x + x0, y - y0, c);
            pd.drawPixel(x - x0, y + y0, c);
            pd.drawPixel(x - x0, y - y0, c);
            error = 2 * (delta + y0) - 1;
            if(delta < 0 && error <= 0) {
                ++x0;
                delta += 2 * x0 + 1;
                continue;
            }
            error = 2 * (delta - x0) - 1;
            if(delta > 0 && error > 0) {
                --y0;
                delta += 1 - 2 * y0;
                continue;
            }
            ++x0;
            delta += 2 * (x0 - y0);
            --y0;
        }
    }

    @Override
    public void setPixelDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return pd;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }
}
