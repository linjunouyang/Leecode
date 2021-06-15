public class _0953VerifyingAnAlienDictionary {
    /**
     * 1. HashMap, adjacent comparison
     *
     * Time complexity: O(number of words * length)
     * Space complexity:  O(1)
     */
    public boolean isAlienSorted(String[] words, String order) {
        // char - 'a' -> order
        int[] map = new int[26];

        for (int i = 0; i < order.length(); i++) {
            map[order.charAt(i) - 'a'] = i;
        }

        for (int i = 1; i < words.length; i++) {
            if (bigger(map, words[i - 1], words[i])) {
                return false;
            }
        }
        return true;
    }

    boolean bigger(int[] map, String s1, String s2) {
        int end = Math.min(s1.length(), s2.length());
        for (int i = 0; i < end; ++i) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return map[s1.charAt(i) - 'a'] > map[s2.charAt(i) - 'a'];
            }
        }
        return s1.length() > s2.length();
    }

}
