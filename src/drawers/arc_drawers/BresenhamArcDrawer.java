package drawers.arc_drawers;

import Utils.ArcDrawer;
import Utils.PixelDrawer;

import java.awt.*;

public class BresenhamArcDrawer implements ArcDrawer {
    private PixelDrawer pd;
    private Color c;

    public BresenhamArcDrawer(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    private void drawPixels(int x, int y, int x0, int y0, PixelDrawer pd, double[][] tan) // Рисование пикселя для первого квадранта, и, симметрично, для остальных
    {
        if ((double) y0 / x0 > tan[3][0] || (double) y0 / x0 < tan[3][1])
            pd.drawPixel(x + x0, y + y0, c);
        if (((double) -y0 / x0 > tan[0][0] || (double) -y0 / x0 < tan[0][1]))
            pd.drawPixel(x + x0, y - y0, c);
        if ((double) y0 / x0 > tan[1][0] || (double) y0 / x0 < tan[1][1])
            pd.drawPixel(x - x0, y - y0, c);
        if (((double) -y0 / x0 > tan[2][0] || (double) -y0 / x0 < tan[2][1]))
            pd.drawPixel(x - x0, y + y0, c);
    }

    private void drawPixelInFourth(int x, int y, int x0, int y0, boolean isNotDraw, double tg1, double tg2, int fourth) {
        if (isNotDraw) {
            return;
        }
    }

    private double[][] toTan(int[][] degrees) {
        double[][] tan = new double[degrees.length][degrees[1].length];
        for (int i = 0; i < degrees.length; i++) {
            for (int j = 0; j < degrees[i].length; j++) {
                if (degrees[i][j] == 90 || degrees[i][j] == 270) {
                    tan[i][j] = Double.MAX_VALUE;
                }else {
                    tan[i][j] = -1*Math.tan(Math.toRadians(degrees[i][j]));
                }
            }
        }
        return tan;
    }

    @Override
    public void drawArc(int x, int y, int r, int R, int degreeStart, int degreeEnd) {
        double tg1 = -1 * Math.tan(Math.toRadians(degreeStart));
        double tg2 = -1 * Math.tan(Math.toRadians(degreeEnd));
        int[][] fourths = setFourths(degreeStart, degreeEnd);
        double[][] tan = toTan(fourths);
        int x0 = 0;
        int y0 = R;
        int a_sqr = r * r;
        int b_sqr = R * R;
        int delta = 4 * b_sqr * ((x0 + 1) * (x0 + 1)) + a_sqr * ((2 * y0 - 1) * (2 * y0 - 1)) - 4 * a_sqr * b_sqr;
        while (a_sqr * (2 * y0 - 1) > 2 * b_sqr * (x0 + 1)) {
            drawPixels(x, y, x0, y0, pd, tan);
            if (delta < 0) {
                x0++;
                delta += 4 * b_sqr * (2 * x0 + 3);
            } else {
                x0++;
                delta = delta - 8 * a_sqr * (y0 - 1) + 4 * b_sqr * (2 * x0 + 3);
                y0--;
            }
        }
        delta = b_sqr * ((2 * x0 + 1) * (2 * x0 + 1)) + 4 * a_sqr * ((y0 + 1) * (y0 + 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1/2, y-1)
        while (y0 + 1 != 0) {
            drawPixels(x, y, x0, y0, pd, tan);
            if (delta < 0) {
                y0--;
                delta += 4 * a_sqr * (2 * y0 + 3);
            } else {
                y0--;
                delta = delta - 8 * b_sqr * (x0 + 1) + 4 * a_sqr * (2 * y0 + 3);
                x0++;
            }
        }
    }

    private int[][] setFourths(int degreeStart, int degreeEnd) {
        int[][] degrees = new int[4][2];
        while (degreeEnd > 360) {
            degreeEnd -= 360;
            degreeStart -= 360;
        }
        int counter = degreeStart / 90;
        degrees[counter][0] = degreeStart;

        if (degreeStart % 90 == 0) {
            counter--;
        }

        for (int i = degreeStart; i <= degreeEnd; i++) {
            if (i % 90 == 0 && counter + 1 < 4) {
                counter++;
                degrees[counter][0] = i;
            }
            degrees[counter][1] = i;
        }
        return degrees;
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
