package afine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AfineControl {

    public static final String[] AFINES = {"Scale", "Rotate", "Translation"};

    private List<IAfine> afinesList = new ArrayList<>();
    private int selectedItem = -1;

    public AfineControl() {
        afinesList.add(new Scale());
        afinesList.add(new Rotate());
        afinesList.add(new Translation());

    }

    public void drawControler(int x, int y, Graphics gr) {
        gr.setColor(new Color(0, 0, 0));
        gr.fillRect(x, y, 100, 20 * (afinesList.size() + 1) + 2);
        int fontSize = 10;



        gr.setColor(new Color(136, 138, 134));

        for (int i = 0; i <= afinesList.size(); i++) {
            gr.fillRect(x + 2, y + 20 * i + 2, 96, 18);
        }

        if(selectedItem >= 0){
            gr.setColor(new Color(74, 255,0));
            gr.fillRect(x + 2, y + 20 * selectedItem + 2, 96, 18);
        }

        gr.setColor(Color.BLACK);
        gr.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

        for (int i = 0; i < afinesList.size(); i++) {
            drawCenteredString(gr, afinesList.get(i).toString(), new Rectangle(x, y + 20 * i, 100, 20));
        }

        drawCenteredString(gr, "Add", new Rectangle(x, y + 20 * afinesList.size(), 100, 20));
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect) {

        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;

        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(text, x, y);
    }

    public void redactIAfine(Container container){
        IAfine afine = afinesList.get(selectedItem);
        if(afine instanceof Scale){
            redactScale(container, (Scale)afine);
        }else if(afine instanceof Translation){
            redactTranslate(container, (Translation) afine);
        }else if(afine instanceof Rotate){
            redactRotate(container, (Rotate)afine);
        }
    }

    private void redactTranslate(Container container, Translation translation){
        String result = JOptionPane.showInputDialog(container, new String[] {"Введите увелечиние по x", "Сейчас увеличение: " + translation.getX()});

        if(result == null){
            return;
        }

        translation.setX(Double.parseDouble(result));
        result = JOptionPane.showInputDialog(container, new String[] {"Введите увелечиние по y", "Сейчас увеличение: " + translation.getY()});

        if(result == null){
            return;
        }

        translation.setY(Double.parseDouble(result));
    }

    private void redactRotate(Container container, Rotate rotate){
        String result = JOptionPane.showInputDialog(container, new String[] {"Введите угол поворота", "Сейчас угол: " + Math.toDegrees(rotate.getRadian())});

        if(result == null){
            return;
        }

        rotate.setRadian(Math.toRadians(Double.parseDouble(result)));
    }

    private void redactScale(Container container, Scale scale){
        String result = JOptionPane.showInputDialog(container, new String[] {"Введите увелечиние по x", "Сейчас увеличение: " + scale.getxScale()});

        if(result == null){
            return;
        }

        scale.setxScale(Double.parseDouble(result));
        result = JOptionPane.showInputDialog(container, new String[] {"Введите увелечиние по y", "Сейчас увеличение: " + scale.getyScale()});

        if(result == null){
            return;
        }

        scale.setyScale(Double.parseDouble(result));
    }

    public void moveIAfine(int i1, int i2){
        IAfine buffer = afinesList.get(i1);
        afinesList.set(i1, afinesList.get(i2));
        afinesList.set(i2, buffer);
    }

    public List<IAfine> getAfinesList() {
        return afinesList;
    }

    public int getHeight(){
        return 20 * (afinesList.size() + 1);
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void addIAfine(Container container){

        Object result = JOptionPane.showInputDialog(
                container,
                "Выберите IAfine :",
                "Выбор IAfine",
                JOptionPane.QUESTION_MESSAGE,
                null, AFINES, AFINES[0]);

        if(result == null){
            return;
        }

        Class newClass;
        IAfine afine = null;
        try {
           newClass = Class.forName("afine." + result);
           afine = (IAfine) newClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        afinesList.add(afine);
    }
}
