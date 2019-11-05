public class _0245ShortestWordDistanceIII {
    /**
     * 1. One pass
     *
     * same is for minimizing the number of string comparisons (word1, word2)
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param words
     * @param word1
     * @param word2
     * @return
     */
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int dist = words.length - 1;
        int i1 = words.length;
        int i2 = -words.length;
        boolean same = word1.equals(word2);

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                if (same) {
                    i2 = i1;
                }
                i1 = i;

            } else if (words[i].equals(word2)) {
                // must use else if
                // ["a", "a"] "a" "a"
                // Output: 0, Expected: 1
                i2 = i;
            }

            dist = Math.min(dist, Math.abs(i1 - i2));
        }

        return dist;
    }
}
