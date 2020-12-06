package fillers.oval_fillers;

import Utils.OvalFiller;
import Utils.PixelDrawer;

import java.awt.*;

public class BresenhamOvalFiller implements OvalFiller {
    private PixelDrawer pd;
    private Color c;

    public BresenhamOvalFiller(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    private void drawPixels(int x, int y, int x0, int y0, PixelDrawer pd) // Рисование пикселя для первого квадранта, и, симметрично, для остальных
    {
        pd.drawPixel(x + x0, y + y0, c);
        pd.drawPixel(x + x0, y - y0, c);
        pd.drawPixel(x - x0, y - y0, c);
        pd.drawPixel(x - x0, y + y0, c);
        drawFillLine(x - x0, x + x0, y + y0);
        drawFillLine(x - x0, x + x0, y - y0);
    }

    @Override
    public void fillOval(int x, int y, int r, int R) {
        int x0 = 0; // Компонента x
        int y0 = R; // Компонента y
        int a_sqr = r * r; // a^2, a - большая полуось
        int b_sqr = R * R; // b^2, b - малая полуось
        int delta = 4 * b_sqr * ((x0 + 1) * (x0 + 1)) + a_sqr * ((2 * y0 - 1) * (2 * y0 - 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1, y-1/2)
        while (a_sqr * (2 * y0 - 1) > 2 * b_sqr * (x0 + 1)) // Первая часть дуги
        {
            drawPixels(x, y, x0, y0, pd);
            if (delta < 0) // Переход по горизонтали
            {
                x0++;
                delta += 4 * b_sqr * (2 * x0 + 3);
            } else // Переход по диагонали
            {
                x0++;
                delta = delta - 8 * a_sqr * (y0 - 1) + 4 * b_sqr * (2 * x0 + 3);
                y0--;
            }
        }
        delta = b_sqr * ((2 * x0 + 1) * (2 * x0 + 1)) + 4 * a_sqr * ((y0 + 1) * (y0 + 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1/2, y-1)
        while (y0 + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            drawPixels(x, y, x0, y0, pd);
            if (delta < 0) // Переход по вертикали
            {
                y0--;
                delta += 4 * a_sqr * (2 * y0 + 3);
            } else // Переход по диагонали
            {
                y0--;
                delta = delta - 8 * b_sqr * (x0 + 1) + 4 * a_sqr * (2 * y0 + 3);
                x0++;
            }
        }
    }

    private void drawFillLine(int x1, int x2, int y) {
        for (; x1 < x2; x1++) {
            pd.drawPixel(x1, y, c);
        }
    }

    @Override
    public Color getC() {
        return c;
    }

    @Override
    public void setC(Color c) {
        this.c = c;
    }
}
