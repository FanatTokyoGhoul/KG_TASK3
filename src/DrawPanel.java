import Utils.Checker;
import Utils.PixelDrawer;
import afine.AfineControl;
import drawers.pixel_drawer.BufferedImagePixelDrawer;
import figure.IFigure;
import figure.Lozenge;
import screen_conversion.RealPoint;
import screen_conversion.ScreenConvector;
import screen_conversion.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private ScreenConvector sc = new ScreenConvector(-2, 2, 4, 4, 800, 600);
    private ScreenPoint lastPosition = null;
    private Map<IFigure, AfineControl> allFigure = new HashMap<>();
    private IFigure currentFigure = null;
    private IFigure redactedFigure = null;
    private AfineControl currentAfineControl = null;
    private AfineControl redactedAfineControl = null;
    private ScreenPoint controlerCoord = null;

    public DrawPanel() {
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        repaint();
    }

    @Override
    public void paint(Graphics gr) {
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new BufferedImagePixelDrawer(bufferedImage);

        if (currentFigure != null && currentAfineControl != null) {
            currentFigure.draw(pd, sc, currentAfineControl.getAfinesList());
        }

        if(redactedAfineControl != null){
            redactedAfineControl.drawControler(controlerCoord.getX(), controlerCoord.getY(), graphics);
        }

        for (Map.Entry<IFigure, AfineControl> entry : allFigure.entrySet()) {
            entry.getKey().draw(pd, sc, entry.getValue().getAfinesList());
            graphics.setColor(Color.BLACK);
            graphics.fillRect(entry.getKey().getCenter(sc, entry.getValue().getAfinesList()).getX() - 5,
                    entry.getKey().getCenter(sc, entry.getValue().getAfinesList()).getY() - 5, 10, 10);
        }
        graphics.dispose();

        gr.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(redactedAfineControl != null){
            if(Checker.inRectangle(mouseEvent.getX(),mouseEvent.getY(),controlerCoord.getX(),controlerCoord.getY(),100, redactedAfineControl.getHeight())){

                int selectedItem = (mouseEvent.getY() - controlerCoord.getY()) / 20;

                if(redactedAfineControl.getSelectedItem() == -1) {

                    if(redactedAfineControl.getAfinesList().size() <= selectedItem){
                        redactedAfineControl.addIAfine(this);
                        repaint();
                        return;
                    }

                    redactedAfineControl.setSelectedItem(selectedItem);

                }else if(redactedAfineControl.getSelectedItem() == selectedItem){

                    redactedAfineControl.redactIAfine(this);
                    resetSelection();

                }else if(redactedAfineControl.getAfinesList().size() > selectedItem){

                    redactedAfineControl.moveIAfine(redactedAfineControl.getSelectedItem(), selectedItem);
                    redactedAfineControl.setSelectedItem(-1);

                }
            }else {
                resetSelection();
            }
        }

        switch (mouseEvent.getButton()) {
            case MouseEvent.BUTTON3:
                isRedactedFigure(mouseEvent);
                break;
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (redactedFigure == null) {
            switch (mouseEvent.getButton()) {
                case MouseEvent.BUTTON3:
                    lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
                    break;
                case MouseEvent.BUTTON1:
                    currentFigure = new Lozenge(sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())),
                            sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())));
                    currentAfineControl = new AfineControl();
                    break;
            }
        } else {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (redactedFigure == null) {
            switch (mouseEvent.getButton()) {
                case MouseEvent.BUTTON3:
                    lastPosition = null;
                    break;
                case MouseEvent.BUTTON1:
                    allFigure.put(currentFigure, currentAfineControl);
                    currentAfineControl = null;
                    currentFigure = null;
                    break;
            }
        } else {
            lastPosition = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (redactedFigure == null) {
            moveViewingArea(mouseEvent);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setRealHeight(sc.getRealHeight() * scale);
        sc.setRealWidth(sc.getRealWidth() * scale);
        repaint();
    }

    private RealPoint createVector(int x, int y) {
        ScreenPoint currentPosition = new ScreenPoint(x, y);
        ScreenPoint deltaScreen = new ScreenPoint(
                currentPosition.getX() - lastPosition.getX(),
                currentPosition.getY() - lastPosition.getY());
        RealPoint deltaReal = sc.s2r(deltaScreen);
        RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
        return new RealPoint(
                deltaReal.getX() - zeroReal.getX(),
                deltaReal.getY() - zeroReal.getY());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (redactedFigure != null) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
                allFigure.remove(redactedFigure);
                redactedFigure = null;
            }
        } else {
            if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
                allFigure.clear();
                redactedFigure = null;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void moveViewingArea(MouseEvent mouseEvent) {
        if (lastPosition != null) {
            RealPoint vector = createVector(mouseEvent.getX(), mouseEvent.getY());
            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        } else if (currentFigure != null) {
            currentFigure.setP2(sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())));
        }
    }

    private void isRedactedFigure(MouseEvent mouseEvent) {

        boolean allFigureNotRedacted = true;

        for (Map.Entry<IFigure, AfineControl> entry : allFigure.entrySet()) {
            entry.getKey().setRedacted(false);
        }

        for (Map.Entry<IFigure, AfineControl> entry : allFigure.entrySet()) {
            if (entry.getKey().checkClick(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()), sc, entry.getValue().getAfinesList())) {

                if(entry.getKey().equals(redactedFigure)){
                    redactedAfineControl = entry.getValue();
                    controlerCoord = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
                }

                entry.getKey().setRedacted(true);
                redactedFigure = entry.getKey();
                allFigureNotRedacted = false;
                break;
            }
        }
        if (allFigureNotRedacted) {
            redactedFigure = null;
        }
    }

    private void resetSelection(){
        redactedAfineControl.setSelectedItem(-1);
        redactedAfineControl = null;
        controlerCoord = null;
    }
}
