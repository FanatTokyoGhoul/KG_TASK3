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
    void draw(PixelDrawer pd, ScreenConvector sc, List<IAfine> afines);

    boolean checkClick(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines);

    boolean checkClickInReference(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines);

    void setRedacted(boolean redacted);

    Translation getTranslation(List<IAfine> afines);

    Scale getScale(List<IAfine> afines);

    void setP2(RealPoint p2);

    void setP1(RealPoint p1);

    ScreenPoint getCenter(ScreenConvector sc, List<IAfine> afines);

    RealPoint getCenter();

    Rotate getRotate(List<IAfine> afines);

    boolean checkClickInRotationFlag(ScreenPoint sp, ScreenConvector sc, List<IAfine> afines);
}
