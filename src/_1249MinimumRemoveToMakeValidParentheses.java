/**
 * 1. same total number of ( and )
 * 2. along the way, ) should be no more than (
 *
 * It's easy to decide what ')' to delete (rule 2)
 *
 * What '(' should delete?
 * We shouldn't delete '(' that will cause any number of ')' comes before unmatched '('
 *
 * We can make a rule:
 * A ")" always pairs with the closest "(" that doesn't already have a pair.
 *
 * How to guarantee the above rule & find problematic '(': stack
 *
 * Each time we see a "(", we should add its index to the stack. Each time we see a ")", we should remove an index from the stack because the ")" will match with whatever "(" was at the top of the stack. The length of the stack is equivalent to the balance from above. We will need to do the:
 *
 * Remove a ")" if it is encountered when stack was already empty (prevent negative balance).
 * Remove a "(" if it is left on stack at end (prevent non-zero final balance)
 *
 */
public class _1249MinimumRemoveToMakeValidParentheses {
    /**
     * 1.
     *
     * Time: O(n)
     * Space: O(n)
     *
     * @param s
     * @return
     */
    public String minRemoveToMakeValid(String s) {
        int balance = 0;
        int close = 0; // available close
        for (int i = 0; i < s.length(); i++)  {
            if (s.charAt(i) == ')') {
                close++;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (balance == close) {
                    continue;
                }
                balance++;
            } else if (c == ')') {
                close--;
                if (balance == 0) {
                    continue;
                }
                balance--;
            }

            sb.append(c);
        }
        return sb.toString();
    }
}
