package p1;

import common.AppResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Scanner;

public class LinkedStack<E> implements Stack<E> {

    private Node head;
    private int N;

    private class Node {
        private E el;
        private Node next;

        public Node(E el, Node next) {
            this.el = el;
            this.next = next;
        }
    }

    @Override
    public void push(E el) {
        head = new Node(el, head);
        N++;
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return head.el;
    }

    @Override
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E data = head.el;
        head = head.next;
        N--;
        return data;
    }

    @Override
    public boolean isEmpty() {
        return head==null;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedNodeIterator<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node c=head; c!=null; c=c.next) {
            sb.append(c.el);
            if (c.next != null)
                sb.append(", ");
        }
        return sb.toString();
    }

    private class LinkedNodeIterator<E> implements Iterator<E> {
        private Node c = head;

        @Override
        public boolean hasNext() {
            return c != null;
        }

        @Override
        public E next() {
            E data = (E) c.el;
            c = c.next;
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final String TO_BE_FILE = "tobe.txt";

    public static void main(String[] args) throws FileNotFoundException {
        Stack<String> stack = new LinkedStack<>();
        File file = AppResource.getInstance().getFileOf(TO_BE_FILE);
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
    }
}
