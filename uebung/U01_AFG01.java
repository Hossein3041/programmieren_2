// Aufgabe 01: Schreiben Sie ein Java Programm, dass ein Swing Fenster öffnet,
// in das eine Linie von links oben nach rechts unten und eine Linie von rechts oben nach links unten
// gezeichnet wird. Dazu soll die Fenstergröße dynamisch ermittelt werden,
// in der JComponent können Sie hierzu mittels getSize() die Größe abfragen.
package uebung;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class U01_AFG01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        new U01_AFG01_Frame(sc.nextInt(), sc.nextInt());
    }
}

class U01_AFG01_Frame extends JFrame {
    private int x;
    int y;

    U01_AFG01_Frame(int x, int y) {
        this.x = x;
        this.y = y;
        initFrame();
    }

    private void initFrame() {
        setSize(x, y);
        setVisible(true);
        setTitle("U01_AFG01");
        setLocationRelativeTo(null);
        add(new U01_AFG01_Component());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class U01_AFG01_Component extends JComponent {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, 0, getWidth(), getHeight());
        g.drawLine(getWidth(), 0, 0, getHeight());
    }
}
