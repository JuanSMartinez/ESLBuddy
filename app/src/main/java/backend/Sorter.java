package backend;

import java.util.ArrayList;



public class Sorter<T extends Comparable<T>> {

    //Partition procedure for a quick sort algorithm
    private int partition(ArrayList array, int p, int r){
        T x = (T)array.get(r);
        int i = p-1;
        for(int j = p; j < r-1; j++){
            T a_j = (T)array.get(j);
            if(a_j.compareTo(x) < 0){
                i = i+1;
                T temp = (T)array.get(i);
                array.add(i, a_j);
                array.add(j, temp);
            }
        }
        T temp = (T)array.get(i+1);
        array.add(i+1, array.get(r));
        array.add(r, temp);
        return i+1;
    }

    //Quicksort
    public void quickSort(ArrayList array, int p, int r){
        if(p < r){
            int q = partition(array, p, r);
            quickSort(array, p, q-1);
            quickSort(array, q+1, r);
        }
    }

    //Shuffle an array with the fisher-yates shuffle algorithm
    public void shuffleArray(ArrayList<T> array){
        for(int i = 0; i < array.size()-2; i ++){
            int j = (int)Math.random()*(array.size() - i) + i;
            T temp = array.get(i);
            array.add(i, array.get(j));
            array.add(j, temp);
        }
    }

}
