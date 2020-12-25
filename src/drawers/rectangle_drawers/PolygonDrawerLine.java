package drawers.rectangle_drawers;

import Utils.LineDrawer;
import Utils.PixelDrawer;
import Utils.PolygonDrawer;
import screen_conversion.ScreenPoint;

import java.awt.*;

public class PolygonDrawerLine implements PolygonDrawer {
    private PixelDrawer pd;
    private Color c;
    private LineDrawer ld;

    public PolygonDrawerLine(PixelDrawer pd) {
        this.pd = pd;
        this.c = Color.BLACK;
    }


    @Override
    public void drawPolygon(ScreenPoint[] screenPoints) {
        for(int i = 0; i < screenPoints.length - 1; i++){
            ld.drawLine(screenPoints[i], screenPoints[i + 1]);
        }
        ld.drawLine(screenPoints[screenPoints.length - 1], screenPoints[0]);
    }

    @Override
    public void setLineDrawer(LineDrawer ld) {
        this.ld = ld;
    }

    @Override
    public LineDrawer getLineDrawer() {
        return ld;
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
