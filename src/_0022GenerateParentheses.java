import java.util.ArrayList;
import java.util.List;

public class _0022GenerateParentheses {
    /**
     * 1. Brute Force
     *
     * Generate all 2 ^ (2n) sequences, and then validate
     *
     * Time complexity: O(2 ^ (2n) * n)
     * creation and validation both takes O(n)
     *
     * Space complexity: O(2 ^ (2n) * n)
     * every sequence could be valid, but this is not a tight bound
     */

    /**
     * 2. Backtracking
     *
     * Only add ( or ) when we know it's still valid
     *
     * When to add open bracket? If we still have one left
     * When to add closing bracket? If it would not exceed num of opening brackets.
     *
     *
     * StringBuilder.toString() -> return new String(value, 0, count) -> Arrays.copyOfRange
     * ->
     *
     * Time complexity: O(n * 2 ^ (2n))
     * a tree with branching of b (b = 2 here) and max depth of h (h = 2n here)
     * total number of nodes in worst case: 1 + b + b ^ (h - 1) = (b ^ h - 1) / (b - 1) = O(b ^ h)
     *
     * for all the leaf nodes, we call toString(), which takes O(n) time
     *
     * Space: O(n)
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        if (n <= 0) {
            // although n >= 1 is guaranteed.
            return ans;
        }
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder sb, int open, int close, int max){
        if (sb.length() == max * 2) {
            ans.add(sb.toString());
            return;
        }

        if (open < max) {
            sb.append('(');
            backtrack(ans, sb, open+1, close, max);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (close < open) {
            sb.append(")");
            backtrack(ans, sb, open, close + 1, max);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 3. BFS (never mind)
     * Technically, we could use BFS,
     * but it's not efficient
     * space:need to store all intermediate strings
     * time: for every step, we have to examine previous intermediate result
     * to help us decide can we place a left/right parentheses, or should we stop
     */
}
