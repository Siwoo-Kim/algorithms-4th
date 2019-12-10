package collection;

import app.AppIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The {@code BinarySearchTree} class represents an ordered
 * symbol table of generic key-value pairs.
 * It supports the usual <em>put</em>, <em>put</em>, <em>delete</em> methods.
 *
 * @param <K>
 * @param <V>
 */

public class BinarySearchTree<K extends Comparable<? super K>, V> implements SortedSymbolTable<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int N;  //number of nodes in the subtree

        public Node(K key, V value, int N) {
            checkNotNull(key, value);
            assert N > 0;
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }

    private Node root;

    /**
     * returns number of key-value pairs in symbol-table
     * @return
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * return number of key-value pairs in subtree rooted at {@code root}
     *
     * @param root
     * @return
     */
    private int size(Node root) {
        return root == null? 0: root.N;
    }

    @Override
    public int size(K low, K high) {
        return 0;
    }

    /**
     * put the key-value pairs in the BST
     *
     * @param key
     * @param value
     * @throws IllegalArgumentException if {@code key} or {@code value} is {@code null}
     */
    @Override
    public void put(K key, V value) {
        checkNotNull(key, value);
        root = put(root, key, value);
        assert invariant();
    }

    private Node put(Node root, K key, V value) {
        if (root == null)
            return new Node(key, value, 1);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) root.left = put(root.left, key, value);
        else if (cmp > 0) root.right = put(root.right, key, value);
        else root.value = value;
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    /**
     * returns the associated value with the given key.
     *
     * @param key
     * @return
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public V get(K key) {
        checkNotNull(key);
        if (isEmpty())
            return null;
        Node node = get(root, key);
        return node == null? null: node.value;
    }

    private Node get(Node root, K key) {
        if (root == null) return null;
        checkNotNull(key);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) return get(root.left, key);
        else if (cmp > 0) return get(root.right, key);
        else return root;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return
     * @throws NoSuchElementException if the symbol table is empty
     */
    @Override
    public K min() {
        if (isEmpty())
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node min(Node root) {
        if (root.left == null)
            return root;
        return min(root.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return
     * @throws NoSuchElementException if the symbol table is empty
     */
    @Override
    public K max() {
        if (isEmpty())
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node max(Node root) {
        if (root.right == null)
            return root;
        return max(root.right);
    }

    /**
     * Return the number of keys in the symbol table less than given @{code key}
     * @param key
     * @return
     */
    @Override
    public int rank(K key) {
        checkNotNull(key);
        if (isEmpty())
            throw new NoSuchElementException();
        return rank(root, key);
    }

    private int rank(Node root, K key) {
        if (root == null) return 0;
        int cmp = key.compareTo(root.key);
        int t = size(root.left);
        if (cmp < 0) return rank(root.left, key);
        else if (cmp > 0) return t + rank(root.right, key) + 1;
        else return t;
    }

    /**
     * Return the key in the symbol table whose rank is {@code rank}.
     *
     * @param rank
     * @return
     * @throws ArrayIndexOutOfBoundsException unless {@code rank} is between {@code 0} and {@code N-1}
     */
    @Override
    public K rankOf(int rank) {
        checkElementIndex(rank, size());
        return rankOf(root, rank);
    }

    private K rankOf(Node root, int k) {
        if (root == null) return null;
        int t = size(root.left);
        if (t < k) return rankOf(root.left, k);
        if (t > k) return rankOf(root.right, k-t-1);
        return root.key;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to given {@code key}
     *
     * @param key
     * @return
     * @throws NoSuchElementException if the symbol table is empty
     */
    @Override
    public K ceiling(K key) {
        checkNotNull(key);
        if (isEmpty())
            throw  new NoSuchElementException();
        return ceiling(root, key).key;
    }

    private Node ceiling(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root;
        else if (cmp < 0) {
            Node node = ceiling(root.left, key);
            if (node == null)
                node = root;
            return node;
        }
        return ceiling(root.right, key);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to given {@code key}
     *
     * @param key
     * @return
     * @throws NoSuchElementException if the symbol table is empty
     */
    @Override
    public K floor(K key) {
        checkNotNull(key);
        if (isEmpty())
            throw new NoSuchElementException();
        Node node = floor(root, key);
        return node == null? null: node.key;
    }

    private Node floor(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root;
        else if (cmp < 0) return floor(root.left, key);
        Node node = floor(root.right, key);
        if (node == null)
            node = root;
        return node;
    }

    @Override
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<K> keys(K low, K high) {
        checkNotNull(low, high);
        Queue<K> q = new LinkedQueue<>();
        keys(root, low, high, q);
        return q;
    }

    private void keys(Node root, K low, K high, Queue<K> q) {
        if (root == null) return;
        int cmpl = root.key.compareTo(low);
        int cmph = root.key.compareTo(high);
        if (cmpl > 0) keys(root.left, low, high, q);
        if (cmpl >= 0 && cmph <= 0) q.enqueue(root.key);
        if (cmph < 0) keys(root.right, low, high, q);
    }

    @Override
    public void remove(K key) {
        checkNotNull(key);
        root = remove(root, key);
        assert invariant();
    }

    private Node remove(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) root.left = remove(root.left, key);
        else if (cmp > 0) root.right = remove(root.right, key);
        else {
            if (root.right == null) return root.left;
            if (root.left == null) return root.right;
            Node t = root;
            root = min(t.right);
            root.right = removeMin(t.right);
            root.left = t.left;
        }
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }

    /**
     * remove the smallest key-value pairs in the symbol table.
     *
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public void removeMin() {
        if (isEmpty())
            throw new NoSuchElementException();
        root = removeMin(root);
        assert invariant();
    }

    private Node removeMin(Node root) {
        if (root.left == null) return root.right;
        root.left = removeMin(root.left);
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    /**
     * remove the largest key-value pairs in the symbol table.
     *
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public void removeMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        root = removeMax(root);
        assert invariant();
    }

    private Node removeMax(Node root) {
        if (root.right == null)
            return root.left;
        root.right = removeMax(root.right);
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    @Override
    public boolean contains(K key) {
        if (isEmpty())
            return false;
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * =================================== TEST METHOD ===================================
     */

    private boolean invariant() {
        if (!bstInvariant())
            System.out.println("Not in symmetric order");
        if (!sizeInvariant())
            System.out.println("Subtree counts not consistent");
        return bstInvariant() && sizeInvariant();
    }

    private boolean sizeInvariant() {
        return sizeInvariant(root);
    }

    private boolean sizeInvariant(Node root) {
        if (root == null) return true;
        if (root.N != size(root.left) + size(root.right) + 1)
            return false;
        return sizeInvariant(root.left) && sizeInvariant(root.right);
    }

    /**
     * is the bst symmetric order?
     * @return
     */
    private boolean bstInvariant() {
        return bstInvariant(root, null, null);
    }

    /**
     * is the tree rooted at given {@code root} with all keys strictly
     * between {@code min} and {@code max}?
     * @param root
     * @param min
     * @param max
     * @return
     */
    private boolean bstInvariant(Node root, K min, K max) {
        if (root == null) return true;
        if (min != null && root.key.compareTo(min) <= 0) return false;
        if (max != null && root.key.compareTo(max) >= 0) return false;
        return bstInvariant(root.left, min, root.key) && bstInvariant(root.right, root.key, max);
    }

    private static final String TINY_FILE = "tinyST.txt";
    private static final String LONG_FILE = "leipzig1M.txt";

    public static void main(String[] args) {
        SymbolTable<String, Integer> st = new BinarySearchTree<>();
        String[] words = AppIn.getInstance().readAllStrings(TINY_FILE);
        for (int i=0; i<words.length; i++)
            st.put(words[i], i);
        for (String key: st.keys())
            System.out.println(key + " " + st.get(key));

        st = new BinarySearchTree<>();
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
