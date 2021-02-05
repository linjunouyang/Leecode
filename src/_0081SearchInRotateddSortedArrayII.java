/**
 * Follow up: This problem is the same as Search in Rotated Sorted Array,
 * where nums may contain duplicates. Would this affect the run-time complexity? How and why?
 *
 * by having duplicate elements in the array, we often lose the opportunity to  binary search in certain search spaces.
 * Hence, we get O(N) worst case (with duplicates) vs O(logN) best case complexity (without duplicates).
 *
 */

public class _0081SearchInRotateddSortedArrayII {
    /**
     * 1. Brute Force - linear scan
     *
     * Time: O(n)
     * Space: O(1)
     */

    /**
     * 2. Adapted binary Search
     *
     * Alternative:
     * https://www.jiuzhang.com/solution/search-in-rotated-sorted-array-ii/
     * Break into three parts:
     * a) BS pivot b) decide search range c) BS
     *
     * Time: O(n)
     * Space: O(1)
     */
    public boolean search(int[] nums, int target) {
        int start  = 0;
        int end = nums.length - 1;

        //check each num so we will check start == end
        //We always get a sorted part and a half part
        //we can check sorted part to decide where to go next
        while (start <= end){
            int mid = start + (end - start)/2;
            if (nums[mid] == target) {
                return true;
            }

            //if left part is sorted
            if (nums[start] < nums[mid]){
                if (target < nums[start] || target > nums[mid]){
                    //target is in rotated part
                    start = mid + 1;
                } else{
                    end = mid - 1;
                }
            } else if(nums[start] > nums[mid]){
                //right part is rotated

                //target is in rotated part
                if (target < nums[mid] || target > nums[end]){
                    end = mid -1;
                } else{
                    start = mid + 1;
                }
            } else {
                //duplicates, we know nums[mid] != target, so nums[start] != target
                //based on current information, we can only move left pointer to skip one cell
                //thus in the worest case, we would have target: 2, and array like 11111111, then
                //the running time would be O(n)
                // consider cases like 1111111...2...1111111
                start ++;
            }
        }

        return false;
    }

}
