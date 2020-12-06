package afine;

import screen_conversion.RealPoint;

import java.util.ArrayList;
import java.util.List;

public class Rotate  implements IAfine{

    double radian = 0;
    double offsetX = 0;
    double offsetY = 0;

    @Override
    public List<RealPoint> transformation(List<RealPoint> p) {
        List<RealPoint> newP = new ArrayList<>();

        for(RealPoint rp : p){
            newP.add(new RealPoint(rp.getX() - offsetX, rp.getY() - offsetY));
        }

//        for(RealPoint rp : newP){
//            newP.add(new RealPoint(rp.getX()* Math.cos(radian) - rp.getY() * Math.sin(radian),
//                    rp.getX()* Math.sin(radian) + rp.getY() * Math.cos(radian)));
//        }

        for (RealPoint buffer : newP) {
            double x = buffer.getX();
            double y = buffer.getY();
            buffer.setX(x * Math.cos(radian) + y * Math.sin(radian));
            buffer.setY(x * -Math.sin(radian) + y * Math.cos(radian));
        }

        for (RealPoint buffer : newP) {
            buffer.setX(buffer.getX() + offsetX);
            buffer.setY(buffer.getY() + offsetY);
        }


        return newP;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getRadian() {
        return radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    @Override
    public String toString() {
        return "Rotate";
    }
}
