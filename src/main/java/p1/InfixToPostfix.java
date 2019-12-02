package p1;

import collection.LinkedStack;
import collection.Stack;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Write a filter Program that converts an arithmetic expression
 * from infix to postfix.
 *
 * ( 2 + ( ( 3 + 4 ) * ( 5 * 6 ) ) ) => 2 3 4 + 5 6 * * +
 * ( ( ( 5 + ( 7 * ( 1 + 1 ) ) ) * 3 ) + ( 2 * ( 1 + 1 ) ) ) => 5 7 1 1 + * + 3 * 2 1 1 + * +
 *
 */
public class InfixToPostfix {

    private final String exp;

    public InfixToPostfix(String exp) {
        this.exp = exp;
    }

    public String postfix() {
        Stack<String> op = new LinkedStack<>(),
                ps = new LinkedStack<>();
        for (int i=0; i<exp.length(); i++) {
            char c = exp.charAt(i);
            if (Character.isDigit(c))
                ps.push(Character.toString(c));
            else if (isOperand(c))
                op.push(Character.toString(c));
            else if (c == ')') {
                String p1 = ps.pop();
                String p2 = ps.pop();
                ps.push(p2 + " " + p1 + " " + op.pop());
            }
        }
        return ps.pop();
    }

    private boolean isOperand(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        InfixToPostfix iToP = new InfixToPostfix("( 2 + ( ( 3 + 4 ) * ( 5 * 6 ) ) )");
        assertThat(iToP.postfix()).isEqualTo("2 3 4 + 5 6 * * +");
        iToP = new InfixToPostfix("( ( ( 5 + ( 7 * ( 1 + 1 ) ) ) * 3 ) + ( 2 * ( 1 + 1 ) ) )");
        assertThat(iToP.postfix()).isEqualTo("5 7 1 1 + * + 3 * 2 1 1 + * +");
    }
}
