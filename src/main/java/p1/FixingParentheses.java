package p1;

public class FixingParentheses {

    private final String exp;

    public FixingParentheses(String exp) {
        this.exp = exp;
    }

    public String fix() {
        Stack<String> os = new LinkedStack<>(),
                ps = new LinkedStack<>(),
                openBracket = new LinkedStack<>();
        for (int i=0; i<exp.length(); i++) {
            String c = Character.toString(exp.charAt(i));
            if (Character.isDigit(c.charAt(0)))
                ps.push(c);
            else if (isOperand(c))
                os.push(c);
            else if ("(".equals(c))
                openBracket.push(c);
            else if (")".equals(c)) {
                if (!openBracket.isEmpty()) {
                    openBracket.pop();
                }
                String p1 = ps.pop();
                String p2 = ps.pop();
                ps.push("(" + p2 + os.pop() + p1 + ")");
            }
        }
        Queue<String> q = new LinkedQueue<>();
        for (String s: ps)
            q.enqueue(s);
        StringBuilder sb = new StringBuilder();
        for (String s: q)
            sb.append(s);
        return sb.toString();
    }

    private boolean isOperand(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/");
    }

    public static void main(String[] args) {
        FixingParentheses fixingParentheses = new FixingParentheses("1+2)*3-4)*5-6)))");
        System.out.println(fixingParentheses.fix());
    }
}
