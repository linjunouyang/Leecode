public class ValidPerfectSquare {
    /**
     * Intution - Iteration
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param num
     * @return
     */
    public boolean isPerfectSquare1(int num){
        if (num < 0) {
            return false;
        }
        if (num == 0 || num == 1) {
            return true;
        }

        for (int i = 1; i * i <= num; i++) {
            if (i * i == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Binary Search
     * Time Complexity: O(logn)
     * Space Complexity: O(1)
     * @param num
     * @return
     */
    public boolean isPerfectSquare2(int num) {
        if (num == 0 || num == 1) {
            return true;
        }

        int start = 1;
        int end = num - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid < num) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (start * start == num) {
            return true;
        } else if (end * end == num) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The Sum of Odd Numbers
     * Time Complexity: O(sqrt(N))
     * Space Complexity: O(1)
     */
    public boolean isPerfectSquare3(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i = i + 2;
        }
        return num == 0;
    }

    /**
     * Newton
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * @param num
     * @return
     */
    public boolean isPerfectSquare4(int num) {
        long x = num;
        while (x * x > num) {
            x = (x + num / x) / 2;
        }
        return x * x == num;
    }
}
