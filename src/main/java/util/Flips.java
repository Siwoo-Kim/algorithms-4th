package util;

public class Flips {

    /**
     *
     * @param T number of flipping
     * @return delta of heads and tail
     */
    static int flip(int T) {
        Counter heads = new Counter("heads"),
                tails = new Counter("tails");
        for (int i=0; i<T; i++) {
            if (Random.bernoulli(0.5))
                heads.increment();
            else
                tails.increment();
        }
        int delta = heads.tally() - tails.tally();
        return Math.abs(delta);
    }

    public static void main(String[] args) {
        System.out.println(flip(10000));
    }
}
