// Aufgabe 2: Das Fenster zur Darstellung der Deutschlandflagge (oder irgendeiner anderen Flagge)
// soll in der Mitte des Fensters den Namen des Landes, zu der die Flagge gehört, geschrieben werden.
package uebung;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
class U03_AFG02_View extends JComponent{
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
        String countryName = new String("DEUTSCHLAND");
        g.setFont(new Font("Arial Black", Font.BOLD, 20));

        FontMetrics metrics = g.getFontMetrics();   // Textgröße verändern
        int textWidth = metrics.stringWidth(countryName);
        int textHeight = metrics.getHeight();

        int posX = (getSize().width - textWidth) / 2;
        int posY = (getSize().height + textHeight) / 2 - metrics.getDescent();

        g.setColor(Color.WHITE);
        g.drawString(countryName, posX, posY);
    }
}
class U03_AFG02_Controller{
    private U03_AFG02_View m_View;
    private JFrame frame = new JFrame();
    U03_AFG02_Controller(){
        this.m_View = new U03_AFG02_View();
        initFrame();
    }
    public void initFrame(){
        Scanner sc = new Scanner(System.in);
        frame.setSize(sc.nextInt(), sc.nextInt());
        frame.setTitle("U03_AFG02");
        frame.add(m_View);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
public class U03_AFG02 {
    public static void main(String[] args){
        new U03_AFG02_Controller();
    }
}
