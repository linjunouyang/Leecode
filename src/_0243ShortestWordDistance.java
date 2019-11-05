import java.util.ArrayList;
import java.util.List;

public class _0243ShortestWordDistance {
    /**
     * 1. Brute Force
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     *
     * @param words
     * @param word1
     * @param word2
     * @return
     */
    public int shortestDistance(String[] words, String word1, String word2) {
        int minDistance = words.length;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                for (int j = 0; j < words.length; j++) {
                    if (words[j].equals(word2)) {
                        minDistance = Math.min(minDistance, Math.abs(i - j));
                    }
                }
            }
        }
        return minDistance;
    }

    /**
     * 2. One Pass
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Shortest Word Distance.
     * Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Shortest Word Distance.
     *
     * @param words
     * @param word1
     * @param word2
     * @return
     */
    public int shortestDistance2(String[] words, String word1, String word2) {

        int i1 = -1;
        int i2 = -1;
        int minDistance = words.length - 1;

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                i1 = i;
                if (i2 >= 0) {
                    minDistance = Math.min(minDistance, i1 - i2);
                }
            } else if (words[i].equals(word2)) {
                i2 = i;
                if (i1 >= 0) {
                    minDistance = Math.min(minDistance, i2 - i1);
                }
            }
        }
        return minDistance;

    }









}
