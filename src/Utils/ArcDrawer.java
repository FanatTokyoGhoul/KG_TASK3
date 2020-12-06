package Utils;

import java.awt.*;

public interface ArcDrawer {
    void drawArc(int x, int y, int r, int R, int degreeStart, int degreeEnd);
    Color getC();
    void setC(Color c);
}
