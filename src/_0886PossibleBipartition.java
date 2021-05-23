import java.util.*;

public class _0886PossibleBipartition {
    /**
     * 1. DFS (bfs would also work)
     *
     * Graph representation:
     * int[N][N], List<Integer>[]: not space efficient for sparse graph
     *
     *
     */
    public boolean possibleBipartition(int N, int[][] dislikes) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for(int[] dislike : dislikes) {
            adjList.putIfAbsent(dislike[0], new ArrayList<>());
            adjList.get(dislike[0]).add(dislike[1]);
            adjList.putIfAbsent(dislike[1], new ArrayList<>());
            adjList.get(dislike[1]).add(dislike[0]);
        }

        // 0: unprocessed, 1: 1st part, -1: 2nd part
        int[] division = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            if (division[i] == 0) {
                if (!bfs(adjList, i, division)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(HashMap<Integer, List<Integer>> adjList, int start, int[] division) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        division[start] = 1;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int next : adjList.getOrDefault(node, new ArrayList<>())) {
                if (division[next] == -division[node]) {
                    continue;
                }
                if (division[next] == division[node]) {
                    return false;
                }
                division[next] = -division[node];
                queue.offer(next);
            }
        }
        return true;
    }

    /**
     * 2. Union Find
     * Don't understand
     *
     */
    public boolean possibleBipartition2(int N, int[][] dislikes) {
        int[] colors = new int[N + 1];
        for(int i = 1; i <= N; ++i) colors[i] = i;
        for(int i = 0; i < dislikes.length; ++i) {
            int p1 = dislikes[i][0], p2 = dislikes[i][1];
            if(colors[p2] == p2) colors[p2] = p1;
            else {
                int[] uf1 = find(p1, colors), uf2 = find(p2, colors);
                if(uf1[0] == uf2[0] && uf1[1] == uf2[1]) return false;
            }
        }
        return true;
    }

    private int[] find(int p, int[] colors) {
        int color = 0;
        while(colors[p] != p) {
            p = colors[p];
            color = color == 0 ? 1 : 0;
        }
        return new int[] {p, color};
    }
}
