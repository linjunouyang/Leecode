import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class _0150EvaluateReversePolishNotation {
    /**
     * 1. Stack
     *
     * operators: +-* /
     * operands: num
     *
     * String -> Integer: Integer.valueOf(str)
     * Integer -> String: String.valueOf(num)
     *
     * 9 / 5 = 1 (1.8)
     * truncated to a less than or equal number = truncation is towards 0 (1 is closer to 0 than 1.8 is)
     *
     * -9 / 5
     * truncated to a less than or equal number: -2
     * truncation is towards 0: -1
     *
     * Time: O(n * avg len)
     * Space: O(n)
     */
    public int evalRPN(String[] tokens) {
        // Or String operators = "+-*/"
        HashSet<String> operators = new HashSet<>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");

        Deque<String> stack = new ArrayDeque<>();
        for (String token : tokens) {
            if (operators.contains(token)) {
                int operand2 = Integer.valueOf(stack.pop());
                int operand1 = Integer.valueOf(stack.pop());
                int res = 0;
                if (token.equals("+")) {
                    res = operand1 + operand2;
                } else if (token.equals("-")) {
                    res = operand1 - operand2;
                } else if (token.equals("*")) {
                    res = operand1 * operand2;
                } else {
                    res = operand1 / operand2; // since expression is always valid, no worry for operand2 = 0;
                }
                stack.push(String.valueOf(res));
            } else {
                stack.push(token);
            }
        }

        return Integer.valueOf(stack.pop());
    }

    /**
     * https://leetcode.com/problems/evaluate-reverse-polish-notation/solution/
     *
     * Using lambda
     */
}
