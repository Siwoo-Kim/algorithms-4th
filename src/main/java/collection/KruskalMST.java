package collection;

import app.AppIn;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@inheritDoc}
 */
public class KruskalMST implements MST<Integer> {

    private Queue<Edge<Integer>> q;
    private final double weight;

    public KruskalMST(EdgeWeightedGraph<Integer> G) {
        checkNotNull(G);
        checkArgument(G.sizeOfVertices() > 0);
        int V = G.sizeOfVertices();
        q = new LinkedQueue<>();
        UF UF = new QuickUnionUF(G.sizeOfVertices());
        PriorityQueue<Edge<Integer>> minPQ = new BinaryHeap<>(Comparator.reverseOrder());
        for (Edge<Integer> edge: G.edges())
            minPQ.enqueue(edge);
        double weight = 0.0;
        while (!minPQ.isEmpty() && q.size() < V) {
            Edge<Integer> edge = minPQ.dequeue();
            int v = edge.either(), w = edge.other(v);
            if (UF.connected(v, w))
                continue;
            weight += edge.weight();
            UF.union(v, w);
            q.enqueue(edge);
        }
        this.weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Edge<Integer>> edges() {
        return q;
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
        MST<Integer> minimumSpanningTree = new KruskalMST(G);
        for (Edge<Integer> edge: minimumSpanningTree.edges())
            System.out.println(edge);
        System.out.printf("%.5f\n", minimumSpanningTree.weight());
    }
}
