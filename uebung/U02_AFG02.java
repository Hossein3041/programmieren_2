// Aufgabe 2: Schreiben Sie ein Java Programm, dass ein Swing Fenster öffnet und die Deutschlandflagge
// (oder irgendeine andere Flagge) in das Swing Fenster zeichnet.
// Die Flagge soll an die Fenstergröße angepasst sein, die Größe ermitteln Sie mit getSize() Methode
// der JComponent Klasse.
package uebung;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
class U02_AFG02_View extends JComponent{
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int rectWidth = getSize().width; int rectHeight = getSize().height / 3;
        Color schwarz = new Color(0, 0, 0); Color rot = new Color(221, 0, 0); Color gold = new Color(255, 204, 0);
        Color[] color = {schwarz, rot, gold};
        for(int i = 0; i < 3; ++i){
            g.setColor(color[i]);
            int y = i * rectHeight;
            g.fillRect(0, y, rectWidth, rectHeight);
        }
    }
}
class U02_AFG02_Controller{
    private U02_AFG02_View m_View;
    private JFrame frame = new JFrame();
    U02_AFG02_Controller(){
        this.m_View = new U02_AFG02_View();
        initFrame();
    }
    public void initFrame(){
        Scanner sc = new Scanner(System.in);
        frame.setSize(sc.nextInt(), sc.nextInt());
        frame.setTitle("U02_AFG02");
        frame.add(m_View);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
public class U02_AFG02 {
    public static void main(String[] args){
        new U02_AFG02_Controller();
    }
}
