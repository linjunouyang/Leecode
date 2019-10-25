package Lintcode;


import java.util.ArrayDeque;
import java.util.Deque;

public class _0575DecodeString {
    /**
     * 1. Stack
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param s
     * @return
     */
    public String expressionExpand(String s) {
        // stack will contain String and Integer
        Deque<Object> stack = new ArrayDeque<>();
        // buffer for number, because numbers might takes multiple characters to represent
        int number = 0;

        for (char c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                number = number * 10 + c - '0';
            } else if (c == '[') {
                // 3[2[a]] vs 32[a]

                // Integer.valueOf
                stack.push(Integer.valueOf(number));
                // clear buffer
                number = 0;
            } else if (c == ']') {
                String newStr = popStack(stack);
                Integer count = (Integer) stack.pop();

                // be aware of i = 0;
                for (int i = 0; i < count; i++) {
                    stack.push(newStr);
                }
            } else {
                // String instead of Character
                stack.push(String.valueOf(c));
            }
        }

        return popStack(stack);
    }

    /**
     * Pop stack until get a number of empty
     *
     * We must use stack instead of directly reversing strings
     *
     * 2[c2[ab]] = cabab cabab (correct)
     * 2[c2[ab]] = cbaba cbaba (directly reversing, wrong)
     * No: "a" "bc" -> "bc" "a"
     * Yes: "a" "bc" -> "cb" "a"
     * @param stack
     * @return
     */
    private String popStack(Deque<Object> stack) {
        // reverse string back to original order
        Deque<String> buffer = new ArrayDeque<>();

        while (!stack.isEmpty() && (stack.peek() instanceof String)) {
            buffer.push((String) stack.pop());
        }

        StringBuilder sb = new StringBuilder();

        while (!buffer.isEmpty()) {
            sb.append(buffer.pop());
        }

        return sb.toString();
    }

    public String expressionExpand2(String s) {
        // write your code here
        if (s.length() == 0) {
            return "";
        }

        Deque<Integer> repeat = new ArrayDeque<>();
        // outer layer
        Deque<String> tempStr = new ArrayDeque<>();
        // current layer
        String res = "";

        int i = 0;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i))) {
                int count = 0;
                while (Character.isDigit(s.charAt(i))) {
                    count = 10 * count + s.charAt(i) - '0';
                    i++;
                }
                repeat.push(count);
            } else if (s.charAt(i) == '[') {
                tempStr.push(res);
                res = "";
                i++;
            } else if (s.charAt(i) == ']') {
                StringBuilder sb = new StringBuilder(tempStr.pop());
                int repeatTime = repeat.pop();

                for (int index = 0; index < repeatTime; index++) {
                    sb.append(res);
                }
                res = sb.toString();
                i++;
            } else {
                res += s.charAt(i);
                i++;
            }
        }
        return res;
    }


    int index = 0;

    /**
     * 3. Recursion, dfs
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     * number of recursive calls depends on the number of [
     *
     * @param s
     * @return
     */
    public String expressionExpand3(String s) {
        StringBuilder sb = new StringBuilder();

        int repeat = 0;

        while (index < s.length()) {
            char c = s.charAt(index);

            if (c == '[') {
                index++;

                String sub = expressionExpand3(s);
                for (int i = 0; i < repeat; i++) {
                    sb.append(sub);
                }

                // come back from one layer, 2[ab](we're here)2[cd]
                repeat = 0;
                index++;
            } else if (c == ']') {
                return sb.toString();
            } else if (Character.isDigit(c)) {
                repeat = repeat * 10 + c - '0';
                index++;
            } else {
                sb.append(c);
                index++;
            }
        }

        return sb.toString();
    }
}
