import java.util.Arrays;

public class _0493ReversePairs {
    /**
     * 1. Merge Sort
     *
     * n: nums length
     * Time: O(n * logn)
     * Space:
     * recursive call stack: O(logn)
     * merge arr: O(n)
     *
     * nums[i] / 2.0 > nums[j]
     * why not /2? consider[3, 1] 3 > 2 * 1, but 3 / 2 = 1 = 1
     * why not nums[i] > 2 * nums[j], because overflow
     *
     * a more concise, faster version, but kinda hard to read:
     * https://leetcode.com/problems/reverse-pairs/discuss/97306/less-20-lines-Java-code.-Beats-100!!
     *
     */
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        int res = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        res += merge(nums, left, mid, right);

        return res;
    }

    private int merge(int[] nums, int low, int mid, int high) {
        int count = 0;

        for (int i = low, j = mid + 1; i <= mid; i++){
            while(j <= high && nums[i] / 2.0 > nums[j]){
                j++;
            }
            count += (j - (mid+1));
        }

        int[] merge = new int[high - low + 1];
        int k = 0;
        int left = low,right = mid+1;

        while (left <= mid && right <= high){
            if (nums[left]<=nums[right]) {
                merge[k++] = nums[left++];
            } else {
                merge[k++] = nums[right++];
            }
        }

        while (left <= mid){
            merge[k++] = nums[left++];
        }

        while(right <= high){
            merge[k++] = nums[right++];
        }

        System.arraycopy(merge, 0, nums, low, merge.length);
        return count;
    }

    /**
     * 2. Binary Indexed Tree
     *
     * https://www.hackerearth.com/practice/data-structures/advanced-data-structures/fenwick-binary-indexed-trees/tutorial/
     */
    private int search(int[] bit, int i) {
        int sum = 0;

        while (i < bit.length) {
            sum += bit[i];
            i += i & -i;
        }

        return sum;
    }

    private void insert(int[] bit, int i) {
        while (i > 0) {
            bit[i] += 1;
            i -= i & -i;
        }
    }

    public int reversePairs2(int[] nums) {
        int res = 0;
        int[] copy = Arrays.copyOf(nums, nums.length);
        int[] bit = new int[copy.length + 1];

        Arrays.sort(copy);

        for (int ele : nums) {
            res += search(bit, index(copy, 2L * ele + 1));
            insert(bit, index(copy, ele));
        }

        return res;
    }

    private int index(int[] arr, long val) {
        int l = 0, r = arr.length - 1, m = 0;

        while (l <= r) {
            m = l + ((r - l) >> 1);

            if (arr[m] >= val) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return l + 1;
    }

}
