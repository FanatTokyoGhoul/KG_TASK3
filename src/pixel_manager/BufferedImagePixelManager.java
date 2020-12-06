package pixel_manager;

import Utils.PixelManager;

import java.awt.image.BufferedImage;

public class BufferedImagePixelManager implements PixelManager {
    
    private BufferedImage bi;

    public BufferedImagePixelManager(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public int getRGB(int x, int y) {
        return bi.getRGB(x,y);
    }
}
