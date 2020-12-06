package screen_conversion;

public class ScreenConvector {
    private double cornerX, cornerY, realWidth, realHeight;
    private int screenWidth, screenHeight;

    public ScreenConvector(double cornerX, double cornerY, double realWidth, double realHeight, int screenWidth, int screenHeight) {
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public ScreenPoint r2s(RealPoint p){
        int x =(int)((p.getX() - cornerX) * screenWidth /realWidth);
        int y = (int)((cornerY - p.getY()) * screenHeight /realHeight);
        return new ScreenPoint(x,y);
    }

    public RealPoint s2r(ScreenPoint p){
        double x = p.getX() * realWidth/screenWidth + cornerX;
        double y = cornerY - p.getY() * realWidth/screenHeight;
        return new RealPoint(x,y);
    }

    public double getCornerX() {
        return cornerX;
    }

    public void setCornerX(double cornerX) {
        this.cornerX = cornerX;
    }

    public double getCornerY() {
        return cornerY;
    }

    public void setCornerY(double cornerY) {
        this.cornerY = cornerY;
    }

    public double getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public double getRealHeight() {
        return realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
