package domain;

import java.util.Comparator;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code Transaction} class is an immutable data type to encapsulate
 * a commercial transaction.
 */
public class Transaction implements Comparable<Transaction> {

    private static final Comparator<Transaction> comparator =
            Comparator.comparing(Transaction::who)
                .thenComparing(Transaction::when)
                .thenComparing(Transaction::amount);

    private final String who;
    private final Date when;
    private final double amount;

    public Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public static Transaction fromString(String line) {
        checkNotNull(line);
        String[] args = line.split("\\s+");
        String name = args[0].trim();
        Date date = Date.from(args[1]);
        double amount = Double.parseDouble(args[2]);
        return new Transaction(name, date, amount);
    }

    public static Transaction of(String who, Date when, double amount) {
        checkNotNull(who);
        checkArgument(!who.isEmpty());
        checkNotNull(when);
        checkArgument(!Double.isNaN(amount) && !Double.isInfinite(amount));
        return new Transaction(who, when, amount);
    }

    public String who() {
        return who;
    }

    public Date when() {
        return when;
    }

    public double amount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(who, that.who) &&
                Objects.equals(when, that.when);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, when, amount);
    }

    /**
     * Compares two transactions by amount
     * @param that
     * @return
     */
    @Override
    public int compareTo(Transaction that) {
        return comparator.compare(this, that);
    }

    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }
}
