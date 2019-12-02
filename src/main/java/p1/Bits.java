package p1;

import collection.Collection;
import collection.LinkedStack;
import collection.Stack;

import static org.assertj.core.api.Assertions.assertThat;

public class Bits {

    private final int num;

    public Bits(int num) {
        assert num >= 0;
        this.num = num;
    }

    public Collection<Integer> toBits() {
        Stack<Integer> stack = new LinkedStack<>();
        if (num == 0) {
            stack.push(0);
            return stack;
        }
        int n = num;
        while (n > 0) {
            stack.push(n % 2);
            n /= 2;
        }
        return stack;
    }

    public static void main(String[] args) {
        Bits bits = new Bits(50);
        assertThat(bits.toBits()).containsExactly(1, 1, 0, 0, 1, 0);
        bits = new Bits(0);
        assertThat(bits.toBits()).containsExactly(0);
        bits = new Bits(31);
        assertThat(bits.toBits()).containsExactly(1, 1, 1, 1, 1);
    }
}
