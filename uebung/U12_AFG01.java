// Aufgabe 1: Schreiben Sie ein Java Programm (MVC Muster beachten), das ein Swing Fenster öffnet,
// das eine SplitPane beinhaltet. Auf der einen Seite wird ein JTree dargestellt auf der anderen Seite einen Dialog.
// Der JTree soll einen Binärbaum darstellen, der Strings auf Integer Werte abbildet.
// In der Dialogseite befinden sich zwei Textfelder.
// In dem einen Textfeld kann man einen String angeben, in dem anderen Textfeld einen Integer Wert.
// Mittels eines "add" Buttons wird das String/Integer Paar in den Binärbaum eingefügt
// und der JTree muss aktualisiert werden.
package uebung;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.*;
import java.awt.*;

class U12_AFG01_Model {
    private U12_AFG01_BinTree binTree;

    U12_AFG01_Model() {
        binTree = new U12_AFG01_BinTree();
    }

    public boolean insert(String key, Integer data) {
        return binTree.insert(key, data);
    }

    public TreeModel getTreeModel() {
        DefaultMutableTreeNode rootNode = convertToTreeNode(binTree.getRoot());
        return new DefaultTreeModel(rootNode);
    }

    private DefaultMutableTreeNode convertToTreeNode(U12_AFG01_BinTree.Node node) {
        if (node == null)
            return null;

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.m_Key + " : " + node.m_Data);

        DefaultMutableTreeNode leftChild = convertToTreeNode(node.m_Left);
        DefaultMutableTreeNode rightChild = convertToTreeNode(node.m_Right);

        if (leftChild == null && rightChild != null)
            leftChild = new DefaultMutableTreeNode("Dummy");
        else if (leftChild != null && rightChild == null)
            rightChild = new DefaultMutableTreeNode("Dummy");


        if (leftChild != null)
            treeNode.add(leftChild);
        if (rightChild != null)
            treeNode.add(rightChild);
        return treeNode;
    }

    class U12_AFG01_BinTree {
        class Node {
            public Node(String key, Integer data) {
                m_Key = key;
                m_Data = data;
            }

            String m_Key;
            Integer m_Data;
            Node m_Left = null;
            Node m_Right = null;
        }

        public boolean insert(String key, Integer data) {
            Node tmp = m_Root;
            Node father = null;
            while (tmp != null) {
                father = tmp;
                final int RES = key.compareTo(tmp.m_Key);
                if (RES == 0)
                    return false;
                tmp = RES < 0 ? tmp.m_Left : tmp.m_Right;
            }
            tmp = new Node(key, data);
            if (father == null)
                m_Root = tmp;
            else if (key.compareTo(father.m_Key) < 0)
                father.m_Left = tmp;
            else
                father.m_Right = tmp;
            return true;
        }

        public Node getRoot() {
            return m_Root;
        }

        private Node m_Root = null;
    }
}

class U12_AFG01_View extends JComponent {
    private U12_AFG01_Model m_Model;
    private JTree tree;
    private JTextField keyField;
    private JTextField valueField;
    private JButton addButton;
    private JSplitPane splitPane;

    U12_AFG01_View(U12_AFG01_Model model) {
        m_Model = model;
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        tree = new JTree(m_Model.getTreeModel());

        keyField = new JTextField(10);
        valueField = new JTextField(10);
        addButton = new JButton("Add");

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createTreePanel(), createInputPanel());
    }

    private JScrollPane createTreePanel() {
        JScrollPane scrollPane = new JScrollPane(tree);
        return scrollPane;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Key (String):"));
        panel.add(keyField);

        panel.add(new JLabel("Value (Integer):"));
        panel.add(valueField);

        panel.add(new JLabel());
        panel.add(addButton);

        return panel;
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void refreshTree() {
        tree.setModel(m_Model.getTreeModel());
    }

    public String getKeyInput() {
        return keyField.getText();
    }

    public Integer getValueInput() {
        try {
            return Integer.parseInt(valueField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

class U12_AFG01_Controller {
    private U12_AFG01_Model m_Model;
    private U12_AFG01_View m_View;
    private JFrame frame;

    U12_AFG01_Controller() {
        m_Model = new U12_AFG01_Model();
        m_View = new U12_AFG01_View(m_Model);
        initFrame();
        setListeners();
        setVisible();
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setTitle("U12_AFG01");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(m_View);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void setListeners() {
        m_View.addAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = m_View.getKeyInput();
                Integer value = m_View.getValueInput();

                if (key.length() > 10) {
                    JOptionPane.showMessageDialog(frame, "KEY zu lang. Maximal 10 Zeichen erlaubt");
                    return;
                } else if (value != null && value.toString().length() > 10) {
                    JOptionPane.showMessageDialog(frame, "VALUE zu lang. Maximal 10 Zeichen erlaubt!");
                    return;
                }

                if (key != null && value != null) {
                    if (m_Model.insert(key, value))
                        m_View.refreshTree();
                    else
                        JOptionPane.showMessageDialog(frame, "Schlüssel existiert bereits!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Eingabe ungültig!.");
                }
            }
        });
    }

    private void setVisible() {
        frame.setVisible(true);
    }
}

public class U12_AFG01 {
    public static void main(String[] args) {
        new U12_AFG01_Controller();
    }
}