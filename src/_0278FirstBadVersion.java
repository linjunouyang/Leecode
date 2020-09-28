public class _0278FirstBadVersion {
    /**
     * 1. Binary Search
     *
     * Time: O(logn)
     * Space: O(1)
     */
    public int firstBadVersion(int n) {
        // base cases
        if (n <= 0) {
            return -1;
        }

        int start = 1;
        int end = n;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if (!isBadVersion(mid)) {
                start = mid + 1; // start = 4
            } else {
                end = mid; // s = e = 4
            }
        }

        if (isBadVersion(start)) {
            return start;
        } else {
            return -1;
        }
    }

    private boolean isBadVersion(int version) {
        return true;
    }
}
