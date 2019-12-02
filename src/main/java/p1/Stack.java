package p1;

public interface Stack<E> extends Collection<E> {

    /**
     * Add element in the stack
     * @param el
     */
    void push(E el);

    /**
     * Get the most recent added element from the stack
     * @return
     */
    E peek();

    /**
     * Remove the most recent added element from the stack
     * @throws java.util.EmptyStackException
     * @return
     */
    E pop();

}
