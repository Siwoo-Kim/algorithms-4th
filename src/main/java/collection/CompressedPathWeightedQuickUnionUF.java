package collection;

import app.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class CompressedPathWeightedQuickUnionUF implements UF {

    private int[] ids;
    private int[] sizes;
    private int N;

    public CompressedPathWeightedQuickUnionUF(int N) {
        this.N = N;
        this.ids = new int[N];
        this.sizes = new int[N];
        for (int i=0; i<N; i++) {
            ids[i] = i;
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
        if (sizes[rootP] < sizes[rootQ]) {
            ids[rootP] = rootQ;
            sizes[rootQ] += sizes[rootP];
        } else {
            ids[rootQ] = rootP;
            sizes[rootP] += sizes[rootQ];
        }
        N--;
    }

    @Override
    public int find(int p) {
        int root = p;
        while (ids[root] != root)
            root = ids[root];
        while (p != root) {
            int oldId = ids[p];
            ids[p] = root;
            sizes[p] = 1;
            p = oldId;
        }
        return root;
    }

    @Override
    public int count() {
        return N;
    }

    private static final String TINY_UF_FILE = "tinyUF.txt";

    public static void main(String[] args) {
        File file = AppResource.getInstance().getFile(TINY_UF_FILE);
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            int N = scanner.nextInt();
            UF UF = new CompressedPathWeightedQuickUnionUF(N);
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
