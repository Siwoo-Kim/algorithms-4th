package collection;

import common.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LinkedQueue<E> implements Queue<E> {

    private class Node {
        private Node next;
        private Object val;

        public Node(E el) {
            this.val = el;
        }
    }

    private Node first, last;
    private int N;

    @Override
    public void enqueue(E el) {
        if (last == null)
            first = last = new Node(el);
        else {
            Node old = last;
            last = new Node(el);
            old.next = last;
        }
        N++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = (E) first.val;
        first = first.next;
        if (first == null)
            last = null;
        N--;
        return e;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node c=first; c!=null; c=c.next) {
            sb.append(c.val);
            if (c != last)
                sb.append(", ");
        }
        if (isEmpty())
            sb.append("(empty)");
        return sb.toString();
    }

    private class LinkedIterator<E> implements Iterator<E> {
        private Node c = first;

        @Override
        public boolean hasNext() {
            return c != null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            E e = (E) c.val;
            c = c.next;
            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Queue<String> q = new LinkedQueue<>();
        File file = AppResource.getInstance().getFileOf("TO_BE_FILE");
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
