import java.util.*;
import java.util.Map;

public class _0828CountUniqueCharactersOfAllSubstringsOfAGivenString {
    /**
     * 1. Backtracking (TLE)
     *
     * Time: O(n * 2^n)
     *
     * Space:
     * O(1) charToCount
     * O(n) recursion call stack
     */
    public int uniqueLetterString(String s) {
        HashMap<Character, Integer> charToCount = new HashMap<>();
        int[] sum = new int[1];

        countUniqueChars(s, 0, charToCount, sum);

        return (int) (sum[0] % (Math.pow(10, 9) + 7));
    }

    private void countUniqueChars(String s, int idx,
                                  HashMap<Character, Integer> charToCount,
                                  int[] sum) {
        if (idx == s.length())  {
            int len = 0;
            for (Map.Entry<Character, Integer> entry : charToCount.entrySet()) {
                if (entry.getValue() == 1) {
                    len++;
                }
            }
            sum[0] += len;
            return;
        }

        char c = s.charAt(idx);

        // not choose this char
        if (charToCount.size() == 0) {
            // haven't start the substring
            countUniqueChars(s, idx + 1, charToCount, sum);
        } else {
            // substring ends
            int len = 0;
            for (Map.Entry<Character, Integer> entry : charToCount.entrySet()) {
                if (entry.getValue() == 1) {
                    len++;
                }
            }
            sum[0] += len;
        }

        // choose this char
        int oldCount = charToCount.getOrDefault(c, 0);
        charToCount.put(c, oldCount + 1);

        countUniqueChars(s, idx + 1, charToCount, sum);

        if (oldCount == 0) {
            charToCount.remove(c);
        } else {
            charToCount.put(c, oldCount);
        }
    }

    /**
     * 2.
     *
     * Instead of counting all unique characters and struggling with all possible substrings,
     * we can count for every char in S, how many ways to be found as a unique char.
     * We count and sum, and it will be out answer.
     *
     * https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/C%2B%2BJavaPython-One-pass-O(N)
     *
     * Time: O(n)
     * Space: O(n) (can be optimized to O(1)
     */
    public int uniqueLetterString2(String s) {
        List<Integer>[] record = new List[26];
        int len = s.length();
        for (int i = 0; i < 26; i++) {
            record[i] = new ArrayList<>();
        }
        for (int i = 0; i < len; i++) {
            record[s.charAt(i) - 'A'].add(i);
        }

        int M = (int) (1e9 + 7);
        long result = 0;
        for (int i = 0; i < 26; i++) {
            //check every letter. 检查每个字母
            int size = record[i].size();
            for (int j = 0; j < size; j++) {
                //for each position, check left pos and right pos. 对于一个字母的每个位置，查看该位置左边的位置left和右边的位置right
                int position = record[i].get(j);
                int leftPos = j == 0 ? -1 : record[i].get(j - 1);
                int rightPos = j == size - 1 ? len : record[i].get(j + 1);
                result += (position - leftPos) * (rightPos - position);
                result %= M;
            }
        }

        return (int)result;
    }

    public int uniqueLetterString3(String s) {
        int[][] charPos = new int[26][2];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(charPos[i], -1);
        }

        int count = 0;
        int mod = (int) (1e9 + 7);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int[] pos = charPos[c - 'A'];
            count += ((pos[1] - pos[0]) * (i - pos[1])) % mod;
            //count %= mod;
            charPos[c - 'A'] = new int[]{pos[1], i};
        }

        for (int i = 0; i < 26; i++) {
            int[] pos = charPos[i];
            count += ((pos[1] - pos[0]) * (s.length() - pos[1])) % mod;
            //count %= mod;
        }

        return count;
    }
}
