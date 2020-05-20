public class _0157ReadNCharactersGivenRead4 {
    private int read4(char[] buf) {
        // dummy code
        return 4;
    }

    /**
     * 1. One Pass
     *
     * abc 4
     * 3 (abc)
     *
     * Time complexity: O(total)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Read N Characters Given Read4.
     * Memory Usage: 34.5 MB, less than 100.00% of Java online submissions for Read N Characters Given Read4.
     *
     * @param buf
     * @param n
     * @return
     */
    public int read(char[] buf, int n) {
        // Store read chars from Read4
        char[] temp = new char[4];
        int total = 0;

        while (total < n) {
            // Read and store characters in temp. Count will store total chars read from Read4
            int count = read4(temp);

            // Even if we read 4 chars from Read4
            // We don't want to exceed N and only want to read chars till N
            count = Math.min(count, n - total);

            // Transfer all the characters read from Read4 to the buffer
            for (int i = 0; i < count; i++) {
                buf[total] = temp[i];
                total++;
            }

            // Done. Can't read more characters
            if (count < 4) {
                break;
            }
        }

        return total;
    }
}
