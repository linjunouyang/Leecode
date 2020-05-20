public class _0383RansomNote {

    /**
     * 1. Count
     *
     * Time complexity: O(m + n)
     * Space complexity: O(1)
     *
     * Runtime: 4 ms, faster than 73.12% of Java online submissions for Ransom Note.
     * Memory Usage: 37.7 MB, less than 100.00% of Java online submissions for Ransom Note.
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || ransomNote.isEmpty()) {
            return true;
        }

        if (magazine == null || magazine.isEmpty()) {
            return false;
        }

        int[] letterCount = new int[26];

        for (int i = 0; i < ransomNote.length(); i++) {
            letterCount[ransomNote.charAt(i) - 'a']++;
        }

        for (int i = 0; i < magazine.length(); i++) {
            if (--letterCount[magazine.charAt(i) - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

}
