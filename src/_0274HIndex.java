import java.util.Arrays;

public class _0274HIndex {

    /**
     * 1. Histogram Intuition + Sorting
     *
     * Reference:
     * https://leetcode.com/problems/h-index/solution/
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);

        int i = 0;
        while (i < citations.length && citations[citations.length - 1 - i] > i) {
            i++;
        }

        return i;

    }

    /**
     * 2. Counting Sort
     *
     * Reflection from Solution 1:
     * Comparison sorting algorithm has a lower bound of O(nlogn).
     * To achieve better performance, we need non-comparison based sorting algorithms.
     *
     * Counting sort time complexity: O(n + k), k is the range of the non-negative key values.
     * So it is only suitable for cases where the variation in keys is not significantly greater than the number of items.
     *
     * However, the keys are the citations of each paper which can be much larger than the number of papers n
     * but Any citation > n can be replaced by n and the h-index will not change
     * because h-index is upper-bounded by total number of papers: h <= n
     *
     *
     * Reference:
     * https://leetcode.com/problems/h-index/solution/
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param citations
     * @return
     */
    public int hIndex2(int[] citations) {
        int n = citations.length;
        int[] papers = new int[n + 1];

        for (int c : citations) {
            papers[Math.min(n, c)]++;
        }

        int h = n;
        // s : the sum of papers having at least h citations (other paper have citations < h)
        for (int s = papers[n]; h > s; s += papers[h]) {
            h--;
        }

        return h;

    }
}


