package collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class BreadthFirstPaths<E> implements Paths<E> {

    private final Object NULL = new Object();
    private final SymbolTable<E, Object> pathTo;
    private final E source;

    public BreadthFirstPaths(E source, Graph<E> G) {
        this.source = source;
        pathTo = new SeparateChainingHashST<>();
        pathTo.put(source, NULL);
        bfs(source, G);
    }

    private void bfs(E source, Graph<E> G) {
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        Queue<E> q = new LinkedQueue<>();
        visited.put(source, new Object());
        q.enqueue(source);
        while (!q.isEmpty()) {
            E vertex = q.dequeue();
            for (E adj: G.adjacent(vertex)) {
                if (!visited.contains(adj)) {
                    visited.put(adj, new Object());
                    q.enqueue(adj);
                    pathTo.put(adj, vertex);
                }
            }
        }
    }

    @Override
    public boolean hasPathTo(E v) {
        checkNotNull(v);
        return pathTo.contains(v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<E> pathTo(E v) {
        checkNotNull(v);
        if (!hasPathTo(v))
            return new LinkedQueue<>();
        Queue<E> q = new LinkedQueue<>();
        Object path = v;
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
