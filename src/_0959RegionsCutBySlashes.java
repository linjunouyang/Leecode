import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * (Note that backslash characters are escaped, so a \ is represented as "\\".)
 *
 * Similar: 200, 947
 */
public class _0959RegionsCutBySlashes {
    /**
     * 1. DFS (BFS also works) on up-scaled grid
     *
     * Why up-scaled?
     * The problem can be easily solved in continuous domain. Just paint each region with different color and count number of colors used.
     * In discrete domain, minimum unit is a cell. However in this problem a cell is further divided into different regions.
     * Why not extend this problem to continuous domain so that we don't have to (and shouldn't) divide unit cell?
     *
     * Does scale of 2 work?
     * Consider ["//", "/ "]:
     * 0  1  0! 1
     * 1  0! 1  0
     * 0! 1  0  0
     * 1  0  0  0
     *
     * Notice 0!.
     * Traditional 4-direction DFS will count them as 3 separate parts.
     * ? 8-directions might work
     *
     * So, let's try scale of 3
     *
     * Time: O(n * n)
     * Space: O(n * n)
     *
     */
    public int regionsBySlashes(String[] grid) {
        if (grid == null) {
            return 0;
        }

        int n = grid.length;
        // T: slash or visited empty
        // F: not visited empty
        boolean[][] scaledGrid = new boolean[3 * n][3 * n];

        // Mark all the slashes
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row].charAt(col) == '/') {
                    int x = 3 * row;
                    int y = 3 * col;
                    scaledGrid[x][y + 2] = true;
                    scaledGrid[x + 1][y + 1] = true;
                    scaledGrid[x + 2][y] = true;
                }

                if (grid[row].charAt(col) == '\\')  {
                    int x = 3 * row;
                    int y = 3 * col;
                    scaledGrid[x][y] = true;
                    scaledGrid[x + 1][y + 1] = true;
                    scaledGrid[x + 2][y + 2] = true;
                }
            }
        }

        // DFS for finding regions
        int regions = 0;
        for (int row = 0; row < 3 * n; row++) {
            for (int col = 0; col < 3 * n; col++) {
                if (!scaledGrid[row][col]) {
                    regions++;
                    dfs(scaledGrid, row, col);
                }
            }
        }
        return regions;
    }

    private void dfs(boolean[][] scaledGrid, int row, int col) {
        if (0 <= row && row < scaledGrid.length
                && 0 <= col && col < scaledGrid.length
                && !scaledGrid[row][col]) {
            scaledGrid[row][col] = true;
            dfs(scaledGrid, row - 1, col);
            dfs(scaledGrid, row + 1, col);
            dfs(scaledGrid, row, col - 1);
            dfs(scaledGrid, row, col + 1);
        }
    }

    // iterative dfs
    private void dfs2(boolean[][] scaledGrid, int row, int col) {
        Deque<Integer> rowStack = new ArrayDeque<>();
        Deque<Integer> colStack = new ArrayDeque<>();
        rowStack.push(row);
        colStack.push(col);
        int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int n = scaledGrid.length;

        while (!rowStack.isEmpty()) {
            int curRow = rowStack.pop();
            int curCol = colStack.pop();
            scaledGrid[curRow][curCol] = true;
            for (int[] direction : directions) {
                int nextRow = curRow + direction[0];
                int nextCol = curCol + direction[1];
                if (0 <= nextRow && nextRow < n && 0 <= nextCol && nextCol < n
                        && !scaledGrid[nextRow][nextCol]) {
                    rowStack.push(nextRow);
                    colStack.push(nextCol);
                }
            }
        }
    }

    // iterative dfs 2
    private void dfs3(boolean[][] scaledGrid, int row, int col) {
        Deque<int[]> vertexStack = new ArrayDeque<>();
        vertexStack.push(new int[]{row, col});
        int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int n = scaledGrid.length;

        while (!vertexStack.isEmpty()) {
            int[] vertex = vertexStack.pop();
            scaledGrid[vertex[0]][vertex[1]] = true;
            for (int[] direction : directions) {
                int nextRow = vertex[0] + direction[0];
                int nextCol = vertex[1] + direction[1];
                if (0 <= nextRow && nextRow < n && 0 <= nextCol && nextCol < n
                        && !scaledGrid[nextRow][nextCol]) {
                    vertexStack.push(new int[]{nextRow, nextCol});
                }
            }
        }
    }

    // ? why bfs time limit exceeded?

    /**
     * 2. Split one cell into 4 parts + Union Find (probably not the best one)
     *
     * 1st solution upscaled grid to 9N^2 parts, while this one split grid into 4N^2 parts.
     * In that solution, cut path occupy some pixel. In this solution, it doesn't.
     * So it won't fail in case of "X"
     *
     * Time: O(n^2)
     * ? what if the path from element to the root is very long? You cant guarantee that the find() function only iterate once to find the root of x.
     * for union find, if you optimize the merging with sizing (always merge the smaller size to bigger size) and use path compression when doing the find. The average time for both find/merge is O(1).
     *
     * Space: O(n^2)
     */
    int count, n;
    int[] f;
    public int regionsBySlashes2(String[] grid) {
        n = grid.length;
        f = new int[n * n * 4];
        count = n * n * 4;
        for (int i = 0; i < n * n * 4; ++i)
            f[i] = i;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                // union with top cell
                if (i > 0) {
                    union(getIndex(i - 1, j, 2), getIndex(i, j, 0));
                }

                // union with left cell
                if (j > 0) {
                    union(getIndex(i , j - 1, 1), getIndex(i , j, 3));
                }

                // internal union
                if (grid[i].charAt(j) != '/') {
                    // ' ', '\\'
                    union(getIndex(i , j, 0), getIndex(i , j,  1));
                    union(getIndex(i , j, 2), getIndex(i , j,  3));
                }
                if (grid[i].charAt(j) != '\\') {
                    // ' ', '/'
                    union(getIndex(i , j, 0), getIndex(i , j,  3));
                    union(getIndex(i , j, 2), getIndex(i , j,  1));
                }
            }
        }
        return count;
    }

    // find parent and save it to f[x]
    public int find(int x) {
        if (x != f[x]) {
            f[x] = find(f[x]); // path compression
        }
        return f[x];
    }
    public void union(int x, int y) {
        x = find(x); y = find(y);
        if (x != y) {
            f[x] = y;
            count--;
        }
    }
    public int getIndex(int i, int j, int k) {
        // n: row number
        // (i * n + j) : square index

        return (i * n + j) * 4 + k;
    }

    /**
     * 3. Space efficient Union Find
     *
     * https://leetcode.com/problems/regions-cut-by-slashes/discuss/279828/Java-union-find-beats-99.7.-Easy-to-understand-and-with-explanation.
     */
    public int regionsBySlashes3(String[] grid) {
        int n = grid.length + 1;
        UnionFind uf = new UnionFind(n * n);

        // Union nodes on boundaries (top, left, right, bottom)
        for (int col = 1; col < n; col++) {
            uf.union(0, col);
        }

        for (int row = 1; row < n; row++) {
            uf.union(0, n * row);
        }

        for (int row = 1; row < n; row++) {
            uf.union(0, n * row - 1);
        }

        for (int col = 1; col < n; col++) {
            uf.union(0, (n - 1) * n + col);
        }

        int ans = 1;

        for(int i =0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length(); j++) {
                char symbol = grid[i].charAt(j);
                if(symbol == '\\') {
                    int node1 = i * n + j;
                    int node2 = (i + 1) * n + j + 1;
                    if(uf.find(node1) == uf.find(node2)) {
                        ans++;
                    }
                    uf.union(node1, node2);
                } else if (symbol == '/') {
                    int node1 = i * n + j + 1;
                    int node2 = (i + 1) * n + j;
                    if (uf.find(node1) == uf.find(node2)) {
                        ans++;
                    }
                    uf.union(node1, node2);
                }
            }
        }

        return ans;
    }

    class UnionFind {
        private int size;
        private int[] parent;

        public UnionFind(int n) {
            size = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            parent[yRoot] = xRoot;
        }

    }


    /**
     * 4. Euler's Formula + Union Find
     * https://leetcode.com/problems/regions-cut-by-slashes/discuss/205738/Using-Euler's-Formula-(V-E-%2B-F-2)
     */
}
