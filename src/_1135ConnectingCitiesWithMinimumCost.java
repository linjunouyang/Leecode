import java.util.Arrays;

public class _1135ConnectingCitiesWithMinimumCost {
    /**
     * 1. Kruskal
     *
     *
     */
    public int minimumCost(int n, int[][] connections) {
        Arrays.sort(connections, (c1, c2) -> c1[2] - c2[2]); // increasing cost;

        int[] roots = new int[n + 1];
        int[] weights = new int[n + 1];
        for (int city = 1; city <= n; city++) {
            roots[city] = city;
        }

        int minCost = 0;
        for (int[] connection : connections) {
            int city1 = connection[0];
            int city2 = connection[1];
            int cost = connection[2];
            if (union(city1, city2, roots, weights)) {
                minCost += cost;
            }
        }

        for (int city = 2; city <= n; city++) {
            if (find(city, roots) != find(city - 1, roots)) {
                return -1;
            }
        }

        return minCost;
    }

    private int find(int city, int[] roots) {
        while (city != roots[city]) {
            roots[city] = roots[roots[city]];
            city = roots[city];
        }
        return roots[city];
    }

    private boolean union(int city1, int city2, int[] roots, int[] weights) {
        int root1 = find(city1, roots);
        int root2 = find(city2, roots);
        if (root1 == root2) {
            return false;
        }
        if (weights[root1] > weights[root2]) {
            roots[root2] = root1;
        } else if (weights[root1] < weights[root2]){
            roots[root1] = root2;
        } else {
            roots[root1] = root2;
            weights[root2]++;
        }

        return true;
    }
}
