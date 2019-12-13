package collection;

import app.AppIn;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class LinearProbingHashST<K, V> implements SymbolTable<K, V> {

    private static final int DEFAULT_CAPACITY = 13;
    private int M;  //size of table
    private int N;  //number of key-value pairs
    private K[] keys;
    private V[] values;

    public LinearProbingHashST() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashST(int capacity) {
        this.M = BigInteger.valueOf(capacity)
                .nextProbablePrime()
                .intValue();
        this.keys = (K[]) new Object[M];
        this.values = (V[]) new Object[M];
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key, value);
        int hash = hash(key);
        for (;keys[hash]!=null; hash=(hash+1) % M) {
            if (keys[hash].equals(key)) {
                values[hash] = value;
                return;
            }
        }
        keys[hash] = key;
        values[hash] = value;
        N++;
        // double table size if load factor is 50%
        if (N >= (M/2))
            ensureCapacity(M << 1);
    }

    private void ensureCapacity(int capacity) {
        LinearProbingHashST<K, V> st = new LinearProbingHashST<>(capacity);
        for (int i=0; i<M; i++)
            if (keys[i] != null)
                st.put(keys[i], values[i]);
        keys = st.keys;
        values = st.values;
        M = st.M;
        N = st.N;
    }

    /**
     * hash the given {@code key} into [0..M-1]
     *
     * @param key
     * @return
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        int hash = hash(key);
        for ( ;keys[hash]!=null; hash=(hash+1)%M)
            if (keys[hash].equals(key))
                return values[hash];
        return null;
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        if (isEmpty())
            throw new NoSuchElementException();
        if (!contains(key))
            return;
        int hash = hash(key);
        //find the position of the given key
        while (!keys[hash].equals(key))
            hash = (hash + 1) % M;
        //delete the key-value pair
        keys[hash] = null;
        values[hash] = null;
        //rehash all the keys on the right side
        hash = (hash + 1) % M;
        while (keys[hash] != null) {
            K redoK = keys[hash];
            V redoV = values[hash];
            keys[hash] = null;
            values[hash] = null;
            N--;
            put(redoK, redoV);
            hash = (hash + 1) % M;
        }
        N--;
        //halves size of table if load factor is 12.5%
        if (N > 0 && N <= (M/8))
            ensureCapacity(M >> 1);
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> q = new LinkedQueue<>();
        for (int i=0; i<M; i++)
            if (keys[i] != null)
                q.enqueue(keys[i]);
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
        SymbolTable<String, Integer> st = new LinearProbingHashST<>();
        String[] words = AppIn.getInstance().readAllStrings(TINY_FILE);
        for (int i=0; i<words.length; i++)
            st.put(words[i], i);
        for (String key: st.keys())
            System.out.println(key + " " + st.get(key));

        st = new LinearProbingHashST<>();
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
