package p1;

import collection.LinkedStack;
import collection.Stack;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Parentheses {

    private final String exp;
    private static final String[] OPEN_BRACKET = {"[", "(", "{"};
    private static final String[] CLOSE_BRACKET = {"]", ")", "}"};

    public Parentheses(String exp) {
        this.exp = exp;
    }

    public boolean valid() {
        Stack<String> open = new LinkedStack<>();
        for (int i=0; i<exp.length(); i++) {
            String c = Character.toString(exp.charAt(i));
            if (isOpenBracket(c))
                open.push(c);
            else {
                if (open.isEmpty())
                    return false;
                String openBracket = open.pop();
                if (indexOfBracket(openBracket) != indexOfBracket(c))
                    return false;
            }
        }
        return true;
    }

    private int indexOfBracket(String bracket) {
        for (int i=0; i<OPEN_BRACKET.length; i++)
            if (bracket.equals(OPEN_BRACKET[i]))
                return i;
        for (int i=0; i<CLOSE_BRACKET.length; i++)
            if (bracket.equals(CLOSE_BRACKET[i]))
                return i;
        return -1;
    }

    private boolean isOpenBracket(String c) {
        for (int i=0; i<OPEN_BRACKET.length; i++)
            if (OPEN_BRACKET[i].equals(c))
                return true;
        return false;
    }

    public static void main(String[] args) {
        Parentheses parentheses = new Parentheses("[()]{}{[()()]()}");
        assertTrue(parentheses.valid());
        parentheses = new Parentheses("[(])");
        assertFalse(parentheses.valid());
    }
}
