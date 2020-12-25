package figure;

import Utils.LineDrawer;
import Utils.LozengeDrawer;
import Utils.PixelDrawer;
import afine.IAfine;
import afine.Rotate;
import afine.Scale;
import afine.Translation;
import drawers.line_drawers.BresenhamLineDrawer;
import drawers.lozenge_drawer.LozengeDraw;
import screen_conversion.RealPoint;
import screen_conversion.ScreenConvector;
import screen_conversion.ScreenPoint;

import java.util.concurrent.TransferQueue;

public class Line implements IFigure{
    private RealPoint p1,p2;
    private LineDrawer lineDrawer;

    public Line(RealPoint p1, RealPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
        lineDrawer = new BresenhamLineDrawer(null);
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.p1 = new RealPoint(x1, y1);
        this.p2 = new RealPoint(x2, y2);
    }

    public RealPoint getP1() {
        return p1;
    }

    public void setP1(RealPoint p1) {
        this.p1 = p1;
    }

    @Override
    public ScreenPoint getCenter(ScreenConvector sc) {
        return null;
    }

    @Override
    public RealPoint getCenter() {
        return null;
    }

    @Override
    public double getCosFI(int x, int y, ScreenConvector sc) {
        return 0;
    }

    @Override
    public Rotate getRotate() {
        return null;
    }

    @Override
    public boolean checkClickInRotationFlag(ScreenPoint sp, ScreenConvector sc) {
        return false;
    }

    public RealPoint getP2() {
        return p2;
    }

    public void setP2(RealPoint p2) {
        this.p2 = p2;
    }

    @Override
    public void draw(PixelDrawer pd, ScreenConvector sc) {
        lineDrawer.setPixelDrawer(pd);
        lineDrawer.drawLine(sc.r2s(p1), sc.r2s(p2));
    }

    @Override
    public boolean checkClick(ScreenPoint sp, ScreenConvector sc) {
        return false;
    }

    @Override
    public boolean checkClickInReference(ScreenPoint sp, ScreenConvector sc) {
        return false;
    }

    @Override
    public void setRedacted(boolean redacted) {

    }

    @Override
    public Translation getTranslation() {
        return null;
    }

    @Override
    public Scale getScale() {
        return null;
    }
}
