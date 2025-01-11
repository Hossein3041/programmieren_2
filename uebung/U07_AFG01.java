// Aufgabe 02: Wenn das Fenster geschlossen wird, soll ein Dialog erscheinen,
// der abfragt, ob das Fenster wirklich geschlossen werden soll.
package uebung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

class U07_AFG01_CloseDialog extends JDialog {
    private boolean confirmed;
    private JFrame parent;

    public U07_AFG01_CloseDialog(JFrame parent) {
        super(parent, "Confirmed Close", true);
        this.setLayout(new BorderLayout());
        this.parent = parent;
        initDialog();
    }

    public void initDialog() {
        JLabel message = new JLabel("DO YOU REALLY WANT TO CLOSE THE WINDOW ?");
        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("YES");
        JButton noButton = new JButton("NO");

        yesButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        noButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        this.add(message, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

enum U07_AFG01_ShapeType {
    CIRCLE, SQUARE, TRIANGLE
}

abstract class U07_AFG01_MenuAdapter implements MenuListener {
    @Override
    public void menuSelected(MenuEvent e) {
    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }
}

class U07_AFG01_Model {
    private int shapePosX, shapePosY, shapeSize, frameWidth, frameHeight;
    private Color shapeColor = Color.RED;
    private U07_AFG01_ShapeType activeShape = U07_AFG01_ShapeType.CIRCLE;

    U07_AFG01_Model(int frameWidth, int frameHeight, int shapeSize) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.shapeSize = shapeSize;
        newShapePosition();
    }

    public void setFrameSize(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public void newShapePosition() {
        this.shapePosX = (int) (Math.random() * (frameWidth - 2 * shapeSize)) + shapeSize;
        this.shapePosY = (int) (Math.random() * (frameHeight - 2 * shapeSize)) + shapeSize;
    }

    public int getShapePosX() {
        return shapePosX;
    }

    public int getShapePosY() {
        return shapePosY;
    }

    public int getShapeSize() {
        return shapeSize;
    }

    public void setShapeSize(int shapeSize) {
        this.shapeSize = shapeSize;
    }

    public Color getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public U07_AFG01_ShapeType getActiveShape() {
        return activeShape;
    }

    public void setActiveShape(U07_AFG01_ShapeType shape) {
        this.activeShape = shape;
    }

    public void setShapeSizeSmall() {
        setShapeSize(25);
    }

    public void setShapeSizeMedium() {
        setShapeSize(50);
    }

    public void setShapeSizeLarge() {
        setShapeSize(100);
    }

    public void setShapeColorRed() {
        setShapeColor(Color.RED);
    }

    public void setShapeColorGreen() {
        setShapeColor(Color.GREEN);
    }

    public void setShapeColorBlue() {
        setShapeColor(Color.BLUE);
    }

    public void setShapeColorRandom() {
        setShapeColor(newColor());
    }

    public Color newColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new Color(red, green, blue);
    }
}

class U07_AFG01_View extends JComponent {
    private U07_AFG01_Model m_Model;

    U07_AFG01_View(U07_AFG01_Model model) {
        this.m_Model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int posX = m_Model.getShapePosX();
        int posY = m_Model.getShapePosY();
        int size = m_Model.getShapeSize();
        g.setColor(m_Model.getShapeColor());

        switch (m_Model.getActiveShape()) {
            case CIRCLE:
                g.fillOval(posX - size, posY - size, size * 2, size * 2);
                break;
            case SQUARE:
                g.fillRect(posX - size, posY - size, size * 2, size * 2);
                break;
            case TRIANGLE:
                int[] xPoints = {posX, posX - size, posX + size};
                int[] yPoints = {posY - size, posY + size, posY + size};
                g.fillPolygon(xPoints, yPoints, 3);
                break;
        }
    }

    public void showCloseDialog(JFrame frame) {
        U07_AFG01_CloseDialog dialog = new U07_AFG01_CloseDialog(frame);
        dialog.setVisible(true);

        if (dialog.isConfirmed())
            frame.dispose();
    }
}

class U07_AFG01_Controller {
    private U07_AFG01_Model m_Model;
    private U07_AFG01_View m_View;
    private JFrame frame = new JFrame();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu sizeMenu = new JMenu("Change Shape Size");
    private JMenu colorMenu = new JMenu("Change Shape Color");
    private JMenu shapeMenu = new JMenu("Select Shape");

    U07_AFG01_Controller() {
        m_Model = new U07_AFG01_Model(600, 400, 50);
        m_View = new U07_AFG01_View(m_Model);
        initFrame();
        createMenuBar();
        addMouseMotionListenerToView();
        addWindowListenerToView();
        setVisible();
    }

    public void initFrame() {
        frame.setTitle("Catch me if you can");
        frame.setLocationRelativeTo(null);
        frame.add(m_View);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void createMenuBar() {
        createSizeMenu();
        createColorMenu();
        createShapeMenu();

        menuBar.add(sizeMenu);
        menuBar.add(colorMenu);
        menuBar.add(shapeMenu);
        frame.setJMenuBar(menuBar);
    }

    private void createSizeMenu() {
        JMenuItem smallSize = new JMenuItem("Small (25)");
        JMenuItem mediumSize = new JMenuItem("Medium (50)");
        JMenuItem largeSize = new JMenuItem("Large (100)");

        smallSize.addActionListener(e -> {
            m_Model.setShapeSizeSmall();
            m_View.repaint();
        });

        mediumSize.addActionListener(e -> {
            m_Model.setShapeSizeMedium();
            m_View.repaint();
        });

        largeSize.addActionListener(e -> {
            m_Model.setShapeSizeLarge();
            m_View.repaint();
        });

        sizeMenu.add(smallSize);
        sizeMenu.add(mediumSize);
        sizeMenu.add(largeSize);
    }

    private void createColorMenu() {
        JMenuItem redColor = new JMenuItem("Red");
        JMenuItem greenColor = new JMenuItem("Green");
        JMenuItem blueColor = new JMenuItem("Blue");
        JMenuItem randomColor = new JMenuItem("Random");

        redColor.addActionListener(e -> {
            m_Model.setShapeColorRed();
            m_View.repaint();
        });

        greenColor.addActionListener(e -> {
            m_Model.setShapeColorGreen();
            m_View.repaint();
        });

        blueColor.addActionListener(e -> {
            m_Model.setShapeColorBlue();
            m_View.repaint();
        });
        randomColor.addActionListener(e -> {
            m_Model.setShapeColorRandom();
            m_View.repaint();
        });

        colorMenu.add(redColor);
        colorMenu.add(greenColor);
        colorMenu.add(blueColor);
        colorMenu.add(randomColor);
    }

    private void createShapeMenu() {
        JMenuItem shapeSelectionItem = new JMenuItem("Change Shape Form");
        shapeSelectionItem.addActionListener(e -> showShapeSelectionDialog());
        shapeMenu.add(shapeSelectionItem);
    }

    private void addMouseMotionListenerToView() {
        m_View.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int shapeX = m_Model.getShapePosX();
                int shapeY = m_Model.getShapePosY();
                int size = m_Model.getShapeSize();

                boolean contains = false;
                switch (m_Model.getActiveShape()) {
                    case CIRCLE:
                        int distanceSquared = (mouseX - shapeX) * (mouseX - shapeX) + (mouseY - shapeY) * (mouseY - shapeY);
                        contains = distanceSquared <= size * size;
                        break;
                    case SQUARE:
                        contains = mouseX >= shapeX - size && mouseX <= shapeX + size && mouseY >= shapeY - size && mouseY <= shapeY + size;
                        break;
                    case TRIANGLE:
                        Polygon triangle = new Polygon(
                                new int[]{shapeX, shapeX - size, shapeX + size},
                                new int[]{shapeY - size, shapeY + size, shapeY + size},
                                3
                        );
                        contains = triangle.contains(mouseX, mouseY);
                        break;
                }
                if (contains) {
                    int frameWidth = m_View.getWidth();
                    int frameHeight = m_View.getHeight();
                    m_Model.setFrameSize(frameWidth, frameHeight);

                    m_Model.newShapePosition();
                    m_View.repaint();
                }
            }
        });
    }

