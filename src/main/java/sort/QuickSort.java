package sort;

import app.AppIn;

import static org.assertj.core.api.Assertions.assertThat;

public class QuickSort<E extends Comparable<E>> extends AbstractSort<E> {

    QuickSort(E[] elements) {
        super(elements);
    }

    @Override
    public void executeSort() {
        quickSort(0, N-1);
    }

    private void quickSort(int low, int high) {
        //base case
        if (low >= high) return;
        int pivot = partition(low, high);
        quickSort(low, pivot-1);    //sort a[low...pivot-1]
        quickSort(pivot+1, high);   //sort a[pivot+1...high]
    }

    /**
     * partitioning a[] into a[low..pivot-1], pivot, a[pivot+1..high]
     *
     * @param low
     * @param high
     * @return pivot
     */
    private int partition(int low, int high) {
        int i=low, j=high+1;    //scanning from left, right each
        E e = elements[low];
        while (true) {
            while (less(elements[++i], e))
                if (i == high) break;
            while (less(e, elements[--j]))
                if (j == low) break;
            if (i >= j)
                break;
            exchange(i, j);
        }
        exchange(low, j);   //insert pivot into right position
        return j;   //a[low...pivot-1] <= pivot <= a[j+1...high]
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = AppIn.getInstance().readAllStrings(TINY_FILE);
        Sort<String> sort = Sort.create("quick", a);
        assertThat(sort).isInstanceOf(QuickSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
        SortCompare.compare("quick", "selection", 10000, 5);
    }
}
