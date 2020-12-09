import java.util.ArrayDeque;
import java.util.Deque;

/**
 * + follows the associative property. For the expression A+B+CA+B+C, we have (A+B)+C = A+(B+C)(A+B)+C=A+(B+C).
 * However, - does not follow this rule which is the root cause of all the problems in this approach.
 */
public class _0224BasicCalculator {
    /**
     * 1.
     * Principle:
     * (Sign before '+'/'-') = (This context sign);
     * (Sign after '+'/'-') = (This context sign) * (1 or -1);
     *
     * Algorithm:
     * 1. Start from +1 sign and scan s from left to right;
     * 2. if c == digit: This number = Last digit * 10 + This digit;
     * 3. if c == '+': Add num to result before this sign; This sign = Last context sign * 1; clear num;
     * 4. if c == '-': Add num to result before this sign; This sign = Last context sign * -1; clear num;
     * 5. if c == '(': Push this context sign to stack;
     * 6. if c == ')': Pop this context and we come back to last context;
     * 7. Add the last num. This is because we only add number after '+' / '-'.
     *
     * functionally (but not actually) propagating the signs through the parenthesis
     * so that all you have to do is just have a running sum.
     *
     * (1-(4-(5+2))-3)+(6+8)
     *
     * 394. Decode String.
     * the theme is using a stack to help you parse through a string where order of operations is important
     * (i.e. look at the innermost parenthesis first).
     */
    public int calculate(String s) {
        if(s == null) {
            return 0;
        }

        int result = 0;
        int sign = 1;
        int num = 0;

        Deque<Integer> stack = new ArrayDeque<Integer>();
        stack.push(sign);

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');
            } else if(c == '+' || c == '-') {
                result += sign * num;
                sign = stack.peek() * (c == '+' ? 1: -1);
                num = 0;
            } else if (c == '(') {
                stack.push(sign);
            } else if(c == ')') {
                stack.pop();
            }
        }

        result += sign * num; // for cases like 1 + 1
        return result;
    }

}
