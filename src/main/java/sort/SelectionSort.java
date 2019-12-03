package sort;

import app.In;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectionSort<E extends Comparable<E>> extends AbstractSort<E> {

    SelectionSort(E[] elements) {
        super(elements);
    }

    @Override
    public void executeSort() {
        for (int i=0; i<N; i++) {
            //현재까지 알려진 가장 작은 항목
            int min = i;
            // a[i] 를 a[i+1 .. N-1] 항목 중 가장 작은 항목과 교환
            for (int j=i+1; j<N; j++)
                if (less(j, min))
                    min = j;
            exchange(min, i);
        }
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = In.getInstance().readAllStrings(WORDS3_FILE);
        Sort<String> sort = Sort.create("selection", a);
        assertThat(sort).isInstanceOf(SelectionSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
    }
}
