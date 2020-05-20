public class _0158ReadNCharactersGivenRead4II_CallMultipleTimes {
    char[] prevBuf = new char[4];
    int prevSize = 0;
    int prevIndex = 0;

    public int read4(char[] buf) {
        // dummy code
        return 4;
    }

    /**
     * 1.
     *
     * Whenever we call read(n), read from prevBuf first until all characters in prevBuf are consumed
     * (to do this, we need 2 more int variables prevSize and prevIndex,
     * which tracks the actual size of prevBuf and the index of next character to read from prevBuf).
     * Then call read4 to read characters into prevBuf.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Read N Characters Given Read4 II - Call multiple times.
     * Memory Usage: 36.1 MB, less than 100.00% of Java online submissions for Read N Characters Given Read4 II - Call multiple times.
     *
     * @param buf
     * @param n
     * @return
     */
    public int read(char[] buf, int n) {
        int counter = 0;

        while (counter < n) {
            if (prevIndex < prevSize) {
                buf[counter++] = prevBuf[prevIndex++];
            } else {
                prevSize = read4(prevBuf);
                prevIndex = 0;
                if (prevSize == 0) {
                    // no more data to consume from stream
                    break;
                }
            }
        }
        return counter;
    }

}
