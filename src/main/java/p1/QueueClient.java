package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

class QueueClient {

    private final Queue<Integer> queue;

    public QueueClient(Queue<Integer> q) {
        assert q != null;
        this.queue = q;
    }

    public int[] readAllInts(String file) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(file))))) {
            while (scanner.hasNextInt())
                queue.enqueue(scanner.nextInt());
            int[] ints = new int[queue.size()];
            int i=0;
            while (queue.isEmpty())
                ints[i++] = queue.dequeue();
            return ints;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    };

}
