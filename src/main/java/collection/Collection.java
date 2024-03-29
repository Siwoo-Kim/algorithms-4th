package collection;


public interface Collection<E> extends Iterable<E> {

    /**
     * is the collection empty?
     * @return
     */
    boolean isEmpty();

    /**
     * number of elements
     * @return
     */
    int size();
}
