// Aufgabe 03: Implementieren Sie eine Vektorklasse für double Zahlen.
// Fügen Sie der Implementierung eine push_front Methode hinzu,
// die am Anfang des Vektors ein neues Element hinzufügt.
package uebung;
import java.lang.*;
class U07_AFG03_OutOfBoundsException extends Exception{}
class U07_AFG03_MyVector{
    public U07_AFG03_MyVector(int initialCapacity, int capacityIncrement){
        mIncWidth = capacityIncrement;
        mNextFree = 0;
        mDoubles = new double[initialCapacity];
    }
    public U07_AFG03_MyVector(int initialCapacity){
        this(initialCapacity, 0);
    }
    public U07_AFG03_MyVector(){
        this(1, 0);
    }
    public void push_back(double db){
        if(mNextFree >= mDoubles.length)
            resize();
        mDoubles[mNextFree++] = db;
    }
    public void push_front(double db){
        if(mNextFree >= mDoubles.length)
            resize();
        for(int i = mNextFree; i > 0; --i)
            mDoubles[i] = mDoubles[i-1];
        mDoubles[0] = db;
        mNextFree++;
    }
    private void resize(){
        final int newSize = mIncWidth == 0 ? mDoubles.length * 2 : mDoubles.length + mIncWidth;
        double[] newDoubles = new double[newSize];
        for(int i = 0; i < mDoubles.length; ++i)
            newDoubles[i] = mDoubles[i];
        mDoubles = newDoubles;
    }
    public double get(int iIndex) throws U07_AFG03_OutOfBoundsException{
        if(iIndex < size() && 0 <= iIndex)
            return mDoubles[iIndex];
        else
            throw new U07_AFG03_OutOfBoundsException();
    }
    public void set(int iIndex, double db) throws U07_AFG03_OutOfBoundsException{
        if(iIndex <= size() && 0 <= iIndex)
            mDoubles[iIndex] = db;
        else
            throw new U07_AFG03_OutOfBoundsException();
    }
    public int size(){
        return mNextFree;
    }
    private double[] mDoubles;
    private final int mIncWidth;
    private int mNextFree;
}
public class U07_AFG03 {
    public static void main(String[] args){
        try {
            U07_AFG03_MyVector vec = new U07_AFG03_MyVector(5);

            vec.push_back(1.1);
            vec.push_back(2.2);
            vec.push_back(3.3);
            System.out.println("Nach push_back: ");
            for(int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");

            vec.push_front(0.0);
            vec.push_front(0.9);
            vec.push_front(0.5);
            System.out.println("Nach push_front: ");
            for(int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");

            vec.set(2, 9.9);
            System.out.println("Nach set(2, 9.9): ");
            for(int i = 0; i < vec.size(); ++i)
                System.out.println("Element: (" + i + ") => [" + vec.get(i) + "]");
        } catch (U07_AFG03_OutOfBoundsException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}