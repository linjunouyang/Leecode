import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class _0819MostCommonWord {
    /**
     * 1. String processing in pipeline
     *
     * https://docs.oracle.com/javase/tutorial/essential/regex/literals.html
     * metacharacter - a character with special meaning interpreted by the matcher.
     * The metacharacters supported by this API are: <([{\^-=$!|]})?*+.>
     *
     * There are two ways to force a metacharacter to be treated as an ordinary character:
     * 1) precede the metacharacter with a backslash, or
     * 2) enclose it within \Q (which starts the quote) and \E (which ends it).
     *
     * + usually is a repetition operator, and causes the preceding token to repeat one or more times. a+ would be expressed as aa* i
     *
     * n: num of chars in paragraph
     * m: number of characters in the banned list
     *
     * Time: O(n + m)
     * Space: O(n + m)
     *
     */
    public String mostCommonWord(String paragraph, String[] banned) {

        // 1). replace the punctuations with spaces,
        // and put all letters in lower case
        String normalizedStr = paragraph.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();

        // 2). split the string into words
        // escape characters that have special meaning. In other words, to force them to be treated as ordinary characters.
        String[] words = normalizedStr.split("\\s+");

        Set<String> bannedWords = new HashSet();
        for (String word : banned) {
            bannedWords.add(word);
        }

        HashMap<String, Integer> wordCount = new HashMap();
        // 3). count the appearance of each word, excluding the banned words
        for (String word : words) {
            if (!bannedWords.contains(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        // 4). return the word with the highest frequency
        return Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * 2. Character Processing in one-pass
     */
    public String mostCommonWord2(String paragraph, String[] banned) {
        HashSet<String> bannedSet = new HashSet<>();
        for (String bannedWord : banned) {
            bannedSet.add(bannedWord);
        }

        StringBuilder sb = new StringBuilder();
        HashMap<String, Integer> wordToCount = new HashMap<>();
        String ans = "";
        int maxCount = 0;

        for (int i = 0; i < paragraph.length(); i++) {
            char c = paragraph.charAt(i);

            if (Character.isAlphabetic(c)) {
                sb.append(Character.toLowerCase(c));
                if (i != paragraph.length() - 1) {
                    continue;
                }
            }

            if (sb.length() != 0) {
                // prevent "" enter HashMap
                String word = sb.toString();
                if (!bannedSet.contains(word)) {
                    int oldCount = wordToCount.getOrDefault(word, 0);
                    wordToCount.put(word, oldCount + 1);
                    if (oldCount + 1 > maxCount) {
                        maxCount = oldCount + 1;
                        ans = word;
                    }
                }

                // reset buffer for the next word
                sb = new StringBuilder();
            }
        }

        return ans;
    }
}
