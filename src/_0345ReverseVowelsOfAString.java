import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _0345ReverseVowelsOfAString {
    /**
     * 1. Two Pointers
     *
     * ------
     * HashSet or boolean array of size 256 (ASCII Table)
     *
     * Conceptually, your approach does not differ from mine. However, it would be slower for the following 2 reasons:
     *
     * 1. Hash tables require a mathematical function to get the index.
     * This is not that costly in this case, but it is still slower than direct access in an array.
     *
     * 2 The add and contains use hash function;
     * contains also leads to the hash table iteration over conflicting entries
     * ------
     * Arrays.asList(T... a)
     * Returns a fixed-size list backed by the specified array.
     * (Changes to the returned list "write through" to the array.)
     * This method acts as bridge between array-based and collection-based APIs, in combination with Collection.toArray().
     * The returned list is serializable and implements RandomAccess.
     *
     * This method also provides a convenient way to create a fixed-size list initialized to contain several elements:
     * List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
     * ------
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 5 ms, faster than 55.92% of Java online submissions for Reverse Vowels of a String.
     * Memory Usage: 38.2 MB, less than 100.00% of Java online submissions for Reverse Vowels of a String.
     *
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        int left = 0;
        int right = s.length() - 1;

        boolean isLeftVowel;
        boolean isRightVowel;

        char[] chars = s.toCharArray();

        while (left < right) {
            isLeftVowel = set.contains(s.charAt(left));
            isRightVowel = set.contains(s.charAt(right));

            if (isLeftVowel && isRightVowel) {
                char c = s.charAt(left);
                chars[left++] = chars[right];
                chars[right--] = c;
            } else {
                if (!isLeftVowel) {
                    left++;
                }

                if (!isRightVowel) {
                    right--;
                }
            }
        }

        return new String(chars);
    }
}
