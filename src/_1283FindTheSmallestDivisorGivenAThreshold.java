/**
 * Binary Search the result
 *
 * It's guaranteed to have a solution
 */
public class _1283FindTheSmallestDivisorGivenAThreshold {

    public int smallestDivisor(int[] nums, int threshold) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }

        int left = 1;
        int right = max;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int sum = getDivisionSum(nums, mid);
            if (sum > threshold) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private int getDivisionSum(int[] nums, int divisor) {
        int sum = 0;
        for (int num : nums) {
            // Math.ceil is significantly slow, because of overhead
            // or (num + divisor - 1) / divisor
            // for num: [1, divisor] res should be 1
            // for num: [divisor + 1, 2 * divisor] res should be 2
            sum += Math.ceil((double) num / divisor);
        }
        return sum;
    }
}
