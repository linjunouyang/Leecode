import java.util.HashMap;

/**
 * Forgot to handle 0
 * Should ask about 0 during interview
 * Walk through some cases before starting to code
 *
 * Similar Questions:
 * 62 Unique Paths
 * 70 Climbing Stairs
 * 509 Fibonacci Number
 *
 */
public class _0091DecodeWays {
    HashMap<Integer, Integer> memo = new HashMap<>();

    /**
     * 1. Recursion with memoization
     *
     * Time Complexity: O(N), where N is length of the string.
     * Memoization helps in pruning the recursion tree and hence decoding for an index only once.
     *
     * Space Complexity: O(N).
     * There would be an entry for each index value.
     * The recursion stack would also be equal to the length of the string.
     */
    public int numDecodings(String s) {
        if (s == null) {
            return 0;
        }
        return recursiveWithMemo(0, s);
    }

    /**
     * Can't combine first and third if condition: '0' '12'
     */
    private int recursiveWithMemo(int index, String str) {
        if (index == str.length()) {
            return 1;
        }

        if (str.charAt(index) == '0') {
            return 0;
        }

        if (index == str.length() - 1) {
            return 1;
        }

        if (memo.containsKey(index)) {
            return memo.get(index);
        }

        int ans = recursiveWithMemo(index + 1, str);
        if (Integer.parseInt(str.substring(index, index + 2)) <= 26) {
            ans += recursiveWithMemo(index + 2, str);
        }

        memo.put(index, ans);

        return ans;
    }

    /**
     * 2. Dynamic Programming:
     *
     * dp[i] = Number of ways of decoding substring s[: ith char (s[i - 1])].
     *
     * dp[0] = 1 (baton to be passed)
     * dp[1] = 0 (if s[i-1] is 0) OR 1
     * dp[i] = ([i - 1] == 0 ? 0 : dp[i - 1]) + (10 <= s[i-2]s[i-1] <= 26 ? dp[i - 2] : dp[i])
     *
     */
    public int numDecodings2(String s) {
        if(s == null || s.length() == 0) {
            // Question states that it's non-empty string
            return 0;
        }

        // DP array to store the subproblem results
        int[] dp = new int[s.length()];
        dp[0] = s.charAt(0) == '0' ? 0 : 1;

        for(int i = 1; i < s.length(); ++i) {
            // Check if successful single digit decode is possible.
            if(s.charAt(i) != '0') {
                dp[i] += dp[i-1];
            }

            // Check if successful two digit decode is possible.
            int twoDigit = (s.charAt(i - 1) - '0') * 10  + s.charAt(i) - '0';
            if(twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += i >= 2 ? dp[i - 2] : 1;
            }
        }
        return dp[s.length() - 1];
    }

    /**
     * 3. DP with space optimization
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     */
    public int numDecodings3(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }

        // DP array to store the subproblem
        int prevPrev = 1;
        // Ways to decode a string of size 1 is 1. Unless the string is '0'.
        // '0' doesn't have a single digit decode.
        int prev = s.charAt(0) == '0' ? 0 : 1;

        for(int i = 2; i <= s.length(); i += 1) {
            int curr = 0;

            // Check if successful single digit decode is possible.
            if(s.charAt(i-1) != '0') {
                curr += prev;
            }

            // Check if successful two digit decode is possible.
            int twoDigit = Integer.valueOf(s.substring(i-2, i));
            if(twoDigit >= 10 && twoDigit <= 26) {
                curr += prevPrev;
            }

            prevPrev = prev;
            prev = curr;
        }
        return prev;
    }

    /**
     * 4.
     *
     * Top-down:
     * https://leetcode.com/problems/decode-ways/discuss/30625/Sharing-my-java-memoized-solution
     *
     */

}
