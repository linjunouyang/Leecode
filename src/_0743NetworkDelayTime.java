import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class _0743NetworkDelayTime {
    /**
     * 1. Dijkstra
     *
     * Time: O(V logE + E logE) = O(E logE) = O(E logV^2) = O(E logV)
     * Build AdjLists: O(V + E)
     * total poll: O(V logE) (process each node once)
     * total offer: O(E logE) (dumping every neighbor (which is associated with an edge) into PQ)
     *
     * Space: O(E)
     * adjLists: O(V + E)
     * minTime: O(V)
     * minHeap: O(E)
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adjLists = new ArrayList<>();  // [neighbor, time]
        for (int i = 0; i <= n; i++) {
            // node labelled as [1, n]
            adjLists.add(new ArrayList<>());
        }

        for (int[] edge : times) {
            int source = edge[0];
            int target = edge[1];
            int time = edge[2];

            adjLists.get(source).add(new int[]{target, time});
        }

        HashMap<Integer, Integer> minTime = new HashMap<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((e1, e2) -> {
            // [node, time]
            return e1[1] - e2[1];
        });
        minHeap.offer(new int[]{k, 0});

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int currNode = curr[0];
            int currTime = curr[1];

            if (minTime.containsKey(currNode)) {
                continue;
            }
            minTime.put(currNode, currTime);

            for (int[] edge : adjLists.get(currNode)) {
                int nextNode = edge[0];
                int nextTime = currTime + edge[1];
                if (minTime.containsKey(nextNode)) {
                    continue;
                }
                minHeap.offer(new int[]{nextNode, nextTime});
            }
        }

        if (minTime.size() < n) {
            return -1;
        }

        int maxTime = 0;
        for (int time : minTime.values()) {
            maxTime = Math.max(maxTime, time);
        }
        return maxTime;
    }

    /**
     * 2. Bellman-Ford
     *
     * Time: O(VE)
     * Space: O(V)
     */
    public int networkDelayTime2(int[][] times, int N, int K) {
        if (times == null || times.length == 0) {
            return -1;
        }

        /* Subproblem: dp(i) represents minimum distance from K to i (iteratively update dp(i) when we find another shorter distance from K to i)*/
        int[] dp = new int[N + 1];

        /* Base case 1: initialize MAX value as initial minimum distance from K to every point*/
        /* Base case 2: initialize 0 as min distance to K itself*/
        for (int i = 0; i < N + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[K] = 0;

        /* traverse all destinations*/
        for (int i = 0; i < N; i++) {
            for (int[] edge : times) {
                int u = edge[0], v = edge[1], w = edge[2];

            /* if dp[u] (starting point of current edge) has already been visited, and new distance from u to v is smaller than previous saved distance
            we should update minimum distance from u to v*/
                if (dp[u] != Integer.MAX_VALUE && dp[v] > dp[u] + w) {
                    dp[v] = dp[u] + w;
                }
            }
        }

        /* after getting minimum distance (travel time) to all points, we should retrieve the max distance from these minimum distance,
         * to ensure that we can travel all points in a specific travel time
         */
        int result = 0;
        for (int i = 1; i <= N; i++) {
            result = Math.max(result, dp[i]);
        }
        /* if we do not visit all points, we should return -1*/
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
