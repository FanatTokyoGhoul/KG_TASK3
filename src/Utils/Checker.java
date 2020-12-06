package Utils;

public class Checker {
    public static boolean aboveLine(double a, double x, double x0, double y0, double y){
        return y > a * (x - x0) + y0;
    }
    public static boolean inSquare(double x, double y, double x0, double y0, double size){
        return Math.abs(x - x0) < size && Math.abs(y - y0) < size;
    }

    public static boolean inRectangle(double x, double y, double x0, double y0, double weight, double height){
        return (x >= x0 && x <= x0 + weight) && (y >= y0 && y <= y0 + height);
    }
}
