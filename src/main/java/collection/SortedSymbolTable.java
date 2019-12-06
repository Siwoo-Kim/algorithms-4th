package collection;

public interface SortedSymbolTable<K extends Comparable<? super K>, V> extends SymbolTable<K, V> {

    /**
     * smallest key in the table
     * @return
     */
    K min();

    /**
     * largest key in the table
     * @return
     */
    K max();

    /**
     * number of keys less than key
     * @param key
     * @return
     */
    int rank(K key);

    /**
     * key of rank {@code rank}
     * @param rank
     * @return
     */
    K rankOf(int rank);

    /**
     * smallest key greater than or equal to given key
     * @param key
     * @return
     */
    K ceiling(K key);

    /**
     * largest key less than or equal to given key
     * @param key
     * @return
     */
    K floor(K key);

    /**
     * remove smallest key
     */
    default void removeMin() {
        remove(min());
    }

    /**
     * remove largest key
     */
    default void removeMax() {
        remove(max());
    }

    /**
     * number of keys
     * @return
     */
    @Override
    default int size() {
        return size(min(), max());
    }

    /**
     * number of keys in [low...high]
     * @param low
     * @param high
     * @return
     */
    default int size(K low, K high) {
        if (low.compareTo(high) >= 0)
            return 0;
        return contains(high)?
                rank(high) - rank(low) + 1:
                rank(high) - rank(low);
    }

    /**
     * all keys in the table, in sorted order
     * @return
     */
    @Override
    default Iterable<K> keys() {
        return keys(min(), max());
    }

    /**
     * keys in [low..high] in sorted order
     * @param low
     * @param high
     * @return
     */
    Iterable<K> keys(K low, K high);
}
