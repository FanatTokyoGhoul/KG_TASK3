package drawers.line_drawers;

import Utils.LineDrawer;
import Utils.PixelDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {

    private PixelDrawer pd;
    private Color c;

    public WuLineDrawer(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {

        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        float gradient = 0;


        int dx = x2 - x1;
        int dy = y2 - y1;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            gradient = (float) dy / dx;
            float interY = y1 + gradient;
            pd.drawPixel(x1, y1, c);
            for (int x = x1; x < x2; ++x) {
                pd.drawPixel(x, (int)interY, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (255 - fractionalPart(interY) * 255)));
                pd.drawPixel(x, (int)interY + 1, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (fractionalPart(interY) * 255)));
                interY += gradient;
            }
        }
        else {
            if (y1 > y2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            gradient = (float) dx / dy;
            float interx = x1 + gradient;
            pd.drawPixel(x1, y1,  c);
            for (int y = y1; y < y2; ++y) {
                pd.drawPixel((int)interx, y, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (255 - fractionalPart(interx) * 255)));
                pd.drawPixel((int)interx + 1, y, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (fractionalPart(interx) * 255)));
                interx += gradient;
            }
        }
        pd.drawPixel(x2, y2, c);
    }

    private float fractionalPart(float x) {
        int tmp = (int) x;
        return x - tmp; //вернёт дробную часть числа
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    public void setPixelDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return pd;
    }
}
