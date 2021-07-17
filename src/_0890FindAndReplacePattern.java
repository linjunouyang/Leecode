import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class _0890FindAndReplacePattern {
    /**
     * 1. HashMap
     *
     * bijection from letters to letters:
     * every letter maps to another letter,
     * and no two letters map to the same letter (remember to check this)
     *
     * Similar Idea:
     * https://www.jiuzhang.com/solution/find-and-replace-pattern/
     * string -> digits
     * each char is represented by a number (1-26).
     *
     * Time: O(num of words * num of chars)
     * Space: O(num of chars)
     */
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        // question to ask: duplicate in words?
        List<String> matches = new ArrayList<>();

        for (String word : words) {
            if (isMatch(word, pattern)) {
                matches.add(word);
            }
        }

        return matches;
    }

    // guaranteed same length
    private boolean isMatch(String word, String pattern) {
        HashMap<Character, Character> wordToPattern = new HashMap<>();
        HashMap<Character, Character> patternToWord = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char wChar = word.charAt(i);
            char pChar = pattern.charAt(i);

            if (wordToPattern.containsKey(wChar) && wordToPattern.get(wChar) != pChar) {
                return false;
            }
            wordToPattern.put(wChar, pChar);

            if (patternToWord.containsKey(pChar) && patternToWord.get(pChar) != wChar) {
                return false;
            }
            patternToWord.put(pChar, wChar);
        }

        return true;
    }
}
