public class Main {
    public static Expression parse(String s) {
        s = s.trim();
        if (s.startsWith("(") && s.endsWith(")")) {
            String content = s.substring(1, s.length() - 1);
            int balance = 0;
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '(') balance++;
                else if (c == ')') balance--;
                else if (balance == 0) {
                    if (c == '+' || c == '-' || c == '*' || c == '/') {
                        Expression left = parse(content.substring(0, i));
                        Expression right = parse(content.substring(i + 1));
                        if (c == '+') return new Add(left, right);
                        if (c == '-') return new Sub(left, right);
                        if (c == '*') return new Mul(left, right);
                        if (c == '/') return new Div(left, right);
                    }
                }
            }
        }

        try {
            return new Number(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new Variable(s);
        }
    }
}
