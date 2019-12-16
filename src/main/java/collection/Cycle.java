package collection;

/**
 * The {@code Cycle} interface represents a data type for
 * determining whether an graph has a simple cycle.
 *
 * @param <E>
 */
public interface Cycle<E> {

    /**
     * returns true if the graph has a cycle.
     *
     * @return
     */
    boolean hasCycle();

    /**
     * returns a cycle in the graph.
     *
     * @return
     */
    Iterable<E> cycle();
}
