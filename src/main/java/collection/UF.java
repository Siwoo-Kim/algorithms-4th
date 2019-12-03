package collection;

/**
 * The interface represents a union-find data structure.
 * It supports the <em>union</em> and <em>find</em> operations, along with
 * methods for determining whether two nodes are in the same component.
 *
 */
public interface UF {

    /**
     * are p and q is in the same component?
     * @param p
     * @param q
     * @return
     */
    boolean connected(int p, int q);

    /**
     * add connection between p and q
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * component identifier for p (0 to N-1)
     * @param p
     * @return
     */
    int find(int p);

    /**
     * number of components
     * @return
     */
    int count();

}
