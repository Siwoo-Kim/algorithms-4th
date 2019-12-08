package util;

import static org.assertj.core.util.Preconditions.checkArgument;

/**
 * A library of static methods to generate pseudo-random numbers.
 */
public final class Random {

    private static final long seed;
    private static final java.util.Random random;

    static {
        seed = System.currentTimeMillis();
        random = new java.util.Random(seed);
    }

    private Random() { }

    /**
     * Returns a random number in 0..1
     * @return
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with
     * success probability <em>p</em>
     *
     * @param p the probability
     * @return
     * @throws IllegalArgumentException {@code 0.0 > p || 1.0 < p}
     */
    public static boolean bernoulli(double p) {
        checkArgument(p > 0.0 && p < 1.0,
                "probability p [%.f] must be between 0.0 and 1.0", p);
        return random.nextDouble() > p;
    }
}
