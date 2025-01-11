// Aufgabe 03: Testen Sie Ihre Hashtabelle, indem Sie viele Werte eintragen.
// Protokollieren Sie die Häufigkeit von Kollisionen.
// Wie viele Kollisionen treten im Durchschnitt auf, wenn es neues Element eingetragen wird
// und die Tabelle zu 40% (respektive 50%, 60%, 70%, 80%, 90%, 95%) gefüllt ist?
//
// Wichtig: Überlegen Sie sich zunächst, wie ein solcher Testaufbau stattfinden kann.
// Würden Sie bei einer Füllung von 40% einen Eintrag vornehmen und die Kollisionen zählen,
// wäre für den nächsten Eintrag die Füllung nicht mehr 40% sondern 40% + 1 Eintrag.
// Dies soll nicht passieren! Führen Sie die Messungen mit unterschiedlichen Hashfunktionen durch.
package uebung;

class U11_AFG03_Hashing {
    class Node {
        public Node(String key, float data) {
            m_Key = key;
            m_Data = data;
        }

        private String m_Key;
        private float m_Data;
    }

    public U11_AFG03_Hashing() {
        m_Entries = new Node[1023];
        m_iNrOfEntries = 0;
    }

    public Float search(String key) {
        int iIndex = hashKey(key, m_Entries.length);
        for (int i = 0; m_Entries[iIndex] != null && i < m_Entries.length; ++i) {
            if (m_Entries[iIndex].m_Key != null && m_Entries[iIndex].m_Key.equals(key))
                return m_Entries[iIndex].m_Data;
            iIndex = (iIndex + 1) % m_Entries.length;
        }
        return null;
    }

    public void insert(String key, float data) {
        int iIndex = hashKey(key, m_Entries.length);
        for (int i = 0; i < m_Entries.length; ++i) {
            if (m_Entries[iIndex] == null) {
                m_Entries[iIndex] = new Node(key, data);
                ++m_iNrOfEntries;
                return;
            }
            iIndex = (iIndex + 1) % m_Entries.length;
        }
    }

    public int simulateInsertAndCountCollisions(String key) {
        int iIndex = hashKey(key, m_Entries.length);
        int collisionsCounter = 0;
        for (int i = 0; i < m_Entries.length; ++i) {
            if (m_Entries[iIndex] == null) {
                return collisionsCounter;
            }
            ++collisionsCounter;
            iIndex = (iIndex + 1) % m_Entries.length;
        }
        return collisionsCounter;
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

public class U11_AFG03 {
    public static void main(String[] args) {
        int tableSize = 1023;
        float[] fillPercentages = new float[]{0.40f, 0.50f, 0.60f, 0.70f, 0.80f, 0.90f, 0.95f};
        testPerforming(tableSize, fillPercentages);
    }

    private static void testPerforming(int tableSize, float[] fillPercentages) {
        for (int i = 0; i < fillPercentages.length; ++i) {
            U11_AFG03_Hashing hashTable = new U11_AFG03_Hashing();
            testFilling(hashTable, tableSize, fillPercentages[i]);
        }
    }

    private static void testFilling(U11_AFG03_Hashing hashTable, int tableSize, float fillPercentage) {
        int maxEntries = (int) (tableSize * fillPercentage);
        int totalCollisions = 0;

        for (int i = 0; i < maxEntries; ++i) {
            String key = "Key" + i;
            float data = (float) Math.random();
            hashTable.insert(key, data);
        }

        int simulatedInserts = 1000;
        for (int i = 0; i < simulatedInserts; ++i) {
            String key = "simulatedKey" + i;
            totalCollisions += hashTable.simulateInsertAndCountCollisions(key);
        }

        float averageCollisions = (float) totalCollisions / simulatedInserts;

        System.out.println("Füllgrad: " + (int) (fillPercentage * 100) + "%, Durchschnittliche Kollisionen pro Eintrag: " + averageCollisions);
    }
}