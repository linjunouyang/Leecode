import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Ask interviewee what does valid mean?
 * Initially I thought only ()()()() is valid
 * but "()(())" answer is 6 instead of 2
 *
 * ()(() answer is 2
 *
 * -> here valid means contiguous, every ( has a later ) to correspond
 *
 */
public class _0032LongestValidParentheses {
    /**
     * 1. Brute Force (TLE)
     *
     *
     * Time complexity: O(n ^ 3)
     * Space complexity: O(n)
     *
     */
    public int longestValidParentheses(String s) {
        int maxLen = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j < s.length(); j += 2) {
                if (isValid(s, i, j)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    private boolean isValid(String s, int start, int end) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    /**
     * 2. 1D DP
     *
     * dp[i]: the length of the longest valid substring ending at ith index
     *
     * Now, it's obvious that the valid substrings must end with ')’.
     * we update the dp only when ')’ is encountered.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int longestValidParentheses2(String s) {
        int[] dp = new int[s.length()];
        int open = 0;
        int max = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            }
            if (s.charAt(i) == ')' && open > 0) {
                // matches found
                dp[i] = 2+ dp[i-1];
                // add matches from previous
                // ()(())
                if(i-dp[i]>=1) {
                    dp[i] += dp[i-dp[i]];
                }
                open--;
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 3. Stack
     *
     * Stack and ArrayDeque are implemented with resizable array, and every time it is full,
     * all of the elements will be copied to a new allocated array, which is costly if this process is invoked repeatedly and thus leads to TLE on large test cases.
     * Specifying an initial capacity can prevent this from happening.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int longestValidParentheses3(String s) {
        int max = 0;
        // last one unpaired ) and all unpaired (
        Deque<Integer> stack = new ArrayDeque<>();
        // You can imagine there is a ) at index -1 position
        // i.e. s[-1] = ')'
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                // 1) if there is a match (i.e. "()"), then we can pop the stack
                // and update the max length by (i - indices.top())
                // (because the string in (indices.top(), i] is a valid parenthese)
                // 2) if there isn't a match (i.e. "))"), then we can substitute
                // the top of the stack by the latest one
                // -> no matter "()" or "))", we all need to pop the stack
                stack.pop();
                // if the stack is empty, it means a "))" occurs
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    // a "()"
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }

    /**
     * Less tricky
     */
    public int longestValidParentheses31(String s) {
        boolean valid[] = new boolean[s.length()];

        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') stack.push(i);
            else if (!stack.isEmpty()) {
                valid[stack.pop()] = valid[i] = true;
            }
        }

        return longest(valid);
    }

    private int longest(boolean[] valid) {
        int max = 0;
        int len = 0;

        for (boolean v : valid) {
            if (v) {
                len++;
                max = Math.max(max, len);
            } else {
                // ()(() expected 2 instead of 4
                len = 0;
            }
        }

        return max;
    }

    /**
     * 4. Scan frontwards and backwards
     *
     * Similar: maximum sum of subsequence
     *
     * When right parentheses are more than left parentheses in the forward pass, we can discard previous parentheses.
     *
     * In the backward pass, when left parentheses are more than right parentheses, we can discard previous parentheses.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int longestValidParentheses4(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right > left) {
                // can't change it to else and delete 2nd loop
                // e.g. (() output 0 but expected 2
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxlength;
    }
}
