import java.util.ArrayList;
import java.util.List;

public class _0006ZigZagConversion {
    /**
     * 1. Sort by Row
     *
     * StringBuffer is thread safe.
     *
     * You can append a StringBuilder to another StringBuilder
     *
     * Time complexity: O(length of s)
     * Space complexity: O(length of s)
     *
     * Runtime: 7 ms, faster than 54.89% of Java online submissions for ZigZag Conversion.
     * Memory Usage: 38.5 MB, less than 84.04% of Java online submissions for ZigZag Conversion.
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (s == null || numRows == 0) {
            return "";
        }

        if (numRows == 1) {
            return s;
        }

        // Initialize every row string builder
        List<StringBuilder> rows = new ArrayList<>();
        numRows = Math.min(s.length(), numRows);
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        // Put each charcter in place
        int curRow = 0;
        boolean goingDown = false;
        for (int i = 0; i < s.length(); i++) {
            rows.get(curRow).append(s.charAt(i));
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }

        // Build final string
        StringBuilder sb = new StringBuilder();
        for (StringBuilder builder : rows) {
            sb.append(builder);
        }

        return sb.toString();
    }

    /**
     * 2. Math
     *
     * Illustration:
     * https://leetcode.com/problems/zigzag-conversion/discuss/3435/If-you-are-confused-with-zigzag-patterncome-and-see!
     *
     * if i points to first or last row (interval = step or 0) only one element per cycle
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert2(String s, int numRows) {
        int length = s.length();

        if (length <= numRows || numRows == 1) {
            return s;
        }

        char[] chars = new char[length];
        int step = 2 * (numRows - 1);
        int count = 0;

        for (int i = 0; i < numRows; i++){
            int interval = step - 2 * i;

            for (int j = i; j < length; j += step){
                chars[count] = s.charAt(j);
                count++;
                if (interval < step && interval > 0
                        && j + interval < length && count < length){
                    chars[count] = s.charAt(j + interval);
                    count++;
                }
            }
        }
        return new String(chars);
    }
}
