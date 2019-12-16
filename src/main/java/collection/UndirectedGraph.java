package collection;

import app.AppIn;

import java.util.Collections;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code UndirectedGraph} class represents an undirected graph,
 * where vertex are generic object.
 *
 * @param <E>
 */
public class UndirectedGraph<E> implements Graph<E> {

    private SymbolTable<E, Bag<E>> adjMap;  //map of adjacent list
    private int V;  // number of vertices
    private int E;  // number of edges

    UndirectedGraph() {
        adjMap = new SeparateChainingHashST<>();
    }

    public UndirectedGraph(Iterable<E[]> edges) {
        this();
        checkNotNull(edges);
        java.util.Set<E> vertices = new HashSet<>();
        for (E[] edge: edges) {
            assert edge != null && edge.length == 2;
            checkArgument(edge.length > 1);
            E v = edge[0], w = edge[1];
            vertices.add(v);
            vertices.add(w);
            addEdge(v, w);
        }
        this.V = vertices.size();
    }

    @Override
    public int sizeOfVertices() {
        return V;
    }

    @Override
    public int sizeOfEdges() {
        return E;
    }

    @Override
    public boolean connected(E v, E w) {
        checkNotNull(v, w);
        Search<E> search = new DepthFirstSearch<>(v, this);
        return search.connected(w);
    }

    @Override
    public Iterable<E> path(E v, E w) {
        checkNotNull(v, w);
        if (!contains(v) || !contains(w))
            return Collections.emptyList();
        Paths<E> paths = new BreadthFirstPaths<>(v, this);
        return paths.pathTo(w);
    }

    @Override
    public int idOfConnectedComponent(E v) {
        ConnectedComponent<E> cc = new DepthFirstSearchCC<>(this);
        return cc.id(v);
    }

    @Override
    public int sizeOfConnectedComponents() {
        ConnectedComponent<E> cc = new DepthFirstSearchCC<>(this);
        return cc.count();
    }

    @Override
    public int sizeOfConnection(E v) {
        Search<E> search = new DepthFirstSearch<>(v, this);
        return search.count();
    }

    @Override
    public boolean isAcyclic() {
        Cycle<E> cycle = new DepthFirstSearchCycle<>(this);
        return !cycle.hasCycle();
    }

    @Override
    public void addEdge(E v, E w) {
        checkNotNull(v, w);
        if (!contains(v))
            adjMap.put(v, new LinkedBag<>());
        if (!contains(w))
            adjMap.put(w, new LinkedBag<>());
        adjMap.get(v).add(w);   //v-w
        adjMap.get(w).add(v);   //w-v
        E++;
    }

    private boolean contains(E v) {
        checkNotNull(v);
        return adjMap.get(v) != null;
    }

    @Override
    public Iterable<E> adjacent(E v) {
        checkNotNull(v);
        if (!contains(v))
            return new LinkedBag<>();
        LinkedBag<E> copied = new LinkedBag<>();
        for (E adj: adjMap.get(v))
            copied.add(adj);
        return copied;
    }

    @Override
    public Iterable<E> vertices() {
        LinkedBag<E> bag = new LinkedBag<>();
        for (E v: adjMap.keys())
            bag.add(v);
        return bag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (adjMap.isEmpty()) {
            sb.append("(empty)");
            return sb.toString();
        }
        sb.append(V).append(" vertices, ")
                .append(E).append(" edges\n");
        for (E v: vertices()) {
            sb.append(v).append(": ");
            for (E adj: adjacent(v))
                sb.append(adj).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    private static final String TINY_G_TXT = "tinyG.txt";

    public static void main(String[] args) {
        Integer[] ints = AppIn.getInstance().readAllInts(TINY_G_TXT);
        int i = 0;
        int V = ints[i++];
        int E = ints[i++];
        Bag<Integer[]> bags = new LinkedBag<>();
        while (i < ints.length) {
            bags.add(new Integer[]{
                    ints[i++], ints[i++]}); //add v-w edge
        }
        UndirectedGraph<Integer> G = new UndirectedGraph<>(bags);
        System.out.println(G);

        System.out.println("Connection");
        for (int v: G.vertices()) {
            System.out.print(v + " :");
            for (int w: G.vertices())
                if (G.connected(v, w))
                    System.out.print(w + " ");
            System.out.println();
        }

        System.out.println();
        int vertex0 = 0;
        for (int v: G.vertices()) {
            if (G.connected(vertex0, v)) {
                System.out.print(vertex0 + " to " + v + ": ");
                for (int x: G.path(vertex0, v)) {
                    if (x == vertex0) System.out.print(x);
                    else System.out.print(x + "-");
                }
                System.out.println();
            }
        }

        Queue<Integer>[] components = new Queue[G.sizeOfConnectedComponents()];
        for (i=0; i<components.length; i++)
            components[i] = new LinkedQueue<>();
        for (int v: G.vertices())
            components[G.idOfConnectedComponent(v)].enqueue(v);
        for (i=0; i<components.length; i++) {
            System.out.printf("%d id: ", i);
            for (int v: components[i])
                System.out.print(v + " ");
            System.out.println();
        }

        Cycle<Integer> c = new DepthFirstSearchCycle<>(G);
        System.out.println();
        System.out.println(c.cycle());
    }
}
