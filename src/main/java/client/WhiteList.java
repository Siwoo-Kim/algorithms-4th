package client;

import app.AppIn;

import java.util.HashSet;
import java.util.Set;

public class WhiteList {

    private static final String LIST_TXT = "list.txt";
    private static final String TINY_TALE_TXT = "tinyTale.txt";

    public static void main(String[] args) {
        Set<String> whitelist = new HashSet<>();
        String[] list = AppIn.getInstance().readAllStrings(LIST_TXT);
        for (String s: list)
            whitelist.add(s);
        String[] words = AppIn.getInstance().readAllStrings(TINY_TALE_TXT);
        for (String word: words)
            if (whitelist.contains(word))
                System.out.println(word);
    }
}
