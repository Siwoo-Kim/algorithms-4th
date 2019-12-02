package p1;

import common.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ArrayStack<E> implements Stack<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    private int N;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void push(E el) {
        if (N == elements.length)
            ensureCapacity(elements.length << 1);
        elements[N++] = el;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        E[] newArray = (E[]) new Object[capacity];
        for (int i=0; i<N; i++)
            newArray[i] = elements[i];
        elements = newArray;
    }


    @Override
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return elements[N-1];
    }

    @Override
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E data = elements[--N];
        elements[N] = null; //help GC
        if (N > 0 && N == (elements.length >> 2))
            ensureCapacity(elements.length >> 1);
        return data;
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
        return new ReverseArrayIterator<>();
    }

    private boolean isFull() {
        return N == elements.length;
    }

    private class ReverseArrayIterator<E> implements Iterator<E> {
        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public E next() {
            return (E) elements[--i];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<N; i++) {
            sb.append(elements[i]);
            if (i != N-1)
                sb.append(", ");
        }
        if (isEmpty())
            sb.append("(empty)");
        return sb.toString();
    }

    private static final String TO_BE_FILES = "C:\\Users\\HOMEPC\\IntelliJIDEAProjects\\algorithms_4th\\src\\main\\resources\\algs4-data\\tobe.txt";

    public static void main(String[] args) throws FileNotFoundException {
        Stack<String> stack = new ArrayStack<>();
        File file = AppResource.getInstance().getFileOf("tobe.txt");
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (!word.equals("-"))
                    stack.push(word);
                else if (!stack.isEmpty())
                    System.out.print(stack.pop() + " ");
            }
            System.out.println("(" + stack.size() + " left on the stack)");
            System.out.println(stack);
        }

        p131();
        p132();
        p133();
        p136();
        p137();
    }

    private static void p137() {
        Stack<String> stack = new ArrayStack<>();
        stack.push("hi");
        assertEquals(stack.peek(), "hi");
        assertTrue(!stack.isEmpty());
        assertEquals(stack.pop(), "hi");
        stack.push("bye");
        assertEquals(stack.peek(), "bye");
        assertTrue(!stack.isEmpty());
        assertEquals(stack.pop(), "bye");
    }

    private static void p136() {
        int[] ints = {1, 2, 3, 4};
        Queue<Integer> q = new LinkedQueue<>();
        for (int i: ints)
            q.enqueue(i);
        Stack<Integer> stack = new LinkedStack<>();
        while (!q.isEmpty())
            stack.push(q.dequeue());
        while (!stack.isEmpty())
            q.enqueue(stack.pop());
        int i=0;
        while (!q.isEmpty())
            ints[i++] = q.dequeue();
        assertArrayEquals(ints, new int[]{4, 3, 2, 1});
    }

    private static void p133() {
        //answer = b, f, g
    }

    private static void p132() {
        Stack<String> stack = new ArrayStack<>();
        for (String s: "it was - the best - of times - - - it was - the - -".split(" "))
            if (s.equals("-"))
                stack.pop();
            else
                stack.push(s);
        assertEquals(stack.pop(), "it");
        assertTrue(stack.isEmpty());
    }

    private static void p131() {
        Stack<String> stack = new ArrayStack<>();
        for (int i=0; i<10; i++)
            stack.push(Integer.toString(i));
        assertTrue(((ArrayStack<String>) stack).isFull());
    }
}
