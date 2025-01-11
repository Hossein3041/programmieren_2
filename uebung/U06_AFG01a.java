// Aufgabe 01: Erweitern Sie Ihre Programme aus der letzten Übung derart,
// dass Sie Größe und Farben der Rechtecke bzw. des Kreises mittels Menus einstellen können.
package uebung;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
class U06_AFG01a_Model{
    public int sizeX, sizeY, m, n, space = 2;
    private Color[][] colors;
    U06_AFG01a_Model(int sizeX, int sizeY, int m, int n){
        this.sizeX = sizeX; this.sizeY = sizeY; this.m = m; this.n = n;
        this.colors = new Color[n][m];
        initColors();
    }
    public void initColors(){
        colors = new Color[n][m];
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < m; ++j){
                colors[i][j] = newColor();
            }
        }
    }
    public Color getColor(int i, int j){
        return colors[i][j];
    }
    public void setColor(int i, int j, Color color){
        colors[i][j] = color;
    }
    public Color newColor(){
        int red = (int)(Math.random() * 256);
        int green = (int)(Math.random() * 256);
        int blue = (int)(Math.random() * 256);
        return new Color(red, green, blue);
    }
    public void setRectSize(int newM, int newN){
        this.m = newM; this.n = newN;
        initColors();
    }
    public void resetRectSize(int orgM, int orgN){
        this.m = orgM; this.n = orgN;
        initColors();
    }
    public void adjustRectSize(int delta){
        this.m = Math.max(1, Math.min(100, this.m + delta));
        this.n = Math.max(1, Math.min(100, this.n + delta));
        initColors();
    }
    public int getOriginalM(){
        return this.m;
    }
    public int getOriginalN(){
        return this.n;
    }
    public void changeColorForAllRects(Color color){
        for(int i = 0; i < this.n; ++i){
            for(int j = 0; j < this.m; ++j){
                setColor(i, j, color);
            }
        }
    }
    public void setRandomColorForAllRects(){
        for(int i = 0; i < this.n; ++i){
            for(int j = 0; j < this.m; ++j){
                setColor(i, j, newColor());
            }
        }
    }
    public void setRandomColorForRect(int row, int col){
        setColor(row, col, newColor());
    }
}
class U06_AFG01a_View extends JComponent{
    private U06_AFG01a_Model m_Model;
    U06_AFG01a_View(U06_AFG01a_Model model){
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
            for(int j = 0; j <  m_Model.m; ++j){
                int x = j * (rectWidth + m_Model.space);
                int y = i * (rectHeight + m_Model.space);
                g.setColor(m_Model.getColor(i, j));
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
}
class U06_AFG01a_Controller{
    private U06_AFG01a_Model m_Model;
    private U06_AFG01a_View m_View;
    private JFrame frame = new JFrame();
    private ImageIcon icon;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu rectSizeMenu = new JMenu("Change Rect Size");
    private JMenu rectColorMenu = new JMenu("Change Rect Color");
    U06_AFG01a_Controller(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Erst Fenstergröße, dann Anzahl Rechtecke + Zeile: ");
        m_Model = new U06_AFG01a_Model(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        m_View = new U06_AFG01a_View(m_Model);

        initFrame();
        setPictureAndCursor();
        addMouseListenerToView();
        addMouseWheelListenerToView();
        createMenuBar();
        closeTheProgram();
        setVisible();
    }
    public void initFrame(){
        frame.add(m_View);
        frame.setSize(m_Model.sizeX, m_Model.sizeY);
        frame.setTitle("U06_AFG01a");
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
    private void addMouseWheelListenerToView(){
        m_View.addMouseWheelListener(e -> {
            int[] indexRect = getIndexRect(e.getX(), e.getY());
            if(indexRect != null){
                m_Model.setColor(indexRect[0], indexRect[1],m_Model.newColor());
                m_View.repaint();
            }
        });
    }
    private int[] getIndexRect(int mousePosX, int mousePosY){
        int width = m_View.getSize().width;
        int height = m_View.getSize().height;

        int rectWidthSpace = width - (m_Model.space * (m_Model.m - 1));
        int rectHeightSpace = height - (m_Model.space * (m_Model.n - 1));

        int rectWidth = rectWidthSpace / m_Model.m;
        int rectHeight = rectHeightSpace / m_Model.n;

        int col = mousePosX / (rectWidth + m_Model.space);
        int row = mousePosY / (rectHeight + m_Model.space);

        if(col >= 0 && col < m_Model.m && row >= 0 && row < m_Model.n){
            return new int[]{row, col};
        }
        return null;
    }
    private void createMenuBar(){

        createRectSizeMenu();
        createRectColorMenu();

        menuBar.add(rectSizeMenu);
        menuBar.add(rectColorMenu);
        frame.setJMenuBar(menuBar);
    }
    private void createRectSizeMenu(){
        JMenuItem minSize = new JMenuItem("Min (1)");
        JMenuItem reduceSize = new JMenuItem("Reduce (-1)");
        JMenuItem resetSize = new JMenuItem("Reset");
        JMenuItem increaseSize = new JMenuItem("Increase (+1)");
        JMenuItem maxSize = new JMenuItem("Max (100)");

        final int orgM = m_Model.getOriginalM();
        final int orgN = m_Model.getOriginalN();

        minSize.addActionListener(e -> {
            m_Model.setRectSize(1, 1);
            recreateColorMenu();
            m_View.repaint();
        });

        reduceSize.addActionListener(e -> {
            m_Model.adjustRectSize(-1);
            recreateColorMenu();
            m_View.repaint();
        });

        resetSize.addActionListener(e -> {
            m_Model.resetRectSize(orgM, orgN);
            recreateColorMenu();
            m_View.repaint();
        });

        increaseSize.addActionListener(e -> {
            m_Model.adjustRectSize(1);
            recreateColorMenu();
            m_View.repaint();
        });

        maxSize.addActionListener(e -> {
            m_Model.setRectSize(100, 100);
            recreateColorMenu();
            m_View.repaint();
        });

        rectSizeMenu.add(minSize);
        rectSizeMenu.add(reduceSize);
        rectSizeMenu.add(resetSize);
        rectSizeMenu.add(increaseSize);
        rectSizeMenu.add(maxSize);
    }
    private void recreateColorMenu(){
        rectColorMenu.removeAll();
        createRectColorMenu();
        menuBar.revalidate();
        menuBar.repaint();
    }
    private void createRectColorMenu(){
        JMenu allRectsMenu = new JMenu("All Rects");
        addColorOptionsToMenu(allRectsMenu, -1, -1);
        rectColorMenu.add(allRectsMenu);

        for(int i = 0; i < m_Model.n; ++i){
            for(int j = 0; j < m_Model.m; ++j){
                JMenu rectMenu = new JMenu("Rect [" + i + "|" + j + "]");
                addColorOptionsToMenu(rectMenu, i, j);
                rectColorMenu.add(rectMenu);
            }
        }
    }
    private void addColorOptionsToMenu(JMenu menu, int rectRow, int rectCol){
        JMenuItem colorRed = new JMenuItem("Red");
        JMenuItem colorGreen = new JMenuItem("Green");
        JMenuItem colorBlue = new JMenuItem("Blue");
        JMenuItem colorRandom = new JMenuItem("Random");

        colorRed.addActionListener(e -> {
            changeRectColor(rectRow, rectCol, Color.RED);
        });

        colorGreen.addActionListener(e -> {
            changeRectColor(rectRow, rectCol, Color.GREEN);
        });

        colorBlue.addActionListener(e -> {
            changeRectColor(rectRow, rectCol, Color.BLUE);
        });

        colorRandom.addActionListener(e -> {
            if(rectRow == -1 && rectCol == -1){
                m_Model.setRandomColorForAllRects();
            } else {
                m_Model.setRandomColorForRect(rectRow, rectCol);
            }
            m_View.repaint();
        });

        menu.add(colorRed);
        menu.add(colorGreen);
        menu.add(colorBlue);
        menu.add(colorRandom);
    }
    private void changeRectColor(int rectRow, int rectCol, Color color){
        if(rectRow == -1 && rectCol == -1){
            m_Model.changeColorForAllRects(color);
        } else {
            m_Model.setColor(rectRow, rectCol, color);
        }
        m_View.repaint();
    }
    private void setVisible(){
        frame.setVisible(true);
    }
    private void closeTheProgram(){
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                frame.dispose();
            }
        });
    }
}
public class U06_AFG01a {
    public static void main(String[] args){
        new U06_AFG01a_Controller();
    }
}