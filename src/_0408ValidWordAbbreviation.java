public class _0408ValidWordAbbreviation {
    /**
     * 1. Two Pointers
     *
     * Time: O(min len)
     * Space: O(1)
     */
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
            } else if ((abbr.charAt(j) > '0') && (abbr.charAt(j) <= '9')) {     //notice that 0 cannot be included
                int num = 0;
                int start = j;
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
                    num = num * 10 + abbr.charAt(j) - '0';
                    j++;
                }
                i += num;
            } else {
                return false;
            }
        }
        return (i == word.length()) && (j == abbr.length()); // (a 2) (hi hi1)
    }
}
