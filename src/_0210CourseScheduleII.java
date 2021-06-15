import java.util.*;

/**
 *
 * ---
 * 8.26 Kinda First Pass. Several small mistakes
 *
 * Any non-empty DAG must have at least one node without incoming links.
 */
public class _0210CourseScheduleII {

    /**
     * 1&2 BFS and DFS with more reusable code
     *
     * Time: O(V + E)
     * build adj list: O(E)
     * offer and poll node to/from queue: O(V)
     *
     * Space: O(V + E)
     * queue: O(V)
     * adjList: O(E)
     */
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjs = new ArrayList<>(numCourses);

        initializeGraph(indegrees, adjs, prerequisites);

//        List<Integer> res = new ArrayList<>();
//        int[] status = new int[numCourses];
//        for (int i = 0; i < numCourses; i++) {
//            if (!dfs(res, status, i, indegrees, adjs)) {
//                return new int[0];
//            }
//        }
//        return schedule;

        return solveByBFS(indegrees, adjs);
    }

    private void initializeGraph(int[] indegrees, List<List<Integer>> adjs, int[][] prerequisites) {
        for (int i = 0; i < indegrees.length; i++) {
            adjs.add(new ArrayList<>());
        }

        for (int[] edge : prerequisites) {
            indegrees[edge[0]]++;
            adjs.get(edge[1]).add(edge[0]);
        }
    }

    private int[] solveByBFS(int[] indegrees, List<List<Integer>> adjs) {
        int[] order = new int[indegrees.length];
        Queue<Integer> toVisit = new ArrayDeque<>();

        for (int i = 0; i < indegrees.length; i++) {
            if (indegrees[i] == 0) {
                toVisit.offer(i);
            }
        }

        int visited = 0;
        while (!toVisit.isEmpty()) {
            int from = toVisit.poll();
            order[visited++] = from;
            for (int to : adjs.get(from)) {
                indegrees[to]--;
                if (indegrees[to] == 0) {
                    toVisit.offer(to);
                }
            }
        }

        return visited == indegrees.length ? order: new int[0];
    }

    /**
     * DFS: explore courses from most advanced -> most basic
     *
     * 0: Vertex is not processed yet. Initially, all vertices are WHITE.
     * 1: Vertex is being processed (DFS for this vertex has started, but not finished
     * which means that all descendants (in DFS tree) of this vertex are not processed yet (or this vertex is in the function call stack)
     * 2: Vertex and all its descendants are processed.
     *
     * While doing DFS, if an edge is encountered from current vertex to a GRAY vertex,
     * then this edge is back edge and hence there is a cycle
     */
    private boolean dfs(List<Integer> res, int[] status, int i,
                        int[] indegrees, List<List<Integer>> adjs) {
        if (status[i] == 2) {
            // when visit = 2, which means the subtree whose root is i has been dfs traversed
            // and all the nodes in subtree has been put in the result(if we request), so we do not need to traverse it again
            //            return true;
            return true;
        } else if (status[i] == 1) {
            return false;
        }

        status[i] = 1;
        for (int next : adjs.get(i)) {
            if (!dfs(res, status, next, indegrees, adjs)) {
                return false;
            }
        }
        status[i] = 2;
        res.add(i);

        return true;
    }
}
