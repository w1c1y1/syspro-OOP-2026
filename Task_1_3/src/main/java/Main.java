public class Main {
    public static Expression parse(String string) {
        string = string.trim();
        if (string.startsWith("(") && string.endsWith(")")) {
            String content = string.substring(1, string.length() - 1);
            int balance = 0;
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '(') {
                    balance++;
                }
                else if (c == ')') {
                    balance--;
                }
                else if (balance == 0) {
                    if (c == '+' || c == '-' || c == '*' || c == '/') {
                        Expression left = parse(content.substring(0, i));
                        Expression right = parse(content.substring(i + 1));
                        if (c == '+') {
                            return new Add(left, right);
                        }
                        if (c == '-') {
                            return new Sub(left, right);
                        }
                        if (c == '*') {
                            return new Mul(left, right);
                        } else {
                            return new Div(left, right);
                        }
                    }
                }
            }
        }

        try {
            return new Number(Integer.parseInt(string));
        } catch (NumberFormatException e) {
            return new Variable(string);
        }
    }
}
