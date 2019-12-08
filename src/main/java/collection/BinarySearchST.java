package collection;

import app.AppIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code BST} class represents an ordered symbol table of generic
 * key-value pairs.
 *
 * @param <K>
 * @param <V>
 */
public class BinarySearchST<K extends Comparable<? super K>, V> implements SortedSymbolTable<K, V> {
    private static final int DEFAULT_CAPACITY = 10;
    private K[] keys;
    private V[] values;
    private int N;

    @SuppressWarnings("unchecked")
    public BinarySearchST() {
        keys = (K[]) new Comparable[DEFAULT_CAPACITY];
        values = (V[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public K min() {
        if (isEmpty())
            throw new NoSuchElementException();
        return keys[0];
    }

    @Override
    public K max() {
        if (isEmpty())
            throw new NoSuchElementException();
        return keys[N-1];
    }

    @Override
    public int rank(K key) {
        checkNotNull(key);
        return rank(0, N-1, key);
    }

    private int rank(int low, int high, K key) {
        if (low > high) return low;
        int mid = (low + high) / 2;
        int cmp = key.compareTo(keys[mid]);
        if (cmp > 0)
            return rank(mid+1, high, key);
        else if (cmp < 0)
            return rank(low, mid-1, key);
        else
            return mid;
    }

    @Override
    public K rankOf(int rank) {
        if (rank < 0 || rank >= N)
            throw new IllegalArgumentException();
        return keys[rank];
    }

    @Override
    public K ceiling(K key) {
        checkNotNull(key);
        int i = rank(key);
        return i == N? null: keys[i];
    }

    @Override
    public K floor(K key) {
        checkNotNull(key);
        int i = rank(key);
        if (i < N && key.equals(keys[i]))
            return key;
        return i == 0? null: keys[i-1];
    }

    @Override
    public Iterable<K> keys(K low, K high) {
        if (low.compareTo(high) >= 0)
            return new LinkedQueue<>();
        Queue<K> q = new LinkedQueue<>();
        final int l = rank(low);
        final int h = rank(high);
        for (int i=l; i<=h; i++)
            q.enqueue(keys[i]);
        return q;
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        checkNotNull(value);
        int rank = rank(key);
        //key is already in the table
        if (rank < N && key.equals(keys[rank])) {
            values[rank] = value;
            return;
        }
        if (N == keys.length)
            ensureCapacity(N << 1);
        //insert new key-value pairs
        for (int i=N; i>rank; rank--) {
            keys[i] = keys[i-1];
            values[i] = values[i-1];
        }
        keys[rank] = key;
        values[rank] = value;
        N++;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        assert capacity >= N;
        K[] newKeys = (K[]) new Comparable[capacity];
        V[] newValues = (V[]) new Object[capacity];
        for (int i=0; i<N; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys;
        values = newValues;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        int rank = rank(key);
        if (rank < N && key.equals(keys[rank]))
            return values[rank];
        else
            return null;
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        int rank = rank(key);
        //key isn't exist.
        if (rank >= N || key.compareTo(keys[rank]) != 0)
            return;
        for (int i=rank;i<N-1; i++) {
            keys[i] = keys[i+1];
            values[i] = values[i+1];
        }
        N--;
        keys[N] = null; //help GC
        values[N] = null;
        if (N > 0 && N == keys.length >> 2)
            ensureCapacity(keys.length >> 1);
    }

    @Override
    public void removeMin() {
        if (isEmpty())
            throw new NoSuchElementException();
        remove(min());
    }

    @Override
    public void removeMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        remove(max());
    }

    @Override
    public boolean contains(K key) {
        checkNotNull(key);
        return get(key) != null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BinarySearchIterator();
    }

    private class BinarySearchIterator implements Iterator<K> {
        private int i=0;

        @Override
        public boolean hasNext() {
            return i<N;
        }

        @Override
        public K next() {
            if (!hasNext())
                throw new ArrayIndexOutOfBoundsException();
            return keys[i];
        }
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
                st.put(words[i], st.get(words[i] + 1));
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
