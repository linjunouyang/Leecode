public class _0038CountAndSay {
    /**
     * 1. Recursion
     *
     * Time complexity: O(sum of lengths of all sequences)
     * Space complexity: O(length of longest sequence)
     *
     * Runtime: 1 ms, faster than 99.56% of Java online submissions for Count and Say.
     * Memory Usage: 34 MB, less than 100.00% of Java online submissions for Count and Say.
     *
     *
     * @param n
     * @return
     */
    public String countAndSay(int n) {
        return countHelper("1", n);
    }

    private String countHelper(String s, int times) {
        if (times == 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length();) {
            int count = 1;

            while (i + 1 < s.length() && s.charAt(i + 1) == s.charAt(i)) {
                i++;
                count++;
            }

            sb.append(count);
            sb.append(s.charAt(i));

            i++;
        }

        return countHelper(sb.toString(), times - 1);
    }

    /**
     * 2. Iteration
     *
     * Time complexity:
     * Space complexity:
     *
     * Runtime: 2 ms, faster than 69.55% of Java online submissions for Count and Say.
     * Memory Usage: 35.3 MB, less than 100.00% of Java online submissions for Count and Say.
     *
     * @param n
     * @return
     */
    public String countAndSay2(int n) {
        String oldString = "1";
        while (--n > 0) {
            StringBuilder sb = new StringBuilder();
            char [] oldChars = oldString.toCharArray();

            for (int i = 0; i < oldChars.length; i++) {
                int count = 1;
                while ((i+1) < oldChars.length && oldChars[i] == oldChars[i+1]) {
                    count++;
                    i++;
                }
                sb.append(String.valueOf(count) + String.valueOf(oldChars[i]));
            }
            oldString = sb.toString();
        }

        return oldString;
    }
}
