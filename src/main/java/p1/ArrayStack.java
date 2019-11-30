package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Scanner;

public class ArrayStack<E> implements Stack<E>{
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
        return sb.toString();
    }

    private static final String TO_BE_FILES = "C:\\Users\\HOMEPC\\IntelliJIDEAProjects\\algorithms_4th\\src\\main\\resources\\algs4-data\\tobe.txt";

    public static void main(String[] args) throws FileNotFoundException {
        Stack<String> stack = new ArrayStack<>();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(TO_BE_FILES))))) {
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
    }
}
