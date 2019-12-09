package util;

import app.AppIn;
import sort.Sort;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;

/**
 * The {@code Arrays} utility class provides operations for array.
 */
public final class Arrays {

    private Arrays() {
        throw new IllegalStateException();
    }

    /**
     * search an index of the element in the given array.
     *
     * @param a sorted {@link Integer} array
     * @param e
     * @return if {@code e} is found return the index
     * otherwise return -1
     */
    public static int binarySearch(Integer[] a, int e) {
        return binarySearch(a, e, 0, a.length-1);
    }

    private static int binarySearch(Integer[] a, int e, int low, int high) {
        if (low > high) return -1;
        int mid = (low + high) / 2;
        if (e < a[mid])
            return binarySearch(a, e, low, mid-1);
        if (e > a[mid])
            return binarySearch(a, e, mid+1, high);
        else
            return mid;
    }

    /**
     * sort the given array
     *
     * @param a array to be sorted
     * @param <E>
     */
    public static <E extends Comparable<E>> void sort(E[] a) {
        Sort<E> sort = Sort.create("QUICK", a);
        sort.execute();
        assert sort.isSort();
    }

    /**
     * reverse ordering of the given array
     *
     * @param a array to be reversed
     * @param <E>
     */
    public static <E> void reverse(E[] a) {
        checkNotNull(a);
        if (a.length < 2) return;
        final int N = a.length;
        for (int i=0; i<N/2; i++) {
            E e = a[N-1-i];
            a[N-1-i] = a[i];
            a[i] = e;
        }
    }

    /**
     * ============================== Test Method ==============================
     */
    private static final String LARGE_W_FILE = "largeW.txt";
    private static final String LARGE_T_FILE = "largeT.txt";

    public static void main(String[] args) {
        unit_test_binarySearch();
        unit_test_reverse();
    }

    private static void unit_test_reverse() {
        Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        reverse(a);
        int n = 10;
        for (int i=0; i<a.length; i++)
            assertEquals((int) a[i], n--);
        a = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        reverse(a);
        n = 9;
        for (int i=0; i<a.length; i++)
            assertEquals((int) a[i], n--);
    }

    private static void unit_test_binarySearch() {
        Integer[] whitelists = AppIn.getInstance().readAllInts(LARGE_W_FILE);
        Integer[] inputs = AppIn.getInstance().readAllInts(LARGE_T_FILE);
        sort(whitelists);
        int cnt = 0;
        for (int i=0; i<inputs.length; i++) {
            if (binarySearch(whitelists, inputs[i]) == -1) {
                System.out.println(inputs[i]);
                cnt++;
            }
        }
        assertEquals(cnt, 367966);
    }
}
