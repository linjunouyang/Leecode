import java.util.ArrayDeque;
import java.util.Deque;

public class _0394DecodeString {
    /**
     * 1. Recursion
     *
     * https://www.reddit.com/r/learnjava/comments/hipvna/what_happens_when_i_use_tostring_method_on_a/
     * toString(): O(n)
     *
     * Time:
     * Space:
     *
     * Time complexity: O(final length)
     * Space complexity: O(string builder) + O(recursion call stack)
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        return helper(s, new int[]{0}).toString();
    }

    public StringBuilder helper(String s, int[] index) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (index[0] < s.length()) {
            char c = s.charAt(index[0]);
            index[0]++;
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '[') {
                StringBuilder sub = helper(s, index);
                for (int i = 0; i < num; i++) {
                    sb.append(sub);
                }
                num = 0;
            } else if (c == ']') {
                break;
            } else {
                sb.append(c);
            }
        }
        return sb;
    }

    /**
     * 2. Iteration, Stack
     *
     * Time: O(final length)
     * Space: O(final length)
     *
     * @param s
     * @return
     */
    public String decodeString2(String s) {
        Deque<StringBuilder> builderStack = new ArrayDeque<>();
        Deque<Integer> kStack = new ArrayDeque<>();
        StringBuilder curBuilder = new StringBuilder();
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                k = k * 10 + c - '0';
            } else if (c == '[') {
                builderStack.push(curBuilder);
                kStack.push(k);
                curBuilder = new StringBuilder();
                k = 0; // a new level, a new k
            } else if (c == ']') {
                StringBuilder temp = curBuilder;
                curBuilder = builderStack.pop();
                k = kStack.pop();
                for (int j = 1; j <= k; j++)  {
                    curBuilder.append(temp);
                }
                k = 0; // previous repeat finish, reset for next repeat sequence
            } else {
                curBuilder.append(c);
            }
        }
        return curBuilder.toString();
    }


}
