package app;

import collection.LinkedQueue;
import collection.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * The class {@code AppIn} supports for reading data from
 * application file.
 *
 */
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
        checkNotNull(filename);
        return Reader.of(filename, String.class).readAll();
    }

    /**
     * Read all integers from given application file name.
     *
     * @param filename
     * @return
     */
    public Integer[] readAllInts(String filename) {
        checkNotNull(filename);
        return Reader.of(filename, Integer.class).readAll();
    }

    /**
     * Read all data of which has same data type from given file.
     *
     * @param <E>
     */
    private static abstract class Reader<E> {
        private final File file;

        @SuppressWarnings("unchecked")
        static <E> Reader<E> of(String filename, Class<E> clazz) {
            checkNotNull(clazz);
            if (clazz == String.class)
                return (Reader<E>) new StringReader(filename);
            else if (clazz == Integer.class)
                return (Reader<E>) new IntReader(filename);
            throw new UnsupportedOperationException();
        }

        private Reader(String fileName) {
            checkNotNull(fileName);
            file = AppResource.getInstance().getFile(fileName);
            checkArgument(file.exists(), "File [%s] doesn't exists", fileName);
        }

        abstract boolean hasNext(Scanner scanner);

        abstract E next(Scanner scanner);

        abstract E[] newArrays(int size);

        E[] readAll() {
            Queue<E> q = new LinkedQueue<>();
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
                while (hasNext(scanner)) {
                    E e = Objects.requireNonNull(next(scanner));
                    q.enqueue(e);
                }
                E[] array = newArrays(q.size());
                for (int i=0; i<array.length; i++) {
                    E e = Objects.requireNonNull(q.dequeue());
                    array[i] = e;
                }
                return array;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class IntReader extends Reader<Integer> {

        private IntReader(String fileName) {
            super(fileName);
        }

        @Override
        boolean hasNext(Scanner scanner) {
            return scanner.hasNextInt();
        }

        @Override
        Integer next(Scanner scanner) {
            return scanner.nextInt();
        }

        @Override
        Integer[] newArrays(int size) {
            return new Integer[size];
        }
    }

    private static class StringReader extends Reader<String> {

        private StringReader(String fileName) {
            super(fileName);
        }

        @Override
        boolean hasNext(Scanner scanner) {
            return scanner.hasNext();
        }

        @Override
        String next(Scanner scanner) {
            return scanner.next();
        }

        @Override
        String[] newArrays(int size) {
            return new String[size];
        }
    }
}
