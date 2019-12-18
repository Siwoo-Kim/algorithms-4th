package collection;

import app.AppIn;
import sun.dc.pr.PRError;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code Paths} interface represents a data type for finding paths
 * from a given source {@link Paths#source()} to every other vertex in an graph.
 *
 */
 class BreadthFirstPaths<E> implements Paths<E> {

    private static final Object NULL = new Object();    //root path
    private final SymbolTable<E, Object> pathTo;
    private final E source;

    BreadthFirstPaths(E source, Graph<E> G) {
        checkNotNull(source, G);
        pathTo = new SeparateChainingHashST<>();
        pathTo.put(source, NULL); // set root path
        this.source = source;
        bfs(source, G);
    }

    private void bfs(E source, Graph<E> G) {
        Queue<E> q = new LinkedQueue<>();
        q.enqueue(source);
        while (!q.isEmpty()) {
            E v = q.dequeue();
            for (E adj: G.adjacent(v)) {
                if (!pathTo.contains(adj)) {
                    q.enqueue(adj);
                    pathTo.put(adj, v);
                }
            }
        }
    }


    @Override
    public boolean hasPathTo(E v) {
        checkNotNull(v);
        return pathTo.contains(v);

    }

    @Override
    public Iterable<E> pathTo(E v) {
        checkNotNull(v);
        if (!hasPathTo(v))
            return new LinkedStack<>();
        Stack<E> stack = new LinkedStack<>();
        Object path = v;
        while (path != NULL) {
            stack.push((E) path);
            path = pathTo.get((E) path);
        }
        return stack;

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

        Paths<Integer> bfs;
        for (Integer v: G.vertices()) {
            bfs = new BreadthFirstPaths<>(v, G);
            for (Integer w : G.vertices()) {
                if (bfs.hasPathTo(w)) {
                    System.out.println(bfs.pathTo(w));
                }
            }
        }
    }
}
