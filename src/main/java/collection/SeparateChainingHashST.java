package collection;

import app.AppIn;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code SeparateChainingHashST} class represents a symbol table of generic
 * key-value pairs.
 *
 * @param <K>
 * @param <V>
 */
public class SeparateChainingHashST<K, V> implements SymbolTable<K, V> {

    private static final int DEFAULT_CAPACITY = 997;
    private int M;  //bucket size
    private int N;  //number of key-value pairs
    private Node<K, V>[] buckets;

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SeparateChainingHashST() {
        this(DEFAULT_CAPACITY);
    }

    public SeparateChainingHashST(int capacity) {
        this.M = BigInteger.valueOf(capacity)
                .nextProbablePrime()
                .intValue();
        @SuppressWarnings("unchecked")
        Node<K, V>[] nodes = new Node[M];
        this.buckets = nodes;
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key, value);
        Node<K, V> node;
        for (node = buckets[hash(key)]; node!=null; node=node.next) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        node = buckets[hash(key)];
        buckets[hash(key)] = new Node<>(key, value, node);
        N++;
        // double buckets size if average length of bucket >= 8
        if (N >= M*8)
            ensureCapacity(M<<1);
    }

    /**
     * resize the table with given {@code capacity}
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        SeparateChainingHashST<K, V> st = new SeparateChainingHashST<>(capacity);
        for (int i=0; i<M; i++)
            for (Node<K, V> c=buckets[i]; c!=null; c=c.next)
                st.put(c.key, c.value);
        buckets = st.buckets;
        M = st.M;
        N = st.N;
    }

    /**
     * hash given {@code key} into [0.. M-1]
     * @param key
     * @return
     */
    private int hash(K key) {
        assert key != null;
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        Node<K, V> node = buckets[hash(key)];
        for (; node!=null; node=node.next)
            if (node.key.equals(key))
                return node.value;
        return null;
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        if (isEmpty())
            throw new NoSuchElementException();
        Node<K, V> node = buckets[hash(key)];
        buckets[hash(key)] = remove(node, key);
        N--;
        //halve the buckets size if average of bucket length <= 2
        if (N > DEFAULT_CAPACITY && N == M*2)
            ensureCapacity(M/2);
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) return null;
        if (node.key.equals(key))
            return node.next;
        node.next = remove(node.next, key);
        return node;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> q = new LinkedQueue<>();
        for (int i=0; i<M; i++)
            for (Node<K, V> node=buckets[i]; node!=null; node=node.next)
                q.enqueue(node.key);
        return q;
    }

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }

    @Override
    public boolean contains(K key) {
        checkNotNull(key);
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    private static final String TINY_FILE = "tinyST.txt";
    private static final String LONG_FILE = "leipzig1M.txt";

    public static void main(String[] args) {
        SymbolTable<String, Integer> st = new SeparateChainingHashST<>();
        String[] words = AppIn.getInstance().readAllStrings(TINY_FILE);
        for (int i=0; i<words.length; i++)
            st.put(words[i], i);
        for (String key: st.keys())
            System.out.println(key + " " + st.get(key));

        st = new SeparateChainingHashST<>();
        final int minLength = 10;
        words = AppIn.getInstance().readAllStrings(LONG_FILE);
        for (int i=0; i<words.length; i++) {
            if (words[i].length() < minLength)
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
