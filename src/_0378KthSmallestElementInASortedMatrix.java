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
     * Time: O(n + k * log n)
     * Space: O(n)
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
     * Time: O(n * log(MAX - MIN))
     * Space: O(1)
     */
    public static int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length;
        int start = matrix[0][0];
        int end = matrix[n - 1][n - 1];

        while (start < end) {
            int mid = start + (end - start) / 2;
            // {biggest num <= mid, smallest num > mid}
            int[] closests = { matrix[0][0], matrix[n - 1][n - 1] };

            int count = countLessEqual(matrix, mid, closests);

            if (count == k) {
                return closests[0];
            } else if (count < k) {
                // Why +1 here or -1 below won't work?
                // because closests are actual nums in the matrix, they might be the kth smallest
                start = closests[1]; // search higher (+1 won't work)
            } else {
                end = closests[0]; // search lower (-1 won't work)
            }
        }

        return start; // or return end
    }

    // count number of elements that are less than or equal to the mid
    // update biggest num less than or equal to the mid, smallest num bigger than mid
    private static int countLessEqual(int[][] matrix, int mid, int[] closests) {
        int n = matrix.length;
        int count = 0;
        int row = n - 1;
        int col = 0;

        while (row >= 0 && col < n) {
            int cur = matrix[row][col];
            if (cur > mid) {
                // as matrix[row][col] is bigger than the mid, let's keep track of the
                // smallest number greater than the mid
                closests[1] = Math.min(closests[1], cur);
                row--;
            } else {
                // as matrix[row][col] is less than or equal to the mid, let's keep track of the
                // biggest number less than or equal to the mid
                closests[0] = Math.max(closests[0], cur);
                count += row + 1;
                col++;
            }
        }

        return count;
    }
}
