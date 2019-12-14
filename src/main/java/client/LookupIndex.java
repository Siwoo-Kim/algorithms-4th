package client;

import app.AppIn;
import collection.LinkedQueue;
import collection.Queue;
import collection.SeparateChainingHashST;
import collection.SymbolTable;

public class LookupIndex {

    private static final String MOVIES_TXT = "movies.txt";
    private static final String AMINOI_CSV = "aminoI.csv";

    public static void main(String[] args) {
        LookupIndex lookupIndex = new LookupIndex();
        lookupIndex.lookup(AMINOI_CSV, ",", "Serine", "TCG");
        lookupIndex.lookup(MOVIES_TXT, "/", "Bacon, Kevin", "Tin Men (1987)");
    }

    void lookup(String filename, String splitter, String... queries) {
        String[] lines = AppIn.getInstance().readAllLines(filename);
        SymbolTable<String, Queue<String>> st = new SeparateChainingHashST<>();
        SymbolTable<String, Queue<String>> ts = new SeparateChainingHashST<>();
        for (String line: lines) {
            String[] words = line.split(splitter);
            String key = words[0];
            for (int i=1; i<words.length; i++) {
                String val = words[i];
                if (!st.contains(key)) st.put(key, new LinkedQueue<>());
                if (!ts.contains(val)) ts.put(val, new LinkedQueue<>());
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }
        for (String query: queries) {
            System.out.println(query);
            if (st.contains(query))
                for (String s: st.get(query))
                    System.out.println(" " + s);
            if (ts.contains(query))
                for (String s: ts.get(query))
                    System.out.println(" " + s);
        }
    }
}
