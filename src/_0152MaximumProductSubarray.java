public class _0152MaximumProductSubarray {
    /**
     * 1. Dynamic Programming
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param nums
     * @return
     */
    public int maxProduct1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = nums[0];

        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = min[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            max[i] = Math.max(nums[i],
                    Math.max(max[i - 1] * nums[i], min[i - 1] * nums[i]));
            min[i] = Math.min(nums[i],
                    Math.min(min[i - 1] * nums[i], max[i - 1] * nums[i]));
            ans = Math.max(ans, max[i]);
        }

        return ans;
    }

    /**
     * 2. Dynamic Programming with Optimized Space
     *
     * at each new element, u could either add the new element to the existing product,
     * or start fresh the product from current index (wipe out previous results)
     *
     * 这道题妙就妙在它不仅仅依赖了一个状态（前一个数所能获得的最大乘积），而是两个状态（最大和最小乘积）。
     * 比较简单的dp问题可能就只是会建立一个dp[]，然后把最大值放到其中。
     * 但是这道题给我们打开了新的思路：我们的dp数组里面可以存更多的信息。
     * 而上面的解法之所以没有用dp数组的原因是dp[i]只依赖于dp[i - 1]因此没有必要把前面所有的信息都存起来，
     * 只需要存前一个dp[i-1]的最大和最小的乘积就可以了
     *
     * 下面的代码使用了自定义的内部类Tuple,从而可以同时存imax和imin,并将所有的imax和imin存到了dp数组中。
     * 虽然稍显复杂，但是有助于加深理解。
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int maxProduct(int[] nums) {
        Tuple[] dp = new Tuple[nums.length];
        dp[0] = new Tuple(nums[0],nums[0]);
        int res = dp[0].imax;
        for (int i = 1; i < nums.length; i++) {
            Tuple prev = dp[i - 1];
            int imax = Math.max(Math.max(nums[i], nums[i] * prev.imax) , nums[i] * prev.imin);
            int imin = Math.min(Math.min(nums[i], nums[i] * prev.imax) , nums[i] * prev.imin);
            dp[i] = new Tuple(imax,imin);
            res = Math.max(imax, res);
        }
        return res;
    }

    class Tuple {
        private int imax;
        private int imin;
        private Tuple(int imax, int imin) {
            this.imax = imax;
            this.imin = imin;
        }
    }

    public int maxProduct2(int[] nums) {
        int max = Integer.MIN_VALUE;    // global max
        int maxloc = 1, minloc = 1;     // max or min end here
        for (int num : nums) {          // negative could cause maxloc and minloc swap
            int prod1 = maxloc * num, prod2 = minloc * num;
            maxloc = Math.max(num, Math.max(prod1, prod2));
            minloc = Math.min(num, Math.min(prod1, prod2));
            max = Math.max(max, maxloc);
        }
        return max;
    }

    /**
     * 3.
     *
     * https://leetcode.com/problems/maximum-product-subarray/discuss/183483/In-Python-it-can-be-more-concise-PythonC%2B%2BJava
     *
     * if there's no zero in the array, then the subarray with maximum product must start with the first element or end with the last element.
     * And therefore, the maximum product must be some prefix product or suffix product.
     * So in this solution, we compute the prefix product A and suffix product B, and simply return the maximum of A and B.
     *
     * Why? Here's the proof:
     *
     * Say, we have a subarray A[i : j](i != 0, j != n) and the product of elements inside is P.
     * Take P > 0 for example: if A[i] > 0 or A[j] > 0, then obviously, we should extend this subarray to include A[i] or A[j];
     * if both A[i] and A[j] are negative, then extending this subarray to include both A[i] and A[j] to get a larger product.
     * Repeating this procedure and eventually we will reach the beginning or the end of A.
     *
     * What if there are zeroes in the array?
     * Well, we can split the array into several smaller ones.
     * That's to say, when the prefix product is 0, we start over and compute prefix profuct from the current element instead.
     * And this is exactly what A[i] *= (A[i - 1]) or 1 does.
     *
     * ___
     * This algorithms is based on one conclusion: the max product must either start with 0 or end with n-1. That is, if A[i..j] is the subarray of max product, then i==0 or j==n-1 must be true.
     *
     * With this claim, we can simply start cumulative product from both ends and find the maximum product from both arrays.
     *
     * The proof is inspired by @keithnull
     *
     * Proof
     *
     * Let's assume there is no zero in this array, since that case reduces to several subproblems.
     * Then consider the special A = [i] and i < 0. It's trivial to get the max product.
     *
     * First, we prove that there is always a subarray A[i...j] whose product P > 0.
     * Suppose we pick any number from the array, say A[i].
     * If A[i] > 0, we are done.
     * If A[i] < 0, then consider the two neighbors A[i-1] and A[i+1] (if i == 0 or i == n-1, the whole claim is proved.)
     *
     * If A[i-1] > 0 or A[i+1] > 0, we can simply choose the positive number as our new subarray and we get a positive product.
     * If A[i-1] < 0 and A[i+1] < 0, then adding either one would make our product positive.
     * So in any case, we can always find a subarray A[i..j] with a positive product.
     * Then we show that we can expand this subarray gradually to one end. Again, consider the two neighbors A[i-1] and A[j+1].
     *
     * If either of them is positive, we can include that into this subarray and our product would still be positive and become larger.
     * If both of them are negative, we can include both and our product is still positive and larger.
     * In either case, we can always expand our subarray to get a larger product, until we hit one of the end.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param A
     * @return
     */
    public int maxProduct3(int[] A) {
        int n = A.length, res = A[0], l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            l =  (l == 0 ? 1 : l) * A[i];
            r =  (r == 0 ? 1 : r) * A[n - 1 - i];
            res = Math.max(res, Math.max(l, r));
        }
        return res;
    }
}
