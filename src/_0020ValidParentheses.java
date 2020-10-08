import java.util.ArrayDeque;
import java.util.Deque;

/**
 * for each requires array or Java.lang.Iterable, not string
 *
 * should ask for clarifications about 'closed in the correct order':
 * They could be chars between a pair, but this middle part should also be valid
 * visualize the process of eliminating every pair (open and close should be adjacent), if empty in the end, then valid
 *
 * parentheses / round brackets ()
 * square brackets / box brackets []
 * braces / curly brackets {}
 *
 */
public class _0020ValidParentheses {
    /**
     * 1.
     *
     * Why not maintain a separate counter for the different types of parenthesis?
     * [{]
     * If we simply keep counters here, then as soon as we encounter the closing square bracket,
     * we would know there is an unmatched opening square bracket available as well.
     * But, the closest unmatched opening bracket available is a curly bracket and not a square bracket and hence the counting approach breaks here.
     *
     * Why stack?
     * Some sub-expression of a valid expression is also valid -> recursive nature
     * OR think of outside pair as the initial element at the bottom of stack
     * every time we encounter a new opening bracket after an previous unmatched opening,
     * it's like pushing a new element into the stack
     *
     * Time: O(n)
     * Space: O(n)
     *
     * A concise version
     * https://leetcode.com/problems/valid-parentheses/discuss/9178/Short-java-solution
     * instead of push current char into stack, we push expected closing bracket for current bracket
     * this way, we don't have to determine whether current bracket matches.
     */
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != c) {
                // in real-world, s might contain other chars
                // so we need to only check this if branch if c is a closing bracket
                return false;
            }
        }
        return stack.isEmpty();
    }
}
