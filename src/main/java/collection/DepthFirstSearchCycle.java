package collection;

public class DepthFirstSearchCycle<E> implements Cycle<E> {

    private Stack<E> cycle;
    private SymbolTable<E, Object> pathTo;
    private final Object NULL = new Object();
    private boolean hasCycle;

    public DepthFirstSearchCycle(Graph<E> G) {
        if (hasSelfLoop(G)) return;
        if (hasParallelEdges(G)) return;
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        pathTo = new SeparateChainingHashST<>();
        for (E v: G.vertices()) {
            if (!visited.contains(v)) {
                pathTo.put(v, NULL);
                dfs(v, null, visited, G);
            }
        }
    }

    private boolean hasParallelEdges(Graph<E> G) {
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        for (E v: G.vertices()) {
            for (E w: G.adjacent(v)) {
                if (visited.contains(w)) {
                    cycle = new LinkedStack<>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                visited.put(w, new Object());
            }
            for (E w: G.adjacent(v)) {
                visited.remove(w);
            }
        }
        return false;
    }

    boolean hasSelfLoop(Graph<E> G) {
        for (E v: G.vertices())
            for (E adj: G.adjacent(v))
                if (v.equals(adj)) {
                    cycle = new LinkedStack<>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
        return false;
    }

    private void dfs(E v, E w, SymbolTable<E, Object> visited, Graph<E> G) {
         visited.put(v, new Object());
         for (E adj: G.adjacent(v)) {
             if (cycle != null)
                 return;
             if (!visited.contains(adj)) {
                 pathTo.put(adj, v);
                 dfs(adj, v, visited, G);
             }
             else if (!v.equals(w)) {
                 cycle = new LinkedStack<>();
                 Object path = v;
                 while (path != NULL) {
                     cycle.push((E) path);
                     path = pathTo.get((E) path);
                 }
                 cycle.push(w);
                 cycle.push(v);
             }
         }
    }

    @Override
    public boolean hasCycle() {
        return cycle != null;
    }

    @Override
    public Iterable<E> cycle() {
        return cycle;
    }
}
