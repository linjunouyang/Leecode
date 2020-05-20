public class _0168ExcelSheetColumnTitle {
    /**
     * 1.
     *
     * Time complexity: O(length of string)
     * Space complexity: O(length of string)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Title.
     * Memory Usage: 33.8 MB, less than 100.00% of Java online submissions for Excel Sheet Column Title.
     *
     * @param n
     * @return
     */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();

        while (n >= 1) {
            n--;
            sb.append((char) (n % 26 + 'A'));
            n = n / 26;
        }

        return sb.reverse().toString();

    }
}
