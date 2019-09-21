package BinarySearch;

/** TBD
 *
 * -
 * 8.22 Don't understand solution 1.
 * Need to checkout other solution on discussion session
 *
 */
public class _33SearchInRotatedSortedArray {
    /*
    1. Two Binary Search.

    First, binary search for existing min index.
    start < end prevents useless loop (start = end, that must be the min)
    When nums[mid] < nums[end], mid might be min, so end = mid instead of mid - 1.
    Otherwise, mid can't be min, so start = mid + 1;
    searching for min is an implicit goal.
    Even if the first mid = min, we still must narrow range down to 1

    Next, binary search for potential target.
    Decide search range by comparing target and nums[end];
    start = end doesn't guarantee the answer, we still need to check.
    searching for a specifc target is clear.
    we can check nums[mid] == target in the loop to exit loop earlier

    Time Complexity: O(logn)
    Space Complexity: O(1)

    Follow up:
    How to work for both ascending and descending order?
    (Microsoft and others)
    */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int m = nums.length - 1;
        int end = m;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
                end = mid; // end = mid - 1 is wrong for [8, 9, 2, 3, 4]
            } else {
                start = mid + 1;
            }
        }

        int minIdx = start;

        start = (target <= nums[m]) ? minIdx : 0;
        end = (target <= nums[m]) ? m : minIdx -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (target < nums[mid]) {
                end = mid - 1;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }

        // must check nums[start] == target if use start < end.
        return -1;
    }

    /*

     */

    /**
     * 2. Binary Search
     *
     * Pivot number: 1st element of rotated sorted array.
     *
     * Why not normal BS?
     * Because target and mid might not be on different half.
     *
     * Solution:
     * Same side -> comparator = nums[mid]
     * target (14) left, mid (12) right -> comparator = INF
     * mid (12) left, target (5) right -> comparator = -INF
     *
     * Time Complexity: O(logn)
     * Space Complexity: O(1)
     *
     * Detailed Discussion:
     * https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/14435/Clever-idea-making-it-simple
     * https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/154836/The-INF-and-INF-method-but-with-a-better-explanation-for-dummies-like-me
     *
     * https://leetcode.wang/leetCode-33-Search-in-Rotated-Sorted-Array.html 解法2 中文解释
     *
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int num = nums[mid];


            if ((nums[mid] < nums[0]) == (target < nums[0])) {
                // nums[mid] and target on the same side of nums[0], just take nums[mid]

                // >=, > e.g. [1] 1
                // > > e.g. [1, 3] 3
                num = nums[mid];
            } else {
                num = target < nums[0] ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            if (target < num) {
                hi = mid - 1;
            } else if (target > num) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * 3. One Binary Search
     *
     * Always check whether target is in the sorted part or rotated part
     *
     * 数组从任意位置劈开后，至少有一半是有序的，什么意思呢？
     *
     * 比如 [ 4 5 6 7 1 2 3] ，从 7 劈开，左边是 [ 4 5 6 7] 右边是 [ 7 1 2 3]，左边是有序的。
     *
     * 基于这个事实。
     *
     * 我们可以先找到哪一段是有序的 (只要判断端点即可)，然后看 target 在不在这一段里，
     * 1) 如果在，那么就把另一半丢弃。
     * 2) 如果不在，那么就把这一段丢弃。
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     *
     * Link:
     * https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/14436/Revised-Binary-Search
     *
     */
    public int search3(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            // check earlier to avoid double check later
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[lo] <= nums[mid]) {
                // equal case must be included in this branch
                // e.g. nums = [3, 1], target = 1
                // lo = mid = 0, end = 1
                // ？any intuitive idea to make sure this

                // left part is in order
                if (nums[lo] <= target && target < nums[mid]) {
                    // target is in left side
                    hi = mid - 1;
                } else {
                    // target is not in left side
                    lo = mid + 1;
                }
            } else {
                // right part is in order
                if (nums[mid] < target && target <= nums[hi]) {
                    // target is in right side
                    lo = mid + 1;
                } else {
                    // target is not in right side
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }


    /**
     * Binary Search with XOR
     *
     * Not intuitive to me
     */
    public int search4(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if ((nums[0] > target) ^ (nums[0] > nums[mid]) ^ (target > nums[mid])) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo == hi && nums[lo] == target ? lo : -1;
    }
}
