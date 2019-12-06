package collection;

import app.AppResource;
import domain.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class BinaryHeap<E extends Comparable<? super E>> implements PriorityQueue<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Comparator<? super E> comparator;   //optional comparator
    private E[] heap;   // store items at [1..N]
    private int N;  //number of items on priority queue

    public BinaryHeap(Comparator<? super E> comparator) {
        @SuppressWarnings("unchecked")
        E[] heap = (E[]) new Comparable[DEFAULT_CAPACITY];
        this.heap = heap;
        this.comparator = comparator;
    }

    public BinaryHeap() {
        this(null);
    }

    @Override
    public void enqueue(E e) {
        if (N == heap.length)
            ensureCapacity(N << 1);
        heap[++N] = e;
        swim(N);    //do reheapify
    }

    /**
     * helper functions to do reheapify for ensure
     * the in-variant of heap.
     * @param k
     */
    private void swim(int k) {
        while (k > 1 && less(heap[k/2], heap[k])) {
            exchange(heap, k, k/2);
            k /= 2;
        }
    }

    private void exchange(E[] a, int i, int j) {
        E e = a[i];
        a[i] = a[j];
        a[j] = e;
    }

    private boolean less(E a, E b) {
        checkNotNull(a);
        checkNotNull(b);
        return comparator == null ?
                a.compareTo(b) < 0 :
                comparator.compare(a, b) < 0;
    }

    private void ensureCapacity(int capacity) {
        @SuppressWarnings("unchecked")
        E[] newHeap = (E[]) new Comparable[capacity];
        for (int i=0; i<=N; i++)
            newHeap[i] = heap[i];
        heap = newHeap;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("heap is empty");
        E max = heap[1];
        exchange(heap, 1, N--);
        heap[N+1] = null;   //help GC
        sink(1);    //do reheapify
        if (N > 0 && N == ((heap.length-1) >> 2))
            ensureCapacity(heap.length >> 1);
        return max;
    }

    private void sink(int k) {
        while (k*2 <= N) {
            int j = k*2;
            if (j < N && less(heap[j], heap[j+1]))
                j++;
            if (!less(heap[k], heap[j]))
                return;
            exchange(heap, k, j);
            k = j;
        }
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return heap[1];
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
    public Iterator<E> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<E> {
        private BinaryHeap<E> copy;

        public HeapIterator() {
            if (comparator == null) copy = new BinaryHeap<>();
            else copy = new BinaryHeap<E>(comparator);
            for (int i=1; i<=N; i++)
                copy.enqueue(heap[i]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.dequeue();
        }
    }

    public static final String TINY_BATCH_FILE = "tinyBatch.txt";

    public static void main(String[] args) throws FileNotFoundException {
        final int M = 5;
        File file = AppResource.getInstance().getFile(TINY_BATCH_FILE);
        PriorityQueue<Transaction> pq = new BinaryHeap<>(Comparator.comparing(Transaction::amount).reversed());
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                pq.enqueue(Transaction.fromString(s));
                if (pq.size() > M)
                    pq.dequeue();
            }
        }
        Stack<Transaction> stack = new LinkedStack<>();
        for (Transaction t: pq)
            System.out.println(t);
        while (!pq.isEmpty())
            stack.push(pq.dequeue());
        for (Transaction t: stack)
            System.out.println(t);
    }
}

