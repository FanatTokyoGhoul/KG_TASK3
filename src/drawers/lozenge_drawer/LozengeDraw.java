package drawers.lozenge_drawer;

import Utils.LineDrawer;
import Utils.LozengeDrawer;
import Utils.PixelDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class LozengeDraw implements LozengeDrawer {

    private PixelDrawer pd;
    private Color c;

    public LozengeDraw(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }

    @Override
    public void drawLozenge(ScreenPoint p1, ScreenPoint p2, ScreenPoint p3, ScreenPoint p4, LineDrawer ld) {
        ld.setColor(c);
        ld.setPixelDrawer(pd);
        ld.drawLine(p1, p2);
        ld.drawLine(p2, p3);
        ld.drawLine(p3, p4);
        ld.drawLine(p4, p1);
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
