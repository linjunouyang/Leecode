import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * https://leetcode.com/problems/reverse-words-in-a-string/solution/
 */
public class _0151ReverseWordsInAString {
    /**
     * 1. Built-in split + Reverse
     *
     * "\s" is a regex class for any kind of whitespace (space, tab, newline, etc).
     * Since Java uses "\" as an escape character in strings (e.g. for newlines: "\n"),
     * we need to escape the escape character ;-) So it becomes "\\s".
     * The "+" means one or more of them.
     *
     * --------------------
     * split(String regex):
     *
     * calling split(regex, 0)
     * limit = 0 -> trailing empty strings are therefore not included, but leading empty strings still exist
     *
     * When there is a positive-width match at the beginning of this string
     * then an empty leading substring is included at the beginning of the resulting array.
     * A zero-width match at the beginning however never produces such empty leading substring.
     *
     * "  abc" -> ["", "abc"]
     * "abc " -> ["abc"]
     * -------------------
     * -> to avoid leading empty strings, we use trim() before split()
     *
     * Arrays.asList : returns a fixed-size list backed by the array
     * Collections.reverse: O(n)
     * Lists.reverse: returns a view. O(1)
     *
     *
     * not mentioned in this solution, but imporant to remember:
     * Java String concatenation is bad, it will create a new String. O(n ^ 2)
     * -> Use StringBuilder instead
     *
     *
     * n : number of characters
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 6 ms, faster than 57.49% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 37.4 MB, less than 97.85% of Java online submissions for Reverse Words in a String.
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        // or split(" +")
        List<String> wordList = Arrays.asList(s.trim().split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }

    /**
     * 2. Reverse the whole String and reverse each word
     *
     * In the case of immutable strings one has first to convert string into mutable data structure,
     * and hence it makes sense to trim all spaces during that conversion.
     *
     * A similar solution:
     * https://leetcode.com/problems/reverse-words-in-a-string/discuss/47720/Clean-Java-two-pointers-solution-(no-trim(-)-no-split(-)-no-StringBuilder)
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param s
     * @return
     */
    public String reverseWords2(String s) {
        // Convert String to String Builder and trim spaces at the same time
        StringBuilder sb = trimSpaces(s);

        // reverse the whole String
        reverse(sb, 0, sb.length() - 1);

        // reverse each word
        reverseEachWord(sb);

        return sb.toString();
    }

    public StringBuilder trimSpaces(String s) {
        int left = 0;
        int right = s.length() - 1;

        // find first non-space character
        while (left <= right && s.charAt(left) == ' ') {
            ++left;
        }

        // find last non-space character
        while (left <= right && s.charAt(right) == ' ') {
            --right;
        }

        // reduce multiple spaces to single one
        StringBuilder sb = new StringBuilder();

        while (left <= right) {
            char c = s.charAt(left);

            if (c != ' ') {
                sb.append(c);
            } else if (sb.charAt(sb.length() - 1) != ' ') {
                sb.append(c);
            }

            ++left;
        }

        return sb;
    }

    public void reverse(StringBuilder sb, int left, int right) {
        while (left < right) {
            char tmp = sb.charAt(left);
            sb.setCharAt(left++, sb.charAt(right));
            sb.setCharAt(right--, tmp);
        }
    }

    public void reverseEachWord(StringBuilder sb) {
        int n = sb.length();
        int start = 0;
        int end = 0;

        while (start < n) {
            // go to the end of every word
            while (end < n && sb.charAt(end) != ' ') ++end;

            // reverse the word
            reverse(sb, start, end - 1);

            // move to the next word
            start = end + 1;
            ++end;
        }
    }


    /**
     * 3. Reverse the whole String and reverse each word
     *
     * storeIdx: the current position available for insertion.
     * j: the end of one word(including one trailing space), used for copying word one by one.
     * i: the beginning of one word.
     *
     * 1. put a blank space in front of the word if this word is not the first word
     * 2. copy the word to the position starts with storeIndex
     * 3. reverse the word
     * 4. update the possible start of next word
     *
     * Notice that if input is " ", output should be ""
     * Input string may contain leading or trailing spaces.
     * However, your reversed string should not contain leading or trailing spaces.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     *
     * Runtime: 2 ms, faster than 86.53% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 36 MB, less than 100.00% of Java online submissions for Reverse Words in a String.
     *
     * @param s
     * @return
     */
    public String reverseWords3(String s) {
        if (s == null || s.length() < 1) return s; // empty string

        char[] str = s.toCharArray();

        // reverse whole string
        reverse(str, 0, str.length - 1);

        int startIdx = 0;

        // reverse word one by one
        for (int i = 0; i < str.length; i++) {
            if (str[i] != ' ') {
                if (startIdx != 0) str[startIdx++] = ' ';
                int j = i;
                while (j < str.length && str[j] != ' ')
                    str[startIdx++] = str[j++];
                reverse(str, startIdx - (j - i), startIdx - 1); // startIdx - 1, NOT startIdx;
                i = j;
            }
        }

        return new String(str, 0, startIdx);
    }

    private void reverse(char[] str, int begin, int end) {
        for (; begin < end; begin++, end--) {
            char tmp = str[begin];
            str[begin] = str[end];
            str[end] = tmp;
        }
    }


}
