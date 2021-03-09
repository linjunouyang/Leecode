import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class _0119PascalTriangleII {
    /**
     * 1. Recursion (TLE)
     *
     * T(row, i) = T(row - 1, i) + T(row - 1, i - 1) + O(1)
     * T(row, i) takes  C(row, i) units of constant time
     * T(row, 0) + ... + T(row, row) = 2 ^ row
     * Time: O(2 ^ row)
     * Space: O(row)
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        for (int col = 0; col <= rowIndex; col++) {
            row.add(compute(rowIndex, col));
        }
        return row;
    }

    private int compute(int row, int col) {
        if (col == 0 || col == row || row == 0) {
            return 1;
        }
        return compute(row - 1, col - 1) + compute(row - 1, col);
    }

    /**
     * 2. Recursion with Memoization / DP top-down
     *
     * Time: O(row ^ 2)
     *
     * Space:
     * Recursion Stack: O(row)
     * HashMap: O(row ^ 2)
     */
    public List<Integer> getRow2(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        HashMap<String, Integer> posToNum = new HashMap<>();
        for (int col = 0; col <= rowIndex; col++) {
            row.add(compute(rowIndex, col, posToNum));
        }
        return row;
    }

    private int compute(int row, int col, HashMap<String, Integer> posToNum) {
        if (col == 0 || col == row) {
            return 1;
        }
        String pos = row + "," + col;
        if (posToNum.containsKey(pos)) {
            return posToNum.get(pos);
        }
        int num = compute(row - 1, col - 1, posToNum) + compute(row - 1, col, posToNum);
        posToNum.put(pos, num);
        return num;
    }

    /**
     * 3. DP bottom up
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    public List<Integer> getRow3(int rowIndex) {
        List<Integer> prevRow = new ArrayList<>();
        for (int row = 0; row <= rowIndex; row++) {
            List<Integer> curRow = new ArrayList<>();
            for (int i = 0; i <= row; i++) {
                if (i == 0 || i == row) {
                    curRow.add(1);
                } else {
                    int num = prevRow.get(i - 1) + prevRow.get(i);
                    curRow.add(num);
                }
            }
            prevRow = curRow;
        }

        return prevRow;
    }

    /**
     * 3.1 Slightly optimized 3
     *
     * Although there is no savings in theoretical computational complexity, in practice there are some minor wins:
     * 1. We have one vector/array instead of two. So memory consumption is roughly half.
     * 2. No time wasted in swapping references to vectors for previous and current row.
     * 3. Locality of reference shines through here.
     * Since every read is for consecutive memory locations in the array/vector, we get a performance boost.
     */
    public List<Integer> getRow31(int rowIndex) {
        List<Integer> list = new ArrayList<>(rowIndex + 1);
        for (int row = 0; row <= rowIndex; row++) {
            for (int i = list.size() - 1; i > 0; i--) {
                list.set(i, list.get(i - 1) + list.get(i));

            }
            list.add(1);
        }

        return list;
    }

}
