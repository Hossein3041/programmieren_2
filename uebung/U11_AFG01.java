// Aufgabe 01: Schreiben Sie ein Java Programm (MVC Muster beachten),
// dass ein Swing Fenster öffnet, das an einer zufälligen Position einen Kreis zeichnet
// und nach einer kurzen Zeit den Kreis an einer neuen Position zeichnet.
// Wenn der Mauszeiger sich im Kreis befindet und die linke Maustaste gedrückt wird,
// soll ein Dialogfenster geöffnet werden, in dem "Treffer" steht.
// Die Anzahl der bisherigen Treffer soll ebenfalls angezeigt werden.
package uebung;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class U11_AFG01_Model {
    private int frameWidth, frameHeight, circlePosX, circlePosY;
    private int circleRadius = 50;
    private int hitCount = 0;

    U11_AFG01_Model(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        newCirclePosition();
    }

    public void setFrameSize(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public synchronized void newCirclePosition() {
        this.circlePosX = (int) (Math.random() * (frameWidth - 2 * circleRadius)) + circleRadius;
        this.circlePosY = (int) (Math.random() * (frameHeight - 2 * circleRadius)) + circleRadius;
    }

    public synchronized int getCirclePosX() {
        return circlePosX;
    }

    public synchronized int getCirclePosY() {
        return circlePosY;
    }

    public synchronized int getCircleRadius() {
        return circleRadius;
    }

    public synchronized boolean isCircleHit(int mouseX, int mouseY) {
        int distanceSquared = (mouseX - circlePosX) * (mouseX - circlePosX)
                + (mouseY - circlePosY) * (mouseY - circlePosY);
        return distanceSquared <= circleRadius * circleRadius;
    }

    public synchronized int getHitCount() {
        return ++hitCount;
    }
}

class U11_AFG01_View extends JFrame {
    private JComponent drawComponent;
    private U11_AFG01_Model m_Model;

    U11_AFG01_View(U11_AFG01_Model model) {
        this.m_Model = model;
        initFrame();
    }

    private void initFrame() {
        setTitle("U11_AFG01");
        setLocationRelativeTo(null);
        setSize(m_Model.getFrameWidth(), m_Model.getFrameHeight());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        drawComponentForFrame();
        add(drawComponent);
    }

    private void drawComponentForFrame() {
        drawComponent = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.fillOval(m_Model.getCirclePosX() - m_Model.getCircleRadius(),
                        m_Model.getCirclePosY() - m_Model.getCircleRadius(),
                        m_Model.getCircleRadius() * 2, m_Model.getCircleRadius() * 2);
            }
        };
    }

    public void repaintComponent() {
        drawComponent.repaint();
    }
}

class U11_AFG01_Controller {
    private U11_AFG01_Model m_Model;
    private U11_AFG01_View m_View;
    private boolean isRunning = true;

    U11_AFG01_Controller() {
        m_Model = new U11_AFG01_Model(600, 400);
        m_View = new U11_AFG01_View(m_Model);
        addMouseListenerToView();
        startCircleMovement();
        stopCircleMovement();
        setVisible();
    }

    private void addMouseListenerToView() {
        m_View.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    handleCircleHit(e.getX(), e.getY());
                }
            }
        });
    }

    private void handleCircleHit(int mouseX, int mouseY) {
        if (m_Model.isCircleHit(mouseX, mouseY)) {
            int hitCount = m_Model.getHitCount();
            JOptionPane.showMessageDialog(null, "Treffer! Anzahl Treffer:" + hitCount);
        }
    }

    private void startCircleMovement() {
        Thread movementThread = new Thread(() -> {
            while (isRunning) {
                updateCirclePosition();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        movementThread.start();
    }

    private void updateCirclePosition() {
        m_Model.setFrameSize(m_View.getWidth(), m_View.getHeight());
        m_Model.newCirclePosition();
        m_View.repaintComponent();
    }

    private void stopCircleMovement() {
        m_View.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRunning = false;
            }
        });
    }

    private void setVisible() {
        m_View.setVisible(true);
    }
}

public class U11_AFG01 {
    public static void main(String[] args) {
        new U11_AFG01_Controller();
    }
}