package collection;

import app.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class QuickFindUF implements UF {
    private int[] nodes;
    private int N;

    public QuickFindUF(final int N) {
        this.N = N;
        nodes = new int[N];
        for (int i=0; i<N; i++)
            nodes[i] = i;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void union(int p, int q) {
        int pId = nodes[p], qId = nodes[q];
        if (pId == qId)
            return;
        N--;
        //set p's component as q's component
        for (int i=0; i<nodes.length; i++)
            if (nodes[i] == pId)
                nodes[i] = qId;
    }

    @Override
    public int find(int p) {
        return nodes[p];
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
            UF UF = new QuickFindUF(N);
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
