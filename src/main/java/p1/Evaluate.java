package p1;

public class Evaluate {

    private final String exp;

    public Evaluate(String exp) {
        assert exp != null;
        this.exp = exp;
    }

    public double eval() {
        Stack<String> os = new ArrayStack<>();
        Stack<String> ps = new ArrayStack<>();
        for (int i=0; i<exp.length(); i++) {
            String el = Character.toString(exp.charAt(i));
            if ("+".equals(el)) os.push(el);
            else if ("-".equals(el)) os.push(el);
            else if ("*".equals(el)) os.push(el);
            else if ("/".equals(el)) os.push(el);
            else if (Character.isDigit(el.charAt(0))) ps.push(el);
            else if (")".equals(el)) {
                double po1 = Double.parseDouble(ps.pop());
                double po2 = Double.parseDouble(ps.pop());
                String op = os.pop();
                String r = "";
                if ("+".equals(op)) r = Double.toString(po1 + po2);
                if ("-".equals(op)) r = Double.toString(po1 - po2);
                if ("*".equals(op)) r = Double.toString(po1 * po2);
                if ("/".equals(op)) r = Double.toString(po1 / po2);
                ps.push(r);
            }
        }
        return Double.parseDouble(ps.pop());
    }

    public static void main(String[] args) {
        Evaluate evaluate = new Evaluate("(1+((2+3)*(4*5)))");
        System.out.println(evaluate.eval());
    }
}
