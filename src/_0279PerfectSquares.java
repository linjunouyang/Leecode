import java.lang.reflect.Array;
import java.util.*;

/**
 * Dynamic Programming, Math, BFS
 *
 * 6.26 First Failed to come up with dynamic programming recursion relationship
 *
 * there is also an static DP and math solution
 *
 */
public class _0279PerfectSquares {
    /**
     * 1. Dynamic Programming
     *
     * dp[0] = 0
     * dp[1] = dp[0]+1 = 1
     * dp[2] = dp[1]+1 = 2
     * dp[3] = dp[2]+1 = 3
     * dp[4] = Min{ dp[4-1*1]+1, dp[4-2*2]+1 }
     *       = Min{ dp[3]+1, dp[0]+1 }
     *       = 1
     * dp[5] = Min{ dp[5-1*1]+1, dp[5-2*2]+1 }
     *       = Min{ dp[4]+1, dp[1]+1 }
     *       = 2
     * dp[6] = Min{ dp[6-1*1]+1, dp[6-2*2]+1 }
     *       = Min{ dp[5]+1, dp[2]+1 }
     *       = 3
     * dp[7] = Min{ dp[7-1*1]+1, dp[7-2*2]+1 }
     *       = Min{ dp[6]+1, dp[3]+1}
     *       = 4
     * dp[8] = 2
     * 						.
     * 						.
     * dp[13] = Min{ dp[13-1*1]+1, dp[13-2*2]+1, dp[13-3*3]+1 }
     *        = Min{ dp[12]+1, dp[9]+1, dp[4]+1 }
     *        = 2
     * 						.
     * 						.
     * 						.
     * dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1
     *
     * Time complexity:
     * dp[] array has O(n) entries
     *
     */
    public int numSquares1(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j*j] + 1);
            }
        }
        return dp[n];
    }

    /**
     * 2. BFS
     *
     *
     * four-square theorem (every natural number can be represented as the sum of four integer squares)
     *
     * Time complexity: ?
     * There will be at most n numbers into the queue,
     * every inner for loop takes sqrt(n) steps
     * total: n * sqrt(n) ?
     *
     * another bound:
     * For the time complexity: would it be n²?
     *
     * The depth of the BFS is at most 4 by the four-square theorem (every natural number can be represented as the sum of four integer squares)
     * At every dept there will be at most sqrt(n) children, because there's at most sqrt(n) perfect squares that are smaller than n
     * Therefore, (sqrt(n))^4 = n²
     *
     * Space complexity: O(n) because when v > n, we don't store v.
     *
     * Runtime: 61 ms, faster than 22.12% of Java online submissions for Perfect Squares.
     * Memory Usage: 38.4 MB, less than 12.40% of Java online submissions for Perfect Squares.
     *
     */
    public int numSquares2(int n) {
        // for BFS
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(0);

        // avoid adding duplicate elements to queue
        Set<Integer> visited = new HashSet<>();
        visited.add(0);

        int depth = 0;

        while (!q.isEmpty()) {
            // represents a level search
            int size = q.size();
            depth++;

            while(size-- > 0) {
                // go through all the nodes in the current level
                // add next level's elements
                int u = q.poll();

                for (int i = 1; i * i <= n; i++) {
                    int v = u + i * i;
                    if (v == n) {
                        return depth;
                    }

                    if (v > n) {
                        break;
                    }

                    if (!visited.contains(v)) {
                        // if contains, v can be achieved with less numbers
                        // during a previous level search
                        q.offer(v);
                        visited.add(v);
                    }
                }
            }
        }

        // actually not reachable
        return depth;
    }
}
