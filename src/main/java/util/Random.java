package util;

/**
 * A library of static methods to generate pseudo-random numbers.
 */
public class Random {

    private static final long seed;
    private static final java.util.Random random;

    static {
        seed = System.currentTimeMillis();
        random = new java.util.Random(seed);
    }

    private Random() { }

    public static double uniform() {
        return random.nextDouble();
    }
}
