public class _0011ContainerWithMostWater {

    /**
     * 1. Two Pointers - running to each other
     *
     *
     * the area formed between the lines is limited by the height of the shorter line.
     *
     * The widest container (using first and last line) is a good starting candidate, because of its width.
     *
     * -> If we move the pointer at the longer line inwards, we won't gain any increase in area, since it is limited by the shorter line.
     * -> moving the shorter line's pointer could be beneficial, as per the same argument, despite the reduction in the width
     * moving the shorter line's pointer might overcome the reduction in area caused by the width reduction.
     *
     * A visualized proof: https://leetcode.com/problems/container-with-most-water/discuss/6099/Yet-another-way-to-see-what-happens-in-the-O(n)-algorithm
     *
     *
     *
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 2 ms, faster than 95.21% of Java online submissions for Container With Most Water.
     * Memory Usage: 40 MB, less than 94.23% of Java online submissions for Container With Most Water.
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int maxArea = 0;

        int l = 0;
        int r = height.length - 1;

        while (l < r) {
            maxArea = Math.max(maxArea, Math.min(height[l], height[r]) * (r - l));

            if (height[l] < height[r]) {
                l = l + 1;
            } else {
                r = r - 1;
            }
        }

        return maxArea;
    }
}
