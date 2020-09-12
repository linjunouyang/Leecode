import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class _0017LetterCombinationsOfAPhoneNumber {
    /**
     * 1. Backtracking (DFS)
     *
     * Time complexity:
     * 3 + 9 + ... + 3 ^ (len) = 3（3 ^ n  - 1) / (3 - 1) = O(3 ^ n)
     *
     * Space complexity: O(len)
     */
    private static final String[] LETTERS = {" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return res;
        }

        backtrack(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void backtrack(String digits, int idx, StringBuilder sb, List<String> res) {
        if (idx == digits.length()) {
            res.add(sb.toString());
            return;
        }

        int num = digits.charAt(idx) - '0';
        String map = LETTERS[num];
        for (int i = 0; i < map.length(); i++) {
            sb.append(map.charAt(i));
            backtrack(digits, idx + 1, sb, res);
            sb.deleteCharAt(sb.length() - 1); // or we could use sb.setLength()
        }
    }

    /**
     * 2. BFS
     * on ith level 3^i strings, each string has length i,
     * to create a string, we need O(i)
     * Time spent at each level: O(i * 3^i)
     * according to wolframalpha: 0.75(2 * n * 3^n - 3^n + 1)
     * O(n * 3^n)
     *
     * Space: O(4 ^ n)
     * Consider this as a tree
     * the last level is our desired answer, which doesn't contribute to space complexity:
     * the last 2nd level is the biggest space we use, O(4 ^(n - 1))
     *
     */
    public List<String> letterCombinations2(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if (digits.isEmpty()) {
            return ans;
        }
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        while(ans.peek().length()!=digits.length()){
            String remove = ans.remove();
            String map = mapping[digits.charAt(remove.length())-'0'];
            for(char c: map.toCharArray()){
                ans.addLast(remove+c);
            }
        }
        return ans;
    }

}
