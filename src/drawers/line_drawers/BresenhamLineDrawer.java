package drawers.line_drawers;

import Utils.LineDrawer;
import Utils.PixelDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {

    private PixelDrawer pd;
    private Color c;

    public BresenhamLineDrawer(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        int dx = (x2 - x1 >= 0 ? 1 : -1);
        int dy = (y2 - y1 >= 0 ? 1 : -1);

        int lengthX = Math.abs(x2 - x1);
        int lengthY = Math.abs(y2 - y1);

        int length = Math.max(lengthX, lengthY);

        if (lengthY <= lengthX)
        {
            int x = x1;
            int y = y1;
            int d = -lengthX;

            length++;
            while(length != 0)
            {
                length--;
                pd.drawPixel(x,y,c);
                x += dx;
                d += 2 * lengthY;
                if (d > 0) {
                    d -= 2 * lengthX;
                    y += dy;
                }
            }
        }
        else
        {
            int x = x1;
            int y = y1;
            int d = - lengthY;

            length++;
            while(length != 0)
            {
                length--;
                pd.drawPixel(x,y,c);
                y += dy;
                d += 2 * lengthX;
                if (d > 0) {
                    d -= 2 * lengthY;
                    x += dx;
                }
            }
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

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public void setColor(Color c) {
        this.c = c;
    }
}
