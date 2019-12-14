package client;

import collection.LinearProbingHashST;
import collection.SeparateChainingHashST;
import collection.SymbolTable;

import java.util.Arrays;

public class SparseVector {
    private SymbolTable<Integer, Double> st;

    public SparseVector() {
        st = new SeparateChainingHashST<>();
    }

    public int size() {
        return st.size();
    }

    public void put(int i, double x) {
        if (Double.compare(x, 0.0) != 0)
            st.put(i, x);
    }

    public double get(int i) {
        if (!st.contains(i)) return 0.0;
        else return st.get(i);
    }

    public double dot(double[] that) {
        double sum = 0.0;
        for (int i: st.keys())
            sum += st.get(i) * that[i];
        return sum;
    }

    private static final int N = 5;

    private static double[][] a = {
            {0, .9, 0, 0, 0},
            {0, 0, .36, .36, .18},
            {0, 0, 0, .9, 0},
            {.9, 0, 0, 0, 0},
            {.47, 0, .47, 0, 0}};
    private static double[] x = {.05, .04, .36, .37, .19};

    private static double[] r = new double[N];

    public static void main(String[] args) {
        for (int i=0; i<N; i++) {
            double sum = .0;
            for (int j=0; j<N; j++)
                sum += a[i][j] * x[j];
            r[i] = sum;
        }
        System.out.println(Arrays.toString(r));

        SparseVector[] svs = new SparseVector[N];
        for (int i=0; i<N; i++) {
            svs[i] = new SparseVector();
            for (int j=0; j<N; j++)
                svs[i].put(j, a[i][j]);
        }
        for (int i=0; i<N; i++)
            r[i] = svs[i].dot(x);
        System.out.println(Arrays.toString(r));
    }
}
