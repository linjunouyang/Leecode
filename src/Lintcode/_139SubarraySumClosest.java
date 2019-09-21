package Lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _139SubarraySumClosest {
    /**
     *
     * 我们首先需要回顾一下，在 subarray 这节课里，我们讲过一个重要的知识点，叫做 Prefix Sum
     * 比如对于数组 [1,2,3,4]，他的 Prefix Sum 是 [1,3,6,10]
     * 分别表示 前1个数之和，前2个数之和，前3个数之和，前4个数之和
     * 这个时候如果你想要知道 子数组 从下标  1 到下标 2 的这一段的和(2+3)，就用前 3个数之和 减去 前1个数之和 = PrefixSum[2] - PrefixSum[0] = 6 - 1 = 5
     * 你可以看到这里的 前 x 个数，和具体对应的下标之间，存在 +-1 的问题
     * 第 x 个数的下标是 x - 1，反之 下标 x 是第 x + 1 个数
     * 那么问题来了，如果要计算 下标从 0~2 这一段呢？也就是第1个数到第3个数，因为那样会访问到 PrefixSum[-1]
     * 所以我们把 PrefixSum 整体往后面移动一位，把第0位空出来表示前0个数之和，也就是0. => [0,1,3,6,10]
     * 那么此时就用 PrefixSum[3] - PrefixSum[0] ，这样计算就更方便了。
     * 此时，PrefixSum[i] 代表 前i个数之和，也就是 下标区间在 0 ~ i-1 这一段的和
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(n)
     *
     *
     * @param nums
     * @return
     */
    public int[] subarraySumClosest(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }

        int[] res = new int[2];

        if (nums.length == 1) {
            res[0] = res[1] = 0;
            return res;
        }

        // 计算PrefixSum数组，建立sum和index之间的映射关系

        int[] sums = new int[nums.length];
        // PrefixSum[0]
        sums[0] = 0;

        Map<Integer, Integer> sumToIndex = new HashMap<>();
        // PrefixSum[0] 最后小的index会+1，所以设置成-1
        sumToIndex.put(0, -1);

        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sums[i] = sum;
            if (sumToIndex.containsKey(sum)) {
                // 还是有可能出现sum为0的sub array
                res[0] = sumToIndex.get(sum) + 1;
                res[1] = i;
                return res;
            }
            sumToIndex.put(sum, i);
        }

        // 排序，做差，得到index
        Arrays.sort(sums);

        int min = Integer.MAX_VALUE;
        int sum1 = 0;
        int sum2 = 0;

        for (int i = 1; i < sums.length; i++) {
            int diff = Math.abs(sums[i] - sums[i - 1]);

            if (min > diff) {
                min = diff;
                sum1 = sums[i];
                sum2 = sums[i - 1];
            }
        }

        if (sumToIndex.get(sum1) < sumToIndex.get(sum2)) {
            res[0] = sumToIndex.get(sum1) + 1;
            res[1] = sumToIndex.get(sum2);
        } else {
            res[0] = sumToIndex.get(sum2) + 1;
            res[1] = sumToIndex.get(sum1);
        }

        return res;
    }
}
