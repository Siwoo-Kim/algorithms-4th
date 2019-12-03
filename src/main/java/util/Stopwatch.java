package util;

/**
 * A utility class to measure the running time of a program.
 */
public class Stopwatch {

    private long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public Stopwatch start() {
        start = System.currentTimeMillis();
        return this;
    }

    public double elapseTime() {
        long end = System.currentTimeMillis();
        return (end - start) / 1000.0;
    }
}
