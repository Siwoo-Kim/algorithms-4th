package collection;

import app.AppIn;

import static com.google.common.base.Preconditions.checkNotNull;

public class EdgeWeightedGraphImpl<E> implements EdgeWeightedGraph<E> {

    private SymbolTable<E, Bag<Edge<E>>> adjMap;
    private int V;
    private int E;

    public EdgeWeightedGraphImpl() {
        adjMap = new SeparateChainingHashST<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(Edge<E> edge) {
        checkNotNull(edge);
        E v = edge.either(), w = edge.other(v);
        putIfAbsent(v);
        putIfAbsent(w);
        adjMap.get(v).add(edge);
        adjMap.get(w).add(edge);
        E++;
    }

    private void putIfAbsent(E v) {
        if (!adjMap.contains(v)) {
            adjMap.put(v, new LinkedBag<>());
            V++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Edge<E>> edges() {
        SymbolTable<Edge<E>, Object> set = new SeparateChainingHashST<>();
        Bag<Edge<E>> edges = new LinkedBag<>();
        final Object NULL = new Object();
        for (E v: vertices()) {
            boolean selfLoop = false;
            for (Edge<E> edge: adjMap.get(v)) {
                if (!selfLoop && edge.other(v).equals(v)) {
                    edges.add(edge);
                    selfLoop = true; //add only one copy of each self loop.
                }
                else if (!set.contains(edge) &&
                        !set.contains(edge.reverse())) {
                    edges.add(edge);
                    set.put(edge, NULL);
                }
            }
        }
        return edges;
    }

    @Override
    public Iterable<Edge<E>> incidentTo(E v) {
        checkNotNull(v);
        if (!contains(v))
            return new LinkedBag<>();
        return adjMap.get(v);
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
        return false;
    }

    @Override
    public Iterable<E> path(E v, E w) {
        return null;
    }

    @Override
    public int idOfConnectedComponent(E v) {
        return 0;
    }

    @Override
    public int sizeOfConnectedComponents() {
        return 0;
    }

    @Override
    public int sizeOfConnection(E v) {
        return 0;
    }

    @Override
    public boolean isAcyclic() {
        return false;
    }

    @Override
    public void addEdge(E v, E w) {
        checkNotNull(v, w);
        Edge<E> edge = new WeightedEdge<>(v, w);
        addEdge(edge);
    }

    @Override
    public Iterable<E> adjacent(E v) {
        checkNotNull(v);
        Bag<E> adjacents = new LinkedBag<>();
        for (Edge<E> edge: incidentTo(v))
            adjacents.add(edge.other(v));
        return adjacents;
    }

    @Override
    public Iterable<E> vertices() {
        if (isEmpty())
            return new LinkedBag<>();
        return adjMap.keys();
    }

    private boolean isEmpty() {
        return adjMap.isEmpty();
    }

    private boolean contains(E v) {
        checkNotNull(v);
        return adjMap.contains(v);
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "(empty)";
        StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices,")
                .append(E).append(" edges.\n");
        for (E v: vertices()) {
            sb.append(v).append(": ");
            for (Edge<E> edge: incidentTo(v))
                sb.append(edge.other(v))
                        .append("[").append(edge.weight()).append("]")
                        .append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * ======================= TEST METHOD =======================
     */
    private static final String TINYEWG_TXT = "tinyEWG.txt";

    public static void main(String[] args) {
        Double[] doubles = AppIn.getInstance().readAllDoubles(TINYEWG_TXT);
        EdgeWeightedGraph<Integer> G = new EdgeWeightedGraphImpl<>();
        for (int i=2; i<doubles.length; ) {
            int v = doubles[i++].intValue(),
                    w = doubles[i++].intValue();
            double weight = doubles[i++];
            Edge<Integer> edge = new WeightedEdge<>(v, w, weight);
            G.addEdge(edge);
        }
        System.out.println(G);
        for (Edge<Integer> edge: G.edges())
            System.out.println(edge);
    }
}
