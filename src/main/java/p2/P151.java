package p2;

import collection.QuickFindUF;
import collection.QuickUnionUF;
import collection.UF;
import common.AppResource;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class P151 {

    private static final String FILE = "p151.txt";
    @Test
    public void test() {
        File file = AppResource.getInstance().getFileOf(FILE);
        UF UF = null;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            int N = scanner.nextInt();
            UF = new QuickFindUF(N);
            while (scanner.hasNextInt()) {
                int p = scanner.nextInt(),
                        q = scanner.nextInt();
                UF.union(p, q);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        assertThat(UF.count()).isEqualTo(2);
        assertThat(UF.find(0)).isEqualTo(1);
        assertThat(UF.find(1)).isEqualTo(1);
        assertThat(UF.find(2)).isEqualTo(1);
        assertThat(UF.find(3)).isEqualTo(1);
        assertThat(UF.find(4)).isEqualTo(1);
        assertThat(UF.find(5)).isEqualTo(1);
        assertThat(UF.find(6)).isEqualTo(6);
        assertThat(UF.find(7)).isEqualTo(1);
        assertThat(UF.find(8)).isEqualTo(1);
        assertThat(UF.find(9)).isEqualTo(1);
    }
}
