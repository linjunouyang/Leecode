import java.util.TreeSet;

public class _0363MaxSumOfRectangleNoLargerThanK {

    /**
     * 1. Prefix Sum + Binary Search (Treeset)
     *
     * This solution also solves follow-up:
     * What if the number of rows is much larger than the number of columns?
     *
     * Time:
     * O(cols * cols * row)
     * Space:
     * O(rows)
     *
     * https://www.jiuzhang.com/solution/max-sum-of-rectangle-no-larger-than-k/#tag-lang-java
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return -1;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int ret = Integer.MIN_VALUE;

        for (int i = 0; i < cols; ++i) {		// 每次以i为起点
            int [] sum = new int [rows]; // prefix Sum for every row
            for (int j = i; j < cols; ++j) {		//j控制当前区域的尾部
                for (int r = 0; r < rows; ++r) {		//r代表每行
                    sum[r] += matrix[r][j];			//统计i列至j列中每行的元素和
                }

                int curSum = 0; //curSum保存当前i列至j列中前r行的元素和
                int curMax = Integer.MIN_VALUE; // max area (<= k) that we encountered in this area so far
                TreeSet<Integer> sumSet = new TreeSet<>() ;
                sumSet.add(0);
                for (int r = 0; r < rows; ++r) {
                    curSum += sum[r];
                    // ceiling: return the least element in this set greater than or equal to the given element, or null if there is no such element.
                    Integer it = sumSet.ceiling(curSum - k);	//找到第一个大于等于curSum-k的值，那么curSum减去之前小矩形内元素和可以得到小矩形区域，且其元素和不超过k
                    if(it != null) {
                        curMax = Math.max(curMax, curSum - it);
                    }
                    sumSet.add(curSum);
                }
                ret = Math.max(ret, curMax);
            }
        }
        return ret;

    }
}
