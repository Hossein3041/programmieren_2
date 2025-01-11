// Aufgabe 1: Erweitern Sie Ihre Programme aus der zweiten Übung derart,
// dass Sie die Icons der Fenster und die Mousecursor verändern.
package uebung;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
class U03_AFG01b_Model{
    public int sizeX, sizeY, m, n, space = 2;
    U03_AFG01b_Model(int sizeX, int sizeY, int m, int n){
        this.sizeX = sizeX; this.sizeY = sizeY; this.m = m; this.n = n;
    }
}
class U03_AFG01b_View extends JComponent{
    private U03_AFG01b_Model m_Model;
    U03_AFG01b_View(U03_AFG01b_Model model){
        this.m_Model = model;
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getSize().width;
        int height = getSize().height;

        int rectWidthSpace = width - (m_Model.space * (m_Model.m - 1));
        int rectHeightSpace = height - (m_Model.space * (m_Model.n - 1));

        int rectWidth = rectWidthSpace / m_Model.m;
        int rectHeight = rectHeightSpace / m_Model.n;

        for(int i = 0; i < m_Model.n; ++i){
            for(int j = 0; j < m_Model.m; ++j){
                int x = j * (rectWidth + m_Model.space);
                int y = i * (rectHeight + m_Model.space);
                g.setColor(newColor());
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }

    private Color newColor(){
        int red = (int)(Math.random() * 256);
        int green = (int)(Math.random() * 256);
        int blue = (int)(Math.random() * 256);
        return new Color(red, green, blue);
    }
}
class U03_AFG01b_Controller{
    private U03_AFG01b_Model m_Model;
    private U03_AFG01b_View m_View;
    private JFrame frame = new JFrame();
    private ImageIcon icon;
    U03_AFG01b_Controller(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Erst Fenstergröße, dann Anzahl Rechtecke + Zeile: ");
        m_Model = new U03_AFG01b_Model(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        m_View = new U03_AFG01b_View(m_Model);
        initFrame();
        setPictureAndCursor();
    }
    public void initFrame(){
        frame.add(m_View);
        frame.setSize(m_Model.sizeX, m_Model.sizeY);
        frame.setVisible(true);
        frame.setTitle("U03_AFG01b");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setPictureAndCursor(){
        icon = new ImageIcon("src/uebung/U03_AFG00.png");
        frame.setIconImage(icon.getImage());
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
}
public class U03_AFG01b {
    public static void main(String[] args){
        new U03_AFG01b_Controller();
    }
}
