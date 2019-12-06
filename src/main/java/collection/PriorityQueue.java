package collection;

/**
 * The {@code PriorityQueue} interface represents a priority queue of generic keys.
 * It supports the usual <em>enqueue</em> and <em>dequeue</em> ,which delete the
 * maximum priority, element operations.
 *
 * @param <E>
 */
public interface PriorityQueue<E extends Comparable<? super E>> extends Queue<E> {

    /**
     * insert a element into the priority queue.
     * @param el
     */
    @Override
    void enqueue(E el);

    /**
     * return and remove the most prior element
     * @return
     * @throws java.util.NoSuchElementException throws if queue is empty
     */
    @Override
    E dequeue();

    /**
     * return the most prior element
     * @return
     * @throws java.util.NoSuchElementException throws if queue is empty
     */
    @Override
    E peek();
}
