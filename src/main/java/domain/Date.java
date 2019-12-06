package domain;

import java.time.LocalDate;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class Date implements Comparable<Date> {
    private final int month;
    private final int day;
    private final int year;

    private Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static Date of(int year, int month, int day) {
        LocalDate.of(year, month, day); //validation
        return new Date(year, month, day);
    }

    public static Date from(String date) {
        String[] fields = date.split("/");
        checkArgument(fields.length == 3);
        int month = Integer.parseInt(fields[0]);
        int day   = Integer.parseInt(fields[1]);
        int year  = Integer.parseInt(fields[2]);
        return new Date(year, month, day);
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return month == date.month &&
                day == date.day &&
                year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, day, year);
    }

    @Override
    public int compareTo(Date d) {
        if (year < d.year) return -1;
        if (year > d.year) return 1;
        if (month < d.month) return -1;
        if (month > d.month) return 1;
        if (day < d.day) return -1;
        if (day > d.day) return 1;
        else return 0;
    }
}
