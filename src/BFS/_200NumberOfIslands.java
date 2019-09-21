package BFS;

import java.util.LinkedList;
import java.util.Queue;

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Map {
    final static char ISLAND = '1';
    final static char WATER = '0';
}

public class _200NumberOfIslands {

    /**
     * BFS
     *
     * Make code reusable
     * 1) helper class
     * 2) private helper method
     * 3) avoid magic number, constant
     *
     * Runtime: 3 ms, faster than 28.37% of Java online submissions for Number of Islands.
     * Memory Usage: 40.9 MB, less than 84.65% of Java online submissions for Number of Islands.
     *
     * @param grid
     * @return
     */

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0  || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        // iterate over each spot
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == Map.ISLAND) {
                    markByBFS(grid, i, j);
                    islands++;
                }
            }
        }
    }

    private void markByBFS(char[][] grid, int x, int y) {
        int[] directionX = {0, 1, -1, 0};
        int[] directionY = {1, 0, 0, -1};

        Queue<Coordinate> queue = new LinkedList<>();

        queue.offer(new Coordinate(x, y));
        grid[x][y] = Map.WATER;

        while (!queue.isEmpty()) {
            Coordinate coor = queue.poll();

            // check up, down, left, right
            for (int i = 0; i < 4; i++) {
                Coordinate adj = new Coordinate(
                        coor.x + directionX[i],
                        coor.y + directionY[i]
                );

                if (!inBound(adj, grid)) {
                    continue;
                }

                if (grid[adj.x][adj.y] == Map.ISLAND) {
                    grid[adj.x][adj.y] = Map.WATER;
                    queue.offer(adj);
                }
            }
        }
    }

    private boolean inBound(Coordinate coor, char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        return coor.x >= 0 && coor.x < rows && coor.y >= 0 && coor.y < cols;
    }
}
