package Utils;

import screen_conversion.ScreenPoint;

import java.awt.*;

public interface PolygonDrawer {
    void drawPolygon(ScreenPoint[] screenPoints);
    void setLineDrawer(LineDrawer pd);
    LineDrawer getLineDrawer();
    Color getColor();
    void setColor(Color c);
}
