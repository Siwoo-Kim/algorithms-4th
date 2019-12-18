package collection;

/**
 * The {@code Digraph} represents a directed graph of vertices for generic elements.
 *
 * It supports the following two primary operations: add an edge to the digraph, iterate over
 * all of the vertices  adjacent from a given vertex.
 *
 * @param <E>
 */
public interface Digraph<E> extends Graph<E> {

    /**
     * Returns the reveres of the digraph
     *
     * @return
     */
    Digraph<E> reverse();

}