    private void showShapeSelectionDialog() {
        JDialog dialog = new JDialog(frame, "Select Shape", true);
        dialog.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("Choose a new shape:", JLabel.CENTER);
        dialog.add(label);

        JButton circleButton = new JButton("Circle");
        circleButton.addActionListener(e -> {
            m_Model.setActiveShape(U07_AFG01_ShapeType.CIRCLE);
            dialog.dispose();
        });
        dialog.add(circleButton);

        JButton squareButton = new JButton("Square");
        squareButton.addActionListener(e -> {
            m_Model.setActiveShape(U07_AFG01_ShapeType.SQUARE);
            dialog.dispose();
        });
        dialog.add(squareButton);

        JButton triangleButton = new JButton("Triangle");
        triangleButton.addActionListener(e -> {
            m_Model.setActiveShape(U07_AFG01_ShapeType.TRIANGLE);
            dialog.dispose();
        });
        dialog.add(triangleButton);

        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        m_Model.newShapePosition();
        m_View.repaint();
    }

    public void addWindowListenerToView() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                m_View.showCloseDialog(frame);
            }
        });
    }

    private void setVisible() {
        frame.setVisible(true);
    }
}

public class U07_AFG01 {
    public static void main(String[] args) {
        new U07_AFG01_Controller();
    }
}