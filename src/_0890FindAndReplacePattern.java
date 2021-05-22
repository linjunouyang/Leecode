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
        List<String> matchedWords = new ArrayList<>();

        for (String word : words) {
            if (isMatch(word, pattern)) {
                matchedWords.add(word);
            }
        }

        return matchedWords;
    }

    private boolean isMatch(String word, String pattern) {
        HashMap<Character, Character> letterMap = new HashMap<>();
        HashSet<Character> mappedChars = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            char wChar = word.charAt(i);
            char pChar = pattern.charAt(i);
            if (letterMap.containsKey(wChar) ) {
                if (pChar != letterMap.get(wChar)) {
                    return false;
                }
            } else {
                if (mappedChars.contains(pChar)) {
                    return false;
                }
                letterMap.put(wChar, pChar);
                mappedChars.add(pChar);
            }
        }
        return true;
    }
}
