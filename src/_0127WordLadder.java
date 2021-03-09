import java.util.*;

public class _0127WordLadder {
    /**
     * 1. BFS
     *
     * Time: O(number of words * len^2)
     * Space: O(number of words * len)
     *
     * Takeaway:
     * 1. new HashSet<>(list)
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<String>(wordList);
        Deque<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) {
                    return level + 1;
                }

                /**
                 * A different approach: 587ms
                 * word length is usually less than size of dict
                 *
                 * List<String> toRemove = new ArrayList<>();
                 *
                 * //dict size * len
                 * for (String otherWord : dict) {
                 *  if (oneDiff(word, otherWord)) {
                 *      queue.offer(otherWord);
                 *      toRemove.add(otherWord);
                 *  }
                 *}
                 *
                 * // O(num of nodes in the next level * len)
                 * for (String what : toRemove) {
                 *  dict.remove(what);
                 * }
                 */

                // O(len * 26 * len)  58ms
                for (int j = 0; j < word.length(); j++) { // O(len)
                    char[] chars = word.toCharArray();      // O(len), StringBuilder is slightly slower
                    for (char ch = 'a'; ch <= 'z'; ch++) {  // O(26)
                        chars[j] = ch;
                        String next = new String(chars);       // O(len)
                        // O(len) from HashSet
                        if (dict.contains(next)) {
                            // check next.equals(endWord) will speed up a little bit
                            queue.offer(next);
                            dict.remove(next);
                        }
                    }
                }
            }
            level++;
        }
        return 0;
    }

    /**
     * 2. Bi-directional BFS
     *
     * "The idea behind bidirectional search is to run two simultaneous searches—one forward from
     * the initial state and the other backward from the goal—hoping that the two searches meet in
     * the middle. The motivation is that b^(d/2) + b^(d/2) is much less than b^d. b is branch factor, d is depth. "
     *
     * ----- section 3.4.6 in Artificial Intelligence - A modern approach by Stuart Russel and Peter Norvig
     *
     * Time: O(number of words * len^2)
     * Space: O(number of words * len)
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord) || !dict.contains(beginWord)) {
            // notice in the BFS, we check whether dict contains before adding it to set
            // If beginWord and endWord is not in dict, we can't do transformation
            // But leetcode seems to be okay with the absence of beginWord.
            return 0;
        }

        HashSet<String> begin = new HashSet<>();
        begin.add(beginWord);
        HashSet<String> end = new HashSet<>();
        end.add(endWord);

        int len = 1;

        while (!begin.isEmpty() && !end.isEmpty()) {
            if (begin.size() > end.size()) {
                HashSet<String> temp = begin;
                begin = end;
                end = temp;
            }

            HashSet<String> beginNext = new HashSet<>();
            for (String word : begin) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char old = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String nextWord = String.valueOf(chars);
                        if (end.contains(nextWord)) {
                            return len + 1;
                        }
                        if (dict.contains(nextWord)) {
                            beginNext.add(nextWord);
                            // why is it okay to remove? Will other end still be able to find it?
                            // The other end will perform char transform, and first call end.contains (instead of dict.contains)
                            dict.remove(nextWord);
                        }
                    }
                    chars[i] = old;
                }
            }

            begin = beginNext;
            len++;
        }

        return 0;
    }
}
