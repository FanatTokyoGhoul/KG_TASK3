package afine;

import screen_conversion.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Translation implements IAfine{
    double x = 0;
    double y = 0;
    @Override
    public List<RealPoint> transformation(List<RealPoint> p) {
        List<RealPoint> newP = new ArrayList<>();
        for(RealPoint rp : p){
            newP.add(new RealPoint(rp.getX() - x, rp.getY() - y));
        }
        return newP;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
