package Utils;

import screen_conversion.ScreenPoint;

import java.awt.*;

public interface LozengeDrawer {
    void drawLozenge(ScreenPoint p1, ScreenPoint p2, ScreenPoint p3, ScreenPoint p4, LineDrawer ld);
    void setPixelDrawer(PixelDrawer pd);
    PixelDrawer getPixelDrawer();
    Color getColor();
    void setColor(Color c);
}
