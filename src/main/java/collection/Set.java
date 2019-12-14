package collection;

public interface Set<E> {

    /**
     * Adds the {@code e} to the set
     *
     * @param e
     * @throws IllegalArgumentException if {@code e} is {@code null}
     */
    void add(E e);

    /**
     * Removes the given {@code e} for the set.
     *
     * @param e
     * @throws IllegalArgumentException if {@code e} is {@code null}
     */
    void remove(E e);

    /**
     * Returns true if the set contains the given {@code e}
     *
     * @param e
     * @return
     * @throws IllegalArgumentException if {@code e} is {@code null}
     */
    boolean contains(E e);

    /**
     * Returns true if the set is empty
     *
     * @return
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the set
     * @return
     */
    int size();
}
