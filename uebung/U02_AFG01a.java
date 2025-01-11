// Aufgabe 1: Stellen Sie Ihre beiden Programme aus der ersten Übung auf das MVC Muster um.
// Die Rechtecke der zweiten Aufgabe sollen zusätzlich farbig gekennzeichnet werden.
// Dazu sollen die Farben zufällig bestimmt werden, sodass jedes Rechteck eine andere Farbe besitzt.
package uebung;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

class U02_AFG01a_Model{
    public int sizeX, sizeY;
    public float startX1, startY1, endX1, endY1, startX2, startY2, endX2, endY2;
    U02_AFG01a_Model(int sizeX, int sizeY){
        this.sizeX = sizeX; this.sizeY = sizeY;
        this.startX1 = this.startY1 = this.startY2 = this.endX2 = 0.0f;
        this.endX1 = this.endY1 = this.startX2 = this.endY2 = 1.0f;
    }
}
class U02_AFG01a_View extends JComponent {
    private U02_AFG01a_Model m_Model;

    U02_AFG01a_View(U02_AFG01a_Model model) {
        m_Model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine((int) m_Model.startX1 * getSize().width, (int) m_Model.startY1 * getSize().height, (int) m_Model.endX1 * getSize().width, (int) m_Model.endY1 * getSize().height);
        g.drawLine((int) m_Model.startX2 * getSize().width, (int) m_Model.startY2 * getSize().height, (int) m_Model.endX2 * getSize().width, (int) m_Model.endY2 * getSize().height);
    }
}

class U02_AFG01a_Controller {
    private U02_AFG01a_Model m_Model;
    private U02_AFG01a_View m_View;
    private JFrame frame = new JFrame();

    U02_AFG01a_Controller() {
        Scanner sc = new Scanner(System.in);
        m_Model = new U02_AFG01a_Model(sc.nextInt(), sc.nextInt());
        m_View = new U02_AFG01a_View(m_Model);
        initFrame();
    }

    private void initFrame() {
        frame.add(m_View);
        frame.setSize(m_Model.sizeX, m_Model.sizeY);
        frame.setVisible(true);
        frame.setTitle("U02_AFG01a");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class U02_AFG01a {
    public static void main(String[] args) {
        new U02_AFG01a_Controller();
    }
}
