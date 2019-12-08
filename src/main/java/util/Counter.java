package util;

/**
 * The {@code Counter} class is a mutable data type to represents a counter.
 *
 */
public final class Counter {
    private final String id;
    private int count;

    public Counter(String id) {
        this.id = id;
    }

    /**
     * Increments the counter by 1
     */
    public void increment() {
        count++;
    }

    /**
     * returns the current value of this counter
     * @return
     */
    public int tally() {
        return count;
    }

    public String toString() {
        return count + " " +id;
    }
}
