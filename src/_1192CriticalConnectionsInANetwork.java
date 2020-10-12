import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _1192CriticalConnectionsInANetwork {
    /**
     * 1. DFS
     *
     * https://leetcode.com/problems/critical-connections-in-a-network/discuss/401340/Clean-Java-Solution-With-Explanation!!!-Great-Question!
     *
     * connected graph:
     * An edge is a critical connection, if and only if it is not in a cycle.
     *
     * hashmap in java se 8 use red-black tree implementation.
     * Even with a array-base hashmap, if initial size is not specified,
     * rehashing also add more than constant time complexity.
     * Generally speaking, with test case in leetcode, hashmap can be 6-time slower than array.
     *
     * DFS time complexity is O(|E| + |V|), attempting to visit each edge at most twice. (the second attempt will immediately return.)
     * As the graph is always a connected graph, |E| >= |V|.
     *
     * So, time complexity = O(|E|).
     *
     * Space complexity = O(graph) + O(visited) + O(timeStamp) + O(recursive callstack) = O(E)
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<Integer>[] graph = new ArrayList[n]; // not ArrayList<Integer>
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> oneConnection :connections) {
            graph[oneConnection.get(0)].add(oneConnection.get(1));
            graph[oneConnection.get(1)].add(oneConnection.get(0));
        }

        int timer[] = new int[1];
        List<List<Integer>> results = new ArrayList<>();
        // We don't need "visited[]". If timestampAtThatNode[I] == 0, then it is not visited, given timer[] start with 1.
        boolean[] visited = new boolean[n];
        int []timeStampAtThatNode = new int[n];

        // input is a connected graph, so no need to loop over all nodes
        criticalConnectionsUtil(graph, -1, 0, timer, visited, results, timeStampAtThatNode);
        return results;
    }


    public void criticalConnectionsUtil(List<Integer>[] graph, int parent, int node, int timer[], boolean[] visited, List<List<Integer>> results, int []timeStampAtThatNode) {
        visited[node] = true;
        timeStampAtThatNode[node] = timer[0]++;
        int currentTimeStamp = timeStampAtThatNode[node];

        for (int oneNeighbour : graph[node]) {
            if (oneNeighbour == parent) {
                /**
                 * Why do we write a special case for parent? before iterating every child ,visited[parent] becomes true.
                 * Ans: I don't want the timing of my currently node's parent to update my current node's timing,
                 * which will always be less than my current node which would ultimately lead to result being populated with all the connections in the graph.
                 */
                continue;
            }
            if (!visited[oneNeighbour]) {
                criticalConnectionsUtil(graph, node, oneNeighbour, timer, visited, results, timeStampAtThatNode);
            }
            timeStampAtThatNode[node] = Math.min(timeStampAtThatNode[node], timeStampAtThatNode[oneNeighbour]);
            if (currentTimeStamp < timeStampAtThatNode[oneNeighbour]) {
                results.add(Arrays.asList(node, oneNeighbour));
            }
        }
    }
}
