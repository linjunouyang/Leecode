public class GuessNumberHigherOrLower {
    /**
     * Binary Search
     * Time Complexity: O(logn)
     * Space Complexity: O(1)
     * @param n
     * @return
     */
    public int guessNumber(int n) {
        int start = 0;
        int end = n;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (guess(mid) == 0) {
                return mid;
            } else if (guess(mid) == -1) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (guess(start) == 0) {
            return start;
        } else {
            return end;
        }
    }
}
