public class _0069SqrtX {
    /**
     * 1. Binary Search
     *
     * For x >= 2 the square root is always smaller than x/2 and larger than 0 : 0 < a < x/2
     *
     * Avoid overflow
     * 1) long res = (long)mid * mid;
     * 2) x / mid vs mid
     *
     *
     *
     * Time: O(x)
     * Space: O(1)
     *
     */
    public int mySqrt(int x) {
        // Boundary cases
        if (x == 0 || x == 1) {
            return x;
        }

        int start = 0;
        int end = x; // or x / 2 + 1

        while (start < end) {
            int mid = start + (end - start) / 2;
            long squared = (long)mid * mid;
            if (squared == x) {
                return mid;
            } else if (squared < x) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        // start, start + 1 (end)
        // 1) start = mid + 1 -> squared less than x, squared less than x, start (end)
        // 2) end = mid -> squared less than x, start (end), start + 1
        return start - 1;
    }

    /**
     * Right tilt middle point
     */
    int mySqrt2(int x) {
        int l=0,r=x;
        while (l<r) {
            int m=(l+r+1)/2;
            if (m>x/m) r=m-1;
            else l=m;
        }
        return r;
    }

    // https://leetcode.com/problems/sqrtx/discuss/25198/3-JAVA-solutions-with-explanation
    // Another way to return

    /**
     * https://leetcode.com/problems/sqrtx/discuss/25048/Share-my-O(log-n)-Solution-using-bit-manipulation
     *
     * Solution 3
     *
     * Bit Manipulation
     */
}
