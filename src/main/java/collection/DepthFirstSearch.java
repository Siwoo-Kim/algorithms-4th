package collection;

import app.AppIn;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *  The {@code DepthFirstSearch} class represents an graph of vertices {@code E}
 *  It supports <em>add an edges</em>, <em>iterate over over all of the vertices adjacent to a vertex</em>
 *
 * @param <E>
 */
class DepthFirstSearch<E> implements Search<E> {

    private static final Object O = new Object(); // dummy object
    private final SymbolTable<E, Object> visited;
    private final E source;

    public DepthFirstSearch(E source, Graph<E> G) {
        checkNotNull(source, G);
        visited = new SeparateChainingHashST<>();
        this.source = source;
        dfs(source, G);
    }

    private void dfs(E source, Graph<E> G) {
        checkNotNull(source);
        visited.put(source, O);
        for (E adj: G.adjacent(source))
            if (!visited.contains(adj))
                dfs(adj, G);
    }

    @Override
    public boolean connected(E v) {
        checkNotNull(v);
        return visited.contains(v);
    }

    @Override
    public int count() {
        return visited.size();
    }

    @Override
    public E source() {
        return source;
    }

    /**
     * ======================== TEST METHOD ======================
     */
    private static final String TINY_DG_TXT = "tinyDG.txt";

    public static void main(String[] args) {
        Integer[] ints = AppIn.getInstance().readAllInts(TINY_DG_TXT);
        Digraph<Integer> G = new DirectedGraph<>();
        for (int i=2; i<ints.length;)
            G.addEdge(ints[i++], ints[i++]);

        DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(0, G);
        for (Integer v: G.vertices())
            if (dfs.connected(v))
                System.out.print(v + " ");
        System.out.println();
        dfs = new DepthFirstSearch<>(9, G);
        for (Integer v: G.vertices())
            if (dfs.connected(v))
                System.out.print(v + " ");
    }
}
