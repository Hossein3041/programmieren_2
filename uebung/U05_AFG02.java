// Aufgabe 02: Schreiben Sie ein Java Programm (MVC Muster beachten), das ein Swing Fenster öffnet,
// indem an einer zufälligen Position ein ausgefüllter Kreis gezeichnet wird.
// Sobald die Maus in den Kreis fährt, soll der Kreis an einer anderen Position gezeichnet werden.
package uebung;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
class U05_AFG02_Model{
    private int circlePosX, circlePosY, circleRadius;
    private int frameWidth, frameHeight;
    U05_AFG02_Model(int frameWidth, int frameHeight, int circleRadius){
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
}
class U05_AFG02_View extends JComponent{
    private U05_AFG02_Model m_Model;
    U05_AFG02_View(U05_AFG02_Model model){
        this.m_Model = model;
        addMouseMotionListener();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int circlePosX = m_Model.getCirclePosX();
        int circlePosY = m_Model.getCirclePosY();
        int radius = m_Model.getCircleRadius();
        g.setColor(Color.RED);
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
class U05_AFG02_Controller{
    private U05_AFG02_Model m_Model;
    private U05_AFG02_View m_View;
    private JFrame frame = new JFrame();
    U05_AFG02_Controller(){
        m_Model = new U05_AFG02_Model(600, 400, 50);
        m_View = new U05_AFG02_View(m_Model);
        initFrame();
    }
    public void initFrame(){
        frame.setTitle("Catch me if you can");
        frame.setLocationRelativeTo(null);
        frame.add(m_View);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
public class U05_AFG02 {
    public static void main(String[] args){
        new U05_AFG02_Controller();
    }
}
