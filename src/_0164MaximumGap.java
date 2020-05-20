import java.util.Arrays;

/**
 * TO-DO: Radix sort
 */
public class _0164MaximumGap {
    /**
     * 1. Sorting
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public int maximumGap1(int[] nums) {
        if (nums == null) {
            return 0;
        }

        Arrays.sort(nums);

        int maxDiff = 0;

        for (int i = 1; i < nums.length; i++) {
            maxDiff = Math.max(maxDiff, nums[i] - nums[i - 1]);
        }

        return maxDiff;
    }

    /**
     * 2. Bucket Sort
     *
     * Average gap: Math.ceil((double) (max - min) / (nums.length - 1)
     * -> there are BIGGER and SMALLER gaps
     * -> No need to check smaller gaps (gaps within a bucket)
     *
     * Why ceiling on gap?
     * If no ceiling -> ArrayIndexOutOfBoundsException
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 2 ms, faster than 97.84% of Java online submissions for Maximum Gap.
     * Memory Usage: 38 MB, less than 65.52% of Java online submissions for Maximum Gap.
     *
     * @param nums
     * @return
     */
    public int maximumGap2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // get the max and min value
        int min = nums[0];
        int max = nums[0];
        for (int i : nums) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }

        // why ceiling
        int gap = (int) Math.ceil((double) (max - min) / (nums.length - 1));
        int[] bucketsMIN = new int[nums.length - 1];
        int[] bucketsMAX = new int[nums.length - 1];
        Arrays.fill(bucketsMIN, Integer.MAX_VALUE);
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);

        // put number into buckets
        for (int i : nums) {
            // We must check i == max, Otherwise [1, 10000000] ArrayIndexOutOfBoundsException: 1
            if (i == max) {
                continue;
            }
            int idx = (i - min) / gap;
            bucketsMIN[idx] = Math.min(i, bucketsMIN[idx]);
            bucketsMAX[idx] = Math.max(i, bucketsMAX[idx]);
        }

        // scan the buckets for the max gap
        int maxGap = Integer.MIN_VALUE;
        int previous = min;
        for (int i = 0; i < nums.length - 1; i++) {
            if (bucketsMIN[i] == Integer.MAX_VALUE && bucketsMAX[i] == Integer.MIN_VALUE) {
                // empty bucket
                continue;
            }
            maxGap = Math.max(maxGap, bucketsMIN[i] - previous);
            previous = bucketsMAX[i];
        }

        maxGap = Math.max(maxGap, max - previous);
        return maxGap;
    }
}
