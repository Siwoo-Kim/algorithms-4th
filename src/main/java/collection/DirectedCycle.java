package collection;

import app.AppIn;

/**
 * The {@code Cycle} interface represents a data type for
 * determining whether an graph has a simple cycle.
 *
 * @param <E>
 */
public class DirectedCycle<E> implements Cycle<E> {

    private static final Object NULL = new Object();  //path root
    private static final Object O = NULL;   //dummy
    private SymbolTable<E, Object> pathTo;
    private Stack<E> cycle;

    public DirectedCycle(Digraph<E> G) {
        pathTo = new SeparateChainingHashST<>();
        SymbolTable<E, Object> onStack = new SeparateChainingHashST<>();
        SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
        for (E v: G.vertices()) {
            if (!visited.contains(v) && cycle == null) {
                pathTo.put(v, NULL); //set root
                dfs(v, onStack, visited, G);
            }
        }
    }

    private void dfs(E v, SymbolTable<E, Object> onStack,
                     SymbolTable<E, Object> visited,
                     Digraph<E> G) {
        onStack.put(v, O);
        visited.put(v, O);
        for (E adj: G.adjacent(v)) {
            if (cycle != null)
                return;
            else if (!visited.contains(adj)) {
                pathTo.put(adj, v);
                dfs(adj, onStack, visited, G);
            } else if (onStack.contains(adj)) {
                cycle = new LinkedStack<>();
                Object path = v;
                while (path != adj) {
                    cycle.push((E) path);
                    path = pathTo.get((E) path);
                }
                cycle.push(adj);
                cycle.push(v);
            }
        }
        onStack.remove(v);
    }

    @Override
    public boolean hasCycle() {
        return cycle != null;
    }

    @Override
    public Iterable<E> cycle() {
        if (!hasCycle())
            return new LinkedQueue<>();
        Queue<E> q = new LinkedQueue<>();
        for (E v: cycle)
            q.enqueue(v);
        return q;
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

        Cycle<Integer> cycle = new DirectedCycle(G);
        System.out.println(cycle.cycle());
    }
}
