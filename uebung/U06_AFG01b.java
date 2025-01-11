// Aufgabe 01: Erweitern Sie Ihre Programme aus der Übung derart,
// dass Sie Größe und Farben der Rechtecke bzw. des Kreises mittels Menus
// einstellen können.
package uebung;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
class U06_AFG01b_Model{
    private int circlePosX, circlePosY, circleRadius, frameWidth, frameHeight;
    private Color circleColor = Color.RED;
    U06_AFG01b_Model(int frameWidth, int frameHeight, int circleRadius){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.circleRadius = circleRadius;
        newCirclePosition();
    }
    public void setFrameSize(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
    public void newCirclePosition(){
        this.circlePosX = (int) (Math.random() * (frameWidth - 2 * circleRadius)) + circleRadius;
        this.circlePosY = (int) (Math.random() * (frameHeight - 2 * circleRadius)) + circleRadius;
    }
    public int getCirclePosX(){
        return circlePosX;
    }
    public int getCirclePosY(){
        return circlePosY;
    }
    public int getCircleRadius(){
        return circleRadius;
    }
    public void setCircleRadius(int circleRadius){
        this.circleRadius = circleRadius;
    }
    public Color getCircleColor(){
        return circleColor;
    }
    public void setCircleSizeSmall(){
        setCircleRadius(25);
    }
    public void setCircleSizeMedium(){
        setCircleRadius(50);
    }
    public void setCircleSizeLarge(){
        setCircleRadius(100);
    }
    public void setCircleColorRed(){
        setCircleColor(Color.RED);
    }
    public void setCircleColorGreen(){
        setCircleColor(Color.GREEN);
    }
    public void setCircleColorBlue(){
        setCircleColor(Color.BLUE);
    }
    public void setCircleColorRandom(){
        setCircleColor(newColor());
    }
    public void setCircleColor(Color circleColor){
        this.circleColor = circleColor;
    }
    public Color newColor(){
        int red = (int)(Math.random() * 256);
        int green = (int)(Math.random() * 256);
        int blue = (int)(Math.random() * 256);
        return new Color(red, green, blue);
    }
}
class U06_AFG01b_View extends JComponent {
    private U06_AFG01b_Model m_Model;
    U06_AFG01b_View(U06_AFG01b_Model model){
        this.m_Model = model;
        addMouseMotionListener();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int circlePosX = m_Model.getCirclePosX();
        int circlePosY = m_Model.getCirclePosY();
        int radius = m_Model.getCircleRadius();
        g.setColor(m_Model.getCircleColor());
        g.fillOval(circlePosX - radius, circlePosY - radius, radius * 2, radius * 2);
    }
    private void addMouseMotionListener(){
        this.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();
                int circleX = m_Model.getCirclePosX();
                int circleY = m_Model.getCirclePosY();
                int radius = m_Model.getCircleRadius();

                int distanceSquared = (mouseX - circleX) * (mouseX - circleX) + (mouseY - circleY) * (mouseY - circleY);

                if(distanceSquared <= radius * radius){
                    int frameWidth = getWidth();
                    int frameHeight = getHeight();
                    m_Model.setFrameSize(frameWidth, frameHeight);
                    m_Model.newCirclePosition();
                    repaint();
                }
            }
        });
    }
}
class U06_AFG01b_Controller{
    private U06_AFG01b_Model m_Model;
    private U06_AFG01b_View m_View;
    private JFrame frame = new JFrame();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu sizeMenu = new JMenu("Change Circle Size");
    private JMenu colorMenu = new JMenu("Change Circle Color");
    U06_AFG01b_Controller(){
        m_Model = new U06_AFG01b_Model(600, 400, 50);
        m_View = new U06_AFG01b_View(m_Model);
        initFrame();
        createMenuBar();
        setVisible();
    }
    public void initFrame(){
        frame.setTitle("Catch me if you can");
        frame.setLocationRelativeTo(null);
        frame.add(m_View);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void createMenuBar(){
        createSizeMenu();
        createColorMenu();

        menuBar.add(sizeMenu);
        menuBar.add(colorMenu);
        frame.setJMenuBar(menuBar);
    }
    private void createSizeMenu(){
        JMenuItem smallSize = new JMenuItem("Small (25)");
        JMenuItem mediumSize = new JMenuItem("Medium (50)");
        JMenuItem largeSize = new JMenuItem("Large (100)");

        smallSize.addActionListener(e -> {
            m_Model.setCircleSizeSmall();
            m_View.repaint();
        });

        mediumSize.addActionListener(e -> {
            m_Model.setCircleSizeMedium();
            m_View.repaint();
        });

        largeSize.addActionListener(e -> {
            m_Model.setCircleSizeLarge();
            m_View.repaint();
        });

        sizeMenu.add(smallSize);
        sizeMenu.add(mediumSize);
        sizeMenu.add(largeSize);
    }
    private void createColorMenu(){
        JMenuItem redColor = new JMenuItem("Red");
        JMenuItem greenColor = new JMenuItem("Green");
        JMenuItem blueColor = new JMenuItem("Blue");
        JMenuItem randomColor = new JMenuItem("Random");

        redColor.addActionListener(e -> {
            m_Model.setCircleColorRed();
            m_View.repaint();
        });

        greenColor.addActionListener(e -> {
            m_Model.setCircleColorGreen();
            m_View.repaint();
        });

        blueColor.addActionListener(e -> {
            m_Model.setCircleColorBlue();
            m_View.repaint();
        });
        randomColor.addActionListener(e -> {
            m_Model.setCircleColorRandom();
            m_View.repaint();
        });

        colorMenu.add(redColor);
        colorMenu.add(greenColor);
        colorMenu.add(blueColor);
        colorMenu.add(randomColor);
    }
    private void setVisible(){
        frame.setVisible(true);
    }
}
public class U06_AFG01b {
    public static void main(String[] args){
        new U06_AFG01b_Controller();
    }
}