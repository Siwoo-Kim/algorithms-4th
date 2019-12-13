package collection;

import static com.google.common.base.Preconditions.checkNotNull;

public interface SymbolTable<K, V> extends Iterable<K> {

    /**
     * put key-value pair into the table
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * value paired with the key
     * @param key
     * @return
     */
    V get(K key);

    /**
     * remove key-value pair from the table
     * @param key
     */
    void remove(K key);

    /**
     * number of key-value pairs in the table
     * @return
     */
    int size();

    /**
     * is there a value paired with key?
     * @param key
     * @return
     */
    default boolean contains(K key) {
        checkNotNull(key);
        return get(key) != null;
    };

    /**
     * is the table empty?
     * @return
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * all the keys in the table
     * @return
     */
    Iterable<K> keys();
}
