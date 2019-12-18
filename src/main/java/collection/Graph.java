package collection;

/**
 *  The {@code Graph} interface represents an graph of vertices {@code E}
 *  It supports <em>add an edges</em>, <em>iterate over over all of the vertices adjacent to a vertex</em>
 *
 * @param <E>
 */
public interface Graph<E> {

    /**
     * returns the number of vertices in the graph
     *
     * @return
     */
    int sizeOfVertices();

    /**
     * returns the number of edges in the graph
     *
     * @return
     */
    int sizeOfEdges();

    /**
     * is there a path between the vertex {@code v} and vertex {@code w}?
     *
     * @param v
     * @param w
     * @return
     */
    boolean connected(E v, E w);


    /**
     * returns a path between the source {@code v}
     * and a given vertex {@code w}
     *
     * @param v
     * @return
     */
    Iterable<E> path(E v, E w);

    /**
     * returns the component id of connected component
     * which containing vertex {@code v}
     *
     * @param v
     * @return
     */
    int idOfConnectedComponent(E v);

    /**
     * returns the number of connected components in the graph
     *
     * @return
     */
    int sizeOfConnectedComponents();

    /**
     * returns the number of vertices connected to the given vertex {@code v}
     */
    int sizeOfConnection(E v);

    /**
     * is the graph acyclic?
     *
     * @return
     */
    boolean isAcyclic();

    /**
     * Adds the v-w to the graph
     *
     * @param v
     * @param w
     */
    void addEdge(E v, E w);

    /**
     * returns the vertices adjacent to vertex {@code v}
     *
     * @param v
     * @return
     */
    Iterable<E> adjacent(E v);

    /**
     * returns all vertices in the graph
     *
     * @return
     */
    Iterable<E> vertices();

    /**
     * returns the degree of vertex {@code v}
     *
     * @param v
     * @return
     */
    default int degree(E v) {
        int d = 0;
        for (E adj: adjacent(v))
            d++;
        return d;
    }

    /**
     * returns the max degree of the graph
     *
     * @return
     */
    default int maxDegree() {
        int max = 0;
        for (E v: vertices())
            max = Math.max(degree(v), max);
        return max;
    }

    /**
     * returns the average degree of the graph
     *
     * @return
     */
    default double averageDegree() {
        return (sizeOfEdges() + .0) / sizeOfVertices();
    }

    /**
     * returns the number of self-loop edge
     *
     * @return
     */
    default int sizeOfSelfLoops() {
        int cnt = 0;
        for (E v: vertices())
            for (E adj: adjacent(v))
                if (v.equals(adj))
                    cnt++;
        return cnt / 2; //double count
    }
}
