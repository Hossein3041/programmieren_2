// Aufgabe 1: Erweitern Sie Ihr Programm zur Darstellung der Rechtecke derart,
// dass Sie einzelne Rechtecke mit der Maus anklicken können und auf dem Bildschirm
// (Konsole mittels System.out.println) die Position des Rechtecks ausgegeben wird,
// sprich Zeile und Spalte.
package uebung;
import java.awt.*;
import  javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
class U04_AFG01_Model{
    public int sizeX, sizeY, m, n, space = 2;
    U04_AFG01_Model(int sizeX, int sizeY, int m, int n){
        this.sizeX = sizeX; this.sizeY = sizeY; this.m = m; this.n = n;
    }
}
class U04_AFG01_View extends JComponent{
    private U04_AFG01_Model m_Model;
    U04_AFG01_View(U04_AFG01_Model model){
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
class U04_AFG01_Controller{
    private U04_AFG01_Model m_Model;
    private U04_AFG01_View m_View;
    private JFrame frame = new JFrame();
    private ImageIcon icon;
    U04_AFG01_Controller(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Erst Fenstergröße, dann Anzahl Rechtecke + Zeile: ");
        m_Model = new U04_AFG01_Model(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        m_View = new U04_AFG01_View(m_Model);

        initFrame();
        setPictureAndCursor();
        addMouseListenerToView();
    }
    public void initFrame(){
        frame.add(m_View);
        frame.setSize(m_Model.sizeX, m_Model.sizeY);
        frame.setVisible(true);
        frame.setTitle("U04_AFG01");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setPictureAndCursor(){
        icon = new ImageIcon("src/uebung/U03_AFG00.png");
        frame.setIconImage(icon.getImage());
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    private void addMouseListenerToView(){
        m_View.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                clickRect(e.getX(), e.getY());
            }
        });
    }
    public void clickRect(int mousePosX, int mousePosY){
        int width = m_View.getSize().width;
        int height = m_View.getSize().height;

        int rectWidthSpace = width - (m_Model.space * (m_Model.m));
        int rectHeightSpace = height - (m_Model.space * (m_Model.n));

        int rectWidth = rectWidthSpace / m_Model.m;
        int rectHeight = rectHeightSpace / m_Model.n;
        int remainingHeight = height % m_Model.n;
        rectHeight += remainingHeight / m_Model.n;

        for(int i = 0; i < m_Model.n; ++i){
            for(int j = 0; j < m_Model.m; ++j){
                int rectPosX = j * (rectWidth + m_Model.space);
                int rectPosY = i * (rectHeight + m_Model.space);

                if(mousePosX >= rectPosX && mousePosX <= rectPosX + rectWidth &&
                        mousePosY >= rectPosY && mousePosY <= rectPosY + rectWidth){
                    System.out.println("Rect at position [" + i + "|" + j + "]");
                    return;
                }
            }
        }
    }
}
public class U04_AFG01 {
    public static void main(String[] args){
        new U04_AFG01_Controller();
    }
}
