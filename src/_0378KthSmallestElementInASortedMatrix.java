import java.util.PriorityQueue;

public class _0378KthSmallestElementInASortedMatrix {
    static class Tuple implements Comparable<Tuple> {
        int x, y, val;
        public Tuple (int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo (Tuple that) {
            return this.val - that.val;
        }
    }

    /**
     * 1. Heap
     *
     * x = min {K, N}
     *
     * Time: x + klog(x)
     * Space: o(x)
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        for (int j = 0; j <= n-1; j++) {
            pq.offer(new Tuple(0, j, matrix[0][j]));
        }
        for (int i = 0; i < k-1; i++) {
            Tuple t = pq.poll();
            if (t.x == n-1) {
                continue;
            }
            pq.offer(new Tuple(t.x + 1, t.y, matrix[t.x + 1][t.y]));
        }

        // If we include last poll inside for loop
        // variable tuple might not have been initialized
        return pq.poll().val;
    }

    /**
     * 2. Binary Search
     *
     * Time: O(N * log(MAX - MIN))
     * Space: O(1)
     *
     */
    public static int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length;
        int start = matrix[0][0], end = matrix[n - 1][n - 1];
        while (start < end) {
            int mid = start + (end - start) / 2;
            // {biggest num <= mid, smallest num > mid}
            int[] smallLargePair = { matrix[0][0], matrix[n - 1][n - 1] };

            int count = countLessEqual(matrix, mid, smallLargePair);

            if (count == k)
                return smallLargePair[0];

            if (count < k)
                start = smallLargePair[1]; // search higher
            else
                end = smallLargePair[0]; // search lower
        }

        return start; // or return end
    }

    private static int countLessEqual(int[][] matrix, int mid, int[] smallLargePair) {
        int count = 0;
        int n = matrix.length, row = n - 1, col = 0;
        while (row >= 0 && col < n) {
            if (matrix[row][col] > mid) {
                // as matrix[row][col] is bigger than the mid, let's keep track of the
                // smallest number greater than the mid
                smallLargePair[1] = Math.min(smallLargePair[1], matrix[row][col]);
                row--;
            } else {
                // as matrix[row][col] is less than or equal to the mid, let's keep track of the
                // biggest number less than or equal to the mid
                smallLargePair[0] = Math.max(smallLargePair[0], matrix[row][col]);
                count += row + 1;
                col++;
            }
        }
        return count;
    }
}
