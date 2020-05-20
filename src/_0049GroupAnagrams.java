import java.util.*;

public class _0049GroupAnagrams {
    /**
     * 1. Categorize by Sorted String
     *
     * Time complexity: O(N K logK)
     * N: length of the strs
     * K: Maximum length of a string in strs
     *
     * Space complexity: O(NK)
     *
     * Runtime: 8 ms, faster than 96.99% of Java online submissions for Group Anagrams.
     * Memory Usage: 42.5 MB, less than 86.55% of Java online submissions for Group Anagrams.
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<String>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }

    /**
     * 2. Categorize by Count
     *
     * Time complexity: O(NK) N: the length of strs, K: the max length of a string in strs
     * Space complexity: O(NK)
     *
     * Runtime: 16 ms, faster than 22.92% of Java online submissions for Group Anagrams.
     * Memory Usage: 44.3 MB, less than 43.86% of Java online submissions for Group Anagrams.
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams2(String[] strs) {
        if (strs.length == 0) return new ArrayList();

        Map<String, List> ans = new HashMap<String, List>();

        int[] count = new int[26];

        for (String s : strs) {
            Arrays.fill(count, 0);

            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < 26; i++) {
                sb.append(count[i]);
            }
            String key = sb.toString();

            // or String key = Arrays.toString(count);

            if (!ans.containsKey(key)) {
                ans.put(key, new ArrayList());
            }
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());

    }




}
