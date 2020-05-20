public class _0171ExcelSheetColumnNumber {
    /**
     * 1. One Pass
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param s
     * @return
     */
    public int titleToNumber(String s) {
        if (s == null) {
            return 0;
        }

        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            num = num * 26 +  s.charAt(i) - 'A' + 1;
        }

        return num;
    }
}
