package BFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class BuildPostOfficeII {
    public int EMPTY = 0;
    public int HOUSE = 1;
    public int WALL = 2;

    public int[][] grid;
    public int rows;
    public int cols;

    public int[] deltaX = {0, 1, -1, 0};
    public int[] deltaY = {1, 0, 0, -1};

    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return -1;
        }

        // set rows, cols, grid
        setGrid(grid);

        // get all the houses
        List<Coordinate> houses = getCoordinates(HOUSE);

        // the sum of distance from every house to every empty place
        int[][] distanceSum = new int[rows][cols];
        // empties visited times of bfs from each house
        int[][] visitedTimes = new int[rows][cols];

        // bfs from every house, count distanceSum and vistedTimes for every empty
        for (Coordinate house : houses) {
            bfs(house, distanceSum, visitedTimes);
        }

        // compare every distance and get the min
        int shortest = Integer.MAX_VALUE;
        List<Coordinate> empties = getCoordinates(EMPTY);

        for (Coordinate empty: empties) {
            if (visitedTimes[empty.x][empty.y] != houses.size()) {
                // 从房子出发，不是所有房子都能访问到这个空地
                continue;
            }

            shortest = Math.min(shortest, distanceSum[empty.x][empty.y]);
        }

        if (shortest == Integer.MAX_VALUE) {
            return -1;
        }

        return shortest;
    }

    private void setGrid(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        this.grid = grid;
    }

    private void bfs(Coordinate start, int[][] distanceSum, int[][] visitedTimes) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(start);

        // make sure every spot only enters queue once
        boolean[][] hash = new boolean[rows][cols];
        hash[start.x][start.y] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            // distance for this level
            steps++;

            // number of elements in this level
            int size = queue.size();

            // search this level
            for (int temp = 0; temp < size; temp++) {
                Coordinate coor = queue.poll();

                // up, down, left, right
                for (int i = 0; i < 4; i++) {
                    Coordinate adj = new Coordinate(
                            coor.x + deltaX[i],
                            coor.y + deltaY[i]
                    );

                    // make sure it's a empty that's in bound
                    if (!inBound(adj)) {
                        continue;
                    }

                    // if visited, ignore
                    if (hash[adj.x][adj.y]) {
                        continue;
                    }

                    queue.offer(adj);
                    hash[adj.x][adj.y] = true;
                    distanceSum[adj.x][adj.y] += steps;
                    visitedTimes[adj.x][adj.y]++;
                }
            }
        }
    }

    private boolean inBound(Coordinate coor) {
        if (coor.x < 0 || coor.x >= rows) {
            return false;
        }

        if (coor.y < 0 || coor.y >= cols) {
            return false;
        }

        return grid[coor.x][coor.y] == EMPTY;
    }

    private List<Coordinate> getCoordinates(int type) {
        List<Coordinate> coordinates = new ArrayList<>();
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == type) {
                    coordinates.add(new Coordinate(row, col));
                }
            }
        }
        
        return coordinates;
    }


    

}
