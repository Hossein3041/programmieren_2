// Aufgabe 1: Erweitern Sie Ihre Programme aus der zweiten Übung derart,
// dass Sie die Icons der Fenster und die Mousecursor verändern.
package uebung;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
class U03_AFG01a_Model{
    public int sizeX, sizeY;
    public float startX1, startY1, endX1, endY1, startX2, startY2, endX2, endY2;
    U03_AFG01a_Model(int sizeX, int sizeY){
        this.sizeX = sizeX; this.sizeY = sizeY;
        this.startX1 = this.startY1 = this.startY2 = this.endX2 = 0.0f;
        this.endX1 = this.endY1 = this.startX2 = this.endY2 = 1.0f;
    }
}
class U03_AFG01a_View extends JComponent{
    private U03_AFG01a_Model m_Model;
    U03_AFG01a_View(U03_AFG01a_Model model){
        m_Model = model;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawLine((int)m_Model.startX1 * getSize().width,(int)m_Model.startY1 * getSize().height,(int)m_Model.endX1 * getSize().width,(int)m_Model.endY1 * getSize().height);
        g.drawLine((int)m_Model.startX2 * getSize().width,(int)m_Model.startY2 * getSize().height,(int)m_Model.endX2 * getSize().width,(int)m_Model.endY2 * getSize().height);
    }
}
class U03_AFG01a_Controller{
    private U03_AFG01a_Model m_Model;
    private U03_AFG01a_View m_View;
    private JFrame frame = new JFrame();
    private ImageIcon icon;
    U03_AFG01a_Controller(){
        Scanner sc = new Scanner(System.in);
        m_Model = new U03_AFG01a_Model(sc.nextInt(), sc.nextInt());
        m_View = new U03_AFG01a_View(m_Model);
        initFrame();
        setPictureAndCursor();
    }
    private void initFrame(){
        frame.add(m_View);
        frame.setSize(m_Model.sizeX, m_Model.sizeY);
        frame.setVisible(true);
        frame.setTitle("U03_AFG01a");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setPictureAndCursor(){
        icon = new ImageIcon("src/uebung/U03_AFG00.png");
        frame.setIconImage(icon.getImage());
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
}
public class U03_AFG01a {
    public static void main(String[] args){
        new U03_AFG01a_Controller();
    }
}
