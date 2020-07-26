import java.util.Arrays;

public class _0767ReorganizeString {
    /**
     * 1. Alternative placement
     *
     * Greedily satisfy letters with most occurences
     *
     * count letter appearance and store in hash[i]
     * find the letter with largest occurence.
     * put the letter into even index numbe (0, 2, 4 ...) char array
     * put the rest into the array
     *
     * a _ a _ a _ _ _ _ // fill in "a" at position 0, 2, 4
     * a b a _ a _ b _ b // fill in "b" at position 6, 8, 1
     * a b a c a _ b _ b // fill in "c" at position 3
     * a b a c a d b d b // fill in "d" at position 5, 7
     *
     * Note 2nd step:
     * e.g. if we went to 1,3,5 instead of 6,8,1. It would lead to "abababcdd"
     * -> same chars aligned at the end.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param S
     * @return
     */
    public String reorganizeString(String S) {
        int[] hash = new int[26];
        for (int i = 0; i < S.length(); i++) {
            hash[S.charAt(i) - 'a']++;
        }
        int max = 0, letter = 0;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }
        if (max > (S.length() + 1) / 2) {
            return "";
        }
        char[] res = new char[S.length()];
        int idx = 0;
        while (hash[letter] > 0) {
            res[idx] = (char) (letter + 'a');
            idx += 2;
            hash[letter]--;
        }
        for (int i = 0; i < hash.length; i++) {
            while (hash[i] > 0) {
                if (idx >= res.length) {
                    idx = 1; // this assignment will only happen once because each iteration assigns n/2 spots
                }
                res[idx] = (char) (i + 'a');
                idx += 2;
                hash[i]--;
            }
        }
        return String.valueOf(res);
    }
}
