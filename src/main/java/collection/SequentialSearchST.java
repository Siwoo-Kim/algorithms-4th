package collection;

import app.AppIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class represents an (unordered) symbol table of generic key-value pairs.
 *
 * @param <K>
 * @param <V>
 */
public class SequentialSearchST<K, V> implements SymbolTable<K, V> {

    private Node head;  // the linked list of key-value pairs
    private int N;  // number of key-value pairs

    private class Node {
        private K key;
        private V value;
        private Node next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        checkNotNull(value);
        for (Node c=head; c!=null; c=c.next)
            if (key.equals(c.key)) {
                c.value = value;
                return;
            }
        head = new Node(key, value, head);
        N++;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        for (Node c=head; c!=null; c=c.next) {
            if (key.equals(c.key))
                return c.value;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        head = remove(head, key);
    }

    private Node remove(Node node, K key) {
        if (node == null)
            return null;
        if (key.equals(node.key))  {
            N--;
            return node.next;
        }
        node.next = remove(node, key);
        return node;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> q = new LinkedQueue<>();
        for (K key: this)
            q.enqueue(key);
        return q;
    }

    @Override
    public Iterator<K> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<K> {
        private Node c = head;

        @Override
        public boolean hasNext() {
            return c != null;
        }

        @Override
        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            K key = c.key;
            c = c.next;
            return key;
        }
    }

    @Override
    public boolean contains(K key) {
        checkNotNull(key);
        if (isEmpty())
            return false;
        return get(key) != null;
    }

    private static final String TINY_FILE = "tinyST.txt";
    private static final String LONG_FILE = "leipzig1M.txt";

    public static void main(String[] args) {
        SymbolTable<String, Integer> st = new SequentialSearchST<>();
        String[] words = AppIn.getInstance().readAllStrings(TINY_FILE);
        for (int i=0; i<words.length; i++)
            st.put(words[i], i);
        for (String key: st.keys())
            System.out.println(key + " " + st.get(key));

        st = new SequentialSearchST<>();
        final int minLength = 10;
        words = AppIn.getInstance().readAllStrings(LONG_FILE);
        for (int i=0; i<words.length; i++) {
            if (words.length < minLength)
                continue;
            if (st.contains(words[i]))
                st.put(words[i], st.get(words[i]) + 1);
            else
                st.put(words[i], 1);
        }
        String max = "";
        st.put(max, 0);
        for (String key: st.keys())
            if (st.get(max) < st.get(key))
                max = key;
        System.out.println(max + " " + st.get(max));
    }
}
