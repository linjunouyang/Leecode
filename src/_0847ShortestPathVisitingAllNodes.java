import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class _0847ShortestPathVisitingAllNodes {
    /**
     * 0. DFS
     *
     * https://leetcode.com/submissions/detail/514035483/
     * Can't use hashset to represent path -> we might visit same nodes multiple times
     * visited.remove() will remove those revisited nodes, which leads to wrong answer
     *
     * HashMap visitCount + ArrayList Path -> can only pass one case. TLE
     * https://leetcode.com/submissions/detail/514034130/
     * My original idea:
     * 1) visit all unvisited neighbors
     * 2) visited visited neighbors, which have unvisited neighbors (second-level neighbor to cur node)
     * But this logic is wrong for graph that have cycles, e.g. graph = [[1],[0,2,4],[1,3,4],[2],[1,2]]
     * start from 0, we will end up looping in case 2 (between 1 and 2),
     * even tho 1&2 are visited, they both have unvisited neighbor 4
     *
     * https://leetcode-cn.com/problems/shortest-path-visiting-all-nodes/solution/java-bao-li-shen-sou-jian-zhi-kan-dao-ti-jie-du-me/
     * 如果在同一条路径上，同一有向边只允许被访问一次。否则可能会导致死循环。
     */
    private int[][] graph;
    private int totalNodeCount;  // 总节点数
    private int ansMinLen = Integer.MAX_VALUE;  // 访问所有节点的最小路径长度

    // 计算已经访问过的不同节点数（同一节点出现多次，就计一次）
    private int calcVisitedCount(int[] visitedNodeCountArr) {
        int ans = 0;
        for (int count : visitedNodeCountArr) {
            if (count > 0) {
                ans++;
            }
        }

        return ans;
    }

    /**
     * 递归暴力寻找访问所有节点的最短路径
     * @param curNode 当前节点
     * @param visitedNodeCountArr 访问过的节点出现的次数（一个节点可能被多次访问）
     * @param level 当前递归深度（也就是路径长度）
     * @param edgeVisited 标记访问过的有向边，避免同一条路径上重复访问同一有向边
     */
    private void backTrack(int curNode, int[] visitedNodeCountArr, int level, boolean[][] edgeVisited) {
        if (level >= ansMinLen) {
            return;
        }

        visitedNodeCountArr[curNode]++;

        int visitedNodeCount = calcVisitedCount(visitedNodeCountArr);
        if (totalNodeCount - visitedNodeCount + level >= ansMinLen) {
            // 剩下的未访问的节点数 + 当前的路径 如果已经超过当前最小值，说明当前路径长度不可能更小了，直接返回即可
            visitedNodeCountArr[curNode]--;
            return;
        }

        if (visitedNodeCount == totalNodeCount) {
            ansMinLen = level;
            visitedNodeCountArr[curNode]--;
            return;
        }

        for (int nextNode : graph[curNode]) {
            if (edgeVisited[curNode][nextNode]) {
                // 当前路径上，访问过的有向边避免重复访问，防止死循环
                continue;
            }

            edgeVisited[curNode][nextNode] = true;
            backTrack(nextNode, visitedNodeCountArr, level + 1, edgeVisited);
            edgeVisited[curNode][nextNode] = false;
        }

        visitedNodeCountArr[curNode]--;
    }

    public int shortestPathLength(int[][] graph) {
        this.graph = graph;
        totalNodeCount = graph.length;

        for (int i = 0; i < totalNodeCount; i++) {
            backTrack(i, new int[totalNodeCount], 0, new boolean[totalNodeCount][totalNodeCount]);
        }

        return ansMinLen;
    }

    /**
     * Another DFS (tricky, needs improvement)
     */
    public int shortestPathLength01(int[][] graph) {
        int n = graph.length;
        if (n <= 1) {
            return 0;
        }
        int allVisited = (1 << n) - 1;
        int res = Integer.MAX_VALUE;
        boolean[][] visited = new boolean[n][allVisited + 1];
        for (int i = 0; i < n; i++) {
            int state = 1 << i;
            Integer[][] dp = new Integer[n][allVisited + 1];
            res = Math.min(res, helper(graph, i, state, dp, visited, allVisited));
        }
        return res;
    }

    public int helper(int[][] graph, int cur, int state, Integer[][] dp, boolean[][] visited, int allVisited) {
        if (state == allVisited) {
            return 0;
        }
        if (dp[cur][state] != null) {
            return dp[cur][state];
        }

        int res = 1000000007;
        for (int next : graph[cur]) {
            int nextState = state | (1 << next);
            if (visited[next][nextState]) {
                continue;
            }
            visited[next][nextState] = true;
            int temp = helper(graph, next, nextState, dp, visited, allVisited);
            res = Math.min(res, temp + 1);
            visited[next][nextState] = false;
        }

        if (res == 1000000007) {
            return res;
        }

        dp[cur][state] = res;
        return res;
    }

    /**
     * 1. BFS
     *
     * shortest path -> BFS
     * ------
     * States:
     * In traditional BFS, we use a hashset to remember visited nodes
     * to prevent revisiting same nodes -> endless loop.
     * Visited nodes is the 'state' that we need to remember, need to avoid revisiting.
     *
     * In this question, we can revisit same node if the node might leads to unvisited nodes
     * The 'state' is [visited nodes && curr node]
     * ------
     * we can start and stop at any node
     * -> multiple start BFS
     *
     * Time: O(n * n * 2^n)
     * Every state enters and leaves queue once,
     * total time complexity depends on number of states
     *
     * each state takes O(n) because of toString()
     *
     * Space: O(n * n * 2^n)
     */
    class State {
        int curNode;
        int visited;

        public State(int curNode, int visited) {
            this.curNode = curNode;
            this.visited = visited;
        }

        public String toString() {
            return curNode + "," + visited;
        }
    }


    public int shortestPathLength2(int[][] graph) {
        int nodes = graph.length;
        int allNodesMask = (1 << nodes) - 1; // minus 1

        Deque<State> queue = new ArrayDeque<>();
        HashSet<String> states = new HashSet<>();
        for (int start = 0; start < nodes; start++) {
            State state = new State(start, 1 << start);
            queue.offer(state);
            states.add(state.toString());
        }

        int minLen = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State state = queue.poll();
                if (state.visited == allNodesMask) {
                    return minLen;
                }
                for (int nextNode: graph[state.curNode]) {
                    int nextVisited = state.visited | (1 << nextNode);
                    State nextState = new State(nextNode, nextVisited);
                    if (!states.contains(nextState.toString())) {
                        states.add(nextState.toString());
                        queue.offer(nextState);
                    }
                }
            }
            minLen++;
        }

        return minLen;
    }
}
