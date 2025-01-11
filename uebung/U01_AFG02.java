// Schreiben Sie ein Java Programm, dass ein Swing Fenster öffnet und ausgefüllte Rechtecke
// in das Fenster zeichnet. Es sollen m-viele Rechtecke in einer Zeile und n-viele Zeilen
// untereinander gezeichnet werden. Zwischen den Rechtecken soll immer 2 Pixel Platz gelassen werden.
// Die beiden Werte für m und n werden im Konstruktor Ihrer Klasse angegeben.
// Zum Errechnen der Größe der Rechtecke benötigen Sie die Größe der JComponent.
// Diese berechnen Sie mittels der getSize() Methode.
package uebung;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class U01_AFG02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Erst Fenstergröße, dann Anzahl Rechtecke + Zeile: ");
        new U01_AFG02_Frame(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
    }
}

class U01_AFG02_Frame extends JFrame {
    private int x, y, m, n;

    U01_AFG02_Frame(int x, int y, int m, int n) {
        this.x = x;
        this.y = y;
        this.m = m;
        this.n = n;
        initFrame();
    }

    private void initFrame() {
        setSize(x, y);
        setTitle("U01_AFG02");
        setLocationRelativeTo(null);
        add(new U01_AFG02_Component(m, n));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class U01_AFG02_Component extends JComponent {
    private int m, n, space = 2;

    U01_AFG02_Component(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getSize().width;
        int height = getSize().height;

        int rectWidthSpace = width - (space * (m - 1));
        int rectHeightSpace = height - (space * (n - 1));

        int rectWidth = rectWidthSpace / m;
        int rectHeight = rectHeightSpace / n;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int x = j * (rectWidth + space);
                int y = i * (rectHeight + space);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
}
