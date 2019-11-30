package p1;


public interface Collection<E> extends Iterable<E> {

    /**
     * is the queue empty?
     * @return
     */
    boolean isEmpty();

    /**
     * number of elements
     * @return
     */
    int size();
}
