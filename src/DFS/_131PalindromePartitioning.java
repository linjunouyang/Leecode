package DFS;

import java.util.ArrayList;
import java.util.List;

public class _131PalindromePartitioning {

    /**
     * 1. DFS
     *
     * Notice
     * 1. str.length() instead of str.length
     * 2. working with collections and objects, partitions.add(new ArrayList<>(partition))
     * 3. parition.remove() parameter is the index, not the element
     * 4. for loop syntax (comma) : for (int i = 0, j = str.length() - 1; i < j; i++, j--)
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> results = new ArrayList<>();

        if (s == null || s.length() == 0) {
            return results;
        }

        List<String> partition = new ArrayList<>();
        helper(s, 0, partition, results);

        return results;
    }

    private void helper (String s, int start, List<String> partition, List<List<String>> results) {
        if (start == s.length()) {
            results.add(new ArrayList<>(partition));
            return;
        }

        for (int i = start; i < s.length(); i++) {
            String subStr = s.substring(start, i + 1);

            if (!isPalindorme(subStr)) {
                continue;
            }

            partition.add(subStr);
            helper(s, start + 1, partition, results);
            partition.remove(partition.size() - 1);
        }
    }

    private boolean isPalindorme(String str) {
        for (int i = 0, j = str.length() - 1; i < j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }

        return true;
    }
}
