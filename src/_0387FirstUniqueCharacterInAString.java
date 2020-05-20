import java.util.*;

public class _0387FirstUniqueCharacterInAString {

    /**
     * 1. Two Iterations
     *
     * Follow up:
     * 1) if the string contains any characters which are represented by ASCII
     * int[256]
     *
     * Good O(n) solution, but it still can be improved, since you read through the whole array twice.
     * Take an example of DNA sequence: there could be millions of letters long with just 4 alphabet letter.
     * What happened if the non-repeated letter is at the end of DNA sequence?
     * This would dramatically increase your running time since we need to scan it again.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 58 ms, faster than 6.29% of Java online submissions for First Unique Character in a String.
     * Memory Usage: 37.9 MB, less than 99.29% of Java online submissions for First Unique Character in a String.
     *
     * @param s
     * @return
     */
    public int firstUniqChar(String s) {
        if (s == null) {
            return -1;
        }

        // or use array: new int[26]
        // freq [s.charAt(i) - 'a']
        Map<Character, Integer> map = new HashMap<>();

        // count the number of occurances
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        // find the first unique character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.get(c) == 1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 2. LinkedHashMap
     *
     * Why Suitable for data stream？
     * No need to implement linked list node and a hash map from value to node
     *
     * Why set?
     * For cases like "aaa"
     *
     * map.entrySet().iterator().next().getValue():
     * 1.
     * Let's say after the for loop, map = {e:1 ,d:5, f:8}
     * map.entrySet() will return a Set of Map.Entry<K, V> objects ( Set<Map.Entry<K, V>>),
     * like this {<e,1>, <d,5>, <f,8>} where each element is a Map.Entry object.
     * 2.
     * If you want to loop a set, you use an iterator, that is why the map.entrySet().iterator() is used.
     * 3.
     * LinkedHashMap iterator maintains a Doubly Linked List, next() just returns the head and moves the pointer
     * map.entrySet().iterator().next() will return the next Map.Entry object added to the map by starting at the first one (LinkedHashMap guarantee that).
     *
     *
     *
     * @param s
     * @return
     */
    public int firstUniqChar2(String s) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                map.remove(s.charAt(i));
            } else {
                map.put(s.charAt(i), i);
                set.add(s.charAt(i));
            }
        }

        return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
    }

    /**
     * 3. Two Pointers
     *
     * slow: current unique character
     * fast: for counting character
     *
     * What to do when slow character is repeated:
     * move to the next unique or not visited.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 7 ms, faster than 90.17% of Java online submissions for First Unique Character in a String.
     * Memory Usage: 36.8 MB, less than 100.00% of Java online submissions for First Unique Character in a String.
     * @param s
     * @return
     */
    public int firstUniqChar3(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }

        int slow = 0;
        int fast = 0;

        int[] count = new int[256];
        int n = s.length();

        while (fast < n) {
            count[s.charAt(fast)]++;

            while (slow < n && count[s.charAt(slow)] > 1) {
                slow++;
            }

            if (slow == n) {
                return -1;
            }

            fast++;
        }

        return slow;
    }
}
