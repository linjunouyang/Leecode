import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0392IsSubsequence {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(t)
     * Space complexity: O(1)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /**
     * 2. Divide and Conquery with Greedy
     * String match problems can often solved by Divide and Conquer
     *
     * https://leetcode.com/problems/is-subsequence/solution/
     *
     */

    /**
     * 3. Greedy Match with Character Indices Hashmap
     *
     * In other words, regardless of the source strings, in the worst case,
     * we have to scan the target string repeatedly, even though the target string remains the same.
     *
     * Now with the bottleneck identified, we could ask ourselves if we could do something about it.
     *
     * The reason why we scan the target string is to
     * look for the next character that matches a given character in the source string.
     * In essence, this is a lookup operation in the array data structure.
     *
     * To speed up the lookup operation, the data structure of hashmap could come in handy,
     * since it has a \mathcal{O}(1)O(1) time complexity for its lookup operation.
     *
     * Indeed, we could build a hashmap out of the target string,
     * with each unique character as key and the indices of its appearance as value.
     *
     * Moreover, we should pre-compute this hashmap once and then reuse it for all the following matches.
     *
     * Time complexity: O(T + S * logT)
     * Space complexity: O(T)
     */
    public boolean isSubsequence3(String s, String t) {
        // if we redesign the API to better fit the scenario of the follow-up question, we should put the construction of the hashmap in the constructor of the class, which should be done only onc
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            List<Integer> list = map.getOrDefault(t.charAt(i), new ArrayList<>());
            list.add(i);
            map.put(t.charAt(i), list);
        }

        int lastIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)) {
                return false;
            }
            List<Integer> list = map.get(c);
            lastIndex = binarySearch(list, lastIndex);
            if (lastIndex == -1) {
                return false;
            }
        }

        return true;
    }

    // Find the list's smallest number that is bigger than lowBound
    private int binarySearch(List<Integer> list, int lowBound) {
        if (list == null || list.size() == 0) {
            return -1;
        }

        int low = 0;
        int high = list.size() - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            int midVal = list.get(mid);
            if (midVal <= lowBound) {
                low = mid + 1;
            } else if (midVal > lowBound) {
                high = mid;
            }
        }

        if (list.get(low) > lowBound) {
            return list.get(low);
        } else {
            return -1;
        }
    }

    /**
     * 4. Dynamic Programming:
     *
     * Not ideal
     *
     * Time complexity: O(S * T)
     * Space complexity: O(S * T)
     *
     */
}

