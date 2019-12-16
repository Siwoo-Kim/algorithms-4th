package collection;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

public class TreeSet<E extends Comparable<? super E>> implements Set<E> {

    private TreeMap<E, Object> map;

    private static class TreeMap<K extends Comparable<? super K>, V> implements SortedSymbolTable<K, V> {

        private enum Color {
            RED, BLACK;
        }

        static class Node<K, V> {
            private K key;
            private V value;
            private int N;
            private Color color;
            private Node<K, V> left, right;

            public Node(K key, V value, int N, Color color) {
                this.key = key;
                this.value = value;
                this.N = N;
                this.color = color;
            }

            void flipColor() {
                color = color == Color.BLACK? Color.RED: Color.BLACK;
            }
        }

        private Node<K, V> root;

        Node<K, V> rotateLeft(Node<K, V> node) {
            Node<K, V> p = node.right;
            node.right = p.left;
            p.left = node;
            p.color = node.color;
            node.color = Color.RED;
            p.N = node.N;
            node.N = size(node.left) + size(root.right) + 1;
            return p;
        }

        Node<K, V> rotateRight(Node<K, V> node) {
            Node<K, V> p = node.left;
            node.left = p.right;
            p.right = node;
            p.color = node.color;
            node.color = Color.RED;
            p.N = node.N;
            node.N = size(node.left) + size(node.right) + 1;
            return p;
        }

        private int size(Node<K, V> node) {
            if (node == null) return 0;
            return node.N;
        }

        @Override
        public K min() {
            assert root != null;
            return min(root).key;
        }

        private Node<K, V> min(Node<K, V> root) {
            if (root.left == null)
                return root;
            return min(root.left);
        }

        @Override
        public K max() {
            assert root != null;
            return max(root).key;
        }

        private Node<K, V> max(Node<K, V> root) {
            if (root.right == null)
                return root;
            return max(root.right);
        }

        @Override
        public int rank(K key) {
            assert key != null;
            return rank(root, key);
        }

        private int rank(Node<K, V> root, K key) {
            if (root == null) return 0;
            int t = size(root.left);
            int cmp = key.compareTo(root.key);
            if (cmp < 0) return rank(root.left, key);
            if (cmp > 0) return t + rank(root.right, key) + 1;
            else return t;
        }

        @Override
        public K rankOf(int rank) {
            assert root != null;
            assert rank >= 0 && rank < size();
            return rankOf(root, rank);
        }

        private K rankOf(Node<K, V> root, int rank) {
            if (root == null) return null;
            int t = size(root.left);
            if (t < rank) return rankOf(root.left, rank-t-1);
            if (t > rank) return rankOf(root.right, rank);
            else return root.key;
        }

        @Override
        public K ceiling(K key) {
            assert root != null;
            assert key != null;
            return ceiling(root, key).key;
        }

        private Node<K, V> ceiling(Node<K, V> root, K key) {
            if (root == null) return null;
            int cmp = key.compareTo(root.key);
            if (cmp < 0) {
                Node node = ceiling(root.left, key);
                if (node != null)
                    return node;
            }
            if (cmp > 0) return ceiling(root.right, key);
            else return root;
        }

        @Override
        public K floor(K key) {
            assert root != null;
            assert key != null;
            return floor(root, key).key;
        }

        private Node<K, V> floor(Node<K, V> root, K key) {
            if (root == null) return null;
            int cmp = key.compareTo(root.key);
            if (cmp < 0) return floor(root.left, key);
            if (cmp > 0) {
                Node<K,V> node = floor(root.right, key);
                if (node != null)
                    return node;
            }
            return root;
        }

        @Override
        public Iterable<K> keys(K low, K high) {
            return null;
        }

        @Override
        public void put(K key, V value) {
            checkNotNull(key);
            root = put(root, key, value);
            root.color = Color.BLACK;
        }

        private Node<K, V> put(Node<K, V> root, K key, V value) {
            if (root == null)
                return new Node<>(key, value, 1, Color.RED);
            int cmp = key.compareTo(root.key);
            if (cmp < 0) root.left = put(root.left, key, value);
            if (cmp > 0) root.right = put(root.right, key, value);
            else {
                root.value = value;
                return root;
            }
            root.N = size(root.left) + size(root.right) + 1;
            if (!isRed(root.left) && isRed(root.right))
                root = rotateLeft(root);
            if (isRed(root.left) && isRed(root.left.left))
                root = rotateRight(root);
            if (isRed(root.left) && isRed(root.right))
                flipColors(root);
            return root;
        }

        private void flipColors(Node<K, V> root) {
            assert root != null
                    && root.left != null
                    && root.right != null;
            root.flipColor();
            root.left.flipColor();
            root.right.flipColor();
        }

        private boolean isRed(Node<K, V> root) {
            if (root == null) return false;
            return Color.RED == root.color;
        }

        @Override
        public V get(K key) {
            checkNotNull(key);
            Node<K, V> node = get(root, key);
            return node == null? null: node.value;
        }

        private Node<K, V> get(Node<K, V> root, K key) {
            if (root == null) return null;
            int cmp = key.compareTo(root.key);
            if (cmp < 0) return get(root.left, key);
            if (cmp > 0) return get(root.right, key);
            return root;
        }

        @Override
        public void remove(K key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<K> iterator() {
            return null;
        }
    }

    public TreeSet() {
        this.map = new TreeMap<>();
    }

    @Override
    public void add(E e) {
        checkNotNull(e);
        map.put(e, new Object());
    }

    @Override
    public void remove(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);
        return map.contains(e);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    public E ceiling(E e) {
        checkNotNull(e);
        return map.ceiling(e);
    }

    public E floor(E e) {
        checkNotNull(e);
        return map.floor(e);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>();

        set.add("www.cs.princeton.edu");
        set.add("www.cs.princeton.edu");    // overwrite old value
        set.add("www.princeton.edu");
        set.add("www.math.princeton.edu");
        set.add("www.yale.edu");
        set.add("www.amazon.com");
        set.add("www.simpsons.com");
        set.add("www.stanford.edu");
        set.add("www.google.com");
        set.add("www.ibm.com");
        set.add("www.apple.com");
        set.add("www.slashdot.com");
        set.add("www.whitehouse.gov");
        set.add("www.espn.com");
        set.add("www.snopes.com");
        set.add("www.movies.com");
        set.add("www.cnn.com");
        set.add("www.iitb.ac.in");

        System.out.println(set.contains("www.cs.princeton.edu"));
        System.out.println(!set.contains("www.harvardsucks.com"));
        System.out.println(set.contains("www.simpsons.com"));
        System.out.println();

        System.out.println("ceiling(www.simpsonr.com) = " + set.ceiling("www.simpsonr.com"));
        System.out.println("ceiling(www.simpsons.com) = " + set.ceiling("www.simpsons.com"));
        System.out.println("ceiling(www.simpsont.com) = " + set.ceiling("www.simpsont.com"));
        System.out.println("floor(www.simpsonr.com)   = " + set.floor("www.simpsonr.com"));
        System.out.println("floor(www.simpsons.com)   = " + set.floor("www.simpsons.com"));
        System.out.println("floor(www.simpsont.com)   = " + set.floor("www.simpsont.com"));
        System.out.println();
    }
}
