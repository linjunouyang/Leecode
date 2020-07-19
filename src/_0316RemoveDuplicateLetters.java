import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class _0316RemoveDuplicateLetters {
    /**
     * 1. Greedy with Recursion
     *
     * Lexicographical order:
     * comparison between the first unequal corresponding character
     *
     * The leftmost letter will be
     * 1) [smallest] letter
     * 2) such that [the suffix from that letter contains every other]
     *
     * If there are multiple smallest letters, then we pick the leftmost one simply because it gives us more options.
     * We can always eliminate more letters later on, so the optimal solution will always remain in our search space.
     *
     * "cbacdcbc"
     * a: 1 -> 0, b: 2 -> 1, c: 4 -> 3, d: 1
     *
     * "cdcbc" pos = 0
     * b: 1, c: 2 d: 1 -> 0
     *
     * "db"
     * b: 1, d: 1 -> 0
     *
     * "b"
     * b: 1 -> 0
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     * Each time we slice the string we're creating a new one (strings are immutable).
     * The number of slices is bound by a constant, so we have O(N) * C = O(N)O(N)∗C=O(N).
     *
     */
    public String removeDuplicateLetters(String s) {
        // we create a counter and end the iteration once the suffix doesn't have each unique character
        // pos will be the index of the smallest character we encounter before the iteration ends
        if (s.length() == 0) {
            return "";
        }

        int[] cnt = new int[26];
        int pos = 0; // index of the leftmost letter in our solution
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(pos)) pos = i;
            if (--cnt[s.charAt(i) - 'a'] == 0) {
                // this letter doesn't show up later, we must include it.
                break;
            }
        }

        // our answer is the leftmost letter plus the recursive call on the remainder of the string
        // note that we have to get rid of further occurrences of s[pos] to ensure that there are no duplicates
        return  s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
    }

    /**
     * 2. Greedy with Stack
     *
     * As we iterate over our string,
     * 1) if character i is greater than character i+1
     * 2) another occurrence of character i exists later in the string,
     * deleting character i will always lead to the optimal solution.
     * Characters that come later in the string i don't matter in this calculation because i is in a more significant spot.
     * Even if character i+1 isn't the best yet, we can always replace it for a smaller character down the line if possible.
     *
     * Since we try to remove characters as early as possible,
     * and picking the best letter at each step leads to the best solution,
     * "greedy" should be going off like an alarm.
     *
     *
     * StringBuilder:
     * if you know the approximate size of the string you are dealing with then
     * you can tell StringBuilder how much size to allocate upfront
     * otherwise it will, if it runs out of space, have to double the size by creating a new char[] array then copy data over
     * You can cheat by giving the size and then there is no need for this array creation
     *
     * The StringBuffer class is a subclass of AbstractStringBuilder, which defines a char[] to hold the characters.
     * It uses System.arraycopy to move the existing characters out of the way on a call to insert.
     * so insert() is O(n)
     *
     * StringBuilder vs String concatenation:
     * The key is whether you are writing a single concatenation all in one place or accumulating it over time.
     * For cases like "a" + "b", there's no point in explicitly using StringBuilder.
     * But if you are building a string e.g. inside a loop, use StringBuilder.
     *
     * Time complexity : O(N).
     * Although there is a loop inside a loop, the time complexity is still O(N).
     * This is because the inner while loop is bounded by the total number of elements added to the stack (each time it fires an element goes).
     * This means that the total amount of time spent in the inner loop is bounded by O(N), giving us a total time complexity of O(N).
     *
     * Space complexity : O(1).
     * At first glance it looks like this is O(N), but that is not true!
     * seen will only contain unique elements, so it's bounded by the number of characters in the alphabet (a constant).
     * You can only add to stack if an element has not been seen, so stack also only consists of unique elements.
     * This means that both stack and seen are bounded by constant, giving us O(1) space complexity.
     *
     */
    public String removeDuplicateLetters2(String s) {
        StringBuilder sb = new StringBuilder(); // answer stack

        // this lets us keep track of what's in our solution in O(1) time
        boolean[] seen = new boolean[26];

        // this will let us know if there are any more instances of s[i] left
        int[] count = new int[26];
        for(int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            // we can only try to add c if it's not already in our solution
            // this is to maintain only one of each character
            if (!seen[c - 'a']){
                // if the last letter in our solution:
                //     1. exists
                //     2. is greater than c so removing it will make the string smaller
                //     3. it's not the last occurrence
                // we remove it from the solution to keep the solution optimal
                while(sb.length() > 0 && c < sb.charAt(sb.length() - 1) && count[sb.charAt(sb.length() - 1) - 'a'] > 0){
                    seen[sb.charAt(sb.length() - 1) - 'a'] = false;
                    sb.deleteCharAt(sb.length() - 1);
                }
                seen[c - 'a'] = true;
                sb.append(c);
            }
            count[c - 'a']--;
        }

        return sb.toString();
    }
}
