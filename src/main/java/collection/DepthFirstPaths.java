package collection;

public class DepthFirstPaths<E> implements Paths<E> {
    private SymbolTable<E, Object> pathTo;
    private final Object NULL = new Object();
    private E source;

    public DepthFirstPaths(E source, Graph<E> G) {
        this.source = source;
        pathTo = new SeparateChainingHashST<>();
        pathTo.put(source, NULL);
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        dfs(G, source, visited);
    }

    private void dfs(Graph<E> G, E source, SymbolTable<E, Object> visited) {
        visited.put(source, new Object());
        for (E adj: G.adjacent(source)) {
            if (!visited.contains(adj)) {
                pathTo.put(adj, source);
                dfs(G, adj, visited);
            }
        }
    }

    @Override
    public boolean hasPathTo(E v) {
        return pathTo.contains(v);
    }

    @Override
    public Iterable<E> pathTo(E v) {
        if (!hasPathTo(v))
            return new LinkedQueue<>();
        Object path = v;
        Queue<E> q = new LinkedQueue<>();
        while (path != NULL) {
            q.enqueue((E) path);
            path = pathTo.get((E) path);
        }
        return q;
    }

    @Override
    public E source() {
        return source;
    }
}
