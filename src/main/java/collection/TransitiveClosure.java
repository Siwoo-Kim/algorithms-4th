package collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code TransitiveClosure} class represents a data type for computing
 * the transitive closure of a digraph.
 *
 * @param <E>
 */
class TransitiveClosure<E> {
    private SymbolTable<E, DepthFirstSearch<E>> all;

    public TransitiveClosure(Digraph<E> G) {
        this.all = new SeparateChainingHashST<>(G.sizeOfVertices());
        for (E v: G.vertices())
            all.put(v, new DepthFirstSearch<>(v, G));
    }

    boolean reachable(E v, E w) {
        checkNotNull(v, w);
        return all.get(v).connected(w);
    }
}
