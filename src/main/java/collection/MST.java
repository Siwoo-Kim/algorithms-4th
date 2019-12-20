package collection;

/**
 * The class {@code LazyPrim} represents MST of the given {@link EdgeWeightedGraph}.
 * It supports the {@link #edges()} to iterate over all edges in the MST
 * and provide the total weights of the MST.
 *
 * @param <E>
 */
public interface MST<E> {

    /**
     * Returns all edges in the MST
     *
     * @return
     */
    Iterable<Edge<E>> edges();

    /**
     * Returns total weights for the MST
     *
     * @return
     */
    double weight();

}
