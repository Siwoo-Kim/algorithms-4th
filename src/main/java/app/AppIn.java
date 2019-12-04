package app;

import collection.LinkedQueue;
import collection.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public final class AppIn {

    private static final AppIn SINGLETON = new AppIn();

    private AppIn() { }

    public static AppIn getInstance() {
        return SINGLETON;
    }

    /**
     * Read all strings from given application file name.
     *
     * @param filename
     * @return
     */
    public String[] readAllStrings(String filename) {
        Queue<String> q = new LinkedQueue<>();
        File file = AppResource.getInstance().getFile(filename);
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
