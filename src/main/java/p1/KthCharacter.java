package p1;

import collection.LinkedQueue;
import collection.Queue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * P.1.3.15
 *
 * Print k'th string from the end of
 * the input strings using Queue.
 *
 */
public class KthCharacter {

    private final String[] strings;
    private final int k;

    public KthCharacter(String[] strings, int k) {
        this.strings = strings.clone();
        this.k = k;
    }

    public String kth() {
        Queue<String> q = new LinkedQueue<>();
        for (String s: strings)
            q.enqueue(s);
        int kth = q.size() - k;
        for (int i=0; i<kth-1; i++)
            q.dequeue();
        return q.dequeue();
    }

    public static void main(String[] args) {
        String[] s = {"a", "b", "c", "d", "e"};
        KthCharacter kthCharacter = new KthCharacter(s, 1);
        assertThat(kthCharacter.kth()).isEqualTo("d");
        kthCharacter = new KthCharacter(s, 2);
        assertThat(kthCharacter.kth()).isEqualTo("c");
        kthCharacter = new KthCharacter(s, 5);
        assertThat(kthCharacter.kth()).isEqualTo("a");
    }
}
