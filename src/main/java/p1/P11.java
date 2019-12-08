package p1;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class P11 {

    @Test
    public void p111() {
        assertEquals((0+15)/2, 7);
        assertEquals(2.0e-6 * 100000000.1 , 200.0000002, 0.0000000001);
        assertTrue(true && false || true && true); //eval || after &&
    }

    @Test
    public void p112() {
        assertThat((1 + 2.236) / 2).isInstanceOf(Double.class);
        assertThat(1+2+3+4.0).isInstanceOf(Double.class);
        assertThat(4.1 >= 4).isInstanceOf(Boolean.class);
        assertThat(1 + 2 + "3").isInstanceOf(String.class);
    }

    public boolean allEqual(int[] a) {
        assert a.length != 0;
        int e = a[0];
        for (int i=1; i<a.length; i++) {
            if (a[i] != e)
                return false;
        }
        return true;
    }

    @Test
    public void p113() {
        int[] a = {1, 2, 2};
        assertFalse(allEqual(a));
        a = new int[]{2, 2, 2};
        assertTrue(allEqual(a));
        a = new int[]{1, 2, 3};
        assertFalse(allEqual(a));
    }

    public boolean boundIn0to1(double x) {
        return Double.compare(x, 0) > 0 && Double.compare(x, 1) < 0;
    }

    @Test
    public void p115() {
        assertTrue(boundIn0to1(0.1234));
        assertTrue(boundIn0to1(0.9));
        assertTrue(boundIn0to1(0.0000001));
        assertTrue(boundIn0to1(0.9999999));
        assertTrue(boundIn0to1(0.1111111));

        assertFalse(boundIn0to1(-0.1234567));
        assertFalse(boundIn0to1(-0.0000001));
        assertFalse(boundIn0to1(-0.33333));
        assertFalse(boundIn0to1(1.0));
        assertFalse(boundIn0to1(1.00000001));
        assertFalse(boundIn0to1(333.33333));
    }

    //0, f=1,g=0
    //1, f=1,g=1
    //1, f=2,g=1
    //2, f=3,g=2
    //3, f=5,g=3
    //5, f=8,g=5
    //8, f=13,g=8
    //13, f=21,g=13
    //21, f=34,g=21
    //34, f=55,g=34
    //55, f=89,g=55
    //89, f=144,f=89
    //144, f=233,f=144
    //...
    @Test
    public void p116() {
        int f=0, g=1;
        for (int i=0; i<=15; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }
    }

    @Test
    public void p119() {
        int N = 148;
        StringBuilder sb = new StringBuilder();
        for (int i=N; i>0; i/=2)
            sb.append(i % 2);
        sb = sb.reverse();
        assertEquals(sb.toString(), Integer.toBinaryString(N));
    }

    @Test
    public void p1111() {
        boolean[][] matrix = {
                {true, false, false, false, true},
                {true, true, false, true, true},
                {true, true, true, true, true},
                {true, false, true, false, true},
                {true, true, true, true, true},
        };

        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++)
                System.out.printf("[%d][%d] %s ", i, j, (matrix[i][j] ? "*": " "));
            System.out.println();
        }
    }

    @Test
    public void p1112() {
        int[] a = new int[10];
        for (int i=0; i<10; i++)
            a[i] = 9-i;
        // {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
        for (int i=0; i<10; i++)
            a[i] = a[a[i]];
        // {0, 1, 2, 3, 4, 4, 3, 2, 1, 0}
        System.out.println(Arrays.toString(a));
    }
}
