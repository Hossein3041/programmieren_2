// Aufgabe 2: Implementieren Sie den Heap- und Mergesort für int-Arrays. Testen Sie Ihre
//Programme mit Arrays der Größen 10, 100, 1000 und 10000. Dabei sollen die
//Arrays bereits sortiert, invers sortiert und mit zufälligen Zahlen belegt sein.

package uebung;
import java.util.Arrays;
import java.util.Random;

public class U06_AFG02 {
    static class MergeSort{
        static void sort(int[] field){
            sortHelper(field, 0, field.length - 1);
        }
        private static void sortHelper(int[] field, int left, int right){
            if(left < right){
                int middle = (left + right) / 2;
                sortHelper(field, left, middle);
                sortHelper(field, middle + 1, right);
                merge(field, left, middle, right);
            }
        }
        private static void merge(int[] field, int left, int middle, int right){
            int[] temp = new int[right - left + 1];
            int i = left, j = middle + 1, k = 0;

            while(i <= middle && j <= right)
                if(field[i] <= field[j])
                    temp[k++] = field[i++];
                else
                    temp[k++] = field[j++];
            while(i <= middle)
                temp[k++] = field[i++];
            while(j <= right)
                temp[k++] = field[j++];
            System.arraycopy(temp, 0, field, left, temp.length);
        }
    }

    static class HeapSort{
        static void sort(int[] field){
            for(int i = (field.length - 2) / 2; i >= 0; --i){
                downheap(field, field.length, i);
            }
            for(int i = field.length - 1; i > 0; --i){
                swap(field, 0, i);
                downheap(field, i, 0);
            }
        }
        private static void swap(int[] field, int iPos1, int iPos2){
            int temp = field[iPos1];
            field[iPos1] = field[iPos2];
            field[iPos2] = temp;
        }
        private static void downheap(int[] keys, int end, int index){
            int k = keys[index];
            while(index < end / 2){
                int son = 2 * index + 1;
                if(son < end - 1 && keys[son] < keys[son + 1])
                    son++;
                if(!(k < keys[son]))
                    break;
                keys[index] = keys[son];
                index = son;
            }
            keys[index] = k;
        }
    }
    public static void main(String[] args){
        int[] sizes = {10, 100, 1000, 10000};
        Random rand = new Random();
        for(int sizeIndex = 0; sizeIndex < sizes.length; ++sizeIndex){
            int size = sizes[sizeIndex];
            int[] sorted = new int[size];
            int[] reverse = new int[size];
            int[] random = new int[size];

            for(int j = 0; j < size; ++j){
                sorted[j] = j;
                reverse[j] = size - j - 1;
                random[j] = rand.nextInt(size);
            }

            System.out.println();
            System.out.println("MergeSort test on array-size:" + size);
            testSort(sorted.clone(), "sorted", "MergeSort");
            testSort(reverse.clone(), "reverse", "MergeSort");
            testSort(random.clone(), "random", "MergeSort");

            System.out.println();
            System.out.println("HeapSort test on array-size:" + size);
            testSort(sorted.clone(), "sorted", "HeapSort");
            testSort(reverse.clone(), "reverse", "HeapSort");
            testSort(random.clone(), "random", "HeapSort");
        }
    }
    private static void testSort(int[] array, String description, String sortType){
        long startTime = System.nanoTime();
        if(sortType.equals("MergeSort"))
            MergeSort.sort(array);
        else
            HeapSort.sort(array);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(description + ": " + " | " + duration + " ms :" + Arrays.toString(array));
    }
}
