package Lintcode;

import java.util.Arrays;

public class _00593SumClosest {
    /**
     * 1. Sorting and Three pointers
     *
     * Time complexity: O(n^2)
     * Space complexity: O(1)
     *
     * @param numbers
     * @param target
     * @return
     */
    public int threeSumClosest(int[] numbers, int target) {
        if (numbers == null || numbers.length < 3) {
            return 0;
        }

        Arrays.sort(numbers);

        // notice initialization
        int best = numbers[0] + numbers[1] + numbers[2];

        // explore first number
        for (int i = 0; i < numbers.length; i++) {
            int left = i + 1;
            int right = numbers.length - 1;

            // explore second and third number
            while (left < right) {
                int sum = numbers[i] + numbers[left] + numbers[right];

                if (Math.abs(target - sum) < Math.abs(target - best)) {
                    best = sum;
                }

                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return target;
                }
            }
        }

        return best;
    }
}
