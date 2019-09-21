package DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * Two queens can't show in the same row, column, and 斜线
 *
 * Time complexity: O(s * n^3)
 * s: the number of answers
 * n^3: the construction time for each answer
 * (there are n elements in the cols, each element takes roughly O(n/2) time on search for loop
 * and O(n/2) time on isValid, O(n^2) on drawChessBoard
 *
 *
 * Runtime: 4 ms, faster than 54.76% of Java online submissions for N-Queens.
 * Memory Usage: 37.7 MB, less than 91.89% of Java online submissions for N-Queens.
 */
public class _51NQueens {

    List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }

        search(results, new ArrayList<Integer>(), n);

        return results;
    }

    /**
     * There are multiple ways to represent each queen's coordinate
     *
     * Here we use List<Integer> cols. The row index can be inferred from the list index
     *
     * @param results
     * @param cols
     * @param n
     */
    private void search(List<List<String>> results, List<Integer> cols, int n) {
        if (cols.size() == n) {
            // no need to new a new list, because the function returns a different list every time
            results.add(drawChessboard(cols));
            return;
        }

        for (int colIndex = 0; colIndex < n; colIndex++) {
            if (!isValid(cols, colIndex)) {
                continue;
            }

            cols.add(colIndex);
            search(results, cols, n);
            cols.remove(cols.size() - 1);
        }
    }

    private List<String> drawChessboard(List<Integer> cols) {
        List<String> chessboard = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < cols.size(); j++) {
                sb.append(j == cols.get(i) ? 'Q' : '.');
            }
            chessboard.add(sb.toString());
        }
        return chessboard;
    }

    private boolean isValid(List<Integer> cols, int column) {
        int row = cols.size();
        for (int rowIndex = 0; rowIndex < cols.size(); rowIndex++) {
            // just check the columnn
            // no need to check row, because every element in cols is in a different row
            if (cols.get(rowIndex) == column) {
                return false;
            }
            if (rowIndex + cols.get(rowIndex) == row + column) {
                return false;
            }
            if (rowIndex - cols.get(rowIndex) == row - column) {
                return false;
            }
        }
        return true;
    }
}
