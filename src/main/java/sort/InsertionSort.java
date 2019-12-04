package sort;

import app.AppIn;

import static org.assertj.core.api.Assertions.assertThat;

public class InsertionSort<E extends Comparable<E>> extends AbstractSort<E> {

    InsertionSort(E[] elements) {
        super(elements);
    }

    @Override
    public void executeSort() {
        for (int i = 1; i < N; i++) {
            int j = i;
            //a[i] 을 a[0 .. i-1] 사이의 적합한 위치에 삽입.
            while (j > 0 && less(j, j - 1)) {
                exchange(j, j - 1);
                j--;
            }
        }
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = AppIn.getInstance().readAllStrings(WORDS3_FILE);
        Sort<String> sort = Sort.create("insertion", a);
        assertThat(sort).isInstanceOf(InsertionSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
        SortCompare.compare("insertion", "selection", 10000, 5);
    }
}
