package client;

import app.AppIn;

import java.util.HashSet;

public class DeDup {

    public static final String TINY_TALE_TXT = "tinyTale.txt";

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        String[] words = AppIn.getInstance().readAllStrings(TINY_TALE_TXT);
        for (int i=0; i<words.length; i++) {
            if (!set.contains(words[i])) {
                set.add(words[i]);
                System.out.println(words[i]);
            }
        }
    }
}
