package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Date {
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
}
