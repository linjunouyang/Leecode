import java.util.HashMap;
import java.util.Map;

public class _0748ShortestCompletingWord {
    /**
     * 1. HashMap
     *
     * Details
     * 1. Type Map doesnt have type parameters
     * 2. Character.toLowerCase()
     * 3. Character.isAlphabetic() vs Character.isLetter (upperCase, lowerCase, titleCase, modifier, other)
     *
     * Time: O(lP len + num of words * ave length)
     * Space: O(1)
     *
     */
    public String shortestCompletingWord(String licensePlate, String[] words) {
        HashMap<Character, Integer> charToFrequency = countChars(licensePlate);

        int len = Integer.MAX_VALUE;
        String ans = "";

        for (String word : words) {
            HashMap<Character, Integer> map = countChars(word);
            if (isCompleting(map, charToFrequency) && word.length() < len) {
                ans = word;
                len = word.length();
            }
        }

        return ans;
    }

    private HashMap<Character, Integer> countChars(String s) {
        HashMap<Character, Integer> charToFrequency = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isAlphabetic(c)) {
                continue;
            }
            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            }
            int oldFrequency = charToFrequency.getOrDefault(c, 0);
            charToFrequency.put(c, oldFrequency + 1);
        }

        return charToFrequency;
    }

    private boolean isCompleting(HashMap<Character, Integer> targetMap, HashMap<Character, Integer> sourceMap) {
        for (Map.Entry<Character, Integer> entry : sourceMap.entrySet()) {
            char c = entry.getKey();
            int fre = entry.getValue();
            if (!targetMap.containsKey(c) || targetMap.get(c) < fre) {
                return false;
            }
        }
        return true;
    }
}
