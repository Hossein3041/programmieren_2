// Aufgabe 02: Implementieren Sie jeweils eine search Methode für Ihre Listen und den Vektor.
package uebung;

class U09_AFG02a_Vector_OutOfBoundsException extends Exception {}

class U09_AFG02a_Vector_MyVector {
    public U09_AFG02a_Vector_MyVector(int initialCapacity, int capacityIncrement) {
        mIncWidth = capacityIncrement;
        mNextFree = 0;
        mPrevFree = mNextFree - 1;
        mDoubles = new double[initialCapacity];
    }

    public U09_AFG02a_Vector_MyVector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public U09_AFG02a_Vector_MyVector() {
        this(1, 0);
    }

    public void push_back(double db) {
        if (mNextFree >= mDoubles.length)
            resize(true);
        mDoubles[mNextFree++] = db;
    }

    public void push_front(double db) {
        if (mPrevFree < 0)
            resize(false);
        mDoubles[mPrevFree--] = db;
    }

    private void resize(boolean doPushBack) {
        final int newSize = mIncWidth == 0 ? mDoubles.length * 2 : mDoubles.length + mIncWidth;
        double[] newDoubles = new double[newSize];
        /*
        int startPos;
        if(doPushBack)
            startPos = (newSize - (mDoubles.length + mNextFree - (mPrevFree + 1))) / 2;
        else
            startPos = (newSize - mDoubles.length) / 2;
         */
        int startPos = doPushBack ? (newSize - (mDoubles.length + mNextFree - (mPrevFree + 1))) / 2 : (newSize - mDoubles.length) / 2;
        for (int i = 0; i < mDoubles.length; ++i)
            newDoubles[startPos + i] = mDoubles[i];
        mPrevFree = startPos - 1;
        mNextFree = startPos + mNextFree - mPrevFree - 1;
        mDoubles = newDoubles;
    }

    public double get(int iIndex) throws U09_AFG02a_Vector_OutOfBoundsException {
        int newIndex = mPrevFree + 1 + iIndex;
        if (newIndex < mDoubles.length && 0 <= newIndex)
            return mDoubles[newIndex];
        else
            throw new U09_AFG02a_Vector_OutOfBoundsException();
    }

    public void set(int iIndex, double db) throws U09_AFG02a_Vector_OutOfBoundsException {
        int newIndex = mPrevFree + 1 + iIndex;
        if (newIndex < mDoubles.length && 0 <= newIndex)
            mDoubles[newIndex] = db;
        else
            throw new U09_AFG02a_Vector_OutOfBoundsException();
    }

    public int size() {
        return mNextFree - (mPrevFree + 1);
    }

    public void isSorted(double key) throws U09_AFG02a_Vector_OutOfBoundsException {
        boolean sorted = true;
        if (size() < 2)
            sorted = true;

        for (int i = 1; i < size(); ++i) {
            if (get(i - 1) > get(i)) {
                sorted = false;
                break;
            }
        }

        double result = sorted ? binSearch(key) : seqSearch(key);
        System.out.println("Search result: " + result);
    }

    public double seqSearch(double key) throws U09_AFG02a_Vector_OutOfBoundsException {
        double resultKey = -1;

        for (int i = 0; i < size(); ++i)
            if (get(i) == key) {
                resultKey = get(i);
                break;
            }

        return resultKey;
    }

    public double binSearch(double key) throws U09_AFG02a_Vector_OutOfBoundsException {
        int leftIndex = 0;
        int rightIndex = size() - 1;

        double resultKey = -1;

        while (leftIndex <= rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            double middleValue = get(middleIndex);

            if (middleValue == key) {
                resultKey = middleValue;
                break;
            } else if (middleValue < key) {
                leftIndex = middleIndex + 1;
            } else {
                rightIndex = middleIndex - 1;
            }
        }

        return resultKey;
    }

    private double[] mDoubles;
    private final int mIncWidth;
    private int mNextFree;
    private int mPrevFree;
}

public class U09_AFG02a_Vector {
    public static void main(String[] args) {
        try {

            // Hier Tests für Übung 07, alt
            U09_AFG02a_Vector_MyVector u07_afg04_vec = new U09_AFG02a_Vector_MyVector(5);

            u07_afg04_vec.push_back(1.1);
            u07_afg04_vec.push_back(2.2);
            u07_afg04_vec.push_back(3.3);
            System.out.println("Nach push_back: ");
            for (int i = 0; i < u07_afg04_vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + u07_afg04_vec.get(i) + "]");

            u07_afg04_vec.push_front(0.0);
            u07_afg04_vec.push_front(0.9);
            u07_afg04_vec.push_front(0.5);
            System.out.println("Nach push_front: ");
            for (int i = 0; i < u07_afg04_vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + u07_afg04_vec.get(i) + "]");

            u07_afg04_vec.set(2, 9.9);
            System.out.println("Nach set(2, 9.9): ");
            for (int i = 0; i < u07_afg04_vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + u07_afg04_vec.get(i) + "]");

            // Ab hier Tests für Übungsblatt 9
            U09_AFG02a_Vector_MyVector vec = new U09_AFG02a_Vector_MyVector(5);

            System.out.println("Test 1: Sequentielle Suche in einem unsortierten Vektor");
            vec.push_back(3.3);
            vec.push_back(1.1);
            vec.push_back(4.4);
            vec.push_back(2.2);
            vec.isSorted(4.4);
            vec.isSorted(5.5);

            System.out.println("\nTest 2: Binäre Suche in einem sortierten Vektor");
            U09_AFG02a_Vector_MyVector vec2 = new U09_AFG02a_Vector_MyVector(5);
            vec2.push_back(1.1);
            vec2.push_back(2.2);
            vec2.push_back(3.3);
            vec2.push_back(4.4);
            vec2.push_back(5.5);
            vec2.isSorted(3.3);
            vec2.isSorted(6.6);

            System.out.println("\nTest 3: Grenzfall - Suche in einem leeren Vektor");
            U09_AFG02a_Vector_MyVector vec3 = new U09_AFG02a_Vector_MyVector(5);
            vec3.isSorted(1.1);

            System.out.println("\nTest 4: Grenzfall - Suche in einem Vektor mit nur einem Element");
            U09_AFG02a_Vector_MyVector vec4 = new U09_AFG02a_Vector_MyVector(5);
            vec4.push_back(2.2);
            vec4.isSorted(2.2);
            vec4.isSorted(3.3);

            System.out.println("\nTest 5: Suche nach Werten am Anfang, in der Mitte und am Ende");
            U09_AFG02a_Vector_MyVector vec5 = new U09_AFG02a_Vector_MyVector(5);
            vec5.push_back(1.1);
            vec5.push_back(2.2);
            vec5.push_back(3.3);
            vec5.push_back(4.4);
            vec5.push_back(5.5);
            vec5.isSorted(1.1);
            vec5.isSorted(3.3);
            vec5.isSorted(5.5);

        } catch (U09_AFG02a_Vector_OutOfBoundsException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}