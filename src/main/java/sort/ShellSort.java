package sort;


import app.AppIn;

import static org.assertj.core.api.Assertions.assertThat;

public class ShellSort<E extends Comparable<E>> extends AbstractSort<E> {

    ShellSort(E[] elements) {
        super(elements);
    }

    @Override
    public void executeSort() {
        int h = 1;
        while (h < N / 3)
            h = h * 3 + 1;  // 1/2(3^k-1) = 1, 4, 13, 40, 121
        //h 사전 정렬
        while (h >= 1) {
            for (int i=h; i<N; i++) {
                int j=i;
                while (j >= h && less(j, j-h)) {
                    exchange(j, j-h);
                    j -= h;
                }
            }
            h /= 3;
        }
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = AppIn.getInstance().readAllStrings(WORDS3_FILE);
        Sort<String> sort = Sort.create("shell", a);
        assertThat(sort).isInstanceOf(ShellSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
        SortCompare.compare("shell", "selection", 10000, 5);
    }
}
