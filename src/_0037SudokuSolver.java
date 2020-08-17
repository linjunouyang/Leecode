import java.util.Arrays;

public class _0037SudokuSolver {
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0) {
            return;
        }
        solve(board, 0, 0);
    }

    public boolean solve(char[][] board, int row, int col){
        if (col == 9) {
            // reach the last column, next row
            return solve(board, row + 1, 0);
        }

        if (row == 9) {
            // reach the last row, finish
            return true;
        }

        if (board[row][col] != '.') {
            // preset number, skip
            return solve(board, row, col + 1);
        }

        boolean[] flag = new boolean[10];
        filter(board, row, col, flag);

        for (int k = 1; k <= 9; k++) {
            if (flag[k]) {
                board[row][col] = (char)('0' + k);
                if (solve(board, row, col + 1)) {
                    return true;
                }
            }
        }
        board[row][col] = '.';
        return false;
    }

    private void filter(char[][] board, int row, int col, boolean[] flag) {
        Arrays.fill(flag,true);
        int regionRow = 3 * (row / 3);  //region start row
        int regionCol = 3 * (col / 3);    //region start col
        for (int k = 0; k < 9; k++) {
            if (board[row][k] != '.') {
                flag[board[row][k] - '0'] = false;
            }
            if (board[k][col] != '.') {
                flag[board[k][col] - '0'] = false;
            }
            if (board[regionRow + k / 3][regionCol + k % 3] != '.') {
                flag[board[regionRow + k / 3][regionCol + k % 3] - '0'] = false;
            }
        }
    }

    /**
     * 2.
     *
     * Optimization from 1:
     *
     */
    private final int n = 3;
    private final int N = n * n;

    private boolean[][] rowUsage;
    private boolean[][] colUsage;
    private boolean[][] gridUsage;

    private char[][] board;

    public void solveSudoku2(char[][] board) {
        rowUsage = new boolean[N][N + 1];
        colUsage = new boolean[N][N + 1];
        gridUsage = new boolean[N][N + 1];
        this.board = board;

        // populate usages
        findUsages();

        // fix cells with only one candidate
        int[] fixedUnfixed;
        do {
            fixedUnfixed = fillBoard();
        } while (fixedUnfixed[0] != 0);

        if (fixedUnfixed[1] != 0) {
            backtrack(0, 0);
        }
    }

    private void findUsages() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != '.') {
                    int number = board[i][j] - '0';
                    fillBox(i, j, number);
                }
            }
        }
    }

    private boolean backtrack(int i, int j) {
        if (i == N) {
            return true;
        }

        int ni = (j == N - 1) ? i + 1 : i;
        int nj = (j == N - 1) ? 0 : j + 1;

        if (board[i][j] != '.') {
            return backtrack(ni, nj);
        }

        int gridIdx = getGridIdx(i, j);
        for (int number = 1; number <= 9; number++) {
            if (rowUsage[i][number] || colUsage[j][number] || gridUsage[gridIdx][number]) {
                continue;
            }
            fillBox(i, j, number);
            if (backtrack(ni, nj)) {
                return true;
            }
            removeBox(i, j, number);
        }
        return false;
    }

    private int[] fillBoard() {
        int fixed = 0, unfixed = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '.') {
                    int number = findSoleCandidate(i, j);
                    if (number != 0) {
                        fillBox(i, j, number);
                        fixed++;
                    } else {
                        unfixed++;
                    }
                }
            }
        }
        return new int[]{fixed, unfixed};
    }

    private int findSoleCandidate(int i, int j) {
        int count = 0;
        int candidate = 0;
        int gridIdx = getGridIdx(i, j);
        for (int number = 1; number <= 9; number++) {
            if (rowUsage[i][number] || colUsage[j][number] || gridUsage[gridIdx][number]) {
                continue;
            }
            count++;
            candidate = number;
        }
        return count == 1 ? candidate : 0;
    }

    // number: [1,9]
    private void fillBox(int i, int j, int number) {
        rowUsage[i][number] = true;
        colUsage[j][number] = true;
        gridUsage[getGridIdx(i, j)][number] = true;
        board[i][j] = (char) (number + '0');
    }

    private void removeBox(int i, int j, int number) {
        rowUsage[i][number] = false;
        colUsage[j][number] = false;
        gridUsage[getGridIdx(i, j)][number] = false;
        board[i][j] = '.';
    }

    private int getGridIdx(int i, int j) {
        return (i / n) * n + (j / n);
    }
}
