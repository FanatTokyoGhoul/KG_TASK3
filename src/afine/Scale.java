package afine;

import screen_conversion.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Scale implements IAfine{

    double xScale = 1;
    double yScale = 1;

    @Override
    public List<RealPoint> transformation(List<RealPoint> p) {
        List<RealPoint> newP = new ArrayList<>();
        for(RealPoint rp : p){
            newP.add(new RealPoint(rp.getX()*xScale, rp.getY()*yScale));
        }
        return newP;
    }

    public double getxScale() {
        return xScale;
    }

    public void setxScale(double xScale) {
        this.xScale = xScale;
    }

    public double getyScale() {
        return yScale;
    }

    public void setyScale(double yScale) {
        this.yScale = yScale;
    }

    @Override
    public String toString() {
        return "Scale";
    }
}
