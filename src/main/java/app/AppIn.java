package app;

import collection.LinkedQueue;
import collection.Queue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
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
        Reader<String> reader = Reader.of(filename, Type.STRING);
        return reader.readAll();
    }

    public String[] readAllLines(String filename) {
        checkNotNull(filename);
        Reader<String> reader = Reader.of(filename, Type.LINE);
        return reader.readAll();
    }

    /**
     * Read all integers from given application file name.
     *
     * @param filename
     * @return
     */
    public Integer[] readAllInts(String filename) {
        checkNotNull(filename);
        Reader<Integer> reader = Reader.of(filename, Type.INTEGER);
        return reader.readAll();
    }

    enum Type {
        STRING(String.class),
        LINE(String.class),
        INTEGER(Integer.class);

        private Class<?> clazz;

        Type(Class<?> clazz) {
            this.clazz = clazz;
        }
    }

    /**
     * Read all data of which has same data type from given file.
     *
     * @param <E>
     */
    private static abstract class Reader<E> {
        private final File file;
        private final Type type;


        @SuppressWarnings("unchecked")
        static <E> Reader<E> of(String filename, Type type) {
            checkNotNull(type);
            if (type == Type.STRING)
                return (Reader<E>) new StringReader(filename, type);
            else if (type == Type.LINE)
                return (Reader<E>) new LineReader(filename, type);
            else if (type == Type.INTEGER)
                return (Reader<E>) new IntReader(filename, type);
            throw new UnsupportedOperationException();
        }

        private Reader(String fileName, Type type) {
            checkNotNull(fileName);
            checkNotNull(type);
            file = AppResource.getInstance().getFile(fileName);
            this.type = type;
            checkArgument(file.exists(), "File [%s] doesn't exists", fileName);
        }

        abstract boolean hasNext(Scanner scanner);

        abstract E next(Scanner scanner);

        E[] newArrays(int size) {
            return (E[]) Array.newInstance(type.clazz, size);
        };

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

        public IntReader(String filename, Type type) {
            super(filename, type);
        }

        @Override
        boolean hasNext(Scanner scanner) {
            return scanner.hasNextInt();
        }

        @Override
        Integer next(Scanner scanner) {
            return scanner.nextInt();
        }
    }

    private static class StringReader extends Reader<String> {

        private StringReader(String filename, Type type) {
            super(filename, type);
        }

        @Override
        boolean hasNext(Scanner scanner) {
            return scanner.hasNext();
        }

        @Override
        String next(Scanner scanner) {
            return scanner.next();
        }
    }

    private static class LineReader extends Reader<String> {

        private LineReader(String filename, Type type) {
            super(filename, type);
        }

        @Override
        boolean hasNext(Scanner scanner) {
            return scanner.hasNextLine();
        }

        @Override
        String next(Scanner scanner) {
            return scanner.nextLine();
        }
    }
}
