package collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class DepthFirstSearchCC<E> implements ConnectedComponent<E> {

    private final SymbolTable<E, Integer> ids;
    private final int count;

    public DepthFirstSearchCC(Graph<E> G) {
        ids = new SeparateChainingHashST<>();
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        int count = 0;
        for (E v: G.vertices()) {
            if (!visited.contains(v)) {
                dfs(v, count, G, visited);
                count++;
            }
        }
        this.count = count;
    }

    private void dfs(E v,
                     final int count,
                     Graph<E> G,
                     SymbolTable<E, Object> visited) {
        visited.put(v, new Object());
        ids.put(v, count);
        for (E adj: G.adjacent(v)) {
            if (!visited.contains(adj))
                dfs(adj, count, G, visited);
        }
    }

    /**
     * returns the component id of the connected component containing vertex {@code v}.
     * If the given vertex {@code v} doesn't belong to anywhere returns <em>-1</em>
     *
     * @param v
     * @return
     * @throws NullPointerException if {@code v} is null.
     */
    @Override
    public int id(E v) {
        checkNotNull(v);
        if (!ids.contains(v))
            return -1;
        return ids.get(v);
    }

    @Override
    public boolean connected(E v, E w) {
        checkNotNull(v, w);
        return id(v) == id(w);
    }

    @Override
    public int count() {
        return count;
    }
}
