package collection;

import app.AppIn;

import java.util.Optional;

/**
 * The {@code Topological} class represents a data type for determining
 * a topological order of a <em>directed acyclic graph</em> (DAG).
 * A digraph has a topological order if and only it is a DAG.
 *
 * @param <E>
 */
class Topological<E> {

    private static final Object O = new Object(); //dummy obj
    private Stack<E> order; //topological order

    public Topological(Digraph<E> G) {
        Cycle<E> cycle = new DirectedCycle<>(G);
        if (!cycle.hasCycle()) {
            order = new LinkedStack<>();
            SymbolTable<E, Object> visited = new SeparateChainingHashST<>();
            for (E v: G.vertices()) {
                if (!visited.contains(v))
                    dfs(v, visited, G);
            }
        }
    }

    private void dfs(E v, SymbolTable<E, Object> visited, Digraph<E> G) {
        visited.put(v, O);
        for (E adj: G.adjacent(v))
            if (!visited.contains(adj))
                dfs(adj, visited, G);
        order.push(v);
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Optional<Iterable<E>> order() {
        return Optional.ofNullable(order);
    }


    /**
     * ======================== TEST METHOD ======================
     */
    private static final String JOBS_TXT = "jobs.txt";
    private static final String TINY_DAG_TXT = "tinyDAG.txt";


    public static void main(String[] args) {
        String[] strings = AppIn.getInstance().readAllLines(JOBS_TXT);
        Digraph<String> G = new DirectedGraph<>();
        for (String line: strings) {
            String[] tokens = line.split("/");
            int i = 0;
            String v = tokens[i++];
            while (i < tokens.length)
                G.addEdge(v, tokens[i++]);
        }
        Topological<String> topological = new Topological<>(G);
        System.out.println(topological.order());

        Integer[] ints = AppIn.getInstance().readAllInts(TINY_DAG_TXT);
        Digraph<Integer> IG = new DirectedGraph<>();
        for (int i = 2; i < ints.length; )
            IG.addEdge(ints[i++], ints[i++]);

        Topological<Integer> integerTopological = new Topological<>(IG);
        System.out.println(integerTopological.order());
    }
}
