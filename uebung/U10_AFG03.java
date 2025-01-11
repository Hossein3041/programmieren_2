// Aufgabe 03: Implementieren Sie für Ihren binären Suchbaum eine rekursive Methode sumOfNodes(),
// die die Summe aller int Werte, die in den Knoten gespeichert sind, ermittelt und zurückgibt.
package uebung;

class U10_AFG03_BinTree {
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

    public void insertRec(String key, Integer data) {
        m_Root = insertRec(m_Root, key, data);
    }

    Node insertRec(Node n, String key, Integer data) {
        if (n == null)
            return new Node(key, data);
        else {
            final int RES = key.compareTo(n.m_Key);
            if (RES < 0)
                n.m_Left = insertRec(n.m_Left, key, data);
            else if (RES > 0)
                n.m_Right = insertRec(n.m_Right, key, data);
            return n;
        }
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

    public boolean delete(String key) {
        m_Root = deleteRecursive(m_Root, key);
        return m_Root != null;
    }

    private Node deleteRecursive(Node n, String key) {
        if (n == null)
            return null;
        final int RES = key.compareTo(n.m_Key);
        if (RES < 0)
            n.m_Left = deleteRecursive(n.m_Left, key);
        else if (RES > 0)
            n.m_Right = deleteRecursive(n.m_Right, key);
        else {
            if (n.m_Right == null)
                return n.m_Left;
            if (n.m_Left == null)
                return n.m_Right;
            Node smallestValue = findSmallest(n.m_Right);
            n.m_Key = smallestValue.m_Key;
            n.m_Data = smallestValue.m_Data;

            n.m_Right = deleteRecursive(n.m_Right, smallestValue.m_Key);
        }
        return n;
    }

    private Node findSmallest(Node root) {
        while (root.m_Left != null)
            root = root.m_Left;
        return root;
    }

    @Override
    public String toString() {
        if (m_Root == null) {
            return "Baum ist leer";
        } else {
            StringBuilder sb = new StringBuilder();
            toStringRec(m_Root, sb);
            return sb.toString();
        }
    }

    private void toStringRec(Node n, StringBuilder sb) {
        if (n != null) {
            toStringRec(n.m_Left, sb);
            sb.append("Key: ").append(n.m_Key).append(", Data: ").append(n.m_Data).append("\n");
            toStringRec(n.m_Right, sb);
        }
    }

    public Node searchIterative(String key) {
        Node tmp = m_Root;
        while (tmp != null) {
            final int RES = key.compareTo(tmp.m_Key);
            if (RES == 0)
                return tmp;
            tmp = RES < 0 ? tmp.m_Left : tmp.m_Right;
        }
        return null;
    }

    public Node searchRecursive(Node n, String key) {
        if (n == null)
            return null;
        else {
            final int RES = key.compareTo(n.m_Key);
            if (RES < 0)
                return searchRecursive(n.m_Left, key);
            else if (RES > 0)
                return searchRecursive(n.m_Right, key);
            return n;
        }
    }

    public int nrOfNodes() {
        int numberOfNodes = nrOfNodes(m_Root);
        return numberOfNodes;
    }

    public static int nrOfNodes(Node n) {
        if (n == null)
            return 0;
        else
            return 1 + nrOfNodes(n.m_Left) + nrOfNodes(n.m_Right);
    }

    public int sumOfNodes() {
        int summaryOfNodes = sumOfNodes(m_Root);
        return summaryOfNodes;
    }

    public static int sumOfNodes(Node n) {
        if (n.m_Data == null)
            return 0;
        else
            return n.m_Data + sumOfNodes(n.m_Left) + sumOfNodes(n.m_Right);
    }


    private static Node m_Root = null;
}

public class U10_AFG03 {
    public static void main(String[] args) {
        new U10_AFG03_BinTree();
    }
}
