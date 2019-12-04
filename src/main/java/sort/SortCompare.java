package sort;

import util.Random;
import util.Stopwatch;


/**
 * A utility class to measure the performance of the sort algorithms.
 */
class SortCompare {

    private static double timing(String alg, Double[] a) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Sort.create(alg, a).execute();
        return stopwatch.elapseTime();
    }

    /**
     * Timing executing time of the given sorting algorithms with random input.
     *
     * @param alg
     * @param N   number of elements
     * @param T   times to perform sorting
     * @return
     */
    static double timingRandomInput(String alg, int N, int T) {
        Double[] a = new Double[N];
        double sum = 0;
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < N; j++)
                a[j] = Random.uniform();
            sum += timing(alg, a);
        }
        return sum;
    }

    /**
     * Comparing executing time of the given two sorting algorithms with random input.
     *
     * @param alg1 an algorithm
     * @param alg2 an algorithm to compare
     * @param N    number of elements
     * @param T    times to perform sorting
     * @return
     */
    static void compare(String alg1, String alg2, int N, int T) {
        double t1 = timingRandomInput(alg1, N, T);
        double t2 = timingRandomInput(alg2, N, T);
        System.out.printf("For %d random Doubles\n %s is", N, alg1);
        System.out.printf(" %.1f times faster than %s\n", (t2 / t1), alg2);
    }
}
