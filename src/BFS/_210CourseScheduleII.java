package BFS;

import java.util.*;

/**
 *
 * ---
 * 8.26 Kinda First Pass. Several small mistakes
 */
public class _210CourseScheduleII {


    /**
     * 1. BFS, graph represented by adjacent lists
     *
     * since the graph is likely to be sparse,
     * we use adjacent list
     *
     * Notice:
     * 1. return an empty array -> return new int[0]
     * instead of return new int[numCourses] (there will be numCourses of 0)
     *
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];

        // initialize adjacent list
        List<Integer>[] adj = new ArrayList[numCourses];

        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<>();
        }

        // count the inderee and fill in the adjacent list
        int[] indegree = new int[numCourses];

        for (int[] relation : prerequisites) {
            indegree[relation[0]]++;
            adj[relation[1]].add(relation[0]);
        }

        // push all the courses without prerequisites to queue
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // bfs
        int count = 0;

        while (!queue.isEmpty()) {
            Integer prev = queue.poll();
            res[count] = prev;
            count++;

            for (Integer next : adj[prev]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        if (count == numCourses) {
            return res;
        }

        return new int[0];

    }

    /**
     * 2&3 BFS and DFS with more reuseable code
     */
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjs = new ArrayList<>(numCourses);

        initializeGraph(indegrees, adjs, prerequisites);

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
}
