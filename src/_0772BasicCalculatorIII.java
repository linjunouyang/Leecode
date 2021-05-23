import java.util.ArrayDeque;
import java.util.Deque;

public class _0772BasicCalculatorIII {
    /**
     * 1. Stack
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int num = 0;
        Deque<Integer> nums = new ArrayDeque<>(); // the stack that stores numbers
        Deque<Character> ops = new ArrayDeque<>(); // the stack that stores operators (including parentheses)

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                num = c - '0';
                while (i < s.length() - 1 && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + (s.charAt(i + 1) - '0');
                    i++;
                }
                nums.push(num);
                num = 0; // reset
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                // do the math when we encounter a ')' until '('
                while (ops.peek() != '(') {
                    int res = operation(ops.pop(), nums.pop(), nums.pop());
                    nums.push(res);
                }
                ops.pop(); // get rid of '('
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && precedence(c, ops.peek())) {
                    nums.push(operation(ops.pop(), nums.pop(),nums.pop()));
                }

                // Deal with negative numbers (not required by this question)
                if (c == '-') {
                    if (nums.isEmpty()) {
                        // 1st non-empty character is a negative number
                        nums.push(0);
                    } else {
                        // 1st non-empty character in () is a negative number
                        int index = i - 1;
                        while (index >= 0 && s.charAt(index) == ' ') {
                            index--;
                        }
                        if (s.charAt(index) == '(') {
                            nums.push(0);
                        }
                    }
                }

                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
        }

        return nums.pop();
    }

    private static int operation(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                // assume b != 0
                // be aware of the order
                return a / b;
        }
        return 0;
    }

    // helper function to check precedence of current operator and the uppermost operator in the ops stack
    //  if we have a + b * c. Num stack is a and b and op stack is ' + ' when we have current c as ' * ' ,
    //  the precedence check prevent us from doing a + b first
    // return true is op2 is higher or same level as op1
    private static boolean precedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }

        // op1, op2 {*, /}
        // op2 {*, /}, op1 {+, -}
        return true;
    }

    /**
     * 2. Recursion (TODO)
     */

    public static void main(String[] args) {

        System.out.println(calculate("-1+4*3/3/3") == 0);
        System.out.println(calculate("1-(-7)") == 8);
    }
}
