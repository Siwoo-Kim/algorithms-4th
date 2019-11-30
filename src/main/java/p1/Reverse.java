package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

class Reverse {

    private Stack<Integer> stack;

    public Reverse(Stack<Integer> stack) {
        assert stack != null;
        this.stack = stack;
    }

    public int[] reverseInput(String fileName) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(fileName))))) {
            while (scanner.hasNextInt())
                stack.push(scanner.nextInt());
            int[] ints = new int[stack.size()];
            for (int i=0; i<ints.length; i++)
                ints[i] = stack.pop();
            return ints;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
