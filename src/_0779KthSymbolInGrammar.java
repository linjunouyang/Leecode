/**
 * 0 -> 01
 * 1 -> 10
 * if we go right, idx (k) and curNum will change
 */
public class _0779KthSymbolInGrammar {
    /**
     * 0. Brute Force
     *
     * Make each row
     * Time: 2^0 + ... + 2^(N - 1) -> O(2 ^ N)
     * Space: O(2^N) for the last row
     */

    /**
     * 1. Recursion (top down vs bottom up)
     *
     * Math.row return double
     *
     * bottom-up
     * from row 1 to row n
     * when we reach the final ans, we simply recursively return, no other processing
     *
     * Time: O(N)
     * Space: O(N)
     */
    public int kthGrammar(int N, int K) {
        return helper(N, K, 0);
    }

    private int helper(int height, int k, int curr) {
        if (height == 1) {
            return curr;
        }

        int total = (int) Math.pow(2, height - 1);

        // parent -> child: needs to make a choice
        if (k <= total / 2) {
            // go left, same digit
            return helper(height - 1, k, curr); // child -> parent: no choices, just return to parent
        } else {
            // go right, switch
            int next = curr == 0 ? 1 : 0;
            return helper(height - 1, k - total / 2, next); // child -> parent: no choices, just return to parent
        }
    }

    // top-down
    // from row n to row 1
    // when we start to return, we need to adjust (here: switch) result from previous problem
    public int kthGrammar11(int N, int K)
    {
        // base case: reach row 1
        if (N == 1) {
            return 0;
        }

        // child -> parent: no choice to make
        // current row's kth digit depends on last row's (k/2)th digit.
        // since this k digit is 1-indexed, we add 1 to k and divide by 2 to get the ceiling.
        int parent = kthGrammar(N-1, (K+1)/2);

        // parent -> child: needs to make a choice
        // if current k is even,
        // then kth digit must be in the right side of its parent, SWITCH
        int digit = parent;
        if (K % 2 == 0) {
            digit = parent == 0? 1: 0;
        }
        return digit;
    }

    /**
     * 2. Iteration
     *
     * Time: O(N)
     * Space: O(1)
     */
    public int kthGrammar2(int N, int K) {
        int height = N;
        int idx = K;
        int curNum = 0;
        int total = (int)Math.pow(2, height - 1); // number of leaf nodes in the current subtree

        while (height > 1) {
            // decide to go left or right
            if (idx > total / 2) {
                idx = idx - total / 2;
                curNum = curNum == 1 ? 0 : 1;
            }

            // go to a subtree
            height--;
            total /= 2;
        }
        return curNum;
    }
}
