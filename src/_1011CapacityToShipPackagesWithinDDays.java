/**
 * Similar:
 * 410 DP
 */
public class _1011CapacityToShipPackagesWithinDDays {
    /**
     * 1. Binary Search the answer
     *
     *
     * The answer has a min value and a max value i.e. that solution lies within a range.
     * min: the maximum weight, max: the sum of all the weights.
     *
     * Time: O(n * log(weightSum - maxWeight))
     * Space: O(1)
     */
    public int shipWithinDays(int[] weights, int D) {
        if (weights == null) {
            return -1;
        }

        int left = 0, right = 0;
        for (int w: weights) {
            left = Math.max(left, w);
            right += w;
        }

        while (left < right) {
            int mid = (left + right) / 2;
            int days = 1;
            int dayWeight = 0;
            for (int w: weights) {
                if (dayWeight + w > mid) {
                    days += 1;
                    dayWeight = 0;
                }
                dayWeight += w;
            }
            if (days > D) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
