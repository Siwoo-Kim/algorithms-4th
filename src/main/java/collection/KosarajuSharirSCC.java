package collection;

import app.AppIn;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code KosarajuSharirSCC} class represents a data type for
 * determining the strong componnets in a digraph.
 *
 *
 */
class KosarajuSharirSCC<E> implements ConnectedComponent<E> {

    private static final Object O = new Object(); //dummy obj
    private int count;
    private SymbolTable<E, Integer> scc;

    public KosarajuSharirSCC(Digraph<E> G) {
        checkNotNull(G);
        scc = new SeparateChainingHashST<>();
        Stack<E> ordered = reversedPostOrder(G.reverse());
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        for (E v: ordered) {
            if (!visited.contains(v)) {
                dfs(v, visited, G);
                count++;
            }
        }
    }

    private void dfs(E v, SymbolTable<E, Object> visited, Digraph<E> G) {
        visited.put(v, O);
        scc.put(v, count);
        for (E adj: G.adjacent(v))
            if (!visited.contains(adj))
                dfs(adj, visited, G);
    }

    private Stack<E> reversedPostOrder(Digraph<E> G) {
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        Stack<E> stack = new LinkedStack<>();
        for (E v: G.vertices())
            if (!visited.contains(v))
                dfs(v, visited, stack, G);
        return stack;
    }

    private void dfs(E v,
                     SymbolTable<E, Object> visited,
                     Stack<E> stack,
                     Digraph<E> G) {
        checkNotNull(v);
        visited.put(v, O);
        for (E adj: G.adjacent(v)) {
            if (!visited.contains(adj))
                dfs(adj, visited, stack, G);
        }
        stack.push(v);
    }

    @Override
    public int id(E v) {
        checkNotNull(v);
        if (!scc.contains(v))
            return -1;
        return scc.get(v);
    }

    @Override
    public boolean connected(E v, E w) {
        return id(v) == id(w);
    }

    @Override
    public int count() {
        return count;
    }

    /**
     * ======================== TEST METHOD ======================
     */
    private static final String TINY_DG_TXT = "tinyDG.txt";

    public static void main(String[] args) {
        Integer[] ints = AppIn.getInstance().readAllInts(TINY_DG_TXT);
        Digraph<Integer> G = new DirectedGraph<>();
        for (int i = 2; i < ints.length; )
            G.addEdge(ints[i++], ints[i++]);

        ConnectedComponent<Integer> cc = new KosarajuSharirSCC<>(G);
        for (int v: G.vertices())
            System.out.println(v + " " + cc.id(v));
    }
}
