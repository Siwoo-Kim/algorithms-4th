package collection;

import app.AppIn;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@inheritDoc}
 */
public class PrimMST<E> implements MST<E> {

    private static final Object O = new Object(); //dummy obj
    private final double weight;
    private SymbolTable<E, Object> vertices;    // all vertices in the MST
    private SymbolTable<E, Edge<E>> edges;     // least edges to E
    private SortedSymbolTable<Node, Object> minPQ;

    private class Node implements Comparable<Node> {
        private final E vertex;
        private final double weight;

        public Node(E vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(weight, o.weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(vertex, node.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex);
        }
    }

    public PrimMST(EdgeWeightedGraph<E> G) {
        checkNotNull(G);
        checkArgument(G.sizeOfVertices() > 0);
        vertices = new SeparateChainingHashST<>();
        edges = new SeparateChainingHashST<>();
        minPQ = new BinarySearchTree<>();
        E vertex = G.vertices().iterator().next();
        visit(vertex, G);
        while (!minPQ.isEmpty()) {
            Node node = minPQ.min();    //least crossing edges
            minPQ.removeMin();
            visit(node.vertex, G);
        }
        double weight = 0.0;
        for (E v: vertices.keys())
            if (edges.get(v) != null)
                weight += edges.get(v).weight();
        this.weight = weight;
    }

    private void visit(E vertex, EdgeWeightedGraph<E> G) {
        vertices.put(vertex, O);
        for (Edge<E> edge: G.incidentTo(vertex)) {
            E w = edge.other(vertex);
            if (vertices.contains(w))
                continue;
            //update least edge if find
            double weight = edge.weight();
            if (edges.get(w) == null||
                    edges.get(w).weight() > weight) {
                edges.put(w, edge);
                minPQ.put(new Node(w, weight), O);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Edge<E>> edges() {
        Queue<Edge<E>> edge = new LinkedQueue<>();
        for (E v: vertices.keys())
            if (edges.contains(v))
                edge.enqueue(edges.get(v));
        return edge;
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
        MST<Integer> minimumSpanningTree = new PrimMST<>(G);
        for (Edge<Integer> edge: minimumSpanningTree.edges())
            System.out.println(edge);
        System.out.printf("%.5f\n", minimumSpanningTree.weight());
    }
}
