package collection;

import app.AppIn;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class {@code LazyPrim} represents MST of the given {@link EdgeWeightedGraph}.
 * It supports the {@link #edges()} to iterate over all edges in the MST
 * and provide the total weights of the MST.
 *
 * @param <E>
 */
public class LazyPrim<E> implements MST<E> {

    private static final Object O = new Object(); // dummy obj
    private final SymbolTable<E, Object> vertices;
    private final SymbolTable<Edge<E>, Object> edges;
    private final PriorityQueue<Edge<E>> minPQ;   // least weighted crossing edge.
    private final double weight;

    public LazyPrim(EdgeWeightedGraph<E> G) {
        checkNotNull(G);
        checkArgument(G.sizeOfVertices() > 0);
        vertices = new SeparateChainingHashST<>();
        edges = new SeparateChainingHashST<>();
        minPQ = new BinaryHeap<>(Comparator.reverseOrder());
        double weight = 0.0;
        E e = G.vertices().iterator().next();
        visit(e, G);
        while (!minPQ.isEmpty()) {
            Edge<E> crossingEdge = minPQ.dequeue();
            E v = crossingEdge.either(), w = crossingEdge.other(v);
            if (vertices.contains(v) && vertices.contains(w))
                continue;
            edges.put(crossingEdge, edges);
            weight += crossingEdge.weight();
            if (vertices.contains(v))
                visit(w, G);
            else
                visit(v, G);
        }
        this.weight = weight;
    }

    private void visit(E v, EdgeWeightedGraph<E> G) {
        vertices.put(v, O);
        for (Edge<E> edge: G.incidentTo(v)) {
            E w = edge.other(v);
            if (!vertices.contains(w))
                minPQ.enqueue(edge);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double weight() {
        return weight;
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
        MST<Integer> minimumSpanningTree = new LazyPrim<>(G);
        for (Edge<Integer> edge: minimumSpanningTree.edges())
            System.out.println(edge);
        System.out.printf("%.5f\n", minimumSpanningTree.weight());
    }
}
