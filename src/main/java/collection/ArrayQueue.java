package collection;

import app.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArrayQueue<E> implements Queue<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    private int N;
    private int first, last;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void enqueue(E el) {
        if (N == elements.length)
            ensureCapacity(elements.length << 1);
        elements[last++] = el;
        if (last == elements.length)
            last = 0;
        N++;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        E[] newArray = (E[]) new Object[capacity];
        for (int i=0; i<N; i++)
            newArray[i] = elements[(first + i) % elements.length];
        elements = newArray;
        first = 0;
        last = N;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = elements[first];
        elements[first] = null; //help GC
        first++;
        N--;
        if (first == elements.length)
            first = 0;
        if (N > 0 && N == elements.length >> 2)
            ensureCapacity(elements.length >> 1);
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elements[first];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<N; i++) {
            E e = elements[(i + first) % elements.length];
            sb.append(e);
            if (i != N-1)
                sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator implements Iterator<E> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < N;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E e = elements[(current + first) % elements.length];
            current++;
            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final String TO_BE_FILE = "tobe.txt";

    public static void main(String[] args) {
        Queue<String> q = new ArrayQueue<>();
        File file = AppResource.getInstance().getFile(TO_BE_FILE);
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (!"-".equals(word))
                    q.enqueue(word);
                else
                    System.out.print(q.dequeue() + " ");
            }
            System.out.println("(" + q.size() + " left on the stack)");
            System.out.println(q);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }
    }
}
