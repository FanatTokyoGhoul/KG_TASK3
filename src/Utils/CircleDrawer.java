package Utils;

import screen_conversion.ScreenPoint;

import java.awt.*;

public interface CircleDrawer {
    void drawCircle(ScreenPoint p, int r);
    void setPixelDrawer(PixelDrawer pd);
    PixelDrawer getPixelDrawer();
     Color getC();
     void setC(Color c);
}
