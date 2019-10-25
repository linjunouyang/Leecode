public class _0263UglyNumber {
    /**
     * 1. Intuition
     *
     * Time complexity: O(lg2 n)
     * Space complexity: O(1)
     *
     * @param num
     * @return
     */
    public boolean isUgly(int num) {
        for (int i = 2; num > 0 && i < 6; i++) {
            while (num % i == 0) {
                num /= i;
            }
        }

        return num == 1;
    }
}
