/**
 * 4th Solution
 * Time complexity analysis
 */
public class _0014LongestCommonPrefix {
    /**
     * 1. Horizontal Scanning
     *
     * String isEmpty(): returns true if the length of the string is 0
     *
     * Time complexity: O(S)
     * S is the sum of all characters in all strings.
     *
     * In the worst case all n strings are the same.
     * The algorithm compares the string S1S1 with the other strings [S2 ... Sn]
     * There are S character comparisons,
     * where S is the sum of all characters in the input array.
     *
     * Space complexity: O(1)
     *
     *
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null && strs.length == 0) {
            return "";
        }

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    /**
     * 2. Vertical Scanning
     *
     * Inspiration from horizontal scanning:
     * Imagine a very short string is at the end of the array.
     * The above approach will still do SS comparisons.
     *
     * One way to optimize this case is to do vertical scanning.
     * We compare characters from top to bottom on the same column (same character index of the strings)
     * before moving on to the next column.
     *
     * __________
     * Time complexity: O(S)
     * S is the sum of all characters in all strings.
     * In the worst case there will be n equal strings with length m
     * and the algorithm performs S = m⋅n character comparisons.
     *
     * Even though the worst case is still the same as Approach 1,
     * in the best case there are at most n * minLen comparisons
     * where minLen is the length of the shortest string in the array.
     *
     *
     * Space complexity: O(1)
     * __________
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // scan each column (character)
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);

            // compare that column for every row
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    /**
     * 3. Divide and Conquer
     *
     * Intuition: associative property of LCP operation
     * LCP(S1 ... Sn) = LCP(LCP(S1 ... Sk), LCP(Sk+1 ... Sn))
     *
     * In worst case we have n equal strings with length m:
     *
     * Time complexity?: O(S)
     * T(n) = 2 * T(n/2) + O(m)
     * T(n) = (2^x)T(n/(2^x)) + [2^0 * O(m) + 2^1 * O(m) + ... + 2^(x-1) * O(m)]
     * let n = 2 ^ x, x = logn
     * T(n) = n + [2 ^ logn - 1] * O(m)
     * T(n) = n + (n - 1) * O(m)
     * T(n) = O(mn)
     *
     * Space complexity: O(m * logn)
     * Each recursive call need m space to store the result.
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    /**
     * A helper function for finding longest common prefix in (strs[left], strs[right])
     * by divide and conquer
     *
     * Suppose len = right - left + 1;
     *
     * Time complexity?: len * O(log(len))
     * Space complexity?: O(logn)
     *
     * @param strs
     * @param left
     * @param right
     * @return
     */
    private String longestCommonPrefix(String[] strs, int left, int right) {
        if (left == right) {
            return strs[left];
        } else {
            int mid = left + (right - left) / 2;
            String leftPrefix = longestCommonPrefix(strs, left, mid);
            String rightPrefix = longestCommonPrefix(strs, mid + 1, right);
            return commonPrefix(leftPrefix, rightPrefix);
        }
    }

    /**
     * Find longest prefix from two strings
     *
     * Time complexity?: O(min length of two strings)
     * Space complexity?: O(1)
     *
     * @param leftPrefix
     * @param rightPrefix
     * @return
     */
    private String commonPrefix(String leftPrefix, String rightPrefix) {
        if (leftPrefix == null || rightPrefix == null) {
            return "";
        }

        int end = Math.min(leftPrefix.length(), rightPrefix.length());

        for (int i = 0; i < end; i++) {
            if (leftPrefix.charAt(i) != rightPrefix.charAt(i)) {
                return leftPrefix.substring(0, i);
            }
        }

        // a concise way to avoid ternary expression
        return leftPrefix.substring(0, end);
    }
}
