import java.util.*;

public class _1057CampusBikes {
    /**
     * 1. Custom Sorting + Greedy
     *
     * Time: O(workers * bikes * log(workers * bikes))
     * Space: O(workers * bikes)
     */
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((e1, e2) -> {
            int distanceDiff = Integer.compare(e1[2], e2[2]);

            if (distanceDiff != 0) {
                return distanceDiff;
            }

            if (e1[0] != e2[0]) {
                return Integer.compare(e1[0], e2[0]);
            }

            return Integer.compare(e1[1], e2[1]);
        });

        int numWorkers = workers.length;
        int numBikes = bikes.length;

        for (int worker = 0; worker < numWorkers; worker++) {
            for (int bike = 0; bike < numBikes; bike++) {
                // store distance to avoid repeated computation
                int distance = Math.abs(workers[worker][0] - bikes[bike][0])
                        + Math.abs(workers[worker][1] - bikes[bike][1]);
                minHeap.add(new int[]{worker, bike, distance});
            }
        }

        int[] assignment = new int[numWorkers];
        Arrays.fill(assignment, -1);
        HashSet<Integer> assignedBikes = new HashSet<>();

        while (assignedBikes.size() != numWorkers) {
            int[] pair = minHeap.poll();
            int worker = pair[0];
            int bike = pair[1];
            if (assignment[worker] == -1 && !assignedBikes.contains(bike)) {
                assignment[worker] = bike;
                assignedBikes.add(bike);
            }
        }

        return assignment;
    }

    /**
     * 2. Bucket Sort
     *
     * bucket sort requires linked lists, dynamic arrays or a large amount of pre-allocated memory to hold the sets of items within each bucket,
     * whereas counting sort instead stores a single number (the count of items) per bucket
     *
     * Inspiration:
     *
     * 0 <= workers[i][j], bikes[i][j] < 1000
     * -> distance range: [1, 1998]
     *
     * List<int[]>[]:
     * number of distance: 1998 -> arr
     * each distance: not sure how many pair -> list
     * each pair: workerIdx, bikeIdx -> arr
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public int[] assignBikes2(int[][] workers, int[][] bikes) {
        int numWorkers = workers.length;
        int numBikes = bikes.length;

        List<int[]>[] distanceLists = new List[1999]; // add 1 to avoid 0-indexed

        for (int i = 0; i < numWorkers; i++) {
            int[] worker = workers[i];
            for (int j = 0; j < numBikes; j++) {
                int[] bike = bikes[j];
                int distance = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
                if (distanceLists[distance] == null) {
                    distanceLists[distance] = new ArrayList<>();
                }
                distanceLists[distance].add(new int[]{i, j});
            }
        }

        int[] assignments = new int[numWorkers];
        Arrays.fill(assignments, -1);
        HashSet<Integer> assignedBikes = new HashSet<>();

        for (List<int[]> distanceList : distanceLists) {
            if (distanceList != null) {
                for (int i = 0; i < distanceList.size() && assignedBikes.size() < numWorkers; i++) {
                    int[] workerBikePair = distanceList.get(i);
                    int worker = workerBikePair[0];
                    int bike = workerBikePair[1];
                    if (assignments[worker] == -1 && !assignedBikes.contains(bike)) {
                        assignments[worker] = bike;
                        assignedBikes.add(bike); // maintain
                    }
                }
            }
        }

        return assignments;
    }

    /**
     * 3.
     *
     * https://leetcode.com/problems/campus-bikes/discuss/418060/Step-by-Step-4-solutions-from-600ms-to-14ms-(beating-100)
     */


}
