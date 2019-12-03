package collection;

import common.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class WeightedQuickUnionUF implements UF {
    private int[] nodes;
    //sizes of trees
    private int[] sizes;
    //number of components
    private int N;

    public WeightedQuickUnionUF(int N) {
        this.N = N;
        nodes = new int[N];
        sizes = new int[N];
        for (int i=0; i<N; i++) {
            nodes[i] = i;
            sizes[i] = 1;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p),
                rootQ = find(q);
        if (rootP == rootQ)
            return;
        //merge smaller tree into bigger tree.
        if (sizes[rootP] < sizes[rootQ]) {
            nodes[rootP] = rootQ;
            sizes[rootQ] += sizes[rootP];
        } else {
            nodes[rootQ] = rootP;
            sizes[rootP] += sizes[rootQ];
        }
        N--;
    }

    @Override
    public int find(int p) {
        //find root
        if (p != nodes[p])
            return find(nodes[p]);
        return p;
    }

    @Override
    public int count() {
        return N;
    }

    private static final String TINY_UF_FILE = "tinyUF.txt";
    private static final String LARGE_UF_FILE = "largeUF.txt";

    public static void main(String[] args) {
        File file = AppResource.getInstance().getFileOf(LARGE_UF_FILE);
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            int N = scanner.nextInt();
            UF UF = new WeightedQuickUnionUF(N);
            while (scanner.hasNextInt()) {
                int p = scanner.nextInt(),
                        q = scanner.nextInt();
                if (UF.connected(p, q))
                    continue;
                else
                    UF.union(p, q);
                System.out.printf("P: %d, Q: %d, C: %d%n", p, q, UF.find(p));
            }
            System.out.printf("%d components", UF.count());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
