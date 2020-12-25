package figure;

import Utils.PixelDrawer;
import afine.IAfine;
import afine.Rotate;
import afine.Scale;
import afine.Translation;
import screen_conversion.RealPoint;
import screen_conversion.ScreenConvector;
import screen_conversion.ScreenPoint;

import java.util.List;

public interface IFigure {
    void draw(PixelDrawer pd, ScreenConvector sc);

    boolean checkClick(ScreenPoint sp, ScreenConvector sc);

    boolean checkClickInReference(ScreenPoint sp, ScreenConvector sc);

    void setRedacted(boolean redacted);

    Translation getTranslation();

    Scale getScale();

    void setP2(RealPoint p2);

    void setP1(RealPoint p1);

    ScreenPoint getCenter(ScreenConvector sc);

    RealPoint getCenter();

    double getCosFI(int x, int y, ScreenConvector sc);

    Rotate getRotate();

    boolean checkClickInRotationFlag(ScreenPoint sp, ScreenConvector sc);
}
