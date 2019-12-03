package app;

import collection.LinkedQueue;
import collection.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public final class In {

    private static final In SINGLETON = new In();

    private In() { }

    public static In getInstance() {
        return SINGLETON;
    }

    /**
     * Read all strings from given file.
     *
     * @param filename
     * @return
     */
    public String[] readAllStrings(String filename) {
        Queue<String> q = new LinkedQueue<>();
        File file = AppResource.getInstance().getFileOf(filename);
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNext())
                q.enqueue(scanner.next());

            String[] r = new String[q.size()];
            for (int i=0; i<r.length; i++)
                r[i] = q.dequeue();
            return r;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
