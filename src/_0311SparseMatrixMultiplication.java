import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0311SparseMatrixMultiplication {
    /**
     * 1. Optimized Brute Force
     *
     * for each A[i][k], if it is not zero, it will be used at most B[0].length times
     * If it is, we skip a for-loop, (saving B[0].length calculations)
     *
     * Time complexity:
     * O(A.length * A[0].length + numNonZeroValues(A) * B[0].length)
     *
     * Space complexity: O(1)
     *
     */
    public int[][] multiply(int[][] A, int[][] B) {
        int rows = A.length;
        int n = A[0].length;
        int cols = B[0].length;
        int[][] C = new int[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < cols; j++) {
                        // No need to check if (B[k][j] != 0) because it's is O(1) here
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;
    }

    /**
     * 2. Compressed Matrix
     * Follow-up at FaceBook
     *
     * https://leetcode.com/problems/sparse-matrix-multiplication/discuss/419538/What-the-interviewer-is-expecting-when-this-problem-is-asked-in-an-interview...
     *
     *
     * What if the two input matrix are large and we can not save all of them in memory?
     * Sparse matrix compression. Store the index and value of all non-zero elements
     *
     * What if one vector is significantly longer than the other one???
     * traverse the shorter one and use binary search to find the matched index in the other vector.
     * Then the run time would be O(m*logn).
     * m is the length of the shorter vector while n is the length of the longer vector.
     *
     * https://www.cs.cmu.edu/~scandal/cacm/node9.html
     *
     * https://github.com/SCIN/Facebook-Interview-Coding-1/blob/master/Sparce%20Matrix%20Multiplication.java
     *
     * use two extra vectors to save the value and index of non-zero elements for one sparse matrix.
     * Then the function input will be changed to four vectors, which could save lots of space(non-zero elements are eliminated).
     * The run time is to O(m + n), m and n are the lengths of the compressed vectors separately.
     *
     * 每个vector很大，不能在内存中存下怎么办?
     * 存下非零元素和他们的下标就行
     *
     * 然后问面试官是否可用预处理后的这两个vector非零元素的index和value(假设输入未排序）作为输入 -> O(M * N)
     *
     * 如果已经排好序，双指针 -> O(M + N)
     * 每次移动pair里index0较小的指针，如果相等则进行计算，再移动两个指针。
     *
     * 1 10 29 30 31 32 60
     * 2 3 4 5 6 7 8 9 10
     *
     * 如果两个数组一样长，且一会sparse一会dense怎么办。在two pointer的扫描中内置一个切换二分搜索的机制。
     * 看差值我说过，设计个反馈我说过，他说不好。他期待的解答是，two pointers找到下个位置需要m次比较，而直接二分搜需要log(n)次比较。
     * 那么在你用two pointers方法移动log(n)次以后，就可以果断切换成二分搜索模式了。
     *
     * 如果一个向量比另一个长很多怎么办? 遍历长度短的那一个，然后用二分搜索的方法在另一个vector中找index相同的那个元素，
     * 相乘加入到结果中，这样的话复杂度就是O(M*logN)。
     *
     * Binary search如果找到了一个元素index，那就用这次的index作为下次binary search的开始。可以节约掉之前的东西，不用search了。
     * 然后问，如果找不到呢，如何优化。说如果找不到，也返回上次search结束的index，然后下次接着search。
     * 就是上一次找到了，就用这个index继续找这次的；如果找不到，也有一个ending index，就用那个index当starting index。
     * 比如从[1, 89，100]找90；如果不存在，那么binary search的ending index应该是89，所以下次就从那个index开始。
     * 如果找不到，会返回要插入的位置index + 1，index是要插入的位置，我写的就是返回要插入的index的。
     * 但是不管返回89还是100的index都无所谓，反正只差一个，对performance没有明显影响的。
     *
     * --------
     * Two ways to implement this:
     * 1. Map<Integer, Map<Integer, Integer>: x -> (y -> val)
     * 2. Element {int y, int val};
     * List<List<Element>>: x -> Element
     *
     * Although map involves more overhead, according to LeetCode, map is actually faster,
     * Possible reasons:
     * 1) if there are too many empty rows, List solution will contain lots of empty lists,
     * (Because we need list index aligned with the row number)
     * while Map won't add any empty HashMap for any empty row
     *
     * Map might be a good choice, because
     *
     *
     */
    public int[][] multiply2(int[][] A, int[][] B) {
        if (A == null || B == null) return null;
        if (A[0].length != B.length)
            throw new IllegalArgumentException("A's column number must be equal to B's row number.");
        Map<Integer, HashMap<Integer, Integer>> tableA = compress(A);
        Map<Integer, HashMap<Integer, Integer>> tableB = compress(B);
        int[][] C = new int[A.length][B[0].length];

        for (Integer i: tableA.keySet()) {
            for (Integer k: tableA.get(i).keySet()) {
                if (!tableB.containsKey(k)) {
                    continue;
                }
                for (Integer j: tableB.get(k).keySet()) {
                    C[i][j] += tableA.get(i).get(k) * tableB.get(k).get(j);
                }
            }
        }
        return C;
    }

    private Map<Integer, HashMap<Integer, Integer>> compress(int[][] matrix) {
        Map<Integer, HashMap<Integer, Integer>> res = new HashMap<>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] != 0) {
                    if (res.get(row) == null) {
                        res.put(row, new HashMap<Integer, Integer>());
                    }
                    res.get(row).put(col, matrix[row][col]);
                }
            }
        }
        return res;
    }

    class Element {
        int col;
        int val;
        public Element(int col, int val) {
            this.col = col;
            this.val = val;
        }
    }
    // List<List<Element>>: x/rows -> [{y1, val1}, {yn, valn}]
    public int[][] multiply3(int[][] A, int[][] B) {

        int rows = A.length;
        int n = B.length;
        int cols = B[0].length;
        int[][] res = new int[rows][cols];

        List<List<Element>> a = compressMatrix(A);
        List<List<Element>> b = compressMatrix(B);

        for (int row = 0; row < rows; row++) {
            for (Element e1 : a.get(row)) {
                if (b.get(e1.col).isEmpty()) {
                    continue;
                }
                for (Element e2 : b.get(e1.col)) {
                    res[row][e2.col] += e1.val * e2.val;
                }
            }
        }

        return res;
    }

    private List<List<Element>> compressMatrix(int[][] matrix) {
        List<List<Element>> res = new ArrayList<>(matrix.length);
        for (int row = 0; row < matrix.length; row++) {
            List rowList = new ArrayList<>();
            res.add(rowList);
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] != 0) {
                    rowList.add(new Element(col, matrix[row][col]));
                }
            }
        }
        System.out.println(res);
        return res;
    }

}
