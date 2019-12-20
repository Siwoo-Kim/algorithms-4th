package collection;

/**
 * The {@code Edge} class represents a edge in an {@link Graph}.
 * Each edge consists of two vertices and might have a value weight.
 *
 * @param <E>
 */
public interface Edge<E> extends Comparable<Edge<E>>{


    /**
     * Returns the weight of this edge.
     *
     * @return
     */
    double weight();

    /**
     * Returns either endpoint of this edge.
     * @return
     */
    E either();

    /**
     * Returns the endpoint of this edge that is different from the given {@code v}.
     *
     * @param v
     * @return
     * @throws IllegalArgumentException if the vertex is not one of the endpoints of the edge.
     */
    E other(E v);

    /**
     * Returns revered the endpoint of the edge.
     *
     * @return
     */
    Edge<E> reverse();

}
