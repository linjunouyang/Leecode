import java.util.ArrayList;
import java.util.List;

/**
 * Before writing the condition for determining the existence of an interval
 * List all the possible scenarios so it's easier to gain some insights
 *         xxx     xxxxxxxx
 *        xxxxxx     xxxx
 *
 *        xxxx    xxxx      xxxx
 *      xxxx        xxxx    xxxx
 *
 */
public class _0986IntervalListIntersections {
    /**
     * 1. Two Points
     *
     * Time complexity: O(m + n)
     * Space complexity: O(m + n)
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        if (A == null || B == null) {
            return new int[0][2];
        }

        List<int[]> list = new ArrayList<>();

        for (int p = 0, q = 0; p < A.length && q < B.length;) {
            int start = Math.max(A[p][0], B[q][0]);
            int end = Math.min(A[p][1], B[q][1]);
            if (start <= end) {
                list.add(new int[]{start, end});
            }

            if (A[p][1] > B[q][1]) {
                q++;
            } else {
                p++;
            }
        }

        // OR return list.toArray(new int[0][]);
        // ---
        // Notice:
        // no param for toArray -> Object[] cannot be converted to int[]
        // omit 1st dimension -> array dimension missing
        // omit 2nd dimension -> OK
        // ---
        // Why int[0][]? (seems trivial to me)
        // https://shipilev.net/blog/2016/arrays-wisdom-ancients/#_conclusion

        int[][] res = new int[list.size()][2];
        for(int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }

        return res;

    }
}
