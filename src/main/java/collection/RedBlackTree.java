package collection;

import app.AppIn;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static collection.RedBlackTree.Color.BLACK;
import static collection.RedBlackTree.Color.RED;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code RedBlackTree} class represents an ordered symbol table of generic key-value pairs.
 * It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>, <em>remove</em> operations.
 *
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<? super K>, V> implements SortedSymbolTable<K, V> {

    enum Color {
        BLACK, RED;
        private static final EnumMap<Color, Color> oppositeMap = new EnumMap<>(Color.class);

        static {
            oppositeMap.put(BLACK, RED);
            oppositeMap.put(RED, BLACK);
        }

        public static Color opposite(Color color) {
            return oppositeMap.get(Objects.requireNonNull(color));
        }
    }

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int N;  //number of nodes in the subtree
        private Color color;    //color of parent key

        public Node(K key, V value, int N, Color color) {
            this.key = key;
            this.value = value;
            this.N = N;
            this.color = color;
        }

        private void flipColor() {
            assert color != null;
            color = Color.opposite(color);
        }
    }

    private Node root;  //root of the BST

    @Override
    public void put(K key, V value) {
        checkNotNull(key, value);
        root = put(root, key, value);
        root.color = BLACK; //root always need to be black
        assert invariant();
    }

    private Node put(Node root, K key, V value) {
        if (root == null)
            return new Node(key, value, 1, RED);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) root.left = put(root.left, key, value);
        if (cmp > 0) root.right = put(root.right, key, value);
        if (cmp == 0) {
            root.value = value;
            return root;
        }
        if (!isRed(root.left) && isRed(root.right))
            root = rotateLeft(root);
        if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);
        if (isRed(root.left) && isRed(root.right))
            flipColors(root);
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    private void flipColors(Node root) {
        assert root != null
                && root.left != null
                && root.right != null;
        root.flipColor();
        root.left.flipColor();
        root.right.flipColor();
    }

    private Node rotateRight(Node root) {
        Node newParent = root.left;
        root.left = newParent.right;
        newParent.right = root;
        newParent.color = root.color;
        root.color = RED; //lean to right
        newParent.N = root.N;
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    private Node rotateLeft(Node root) {
        Node newParent = root.right;
        root.right = newParent.left;
        newParent.left = root;
        newParent.color = root.color;
        root.color = RED;   //lean to left
        newParent.N = root.N;
        root.N = size(root.left) + size(root.right) + 1;
        return newParent;
    }

    /**
     * is given {@code root} red?
     *
     * @param root
     * @return
     */
    private boolean isRed(Node root) {
        if (root == null) return false;
        return RED == root.color;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        if (isEmpty())
            return null;
        Node node = get(root, key);
        return node == null? null: node.value;
    }

    /**
     * value associated with the given {@code key} in subtree rooted at {@code root}
     *
     * @param root
     * @param key
     * @return
     */
    private Node get(Node root, K key) {
        assert key != null;
        while (root != null) {
            int cmp = key.compareTo(root.key);
            if (cmp < 0) root = root.left;
            if (cmp > 0) root = root.right;
            return root;
        }
        return null;
    }

    @Override
    public void remove(K key) {

    }

    /**
     * Removes the smallest key-value pair from the symbol table
     */
    @Override
    public void removeMin() {
        if (isEmpty())
            throw new NoSuchElementException();
        //if both children of root are black, set root to red (make 4-node)
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty())
            root.color = BLACK;
    }

    private Node deleteMin(Node root) {
        if (root.left == null) return null;
        //both left and sibling children are 2-node
        if (!isRed(root.left) && !isRed(root.left.left))
            root = moveRedLeft(root);
        root.left = deleteMin(root.left);
        return balance(root);
    }

    /**
     * restore red-black invariant
     * @param root
     * @return
     */
    private Node balance(Node root) {
        assert root != null;
        if (isRed(root.right))
            root = rotateLeft(root);
        if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);
        if (isRed(root.left) && isRed(root.right))
            flipColors(root);
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    /**
     * Assuming that root is red and both root.left and root.left.left are black
     * make root.left or one of its children red.
     * @param root
     * @return
     */
    private Node moveRedLeft(Node root) {
        assert root != null;
        assert isRed(root) && !isRed(root.left) && !isRed(root.left.left);
        flipColors(root);
        //smallest key in next sibling
        if (isRed(root.right.left)) {
            root.right = rotateRight(root.right);
            root = rotateLeft(root);
            flipColors(root);
        }
        return root;
    }

    @Override
    public void removeMax() {

    }

    /**
     * the number of key-value pairs in the symbol table
     * @return
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * number of nodes in subtree rooted at {@code root}
     * @param root
     * @return
     */
    private int size(Node root) {
        if (root == null) return 0;
        return root.N;
    }

    @Override
    public int size(K low, K high) {
        return 0;
    }

    @Override
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public K min() {
        if (isEmpty())
            return null;
        return min(root).key;
    }

    private Node min(Node root) {
        assert root != null;
        if (root.left == null)
            return root;
        return min(root.left);
    }

    @Override
    public K max() {
        if (isEmpty())
            return null;
        return max(root).key;
    }

    private Node max(Node root) {
        assert root != null;
        if (root.right == null)
            return null;
        return max(root.right);
    }

    @Override
    public int rank(K key) {
        checkNotNull(key);
        return rank(root, key);
    }

    private int rank(Node root, K key) {
        if (root == null) return 0;
        int t = size(root.left);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) return rank(root.left, key);
        if (cmp > 0) return t + rank(root.right, key) + 1;
        return t;
    }

    @Override
    public K rankOf(int k) {
        checkElementIndex(k, size());
        Node node = rankOf(root, k);
        assert node != null;
        return node.key;
    }

    private Node rankOf(Node root, int k) {
        if (root == null) return null;
        int t = size(root.left);
        if (k < t) return rankOf(root.left, k);
        if (k > t) return rankOf(root.right, k-t-1);
        else return root;
    }

    @Override
    public K ceiling(K key) {
        checkNotNull(key);
        Node node = ceiling(root, key);
        return node == null? null: node.key;
    }

    private Node ceiling(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            Node node = ceiling(root.left, key);
            if (node != null)
                root = node;
        }
        if (cmp > 0) return ceiling(root.right, key);
        return root;
    }

    @Override
    public K floor(K key) {
        checkNotNull(key);
        Node node = floor(root, key);
        return node == null? null: node.key;
    }

    private Node floor(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) return floor(root.left, key);
        if (cmp > 0) {
            Node node = floor(root.right, key);
            if (node != null)
                root = node;
        }
        return root;
    }

    /**
     * returns all keys in the symbol table in the given [low..high]
     *
     * @param low
     * @param high
     * @return
     */
    @Override
    public Iterable<K> keys(K low, K high) {
        Queue<K> q = new LinkedQueue<>();
        inOrder(root, low, high, q);
        return q;
    }

    private void inOrder(Node root, K low, K high, Queue<K> q) {
        if (root == null) return;
        int cmpl = low.compareTo(root.key);
        int cmph = high.compareTo(root.key);
        if (cmpl < 0) inOrder(root, low, high, q);
        if (cmpl <= 0 && cmpl >= 0)
            q.enqueue(root.key);
        if (cmph > 0) inOrder(root, low, high, q);
    }

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }

    /*
     * =============================== TEST METHOD ==========================
     */

    /**
     * test red-black tree invariants
     * @return
     */
    private boolean invariant() {
        return isBST() && isSizeConsistent();
    }

    /**
     * are all sizes for subtrees correct?
     * @return
     */
    private boolean isSizeConsistent() {
        if (isEmpty())
            return true;
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node root) {
        if (root == null) return true;
        if (root.N != size(root.left) + size(root.right) + 1)
            return false;
        return isSizeConsistent(root.left) && isSizeConsistent(root.right);
    }

    /**
     * does bst has symmetric order?
     *
     * @return
     */
    private boolean isBST() {
        if (isEmpty())
            return true;
        return isBST(root, null, null);
    }

    /**
     * is the given root's key satisfied with two keys between {@code min} and {@code max}
     *
     * @param root
     * @param min
     * @param max
     * @return
     */
    private boolean isBST(Node root, K min, K max) {
        if (root == null) return true;
        if (min != null && min.compareTo(root.key) >= 0) return false;
        if (max != null && max.compareTo(root.key) <= 0) return false;
        return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
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
