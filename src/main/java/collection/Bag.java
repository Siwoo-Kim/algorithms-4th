package collection;

/**
 * The interface represents a bag of generic items.
 * It supports insertion and iterating over the items
 * in no guaranteed order
 *
 * @param <E>
 */
public interface Bag<E> extends Collection<E> {

    /**
     * Add element in the bag
     * @param el
     */
    void add(E el);

}
