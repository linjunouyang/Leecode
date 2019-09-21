package Lintcode;

public class _31PartitionArray {
    public int partitionArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;

        // NOTICE left < right
        // OR left <= right
        while (left < right) {
            // or left <= right
            while (left < right && nums[left] < k) {
                left++;
            }

            // or left <= right
            while (left < right && nums[right] >= k) {
                right--;
            }

            // or left <= right
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }

        // or simply return left
        if (nums[left] < k) {
            return left + 1;
        }

        return left;
    }
}
