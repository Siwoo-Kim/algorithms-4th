package collection;


public interface Queue<E> extends Collection<E> {

    /**
     * Add element in the queue
     * @param el
     */
    void enqueue(E el);

    /**
     * Remove the oldest element from the queue
     * @return
     * @throws java.util.NoSuchElementException throws if queue is empty
     */
    E dequeue();

    /**
     * return the oldest element from the queue
     * @return
     * @throws java.util.NoSuchElementException throws queue is empty
     */
    E peek();
}
