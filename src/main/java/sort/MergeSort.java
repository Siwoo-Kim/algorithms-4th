package sort;

import app.AppIn;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeSort<E extends Comparable<E>> extends AbstractSort<E> {

    private final E[] aux;

    MergeSort(E[] elements) {
        super(elements);
        ///type safe
        @SuppressWarnings("unchecked")
        E[] aux = (E[]) new Comparable[N];
        this.aux = aux;
    }

    @Override
    public void executeSort() {
        //mergeSort(0, N-1);  //top-down
        bottomUpMergeSort();
    }

    private void bottomUpMergeSort() {
        for (int sz=1; sz<N; sz=sz<<1)  //sz 은 부분 배열의 크기
            for (int lo=0; lo<N-sz; lo+=sz<<1)  //lo 은 부분 배열의 인덱스
                merge(lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }

    private void mergeSort(int low, int high) {
        //base case
        if (low >= high) return;
        int mid = (high + low) / 2;
        mergeSort(low, mid);    //sort left
        mergeSort(mid+1, high); //sort right
        merge(low, mid, high);
    }

    private void merge(int low, int mid, int high) {
        if (less(elements[mid], elements[mid+1]))
            return;
        int i=low, j=mid+1;
        //copy a[low..mid..high] to aux
        for (int k=low; k<=high; k++)
            aux[k] = elements[k];
        for (int k=low; k<=high; k++) {
            if (i > mid) elements[k] = aux[j++];
            else if (j > high) elements[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                elements[k] = aux[j++];
            else
                elements[k] = aux[i++];
        }
    }

    private static final String TINY_FILE = "tiny.txt";
    private static final String WORDS3_FILE = "words3.txt";

    public static void main(String[] args) {
        String[] a = AppIn.getInstance().readAllStrings(TINY_FILE);
        Sort<String> sort = Sort.create("merge", a);
        assertThat(sort).isInstanceOf(MergeSort.class);
        sort.execute();
        assertThat(sort.isSort()).isTrue();
        sort.show();
        SortCompare.compare("merge", "selection", 10000, 5);
    }
}
