// Aufgabe 2: Implementieren Sie eine Hashtabelle, die Strings auf float Werte abbildet.
package uebung;

class U11_AFG02_Hashing {
    class Node {
        public Node(String key, float data) {
            m_Key = key;
            m_Data = data;
        }

        private String m_Key;
        private float m_Data;
    }

    public U11_AFG02_Hashing() {
        m_Entries = new Node[1023];
        m_iNrOfEntries = 0;
    }

    public float search(String key) {
        int iIndex = hashKey(key, m_Entries.length);
        for (int i = 0; m_Entries[iIndex] != null && i < m_Entries.length; ++i) {
            if (m_Entries[iIndex].m_Key != null && m_Entries[iIndex].m_Key.equals(key))
                return m_Entries[iIndex].m_Data;
            iIndex = (iIndex + 1) % m_Entries.length;
        }
        return Float.NaN;
    }

    public void insert(String key, float data) {
        int iIndex = hashKey(key, m_Entries.length);
        for (int i = 0; i < m_Entries.length; ++i) {
            if (m_Entries[iIndex] == null) {
                m_Entries[iIndex] = new Node(key, data);
                ++m_iNrOfEntries;
                if (m_iNrOfEntries > m_Entries.length * 8 / 10)
                    resize();
                return;
            }
            iIndex = (iIndex + 1) % m_Entries.length;
        }
    }

    private void resize() {
        final int OLDCAPACITY = m_Entries.length;
        Node[] oldEntries = m_Entries;
        final int iNewCap = (m_Entries.length + 1) * 2 - 1;
        m_Entries = new Node[iNewCap];
        m_iNrOfEntries = 0;
        for (int i = 0; i < OLDCAPACITY; ++i) {
            if (oldEntries[i] != null) {
                insert(oldEntries[i].m_Key, oldEntries[i].m_Data);
            }
        }
    }

    private int hashKey(String key, int iLength) {
        int res = 0;
        for (int i = 0; i < key.length(); ++i)
            res = ((res << 6) + key.charAt(i)) % iLength;
        return res;
    }

    private Node[] m_Entries;
    private int m_iNrOfEntries;
}

public class U11_AFG02 {
    public static void main(String[] args) {
        U11_AFG02_Hashing hashTable = new U11_AFG02_Hashing();

        hashTable.insert("key1", 1.23f);
        hashTable.insert("key2", 4.56f);

        System.out.println("Search key1: " + hashTable.search("key1"));
        System.out.println("Search key3: " + hashTable.search("key3"));
    }
}