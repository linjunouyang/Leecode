import java.util.*;

/**
 * 通过BFS/DFS得到每一个岛屿, 然后把每一个岛屿的形状放到 set 里, 最后 set 的大小就是答案.
 * 问题的关键在于如何描述一个岛屿的形状.
 *
 * 有以下两个基本思路:
 * 1. 记录一个岛屿所有点相对于左上角的点的相对位置.
 * 涉及细节较少, 但是可能复杂度相对较高, 不过 50x50 的数据范围不会超时.
 * 有多种实现方法, 比如一个岛屿形状可以用set记录, 也可以将所有点的相对坐标排序后转换成字符串.
 *
 * 2. 记录一个岛屿的bfs/dfs轨迹
 * 不能仅仅储存下来dfs/bfs移动的方向, 因为涉及到回溯等问题, 可以加上一定的间隔符, 或者除方向之外额外记录一个位置信息.
 */
public class _0694NumberOfDistinctIslands {
    /**
     * 1. DFS + Exploration Path Set
     *
     * Time complexity:
     * O(mn)
     *
     * Space complexity:
     * dfs call stack: O(mn)
     * 1234
     * 8765
     *
     * sb string will be added into the set, so only need to analyze set space:
     * O(mn)
     */
    public int numDistinctIslands(int[][] grid) {
        if (grid == null) {
            return 0;
        }

        Set<String> set= new HashSet<>();

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != 0) {
                    StringBuilder sb = new StringBuilder();
                    dfs(grid, i, j, sb, "o"); // origin
                    set.add(sb.toString());
                }
            }
        }

        return set.size();
    }

    /**
     * Find all the parts of the island which contains (i, j)
     * Remember its shape by documenting moving direction
     */
    public void dfs(int[][] grid, int i, int j, StringBuilder sb, String dir){
        if(i < 0 || i == grid.length || j < 0 || j == grid[i].length || grid[i][j] == 0)  {
            return;
        }
        sb.append(dir);
        grid[i][j] = 0;

        dfs(grid, i - 1, j, sb, "u");
        dfs(grid, i + 1, j, sb, "d");
        dfs(grid, i, j - 1, sb, "l");
        dfs(grid, i, j + 1, sb, "r");
        /**
         * Backtracking:
         * eg:              1 1 1   and    1 1 0
         *                  0 1 0          0 1 1
         * with b:          [ord]b[r]bbb   [ordr]bbbb
         * without b:       ordr           ordr
         */
        sb.append("b");
    }

    private int[][] near = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int[] dir = new int[]{1, 2, 3, 4, 5, 6}; // up, right, down, left, start, separation of different nodes' neighbors

    // return the shape of the island containing grid[row][col]
    private String dfs(int[][] grid, int row, int col) {
        StringBuilder sb = new StringBuilder();
        sb.append(5);
        grid[row][col] = 0;

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] pos = stack.pop();

            for (int i = 0; i < near.length; i++) {
                int newRow = pos[0] + near[i][0];
                int newCol = pos[1] + near[i][1];
                if (0 <= newRow && newRow < grid.length && 0 <= newCol && newCol < grid[0].length
                        && grid[newRow][newCol] == 1) {
                    sb.append(dir[i]);
                    grid[newRow][newCol] = 0;
                    stack.push(new int[]{newRow, newCol});
                }
            }
            sb.append(6);
        }

        return sb.toString();
    }

    /**
     * 2. BFS
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     *
     */
    public int numDistinctIslands2(int[][] grid) {
        if (grid == null ) {
            return 0;
        }
        Set<String> codeSet = new HashSet<String>();
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    codeSet.add(bfs(grid, i, j));
                }
            }
        }
        return codeSet.size();
    }

    int[] rm = new int[]{1,-1,0,0};
    int[] cm = new int[]{0,0,1,-1};

    /**
     * In DFS, we return result for base cases in the beginning of dfs function
     * In BFS, we can only add non-base case into the queue
     *
     * 110  -> 2 -1 0 -1 2 -1 -1
     * 011
     * 000
     * 111 ->  2 -1 0 2 -1 -1 -1
     * 010
     */
    private String bfs(int[][] grid, int i, int j) {
        StringBuilder sb = new StringBuilder();

        Queue<Integer> rq = new ArrayDeque<Integer>();
        Queue<Integer> cq = new ArrayDeque<Integer>();
        rq.offer(i);
        cq.offer(j);
        grid[i][j] = 0;

        while (!rq.isEmpty()) {
            int r = rq.poll();
            int c = cq.poll();
            // It's not enough to just put grid[nr][nc] = 0 here
            // elements from the same level might
            for (int k = 0; k < 4; k++) {
                int nr = r + rm[k];
                int nc = c + cm[k];
                if (nr >= 0 && nc >= 0 && nr < grid.length && nc < grid[0].length && grid[nr][nc] == 1) {
                    rq.offer(nr);
                    cq.offer(nc);
                    grid[nr][nc] = 0;
                    sb.append(nr - i).append(nc - j);
                    // or sb.append(k);
                }
            }
            // or sb.append(-1); -1 as separation of different nodes
        }
        return sb.toString();
    }
}
