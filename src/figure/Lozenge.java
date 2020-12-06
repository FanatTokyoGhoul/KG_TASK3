package figure;

import Utils.*;
import afine.IAfine;
import afine.Rotate;
import afine.Scale;
import afine.Translation;
import drawers.circle_drawers.BresenhamCircleDrawer;
import drawers.line_drawers.BresenhamLineDrawer;
import drawers.line_drawers.BresenhamLineDrawerParagraph;
import drawers.lozenge_drawer.LozengeDraw;
import drawers.rectangle_drawers.PolygonDrawerLine;
import screen_conversion.RealPoint;
import screen_conversion.ScreenConvector;
import screen_conversion.ScreenPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Lozenge implements IFigure {

    private RealPoint p1, p2;
    private LineDrawer lineDrawer;
    private CircleDrawer circleDrawer;
    private LozengeDrawer lozengeDrawer;
    private PolygonDrawer polygonDrawer;
    private boolean redacted;
    List<RealPoint> referencePoints = new ArrayList<>();

    public Lozenge(RealPoint p1, RealPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
        setNewCoord(p1, p2);
        this.lozengeDrawer = new LozengeDraw(null);
        this.lineDrawer = new BresenhamLineDrawer(null);
        this.circleDrawer = new BresenhamCircleDrawer(null);
        this.polygonDrawer = new PolygonDrawerLine(null);
        redacted = false;
    }

    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
    }

    public boolean checkClickInReference(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines) {

        List<RealPoint> referencePoints = new ArrayList<>(this.referencePoints);

        for (IAfine afine : afines) {
            referencePoints = afine.transformation(referencePoints);
        }

        for (RealPoint realPoint : referencePoints) {
            ScreenPoint screenPoint = sc.r2s(realPoint);
            if (Checker.inSquare(sp.getX(), sp.getY(), screenPoint.getX(), screenPoint.getY(), 10)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkClickInRotationFlag(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines) {

        List<RealPoint> rotationPoint = new ArrayList<>();
        rotationPoint.add(p1);
        rotationPoint.add(new RealPoint(p2.getX(), p1.getY()));
        rotationPoint.add(p2);
        rotationPoint.add(new RealPoint(p1.getX(), p2.getY()));

        for (IAfine afine : afines) {
            rotationPoint = afine.transformation(rotationPoint);
        }

        for (RealPoint realPoint : rotationPoint) {
            ScreenPoint screenPoint = sc.r2s(realPoint);
            if (Checker.inSquare(sp.getX(), sp.getY(), screenPoint.getX(), screenPoint.getY(), 10)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkClick(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines) {
        RealPoint rp = sc.s2r(sp);
        double x = rp.getX();
        double y = rp.getY();

        List<RealPoint> referencePoints = new ArrayList<>(this.referencePoints);

        for (IAfine afine : afines) {
            referencePoints = afine.transformation(referencePoints);
        }

        RealPoint center = sc.s2r(getCenter(sc, afines));

        boolean checkClickFirstLine = Checker.aboveLine((referencePoints.get(1).getY() - referencePoints.get(0).getY()) / (referencePoints.get(1).getX() - referencePoints.get(0).getX()), x, referencePoints.get(0).getX(), referencePoints.get(0).getY(), y);
        boolean checkClickSecondLine = Checker.aboveLine((referencePoints.get(2).getY() - referencePoints.get(1).getY()) / (referencePoints.get(2).getX() - referencePoints.get(1).getX()), x, referencePoints.get(1).getX(), referencePoints.get(1).getY(), y);
        boolean checkClickThirdLine = Checker.aboveLine((referencePoints.get(3).getY() - referencePoints.get(2).getY()) / (referencePoints.get(3).getX() - referencePoints.get(2).getX()), x, referencePoints.get(2).getX(), referencePoints.get(2).getY(), y);
        boolean checkClickFourthLine = Checker.aboveLine((referencePoints.get(0).getY() - referencePoints.get(3).getY()) / (referencePoints.get(0).getX() - referencePoints.get(3).getX()), x, referencePoints.get(3).getX(), referencePoints.get(3).getY(), y);

        if(!Checker.aboveLine((referencePoints.get(1).getY() - referencePoints.get(0).getY()) / (referencePoints.get(1).getX() - referencePoints.get(0).getX()), center.getX(), referencePoints.get(0).getX(), referencePoints.get(0).getY(), center.getY()) ){
            checkClickFirstLine = !checkClickFirstLine;
        }

        if(!Checker.aboveLine((referencePoints.get(2).getY() - referencePoints.get(1).getY()) / (referencePoints.get(2).getX() - referencePoints.get(1).getX()), center.getX(), referencePoints.get(1).getX(), referencePoints.get(1).getY(), center.getY())){
            checkClickSecondLine = !checkClickSecondLine;
        }

        if(!Checker.aboveLine((referencePoints.get(3).getY() - referencePoints.get(2).getY()) / (referencePoints.get(3).getX() - referencePoints.get(2).getX()), center.getX(), referencePoints.get(2).getX(), referencePoints.get(2).getY(), center.getY())){
            checkClickThirdLine = !checkClickThirdLine;
        }

        if (!Checker.aboveLine((referencePoints.get(0).getY() - referencePoints.get(3).getY()) / (referencePoints.get(0).getX() - referencePoints.get(3).getX()), center.getX(), referencePoints.get(3).getX(), referencePoints.get(3).getY(), center.getY())) {
           checkClickFourthLine = !checkClickFourthLine;
        }

        return checkClickFirstLine && checkClickSecondLine && checkClickThirdLine && checkClickFourthLine;
    }

    private void setNewCoord(RealPoint p1, RealPoint p2) {
        RealPoint p3 = new RealPoint(p2.getX() - (p2.getX() - p1.getX()) / 2, p1.getY());
        RealPoint p4 = new RealPoint(p2.getX(), p2.getY() - (p2.getY() - p1.getY()) / 2);
        RealPoint p6 = new RealPoint(p1.getX(), p2.getY() - (p2.getY() - p1.getY()) / 2);
        RealPoint p5 = new RealPoint(p2.getX() - (p2.getX() - p1.getX()) / 2, p2.getY());
        referencePoints.clear();
        referencePoints.add(p3);
        referencePoints.add(p4);
        referencePoints.add(p5);
        referencePoints.add(p6);
    }

    public void setP1(RealPoint p1) {
        this.p1 = p1;
        setNewCoord(p1, p2);
    }

    @Override
    public ScreenPoint getCenter(ScreenConvector sc, List<IAfine> afines) {

        List<RealPoint> startPoint = new ArrayList<>();
        startPoint.add(p1);
        startPoint.add(p2);

        for (IAfine afine : afines) {
            startPoint = afine.transformation(startPoint);
        }

        ScreenPoint sp1 = sc.r2s(startPoint.get(0));
        ScreenPoint sp2 = sc.r2s(startPoint.get(1));

        return new ScreenPoint(sp2.getX() - (sp2.getX() - sp1.getX()) / 2, sp2.getY() - (sp2.getY() - sp1.getY()) / 2);
    }

    @Override
    public RealPoint getCenter() {
        List<RealPoint> startPoint = new ArrayList<>();
        startPoint.add(p1);
        startPoint.add(p2);


        RealPoint sp1 = startPoint.get(0);
        RealPoint sp2 = startPoint.get(1);

        return new RealPoint(sp2.getX() - (sp2.getX() - sp1.getX()) / 2, sp2.getY() - (sp2.getY() - sp1.getY()) / 2);
    }


    public void setP2(RealPoint p2) {
        this.p2 = p2;
        setNewCoord(p1, p2);
    }

    public Translation getTranslation(List<IAfine> afines) {
        Translation afine = null;
        for (IAfine a : afines) {
            if (a instanceof Translation) {
                afine = (Translation) a;
            }
        }
        return afine;
    }

    public Rotate getRotate(List<IAfine> afines) {
        Rotate afine = null;
        for (IAfine a : afines) {
            if (a instanceof Rotate) {
                afine = (Rotate) a;
            }
        }
        return afine;
    }

    public Scale getScale(List<IAfine> afines) {
        Scale afine = null;
        for (IAfine a : afines) {
            if (a instanceof Scale) {
                afine = (Scale) a;
            }
        }
        return afine;
    }


    @Override
    public void draw(PixelDrawer pd, ScreenConvector sc, List<IAfine> afines) {
        lineDrawer.setPixelDrawer(pd);
        lozengeDrawer.setPixelDrawer(pd);
        polygonDrawer.setLineDrawer(new BresenhamLineDrawerParagraph(pd));
        List<RealPoint> referencePoints = new ArrayList<>(this.referencePoints);
        List<RealPoint> center = new ArrayList<>();
        center.add(getCenter());

        for (IAfine afine : afines) {

            if(afine instanceof Rotate){
                Rotate buffer = (Rotate) afine;
                buffer.setOffsetX(center.get(0).getX());
                buffer.setOffsetY(center.get(0).getY());
            }

            center = afine.transformation(center);

            referencePoints = afine.transformation(referencePoints);
        }

        lozengeDrawer.drawLozenge(sc.r2s(referencePoints.get(0)), sc.r2s(referencePoints.get(1)), sc.r2s(referencePoints.get(2)), sc.r2s(referencePoints.get(3)), lineDrawer);

        if (redacted) {
            circleDrawer.setC(Color.RED);
            circleDrawer.setPixelDrawer(pd);
            circleDrawer.drawCircle(sc.r2s(referencePoints.get(0)), 4);
            circleDrawer.drawCircle(sc.r2s(referencePoints.get(1)), 4);
            circleDrawer.drawCircle(sc.r2s(referencePoints.get(2)), 4);
            circleDrawer.drawCircle(sc.r2s(referencePoints.get(3)), 4);

            List<RealPoint> startPoint = new ArrayList<>();
            startPoint.add(p1);
            startPoint.add(new RealPoint(p2.getX(), p1.getY()));
            startPoint.add(p2);
            startPoint.add(new RealPoint(p1.getX(), p2.getY()));

            List<RealPoint> rotationPoint = new ArrayList<>();
            rotationPoint.add(p1);
            rotationPoint.add(new RealPoint(p2.getX(), p1.getY()));
            rotationPoint.add(p2);
            rotationPoint.add(new RealPoint(p1.getX(), p2.getY()));

            for (IAfine afine : afines) {
                startPoint = afine.transformation(startPoint);
            }

            for (IAfine afine : afines) {
                rotationPoint = afine.transformation(rotationPoint);
            }

            ScreenPoint[] screenPoints = new ScreenPoint[startPoint.size()];

            for(int i = 0; i < screenPoints.length; i++){
                screenPoints[i] = sc.r2s(startPoint.get(i));
            }

            polygonDrawer.drawPolygon(screenPoints);

            circleDrawer.setC(Color.BLUE);
            circleDrawer.drawCircle(sc.r2s(rotationPoint.get(0)), 4);
            circleDrawer.drawCircle(sc.r2s(rotationPoint.get(1)), 4);
            circleDrawer.drawCircle(sc.r2s(rotationPoint.get(2)), 4);
            circleDrawer.drawCircle(sc.r2s(rotationPoint.get(3)), 4);
        }
    }
}
