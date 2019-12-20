package collection;

/**
 * The {@code EdgeWeightedGraph} class represents an edge-weighted graph of vertices
 * for generic items, where each undirected edge if of type {@link WeightedEdge} and has a weight.
 *
 * @param <E>
 */
public interface EdgeWeightedGraph<E> extends Graph<E> {

    /**
     * Add the undirected edge {@code edge} to the edge-weighted graph.
     *
     * @param edge
     */
    void addEdge(Edge<E> edge);

    /**
     * Returns all edges in the edge-weighted graph.
     *
     * @return
     */
    Iterable<Edge<E>> edges();

    /**
     * Returns edge incident to given {@code v}
     *
     * @param v
     * @return
     */
    Iterable<Edge<E>> incidentTo(E v);

}
