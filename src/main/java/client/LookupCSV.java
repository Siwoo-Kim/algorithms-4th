package client;

import app.AppIn;
import collection.RedBlackTree;
import collection.SeparateChainingHashST;
import collection.SymbolTable;

import static org.assertj.core.util.Preconditions.checkNotNull;

public class LookupCSV {

    private static final String IP_CSV = "ip.csv";
    private static final String AMINO_CSV = "amino.csv";
    private static final String DJIA_CSV = "DJIA.csv";
    private static final String UPC_CSV = "UPC.csv";

    public static void main(String[] args) {
        LookupCSV luCSV = new LookupCSV();
        luCSV.lookupCSV(IP_CSV, 1, 0, "128.112.136.35");
        luCSV.lookupCSV(AMINO_CSV, 0, 3, "TTC");
        luCSV.lookupCSV(DJIA_CSV, 0, 3, "29-Oct-29");
        luCSV.lookupCSV(UPC_CSV, 0, 2, "0002100001086");
    }

    void lookupCSV(String filename, int keyField, int valField, String... query) {
        checkNotNull(filename);
        assert keyField >= 0 && valField >= 0;
        String[] lines = AppIn.getInstance().readAllLines(filename);
        SymbolTable<String, String> st = new SeparateChainingHashST<>();
        for (String line: lines) {
            String[] tokens = line.split(",");
            st.put(tokens[keyField], tokens[valField]);
        }
        for (String q: query) {
            if (st.contains(q))
                System.out.println(q + " " + st.get(q));
        }
    }
}

