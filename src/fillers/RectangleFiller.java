package fillers;

import Utils.LineDrawer;
import Utils.PixelDrawer;
import Utils.PolygonDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class RectangleFiller {
    private PixelDrawer pd;
    private Color c;
    private LineDrawer ld;

    public RectangleFiller(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }


    public void drawRectangle(ScreenPoint screenPoint, int weight, int height) {
        ld.setColor(c);
        for(int i = 0; i <= height; i++){
            ld.drawLine(screenPoint, new ScreenPoint(screenPoint.getX() + weight, screenPoint.getY() + i));
        }
    }

    public void setLineDrawer(LineDrawer ld) {
        this.ld = ld;
    }

    public LineDrawer getLineDrawer() {
        return ld;
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }
}

