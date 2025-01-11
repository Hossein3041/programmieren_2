// Aufgabe 01: Schreiben Sie ein Java Programm (MVC Muster beachten),
// das ein Swing Fenster öffnet, das zwei JSlider besitzt.
// Über diese beiden Slider soll ein Kreis auf dem Bildschirm verschoben werden können.
// Ein Slider stellt die horizontale, eine die vertikale Position ein.
package uebung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class CirclePanel extends JPanel {
    private U09_AFG01_Model m_Model;

    CirclePanel(U09_AFG01_Model model) {
        this.m_Model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int circlePosX = m_Model.getCirclePosX();
        int circlePosY = m_Model.getCirclePosY();
        int circleRadius = m_Model.getCircleRadius();
        g.setColor(m_Model.getCircleColor());
        g.fillOval(circlePosX - circleRadius, circlePosY - circleRadius, circleRadius * 2, circleRadius * 2);
    }
}

class U09_AFG01_Model {
    private int circlePosX, circlePosY;
    private int circleRadius = 30;
    private final Color circleColor = Color.RED;

    U09_AFG01_Model() {
        this.circlePosX = this.circlePosY = 50;
    }

    public void setCirclePosX(int posX) {
        this.circlePosX = posX;
    }

    public void setCirclePosY(int posY) {
        this.circlePosY = posY;
    }

    public void setCircleRadius(int radius) {
        this.circleRadius = radius;
    }

    public int getCirclePosX() {
        return circlePosX;
    }

    public int getCirclePosY() {
        return circlePosY;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public Color getCircleColor() {
        return circleColor;
    }
}

class U09_AFG01_View extends JFrame {
    private U09_AFG01_Model m_Model;
    private JSlider xSlider, ySlider, sizeSlider;
    private JButton resetButton;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private CirclePanel circlePanel;

    U09_AFG01_View(U09_AFG01_Model model) {
        this.m_Model = model;
        initFrame();
        initControls();
        addLayout();
        addListeners();
    }

    public void initFrame() {
        setTitle("U09_AFG01");
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initControls() {
        xSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, m_Model.getCirclePosX());
        ySlider = new JSlider(JSlider.VERTICAL, 0, 100, m_Model.getCirclePosY());
        sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 200, m_Model.getCircleRadius());

        configureSlider(xSlider);
        configureSlider(ySlider);
        configureSlider(sizeSlider);

        resetButton = new JButton("Reset Position");

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));
        controlPanel.add(resetButton);
        controlPanel.add(sizeSlider);

        statusLabel = new JLabel("Circle Position: X=50, Y=50", JLabel.CENTER);
        circlePanel = new CirclePanel(m_Model);
    }

    private void addLayout() {
        setLayout(new BorderLayout());
        add(circlePanel, BorderLayout.CENTER);
        add(xSlider, BorderLayout.SOUTH);
        add(ySlider, BorderLayout.WEST);
        add(controlPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.EAST);
    }

    private void configureSlider(JSlider slider) {
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    }

    private void addListeners() {
        xSlider.addChangeListener(e -> {
            m_Model.setCirclePosX(xSlider.getValue());
            circlePanel.repaint();  // Kreis im CirclePanel neu zeichnen
            updateStatusLabel();
        });

        ySlider.addChangeListener(e -> {
            m_Model.setCirclePosY(ySlider.getValue());
            circlePanel.repaint();  // Kreis im CirclePanel neu zeichnen
            updateStatusLabel();
        });

        sizeSlider.addChangeListener(e -> {
            m_Model.setCircleRadius(sizeSlider.getValue());
            circlePanel.repaint();  // Kreis im CirclePanel neu zeichnen
            updateStatusLabel();
        });

        resetButton.addActionListener(e -> resetPosition());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                xSlider.setMaximum(width - m_Model.getCircleRadius() * 2);
                ySlider.setMaximum(height - m_Model.getCircleRadius() * 2);
            }
        });
    }

    private void resetPosition() {
        xSlider.setValue(50);
        ySlider.setValue(50);
        m_Model.setCirclePosX(50);
        m_Model.setCirclePosY(50);
        m_Model.setCircleRadius(30);
        updateStatusLabel();
        repaint();
    }

    private void updateStatusLabel() {
        statusLabel.setText("PosX=" + m_Model.getCirclePosX() + ", Y=" + m_Model.getCirclePosY() + ", Radius=" + m_Model.getCircleRadius());
    }
}

class U09_AFG01_Controller {
    private U09_AFG01_View m_View;
    private U09_AFG01_Model m_Model;

    U09_AFG01_Controller() {
        m_Model = new U09_AFG01_Model();
        m_View = new U09_AFG01_View(m_Model);
        m_View.setVisible(true);
    }
}

public class U09_AFG01 {
    public static void main(String[] args) {
        new U09_AFG01_Controller();
    }
}
