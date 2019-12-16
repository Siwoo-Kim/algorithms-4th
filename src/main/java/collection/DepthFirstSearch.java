package collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *  The {@code DepthFirstSearch} class represents an graph of vertices {@code E}
 *  It supports <em>add an edges</em>, <em>iterate over over all of the vertices adjacent to a vertex</em>
 *
 * @param <E>
 */
public class DepthFirstSearch<E> implements Search<E> {

    private final SymbolTable<E, Object> visited;   //connected vertices
    private final E source; // source vertex

    public DepthFirstSearch(E source, Graph<E> G) {
        checkNotNull(source, G);
        this.source = source;
        visited = new SeparateChainingHashST<>();
        dfs(G, source);
    }

    private void dfs(Graph<E> G, E source) {
        checkNotNull(G, source);
        visited.put(source, new Object());
        for (E adj: G.adjacent(source))
            if (!visited.contains(adj))
                dfs(G, adj);
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
}
