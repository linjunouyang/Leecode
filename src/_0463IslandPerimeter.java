/**
 * Why labelled as hashmap ?
 *
 * Notice:
 * 1) exactly one island
 * 2) no lakes inside
 *
 */
public class _0463IslandPerimeter {
    /**
     * 1. Count
     *
     * Time complexity: O(mn)
     * Space complexity: O(1)
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int result = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    result += 4;

                    if (r > 0 && grid[r-1][c] == 1) {
                        result -= 2;
                    }

                    if (c > 0 && grid[r][c-1] == 1) {
                        result -= 2;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 2. Count the number of edges adjacent to the water
     * @param grid
     * @return
     */
    public int islandPerimeter2(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    if (i == 0 || grid[i - 1][j] == 0) num++; // UP
                    if (j == 0 || grid[i][j - 1] == 0) num++; // LEFT
                    if (i == rows -1 || grid[i + 1][j] == 0) num++; // DOWN
                    if (j == cols -1 || grid[i][j + 1] == 0) num++; // RIGHT
                }
            }
        }
        return num;
    }

    /**
     * 3. DFS (iteration and recursion)
     *
     */
    public int islandPerimeter3(int[][] grid) {
        int m = grid.length;
        if(m == 0) return 0;
        int n = grid[0].length;
        int[][] dir = {{0,1},{1,0},{-1,0},{0,-1}};

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1){
                    return helper(grid, dir, i, j);
                }
            }
        }
        return 0;
    }

    /*
    He is counting the grids who surround the island.
    1) When DFS to grid[i][j] who is out of bound, count++;
    2) DFS to grid[i][j] == 1, it will be set to -1 and never considered in the future;
    3) DFS to grid[i][j] == 0, count++ (solved the problem that 0 should be counted twice in the corner).
    So rather than count the '1', this algorithm is counting'0's around the island.
     */
    private int helper(int[][] grid, int[][] dir, int i, int j){

        int m = grid.length, n = grid[0].length;
        grid[i][j] = -1;

        int count = 0;
        for (int[] d: dir){
            int x = i + d[0];
            int y = j + d[1];
            if(x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == 0){
                count++;
            } else {
                if(grid[x][y] == 1){
                    count += helper(grid, dir, x, y);
                }
            }
        }
        return count;
    }

    /**
     * 3. HashMap
     *
     * The time complexity should be O(N*M).
     * using a hash table to record which lines we already found on the islands.
     * If a new line is not in the hash table, then add it into the hash table.
     * If it's already in the hash table, then it cannot be a perimeter, as it is shared by two islands.
     * Then just remove it from the hash table (since one line can only associated with one or two islands in this problem setting).
     * Finally just return the length of the hash table should work.
     *
     * The advantage of this solution is that it do not use recursion and is quite easy to code.
     */
}
