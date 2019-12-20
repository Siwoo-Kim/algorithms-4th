package collection;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class {@code WeightedEdge} represents an edge in the edge weighted graph.
 * Each edge consists of two vertices and it's weight value.
 *
 */
public class WeightedEdge<E> implements Edge<E> {
    private final double weight;
    private final E v, w;

    public WeightedEdge(E v, E w) {
        this(v, w, 0.0);
    }

    public WeightedEdge(E v, E w, double weight) {
        checkNotNull(v, w);
        checkArgument(!Double.isNaN(weight));
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double weight() {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E either() {
        return v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E other(E vertex) {
        if (v.equals(vertex))
            return w;
        if (w.equals(vertex))
            return v;
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Edge<E> reverse() {
        return new WeightedEdge<>(w, v, weight);
    }

    @Override
    public int compareTo(Edge<E> o) {
        return Double.compare(weight, o.weight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedEdge<?> that = (WeightedEdge<?>) o;
        return Double.compare(that.weight, weight) == 0 &&
                Objects.equals(v, that.v) &&
                Objects.equals(w, that.w);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, v, w);
    }

    @Override
    public String toString() {
        return String.format("%s-%s %.5f", v, w, weight);
    }
}
