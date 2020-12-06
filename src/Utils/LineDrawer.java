package Utils;

import screen_conversion.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2);
    void setPixelDrawer(PixelDrawer pd);
    PixelDrawer getPixelDrawer();
     Color getColor();
     void setColor(Color c);
}
