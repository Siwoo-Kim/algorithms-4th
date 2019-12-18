package collection;

/**
 * The {@code Paths} interface represents a data type for finding paths
 * from a given source {@link Paths#source()} to every other vertex in an graph.
 *
 */
public interface Paths<E> {

    /**
     * Is there a path between the source {@link #source()}
     * and a given vertex {@code v}?
     *
     * @param v
     * @return
     */
    boolean hasPathTo(E v);

    /**
     * returns a path between the source {@link #source()}
     * and a given vertex {@code v}
     *
     * @param v
     * @return
     */
    Iterable<E> pathTo(E v);

    /**
     * returns a source vertex
     *
     * @return
     */
    E source();
}
