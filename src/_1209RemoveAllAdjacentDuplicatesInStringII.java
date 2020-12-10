import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.Deque;

public class _1209RemoveAllAdjacentDuplicatesInStringII {


    /**
     * 2. Two Stacks
     *
     * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392939/Java-Simple-Stack-O(N)-~-10ms-Clean-code-easy-to-understand
     *
     * Variations:
     * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/393149/JavaPython-3-O(n)-codes-using-Stack-w-brief-explanation-and-analysis.
     * 1. One Deque<Pair<Character, Integer>>
     * getValue(), getKey()
     *
     * Time: O(n)
     * Space: O(n)
     */
    public String removeDuplicates2(String s, int k) {
        Deque<Character> stackChar = new ArrayDeque<>();
        Deque<Integer> stackAdjCnt = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (!stackChar.isEmpty() && stackChar.peek() == c) {
                stackAdjCnt.push(stackAdjCnt.peek() + 1);
            } else {
                stackAdjCnt.push(1);
            }
            stackChar.push(c);
            if (stackAdjCnt.peek() == k) {
                for (int i = 0; i < k; i++) { // pop k adjacent equal chars
                    stackChar.pop();
                    stackAdjCnt.pop();
                }
            }
        }

        // Convert stack to string
        StringBuilder sb = new StringBuilder();
        while (!stackChar.isEmpty()) {
            sb.append(stackChar.pop());
        }
        return sb.reverse().toString();
    }

    public String removeDuplicates21(String s, int k) {
        Deque<Pair<Character, Integer>> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty() || stack.peek().getKey() != c) {
                stack.push(new Pair<>(c, 1));
            } else {
                Pair<Character, Integer> entry = stack.pop();

                if (entry.getValue() < k - 1) {
                    stack.push(new Pair(entry.getKey(), entry.getValue() + 1));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Pair<Character, Integer> pair = stack.pop();
            for (int i = 0; i < pair.getValue(); i++) {
                sb.append(pair.getKey());
            }
        }

        return sb.reverse().toString();
    }

    /**
     * 5. Two pointers
     *
     * Each time we need to erase k characters, we just move the slow pointer k positions back.
     *
     * 1. Initialize the slow pointer (j) with zero.
     * 2. Move the fast pointer (i) through the string:
     *  Copy s[i] into s[j].
     *  If s[j] is the same as s[j - 1], increment the count on the top of the stack.
     *      Otherwise, push 1 to the stack.
     *  If the count equals k, decrease j by k and pop from the stack.
     * 3. Return j first characters of the string.
     *
     * Time: O(n)
     * Space: O(n)
     *
     */
    public String removeDuplicates5(String s, int k) {
        Deque<Integer> counts = new ArrayDeque<>();
        char[] sa = s.toCharArray();
        int j = 0; // slow pointer
        for (int i = 0; i < s.length(); ++i, ++j) {
            sa[j] = sa[i];
            if (j == 0 || sa[j] != sa[j - 1]) {
                counts.push(1);
            } else {
                int incremented = counts.pop() + 1;
                if (incremented == k) {
                    j = j - k;
                } else {
                    counts.push(incremented);
                }
            }
        }
        return new String(sa, 0, j);
    }
}
