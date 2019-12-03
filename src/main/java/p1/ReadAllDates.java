package p1;

import collection.LinkedQueue;
import collection.Queue;
import app.AppResource;
import domain.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * P.1.3.16
 *
 * Get input and creating an array of the date objects
 * using Queue.
 *
 */
public class ReadAllDates {

    public static Date[] readAllDates(Scanner scanner) {
        Queue<Date> q = new LinkedQueue<>();
        while (scanner.hasNextInt()) {
            int y = scanner.nextInt();
            int m = scanner.nextInt();
            int d = scanner.nextInt();
            q.enqueue(Date.of(y, m, d));
        }
        Date[] dates = new Date[q.size()];
        for (int i=0; i<dates.length; i++)
            dates[i] = q.dequeue();
        return dates;
    }

    private static final String DATE_FILE = "dates.txt";

    public static void main(String[] args) throws FileNotFoundException {
        File file = AppResource.getInstance().getFileOf(DATE_FILE);
        Date[] dates = readAllDates(new Scanner(new FileReader(file)));
        assertThat(dates[0]).isEqualTo(Date.of(1989, 3, 4));
        assertThat(dates[3]).isEqualTo(Date.of(1945, 8, 15));
    }
}
