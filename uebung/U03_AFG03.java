// Aufgabe 3: Versuchen Sie bei Aufgabe 2 die Größe der Schrift an die Fenstergröße anzupassen,
// sodass bei größerem Fenster auch die Schrift größer wird. Wie üblich ermitteln Sie die Größe
// der JComponent mittels getSize() Methode.
package uebung;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

class U03_AFG03_View extends JComponent {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rectWidth = getSize().width;
        int rectHeight = getSize().height / 3;
        Color schwarz = new Color(0, 0, 0);
        Color rot = new Color(221, 0, 0);
        Color gold = new Color(255, 204, 0);
        Color[] color = {schwarz, rot, gold};
        for (int i = 0; i < 3; ++i) {
            g.setColor(color[i]);
            int y = i * rectHeight;
            g.fillRect(0, y, rectWidth, rectHeight);
        }
        String countryName = new String("DEUTSCHLAND");
        g.setFont(new Font("Arial Black", Font.BOLD, getSize().height / 10));

        FontMetrics metrics = g.getFontMetrics();   // Textgröße verändern
        int textWidth = metrics.stringWidth(countryName);
        int textHeight = metrics.getHeight();

        int posX = (getSize().width - textWidth) / 2;
        int posY = (getSize().height + textHeight) / 2 - metrics.getDescent();

        g.setColor(Color.WHITE);
        g.drawString(countryName, posX, posY);
    }
}

class U03_AFG03_Controller {
    private U03_AFG03_View m_View;
    private JFrame frame = new JFrame();

    U03_AFG03_Controller() {
        this.m_View = new U03_AFG03_View();
        initFrame();
    }

    public void initFrame() {
        Scanner sc = new Scanner(System.in);
        frame.setSize(sc.nextInt(), sc.nextInt());
        frame.setTitle("U03_AFG03");
        frame.add(m_View);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

public class U03_AFG03 {
    public static void main(String[] args) {
        new U03_AFG03_Controller();
    }
}
