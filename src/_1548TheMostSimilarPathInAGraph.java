import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class _1548TheMostSimilarPathInAGraph {
    /**
     * 1. DFS + Memoization
     *
     * Notice duplicate sub-problems from going through an example:
     * names: [ATL, PEK, LAX, DXB, HND]
     * target:[ATL, DXB, HND, LAX]
     *
     * pI: path Idx
     *
     * ATL, pI=0, cost + 0 = 0
     *     DXB, pI = 1, cost + 0 = 0
     *         ATL, pI = 2, cost + 1 = 1
     *             DXB, pI = 3, cost + 1 = 2, return 2
     *             LAX, pI = 3, cost + 0 = 1, return 1
     *         PEK, pI = 2, cost + 1 = 1
     *             DXB, pI = 3, SOLVED, return
     *             LAX, pI = 3, SOLVED, return
     *             HND, pI = 3, cost + 1 = 2, return 2;
     *     LAX, pI = 1, cost + 1 = 1
     *         ATL, pI = 2, solved, return
     *         PEK, pI = 2, solved, return
     *         HND, pI = 2, cost + 0 = 0;
     *             PEK, pI = 3, cost + ...
     *             LAX, pI = 3, SOLVED, return
     *
     * n - 1 <= m <= (n * (n - 1) / 2)
     * 1 <= len <= 100
     *
     * Time: O(m)
     * min Dist init: O(n * len) = O(n)
     * build adj lists: O(n + m) = O(m)
     * dfs: O(n * len) = O(n)
     * build answer: o(len) = O(1)
     *
     * Space: O(m)
     * minDist, nextCity: O(n * len) = O(n)
     * adjLists: O(n + m) = O(m)
     * dfs depth: O(len) = O(1)
     *
     */
    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        int targetLen = targetPath.length;
        int[][] minDist = new int[n][targetLen];
        int[][] nextCity = new int[n][targetLen];

        // Initialize (don't forget!!)
        for (int[] row : minDist) {
            Arrays.fill(row, -1);
        }

        // Build graph
        List<List<Integer>> adjLists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjLists.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            adjLists.get(road[0]).add(road[1]);
            adjLists.get(road[1]).add(road[0]);
        }

        // From each node, calc min cost and the 'next' city that gave the min cost
        int minCost = Integer.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < n; i++) {
            int cost = dfs(names, i, targetPath, 0,
                    minDist, nextCity, adjLists);
            if (cost < minCost) {
                minCost = cost;
                start = i;
            }
        }

        // Build the answer based on best next choice
        List<Integer> ans = new ArrayList<>();
        int city = start;
        while (ans.size() < targetPath.length) {
            ans.add(city);
            city = nextCity[city][ans.size() - 1];
        }

        return ans;
    }

    public int dfs(String[] names, int nameIdx, String[] targetPath, int pathIdx,
                   int[][] minDist, int[][] nextCity, List<List<Integer>> adjLists) {
        if (minDist[nameIdx][pathIdx] != -1) {
            return minDist[nameIdx][pathIdx];
        }

        int totalDist = (names[nameIdx].equals(targetPath[pathIdx])) ? 0 : 1;

        if (pathIdx == targetPath.length - 1) {
            return totalDist;
        }

        int min = Integer.MAX_VALUE;
        for (int neighbor : adjLists.get(nameIdx)) {
            int dist = dfs(names, neighbor, targetPath, pathIdx + 1,
                    minDist, nextCity, adjLists);
            if (dist < min) {
                min = dist;
                nextCity[nameIdx][pathIdx] = neighbor;
            }
        }

        totalDist += min;
        minDist[nameIdx][pathIdx] = totalDist;
        return totalDist;
    }

    /**
     * 2. Bottom-up DP
     */
    // 1.  Start with creating a graph based on the given roads. Mark 1 for each road pairs.
    // 2. With this graph in mind, now let's look at the actual problem - we need to come up with a path that has least edit distance in comparison to the targetPath. So, if we know the minimum possible edit distance till a given index of the targetPath (say i), we will be able to compute the minimum possible edit distance till (i+1). The only catch here is that we have to check which nodes from ith column, can be used to calculate (i+1)th column for a given cell. We could find this from our initial graph.
    // 3. The perspective is shifted from graph traversal to a DP optimization.
    // 4. For each index we find the minimum possible edit distance using our graph and DP column of index-1. We stop the iteration at index=0.
    // 5. Once we computed the DP matrix bottom, we have to build our solution top down.
    // 6. Once again we traverse our DP matrix starting with row 0 and find the index which gives us the minimum edit distance. We store it in prevInd variable.
    // 7. From there, we find out the node which is connected to the previous node and has the minimum edit distance. We add this to our output. Update our prevInd to the current minimum index (curMinInd).
    // 8. Keep repeating step 7 till we reach the end of the target path.
    public List<Integer> mostSimilar2(int n, int[][] roads, String[] names, String[] targetPath) {
        int[][] graph = new int[n][n];
        for (int[] road : roads) {
            int x = road[0];
            int y = road[1];
            graph[x][y] = graph[y][x] = 1;
        }
        int targetLength = targetPath.length;
        int[][] dp = new int[targetLength][n];
        for (int i = 0; i < n; i++) {
            String targetCity = targetPath[targetLength - 1];
            String currentCity = names[i];
            if (!targetCity.equals(currentCity)) {
                dp[targetLength - 1][i] = 1;
            }
        }
        for (int i = targetLength - 2; i >= 0; i--) {
            String targetCity = targetPath[i];
            for (int j = 0; j < n; j++) {
                String curCity = names[j];
                if (!curCity.equals(targetCity)) {
                    dp[i][j] = 1;
                }
                int minNextValue = Integer.MAX_VALUE;
                for (int x = 0; x < n; x++) {
                    if (graph[x][j] == 1) {
                        minNextValue = Math.min(dp[i + 1][x], minNextValue);
                    }
                }
                dp[i][j] += minNextValue;
            }
        }
        List<Integer> op = new ArrayList<>();
        int prevInd = 0;
        for (int i = 1; i < n; i++) {
            if (dp[0][i] < dp[0][prevInd]) {
                prevInd = i;
            }
        }
        op.add(prevInd);
        for (int i = 1; i < targetLength; i++) {
            int curMinIndex = -1;
            int curMinVal = Integer.MAX_VALUE;
            for (int x = 0; x < n; x++) {
                if (graph[x][prevInd] != 1) {
                    continue;
                }
                if (dp[i][x] < curMinVal) {
                    curMinVal = dp[i][x];
                    curMinIndex = x;
                }
            }
            op.add(curMinIndex);
            prevInd = curMinIndex;
        }
        return op;
    }

    /**
     * DFS + Memoization
     *
     * different definition of minDist and prevCity + pruning
     * but Why significantly slower?
     */
    public List<Integer> mostSimilar3(int n, int[][] roads, String[] names, String[] targetPath) {
        int targetLen = targetPath.length;
        int[][] minDist = new int[n][targetLen];
        int[][] prevCity = new int[n][targetLen];

        // Initialize
        for (int[] row : minDist) {
            Arrays.fill(row, targetLen + 1);
        }

        // Build graph
        List<List<Integer>> adjLists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjLists.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            adjLists.get(road[0]).add(road[1]);
            adjLists.get(road[1]).add(road[0]);
        }

        // From each node, calc min cost and the 'next' city that gave the min cost
        for (int i = 0; i < n; i++) {
            dfs(names, i, targetPath, 0,
                    minDist, prevCity, adjLists, 0, -1);

        }

        // Build the answer based on best next choice
        int minCost = targetLen;
        int end = 0;
        for (int i = 0; i < n; i++)  {
            if (minDist[i][targetLen - 1] < minCost) {
                minCost = minDist[i][targetLen - 1];
                end = i;
            }
        }


        List<Integer> ans = new ArrayList<>();
        ans.add(end);
        for (int i = targetLen - 1; i > 0; i--) {
            ans.add(prevCity[ans.get(ans.size() - 1)][i]);
        }
        Collections.reverse(ans);
        return ans;
    }

    public void dfs(String[] names, int nameIdx, String[] targetPath, int pathIdx,
                    int[][] minDist, int[][] prevCity, List<List<Integer>> adjLists,
                    int dist, int prev) {

        int totalDist = dist + ((names[nameIdx].equals(targetPath[pathIdx])) ? 0 : 1);
        if (totalDist >= minDist[nameIdx][pathIdx]) {
            return;
        }

        minDist[nameIdx][pathIdx] = totalDist;
        prevCity[nameIdx][pathIdx] = prev;

        if (pathIdx == targetPath.length - 1) {
            return;
        }

        for (int neighbor : adjLists.get(nameIdx)) {
            dfs(names, neighbor, targetPath, pathIdx + 1,
                    minDist, prevCity, adjLists, totalDist, nameIdx);
        }

        return;
    }

    /**
     * Follow up:
     *
     * https://leetcode.com/problems/the-most-similar-path-in-a-graph/discuss/790243/Followup-Question-From-Google
     */


}
