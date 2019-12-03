package sort;

import static com.google.common.base.Preconditions.checkElementIndex;

public abstract class AbstractSort<E extends Comparable<E>> implements Sort<E> {

    final E[] elements;
    final int N;
    boolean sort;

    AbstractSort(E[] elements) {
        this.elements = elements;
        this.N = elements.length;
    }

    public void execute() {
        if (sort)
            throw new IllegalStateException("Sorting support only once.");
        executeSort();
    }

    /**
     * @implSpec Subclass should implement the method
     * to sort the {@code elements}
     */
    public abstract void executeSort();

    boolean less(int aIndex, int bIndex) {
        checkElementIndex(aIndex, N);
        checkElementIndex(bIndex, N);
        return elements[aIndex].compareTo(elements[bIndex]) < 0;
    }

    void exchange(int aIndex, int bIndex) {
        checkElementIndex(aIndex, N);
        checkElementIndex(bIndex, N);
        if (aIndex == bIndex)
            return;
        E e = elements[aIndex];
        elements[aIndex] = elements[bIndex];
        elements[bIndex] = e;
    }

    @Override
    public boolean isSort() {
        if (N < 2)
            return true;
        for (int i=1; i<N; i++)
            if (less(i, i-1))
                return false;
        return true;
    }

    @Override
    public void show() {
        for (int i=0; i<N; i++) {
            System.out.print(elements[i]);
            if (i != N-1)
                System.out.print(", ");
        }
        System.out.println();
    }
}
