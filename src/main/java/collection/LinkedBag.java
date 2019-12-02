package collection;

import common.AppResource;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

public class LinkedBag<E> implements Bag<E> {

    private LinkedStack<E> stack = new LinkedStack<>();

    @Override
    public void add(E el) {
        stack.push(el);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public Iterator<E> iterator() {
        return stack.iterator();
    }

    private static final String STATS_FILE = "stats.txt";

    public static void main(String[] args) {
        File file = AppResource.getInstance().getFileOf(STATS_FILE);
        Bag<Integer> bag = new LinkedBag<>();
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextInt()) {
                bag.add(scanner.nextInt());
            }
        } catch (Exception e) {
            throw new IllegalStateException();
        }

        double sum = 0;
        for (Integer n: bag)
            sum += n;
        double mean = sum / bag.size();

        sum = 0;
        for (Integer n: bag)
            sum += Math.pow(mean-n, 2);
        double std = Math.sqrt(sum / (bag.size()-1));
        System.out.printf("Mean: %.2f%n Std: %.2f", mean, std);
    }

}
