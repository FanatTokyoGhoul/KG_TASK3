package Utils;

import screen_conversion.ScreenPoint;

public class DrawUtils {
    public static void drawSnowflake(LineDrawer g, int x, int y, int r, int n){
        double da = 2 * Math.PI / n;
        for(int i = 0; i < n; i++){
            double dx = r * Math.cos(da * i);
            double dy = r * Math.sin(da * i);
            g.drawLine(new ScreenPoint(x, y), new ScreenPoint(x + (int)dx, y + (int)dy));
        }
    }
}
