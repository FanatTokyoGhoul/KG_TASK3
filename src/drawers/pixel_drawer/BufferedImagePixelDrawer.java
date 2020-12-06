package drawers.pixel_drawer;

import Utils.PixelDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixelDrawer implements PixelDrawer {
    private BufferedImage bufferedImage;

    public BufferedImagePixelDrawer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void drawPixel(int x, int y, Color c) {
        if(x >= 0 && y >= 0 && x < bufferedImage.getWidth() && y < bufferedImage.getHeight()){
            bufferedImage.setRGB(x, y, c.getRGB());
        }
    }
}
