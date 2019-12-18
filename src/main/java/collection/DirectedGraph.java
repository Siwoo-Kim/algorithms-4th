package collection;


import app.AppIn;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code Digraph} represents a directed graph of vertices for generic elements.
 *
 * It supports the following two primary operations: add an edge to the digraph, iterate over
 * all of the vertices  adjacent from a given vertex.
 *
 * @param <E>
 */
public class DirectedGraph<E> implements Digraph<E> {

    private SymbolTable<E, Bag<E>> adjMap;
    private int V;
    private int E;

    public DirectedGraph() {
        this(new LinkedBag<>());
    }

    public DirectedGraph(Iterable<E[]> edges) {
        checkNotNull(edges);
        adjMap = new SeparateChainingHashST<>();
        for (E[] edge: edges) {
            checkArgument(edge != null
                    && edge.length == 2);
            E v = edge[0], w = edge[1];
            addEdge(v, w);
        }
    }

    @Override
    public Digraph<E> reverse() {
        Digraph<E> DG = new DirectedGraph<>();
        for (E v: vertices())
            for (E adj: adjacent(v))
                DG.addEdge(adj, v);
        return DG;
    }

    @Override
    public int sizeOfVertices() {
        return V;
    }

    @Override
    public int sizeOfEdges() {
        return E;
    }

    @Override
    public boolean connected(E v, E w) {
        checkNotNull(v, w);
        return new DepthFirstSearch<>(v, this).connected(w);
    }

    @Override
    public Iterable<E> path(E v, E w) {
        checkNotNull(v, w);
        return new BreadthFirstPaths<>(v, this).pathTo(w);
    }

    @Override
    public int idOfConnectedComponent(E v) {
        ConnectedComponent<E> cc = new KosarajuSharirSCC<>(this);
        return cc.id(v);
    }

    @Override
    public int sizeOfConnectedComponents() {
        ConnectedComponent<E> cc = new KosarajuSharirSCC<>(this);
        return cc.count();
    }

    @Override
    public int sizeOfConnection(E v) {
        return new DepthFirstSearch<>(v, this).count();
    }

    @Override
    public boolean isAcyclic() {
        return !(new DirectedCycle<>(this).hasCycle());
    }

    @Override
    public void addEdge(E v, E w) {
        checkNotNull(v, w);
        putIfAbsent(v);
        putIfAbsent(w);
        adjMap.get(v).add(w);
        E++;
    }

    private void putIfAbsent(E v) {
        checkNotNull(v);
        if (!adjMap.contains(v)) {
            adjMap.put(v, new LinkedBag<>());
            V++;
        }
    }

    @Override
    public Iterable<E> adjacent(E v) {
        checkNotNull(v);
        if (!adjMap.contains(v))
            return new LinkedBag<>();
        else
            return adjMap.get(v);
    }

    @Override
    public Iterable<E> vertices() {
        return adjMap.keys();
    }

    @Override
    public String toString() {
        if (adjMap.isEmpty())
            return "(empty)";
        StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices, ")
                .append(E).append(" edges.\n");
        for (E v: vertices()) {
            sb.append(v).append(": ");
            for (E w: adjacent(v))
                sb.append(w).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * ===================== TEST METHOD ======================
     */

    private static final String TINY_DG_TXT = "tinyDG.txt";

    public static void main(String[] args) {
        Integer[] ints = AppIn.getInstance().readAllInts(TINY_DG_TXT);
        Digraph<Integer> G = new DirectedGraph<>();
        int i = 0;
        int V = ints[i++];
        int E = ints[i++];
        while (i < ints.length) {
            G.addEdge(ints[i++], ints[i++]);
        }
        System.out.println(G);
    }
}
