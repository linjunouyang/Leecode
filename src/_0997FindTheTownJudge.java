import java.util.HashSet;
import java.util.Set;

/**
 * Similar: 277 Find the celebrity
 */
public class _0997FindTheTownJudge {
    /**
     * 1. Two Array - Indegree and outdegree
     *
     * Time:
     * loop over trust: O(E)
     * loop over people: O(N)
     * because we terminate early if E < N -  1
     * the worst case: E >= N - 1
     * -> O(E)
     *
     * Space: O(N)
     *
     * This last note is provided more for interest than for interview preparation.
     * A variant of the approach is to use a HashMaps.
     * That way, you'll only need to store in-degrees and out-degrees that are non-zero.
     * This will have no impact on the time complexity, because we still need to look at the entire input Array.
     * It also has no impact on the worst case space complexity, because when a town judge exists,
     * all the other N - 1 people have an out-degree of at least 1 (from their trust of the town judge).
     *
     * In some cases where E ≥ N - 1 but there is no town judge, some memory might be saved,
     * with a best case of O(\sqrt{E}\,)
     * This represents the situation of the number of unique people present in the trust Array being minimized
     * (beyond an easy-level question interview, don't panic!).
     * With the overhead of a HashMap, there's probably no gain of using one over an Array for this problem.
     */
    public int findJudge(int N, int[][] trust) {

        if (trust.length < N - 1) {
            return -1;
        }

        int[] indegrees = new int[N + 1];
        int[] outdegrees = new int[N + 1];

        for (int[] relation : trust) {
            outdegrees[relation[0]]++;
            indegrees[relation[1]]++;
        }

        for (int i = 1; i <= N; i++) {
            if (indegrees[i] == N - 1 && outdegrees[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 2. One Array: indegree - outdegree
     */
    public int findJudge2(int N, int[][] trust) {
        if (trust.length < N - 1) {
            return -1;
        }

        int[] trustScores = new int[N + 1];

        for (int[] relation : trust) {
            trustScores[relation[0]]--;
            trustScores[relation[1]]++;
        }

        for (int i = 1; i <= N; i++) {
            if (trustScores[i] == N - 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 3. Graph
     *
     * Notice two continue statements:
     * We still want to check following elements
     * if we put these conditions in for-loop, they won't be checked
     *
     * Time:
     * graph construction: O(n + E)
     * check candidate: O(n ^ 2)
     *
     * Space:
     * adj list: O(n + E)
     *
     */
    public int findJudge3(int N, int[][] trust) {
        Set<Integer>[] adjLists = new Set[N + 1];
        for (int i = 1; i <= N; i++) {
            adjLists[i] = new HashSet<>();
        }

        for (int[] edge : trust) {
            adjLists[edge[0]].add(edge[1]);
        }

        for (int candidate = 1; candidate <= N; candidate++) {
            if (!adjLists[candidate].isEmpty()) {
                continue;
            }
            boolean isJudge = true;
            for (int other = 1; other <= N; other++) {
                if (other == candidate) {
                    continue;
                }
                if (!adjLists[other].contains(candidate)) {
                    isJudge = false;
                    break;
                }
            }
            if (isJudge) {
                return candidate;
            }
        }

        return -1;
    }
}
