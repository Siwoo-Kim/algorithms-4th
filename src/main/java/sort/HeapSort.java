package sort;

import app.AppIn;

import static org.assertj.core.api.Assertions.assertThat;

public class HeapSort<E extends Comparable<E>> extends AbstractSort<E> {

    HeapSort(E[] elements) {
        super(elements);
    }

    @Override
    public void executeSort() {
        @SuppressWarnings("unchecked")
        E[] elements = (E[]) new Comparable[N+1];
        System.arraycopy(super.elements, 0, elements, 1, N);
        for (int k=N/2; k>=1; k--)
            sink(elements, k, N);
        int i=N;
        while (i > 1) {
            exch(elements, 1, i--);
            sink(elements, 1, i);
        }
        for (int k=1; k<=N; k++) {
            super.elements[k-1] = elements[k];
        }

    }

    private void sink(E[] elements, int k, int N) {
        while (k*2 <= N) {
            int j = k*2;
            if (j < N && less(elements[j], elements[j+1]))
                j++;
            if (!less(elements[k], elements[j]))
                return;
            exch(elements, j, k);
            k = j;
        }
    }

    private void exch(E[] a, int i, int j) {
        E e = a[i];
        a[i] = a[j];
        a[j] = e;
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = AppIn.getInstance().readAllStrings(WORDS3_FILE);
        Sort<String> sort = Sort.create("heap", a);
        assertThat(sort).isInstanceOf(HeapSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
        SortCompare.compare("heap", "selection", 10000, 5);
    }
}
