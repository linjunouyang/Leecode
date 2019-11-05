public class _0275HIndexII {
    /**
     * 1. Binary Search
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     *
     * -----
     * Example:
     *
     * base-0 index: 0, 1, 2, 3, 4
     * base-1 index: 5, 4, 3, 2, 1
     * citations[i]: 0, 1, 4, 5, 7
     *
     * left = 0, right = 4, mid = 2
     * (len - mid) =  3 < 4 = citations[mid = 2]
     * right = 1
     *
     * left = 0, right = 1, mid = 0
     * (len - mid) = 5 > 0 = citations[mid = 0]
     * left = 1
     *
     * left = 1, right = 1, mid = 1
     * (len - mid) = 4 > 1 = citations[mid = 1]
     * left = 2
     * -------
     *
     * How to explain return len - left;
     *
     * Consider when left = right = mid
     *
     * 1) right = mid - 1;
     * (len - mid = len - left < citations[mid]] papers have at least (citations[mid] > len - left) citations)
     * len - left papers have at least len - left citations
     *
     * 2) left = mid + 1;
     * (len - mid > len - left >= citations[mid]) papers have at least (citations[mid] <= len-left) citations
     * (len - left >= citations[mid]) papers have at least len-left citations
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        int len = citations.length;
        int left = 0;
        int right = len - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] == (len - mid)) {
                // (len - mid = citations[mid]) papers have at least citations[mid] citations
                return citations[mid];
            } else if (citations[mid] > (len - mid)) {
                // (len - mid < citations[mid]) papers have at least citations[mid] citations
                right = mid - 1;
            } else {
                // (len - mid > citations[mid]) papers have at least citations[mid] citation
                left = mid + 1;
            }
        }

        // ? how to explain this
        return len - left;
    }
}
