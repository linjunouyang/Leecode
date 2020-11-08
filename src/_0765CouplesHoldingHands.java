public class _0765CouplesHoldingHands {
    /**
     * 1. Cyclic swapping
     *
     * https://leetcode.com/problems/couples-holding-hands/discuss/113362/JavaC%2B%2B-O(N)-solution-using-cyclic-swapping
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int minSwapsCouples(int[] row) {
        int res = 0, N = row.length;

        int[] ptn = new int[N]; // person label -> person partner
        int[] pos = new int[N]; // person label -> person seat

        for (int i = 0; i < N; i++) {
            ptn[i] = (i % 2 == 0 ? i + 1 : i - 1);
            pos[row[i]] = i;
        }

        for (int i = 0; i < N; i++) {
            for (int j = ptn[pos[ptn[row[i]]]]; i != j; j = ptn[pos[ptn[row[i]]]]) {
                swap(row, i, j);
                swap(pos, row[i], row[j]);
                res++;
            }
        }

        return res;
    }

    private void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
