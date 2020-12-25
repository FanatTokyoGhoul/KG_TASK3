import Utils.LineDrawer;
import Utils.PixelDrawer;
import afine.Rotate;
import drawers.pixel_drawer.BufferedImagePixelDrawer;
import figure.IFigure;
import figure.Line;
import figure.Lozenge;
import screen_conversion.RealPoint;
import screen_conversion.ScreenConvector;
import screen_conversion.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private ScreenConvector sc = new ScreenConvector(-2, 2, 4, 4, 800, 600);
    private ScreenPoint lastPosition = null;
    private ArrayList<IFigure> allFigure = new ArrayList<>();
    private IFigure currentFigure = null;
    private IFigure redactedFigure = null;
    private boolean isScale = false;
    private boolean isRotate = false;


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
        if(currentFigure != null){
            currentFigure.draw(pd,sc);
        }

        for(IFigure f : allFigure){
            f.draw(pd, sc);
            graphics.setColor(Color.BLACK);
            graphics.fillRect(f.getCenter(sc).getX() - 5, f.getCenter(sc).getY() - 5,10,10);
        }
        graphics.dispose();

        gr.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        isRedactedFigure(mouseEvent);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(redactedFigure == null) {
            switch (mouseEvent.getButton()) {
                case MouseEvent.BUTTON3:
                    lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
                    break;
                case MouseEvent.BUTTON1:
                    currentFigure = new Lozenge(sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())),
                            sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())));
                    break;
            }
        }else {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                if(redactedFigure.checkClickInReference(new ScreenPoint(mouseEvent.getX(),mouseEvent.getY()),sc)){
                    isScale = true;
                }else if(redactedFigure.checkClickInRotationFlag(new ScreenPoint(mouseEvent.getX(),mouseEvent.getY()),sc)){
                    isRotate = true;
                }
                lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(redactedFigure == null) {
            switch (mouseEvent.getButton()) {
                case MouseEvent.BUTTON3:
                    lastPosition = null;
                    break;
                case MouseEvent.BUTTON1:
                    allFigure.add(currentFigure);
                    currentFigure = null;
                    break;
            }
        }else {
            isScale = false;
            isRotate = false;
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
        if(redactedFigure == null) {
            moveViewingArea(mouseEvent);
        }else {
            if(lastPosition != null) {
                if(isScale){
                    scaleControl(mouseEvent);
                }else if(isRotate){
                    rotateControl(mouseEvent);
                }else {
                    translateControl(mouseEvent);
                }
                lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
            }
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
        double coef = clicks < 0 ? 0.9:1.1;
        for(int i = 0; i < Math.abs(clicks); i++){
            scale *= coef;
        }
        sc.setRealHeight(sc.getRealHeight() * scale);
        sc.setRealWidth(sc.getRealWidth() * scale);
        repaint();
    }

    private RealPoint createVector(int x, int y){
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
        if(redactedFigure != null){
            if(keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
                allFigure.remove(redactedFigure);
                redactedFigure = null;
            }else if(keyEvent.getKeyCode() == KeyEvent.VK_Z){
                Rotate r = redactedFigure.getRotate();

                RealPoint center = redactedFigure.getCenter();

                r.setOffsetX(center.getX());
                r.setOffsetY(center.getY());
                r.setRadian(r.getRadian() + 0.01);
            }
        }else {
            if(keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
                allFigure.clear();
                redactedFigure = null;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void moveViewingArea(MouseEvent mouseEvent){
        if (lastPosition != null) {
            RealPoint vector = createVector(mouseEvent.getX(), mouseEvent.getY());
            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        } else if (currentFigure != null) {
            currentFigure.setP2(sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY())));
        }
    }

    private void isRedactedFigure(MouseEvent mouseEvent){
        if(mouseEvent.getButton() == MouseEvent.BUTTON3) {

            boolean allFigureNotRedacted = true;

            for(IFigure l: allFigure){
                l.setRedacted(false);
            }

            for(IFigure l: allFigure){
                if(l.checkClick(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()), sc)){
                    l.setRedacted(true);
                    redactedFigure = l;
                    allFigureNotRedacted = false;
                    break;
                }
            }
            if(allFigureNotRedacted){
                redactedFigure = null;
            }
        }
    }

    private void scaleControl(MouseEvent mouseEvent){
        RealPoint vector = createVector(mouseEvent.getX(), mouseEvent.getY());
        if(redactedFigure.getCenter(sc).getX() > mouseEvent.getX()){
            vector.setX(vector.getX() * -1);
        }

        if(redactedFigure.getCenter(sc).getY() < mouseEvent.getY()){
            vector.setY(vector.getY() * -1);
        }

        redactedFigure.getScale().setxScale(redactedFigure.getScale().getxScale() + vector.getX());
        redactedFigure.getScale().setyScale(redactedFigure.getScale().getyScale() + vector.getY());
        lastPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
    }

    private void rotateControl(MouseEvent mouseEvent){
        double fi = 0;
        ScreenPoint center = redactedFigure.getCenter(sc);
        if(mouseEvent.getY() - lastPosition.getY() > 0 && center.getX() < mouseEvent.getX()){
            fi = 0.01;
        }else if(mouseEvent.getY() - lastPosition.getY() < 0 && center.getX() < mouseEvent.getX()){
            fi = -0.01;
        }else if(mouseEvent.getY() - lastPosition.getY() > 0 && center.getX() > mouseEvent.getX()){
            fi = -0.01;
        }else if(mouseEvent.getY() - lastPosition.getY() < 0 && center.getX() > mouseEvent.getX()){
            fi = 0.01;
        }
        Rotate r = redactedFigure.getRotate();
        r.setOffsetX(redactedFigure.getCenter().getX());
        r.setOffsetY(redactedFigure.getCenter().getY());
        r.setRadian(redactedFigure.getRotate().getRadian() + fi);
    }

    private void translateControl(MouseEvent mouseEvent){
        RealPoint vector = createVector(mouseEvent.getX(), mouseEvent.getY());
        redactedFigure.getTranslation().setX(redactedFigure.getTranslation().getX() - vector.getX());
        redactedFigure.getTranslation().setY(redactedFigure.getTranslation().getY() - vector.getY());
    }
}
