package drawers.line_drawers;

import Utils.LineDrawer;
import Utils.PixelDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {

    private Graphics g;

    public GraphicsLineDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public Color getColor() {
        return g.getColor();
    }

    @Override
    public void setColor(Color c) {
        g.setColor(c);
    }

    @Override
    public void setPixelDrawer(PixelDrawer pd) {
        return;
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return null;
    }
}
