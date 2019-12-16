package collection;

/**
 * The {@code ConnectedComponent} interface represents a data type for
 * determining the connected components in a graph.
 *
 * The {@link #id(Object)} operation determines in which connected component a given vertex lies.
 * The {@link #connected(Object, Object)} operation determines whether two vertices are in the
 * same component.
 *
 * @param <E>
 */
public interface ConnectedComponent<E> {


    /**
     * returns the component id of connected component
     * which containing vertex {@code v}
     *
     * @param v
     * @return
     */
    int id(E v);

    /**
     * returns true if vertices {@code v} and {@code w} are in the same connected
     * component.
     *
     * @param v
     * @param w
     * @return
     */
    boolean connected(E v, E w);

    /**
     * returns the number of connected components in the graph.
     *
     * @return
     */
    int count();
}
