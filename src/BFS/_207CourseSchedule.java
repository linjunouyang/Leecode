package BFS;

import java.util.*;

/**
 *
 * ---
 * 8.25 First Pass
 */
public class _207CourseSchedule {

    /**
     * 1. Topological Ordering
     *
     * Runtime: 39 ms, faster than 18.35% of Java online submissions for Course Schedule.
     * Memory Usage: 40.7 MB, less than 99.23% of Java online submissions for Course Schedule.
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer> order;
        order = new ArrayList<>();

        // store courses and their number of prerequisites
        Map<Integer, Integer> map = new HashMap<>();

        // count each course's number of prerequisites
        for (int[] pre : prerequisites) {
            if (map.containsKey(pre[0])) {
                map.put(pre[0], map.get(pre[0]) + 1);
            } else {
                map.put(pre[0], 1);
            }
        }

        // put all courses which don't have prerequisites to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!map.containsKey(i)) {
                order.add(i);
                queue.add(i);
            }
        }

        // bfs
        while (!queue.isEmpty()) {
            Integer course = queue.poll();

            for (int[] pre : prerequisites) {
                if (pre[1] == course) {
                    map.put(pre[0], map.get(pre[0]) - 1);

                    if (map.get(pre[0]) == 0) {
                        order.add(pre[0]);
                        queue.add(pre[0]);
                    }
                }
            }
        }

        if (order.size() == numCourses) {
            return true;
        }

        return false;
    }

    /**
     * 2. BFS improved
     *
     * Map is just a more general case of list, where key can be anything.
     *
     * Runtime: 30 ms, faster than 30.89% of Java online submissions for Course Schedule.
     * Memory Usage: 45 MB, less than 90.00% of Java online submissions for Course Schedule.
     */
    public boolean canFinnish2(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];

        for (int[] pair: prerequisites) {
            indegree[pair[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int course = queue.poll();
            numCourses--;
            for (int[] pair: prerequisites) {
                if (pair[1] == course) {
                    indegree[pair[0]]--;
                    if (indegree[pair[0]] == 0) {
                        queue.add(pair[0]);
                    }
                }
            }
        }

        return numCourses == 0;
    }

    /**
     * 3. Adjacent list
     *
     * When doing BFS, instead of going through every course relationship
     * We only consider relevant course relationships,
     * which are stored in adjacent list.
     *
     * _______
     * Notice:
     * List<Integer>[] adj = new ArrayList[numCourses];
     * List<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[4]
     *
     * It's easy to write
     * List<Integer>[] adj = new ArrayList<Integer>[]
     *
     * According to Oracle Documentation:
     * You cannot create arrays of parameterized types
     *
     * In fact, mixing arrays and collections is a bad design.
     * Don't use arrays unless dealing with primitives
     *
     * Disadvantage of arrays:
     * 1) an array can't grow
     * 2) doesn't have standard methods like equals, hashcode, toString etc.
     *
     * List<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
     *
     * ______
     *
     * Runtime: 3 ms, faster than 89.80% of Java online submissions for Course Schedule.
     * Memory Usage: 43 MB, less than 97.69% of Java online submissions for Course Schedule.
     */
    public boolean canFinish3(int numCourses, int[][] prerequisites) {
        // initialize adjacent list
        List<Integer>[] adj = new ArrayList[numCourses];

        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<>();
        }

        // fill in the adjacent list and count indegree
        int[] indegree = new int[numCourses];

        for (int[] prerequisite : prerequisites) {
            adj[prerequisite[1]].add(prerequisite[0]);
            indegree[prerequisite[0]]++;
        }

        // push all courses without prerequisites to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // BFS
        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            for (int nextCourse : adj[course]) {
                indegree[nextCourse]--;
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
            count++;
        }

        return count == numCourses;
    }

}
