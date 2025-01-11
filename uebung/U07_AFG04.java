// Aufgabe 04: Modifizieren Sie Ihre push_front Methode derart,
// dass sie analog wie die push_back Methode auch am Anfang
// mehr Elemente alloziert als notwendig sind.
package uebung;

import java.lang.*;

class U07_AFG04_OutOfBoundsException extends Exception {
}

class U07_AFG04_MyVector {
    public U07_AFG04_MyVector(int initialCapacity, int capacityIncrement) {
        mIncWidth = capacityIncrement;
        mNextFree = 0;
        mPrevFree = mNextFree - 1;
        mDoubles = new double[initialCapacity];
    }

    public U07_AFG04_MyVector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public U07_AFG04_MyVector() {
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

    public double get(int iIndex) throws U07_AFG04_OutOfBoundsException {
        int newIndex = mPrevFree + 1 + iIndex;
        if (newIndex < mDoubles.length && 0 <= newIndex)
            return mDoubles[newIndex];
        else
            throw new U07_AFG04_OutOfBoundsException();
    }

    public void set(int iIndex, double db) throws U07_AFG04_OutOfBoundsException {
        int newIndex = mPrevFree + 1 + iIndex;
        if (newIndex < mDoubles.length && 0 <= newIndex)
            mDoubles[newIndex] = db;
        else
            throw new U07_AFG04_OutOfBoundsException();
    }

    public int size() {
        return mNextFree - (mPrevFree + 1);
    }

    private double[] mDoubles;
    private final int mIncWidth;
    private int mNextFree;
    private int mPrevFree;
}

public class U07_AFG04 {
    public static void main(String[] args) {
        try {
            U07_AFG04_MyVector vec = new U07_AFG04_MyVector(5);

            vec.push_back(1.1);
            vec.push_back(2.2);
            vec.push_back(3.3);
            System.out.println("Nach push_back: ");
            for (int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");

            vec.push_front(0.0);
            vec.push_front(0.9);
            vec.push_front(0.5);
            System.out.println("Nach push_front: ");
            for (int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");

            vec.set(2, 9.9);
            System.out.println("Nach set(2, 9.9): ");
            for (int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");
        } catch (U07_AFG04_OutOfBoundsException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}