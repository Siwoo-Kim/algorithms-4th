package collection;

import app.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class CompressedPathQuickUnionUF implements UF {
    private int[] ids;
    private int N;

    public CompressedPathQuickUnionUF(int n) {
        N = n;
        ids = new int[N];
        for (int i=0; i<N; i++)
            ids[i] = i;
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
        ids[rootP] = ids[rootQ];
        N--;
    }

    /**
     * return the component id.
     * @param p
     * @return
     */
    @Override
    public int find(int p) {
        int root = p;
        while (root != ids[root])
            root = ids[root];
        while (p != root) {
            int oldP = ids[p];
            ids[p] = root;
            p = oldP;
        }
        return root;
    }

    @Override
    public int count() {
        return N;
    }

    private static final String TINY_UF_FILE = "tinyUF.txt";

    public static void main(String[] args) {
        File file = AppResource.getInstance().getFileOf(TINY_UF_FILE);
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            int N = scanner.nextInt();
            UF UF = new CompressedPathQuickUnionUF(N);
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
