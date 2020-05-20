import java.util.ArrayList;
import java.util.List;

public class _0293FlipGame {
    /**
     * 1. Iteration
     *
     * String(char[] value):
     * The contents of the character array are copied;
     * subsequent modification of the character array does not affect the newly created string.
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Flip Game.
     * Memory Usage: 36.2 MB, less than 100.00% of Java online submissions for Flip Game.
     *
     * @param s
     * @return
     */
    public List<String> generatePossibleNextMoves(String s) {
        List<String> results = new ArrayList<>();

        if (s == null) {
            return results;
        }

        char[] chars = s.toCharArray();

        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == '+' && chars[i] == '+') {
                chars[i] = '-';
                chars[i - 1] = '-';

                results.add(new String(chars));

                chars[i] = '+';
                chars[i - 1] = '+';
            }
        }

        return results;
    }

}
