package p1;

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
        System.out.println(bits.toBits()); //110010
        bits = new Bits(0);
        System.out.println(bits.toBits());  //0
        bits = new Bits(31);
        System.out.println(bits.toBits());  //11111


    }
}
