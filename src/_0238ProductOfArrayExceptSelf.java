/**
 * Notice
 * It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array)
 * fits in a 32 bit integer.
 * ->  1. we can use int
 * ->  2. 'prefix' or 'suffix' -> prefix sum variation
 */
public class _0238ProductOfArrayExceptSelf {
    /**
     * 1. Prefix Product and Suffix Product
     *
     * How to come up with this?
     * Visualize the array. The res has two parts, the subarray on the left and right.
     *
     * We could create prefix / suffix product array, but we could also utilize the res array:
     * 1. compute prefix / suffix product
     * 2. compute suffix / prefix product, and compute final product for each entry
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] answer = new int[len];

        // answer[i] contains the product of all the elements to the left
        // Note: for the element at index '0', there are no elements to the left,
        // so the answer[0] would be 1
        answer[0] = 1;
        for (int i = 1; i < len; i++) {
            // answer[i - 1] already contains the product of elements to the left of 'i - 1'
            // Simply multiplying it with nums[i - 1] would give the product of all
            // elements to the left of index 'i'
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R contains the product of all the elements to the right
        // Note: for the element at index 'length - 1', there are no elements to the right,
        // so the R would be 1
        int rightProduct = 1;
        for (int i = len - 1; i >= 0; i--) {
            // For the index 'i', R would contain the
            // product of all elements to the right. We update R accordingly
            answer[i] = answer[i] * rightProduct;
            rightProduct *= nums[i];
        }

        return answer;
    }
}
