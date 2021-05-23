import java.util.*;

public class _1042FlowerPlantingWithNoAdjacent {
    /**
     * 1. Greedy?
     *
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/discuss/290858/JavaC%2B%2BPython-Greedily-Paint
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/discuss/290858/JavaC%2B%2BPython-Greedily-Paint
     *
     *
     * Time: O(N) with O(paths) = O(1.5N)
     * Space: O(N)
     */
    public int[] gardenNoAdj(int n, int[][] paths) {
        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int[] path : paths){
            int x = path[0] - 1; //Due to 1-based indexing
            int y = path[1] - 1; //Due to 1-based indexing
            // Undirected edge
            graph.get(x).add(y);
            graph.get(y).add(x);
        }
        // res[i] represents color of garden i+1
        int[] res = new int[n];

        //Now run graph painting algorithm
        for (int i = 0; i < n; i++){
            boolean[] colorUsage = new boolean[5]; //Use 5 instead of 4 so we can easily use 1-based indexing of the garden colors
            for (int nei : graph.get(i)){
                colorUsage[res[nei]] = true; //Mark the color as used if neighbor has used it before.
            }
            //Now just use a color that has not been used yet
            for (int c = 4; c >= 1; c--){
                if (!colorUsage[c]) {
                    res[i] = c; //so let's use that one
                }
            }
        }

        return res;
    }

    /**
     * 2.
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/discuss/305823/Java-solution-or-12-ms
     *
     * Loop through edges
     * determine the colour of nodes using information about previously determined nodes.
     * Thus, lower numbered nodes are determined first before higher numbered nodes.
     * Think of the colouring as bubbling up to the top
     */
    public int[] gardenNoAdj2(int n, int[][] paths) {
        int[] result = new int[n];
        Arrays.fill(result, 1);

        boolean stop = false;
        while (!stop) {
            stop = true;
            for(int[] edge: paths) {
                int u = Math.min(edge[0], edge[1]);
                int v = Math.max(edge[0], edge[1]);
                if (result[u - 1] == result[v - 1]) {
                    stop = false;
                    result[v - 1] = result[v - 1] == 4 ? 1 : result[v - 1] + 1;
                }
            }
        }
        return result;
    }

    /**
     * 3. Backtrack
     *
     * 'As the constraints(M=4 and Max Degree=3) always guarantee an extra color to paint for any vertex, the code will not backtrack at all and will reach the solution in the first take. This is exactly why the Greedy Algorithm works for this problem.'
     *
     * Not idea because recursive call stack
     *
     * Mine version is below, for a more concise version:
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/discuss/711468/JAVA-BackTracking-(but-wont-backtrack-at-all)
     *
     */
    public int[] gardenNoAdj3(int n, int[][] paths) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int[] path : paths) {
            adjList.putIfAbsent(path[0], new ArrayList<>());
            adjList.get(path[0]).add(path[1]);
            adjList.putIfAbsent(path[1], new ArrayList<>());
            adjList.get(path[1]).add(path[0]);
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            if (res[i] == 0) {
                backtrack(adjList, i, res, 0);
            }
        }
        return res;
    }

    private boolean backtrack(HashMap<Integer, List<Integer>> adjList, int node, int[] res, int lastColor) {
        if (res[node] != 0) {
            if (res[node] == lastColor) {
                return false;
            } else {
                return true;
            }
        }

        for (int color = 1; color <= 4; color++) {
            if (color == lastColor) {
                continue;
            }
            res[node] = color;
            boolean isValid = true;
            for (int next : adjList.getOrDefault(node + 1, new ArrayList<>())) {
                isValid = isValid && backtrack(adjList, next - 1, res, color);
            }
            if (isValid) {
                break;
            }
        }

        return true;
    }
}
