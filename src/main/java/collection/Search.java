package collection;

/**
 * The {@code Search} interface represents a data type for
 * determining the vertices connected to a given {@code #source}.
 *
 * @param <E>
 */
public interface Search<E> {

    /**
     * is there a path between the source vertex {@code #source}
     * and given vertex {@code v}?
     *
     * @param v
     * @return
     */
    boolean connected(E v);

    /**
     * returns the number of vertices connected to the source vertex
     * {@code #source}
     *
     * @return
     */
    int count();
    /**
     * returns the source vertex.
     *
     * @return
     */
    E source();
}
