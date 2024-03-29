package sort;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sorts a sequence of elements in given array.
 */
public interface Sort<E> {

    /**
     * sort the array in ascending order
     */
    void execute();

    /**
     * is the array sorted?
     *
     * @return
     */
    boolean isSort();

    /**
     * display the elements in the array
     */
    void show();

    /**
     * Factory method for sort instance.
     *
     * @param alg
     * @param elements
     * @param <E>
     * @throws UnsupportedOperationException throws if given alg is unknown
     * @return
     */
    static <E extends Comparable<E>> Sort<E> create(String alg, E[] elements) {
        checkNotNull(elements);
        if ("Selection".equalsIgnoreCase(alg))
            return new SelectionSort<>(elements);
        if ("Insertion".equalsIgnoreCase(alg))
            return new InsertionSort<>(elements);
        if ("Shell".equalsIgnoreCase(alg))
            return new ShellSort<>(elements);
        if ("Merge".equalsIgnoreCase(alg))
            return new MergeSort<>(elements);
        if ("Quick".equalsIgnoreCase(alg))
            return new QuickSort<>(elements);
        if ("Heap".equalsIgnoreCase(alg))
            return new HeapSort<>(elements);
        throw new UnsupportedOperationException();
    }
}
