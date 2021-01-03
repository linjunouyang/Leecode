import java.util.*;

public class _1520MaximumNumberOfNonOverlappingSubstrings {
    /**
     * 1. ISMP: Interval Sscheduling Maximization
     *
     * Original Problem Proof:
     * https://cse.buffalo.edu/~hartloff/CSE331-Summer2015/greedy.pdf
     *
     * https://leetcode.com/problems/maximum-number-of-non-overlapping-substrings/discuss/744420/C%2B%2BJavaPython-Interval-Scheduling-Maximization-(ISMP)
     *
     * First find all the possible substrings. There will be at most one for each letter in s.
     * If we start at the first occurence of each letter and keep expanding the range to cover all occurences, we'll find all the substrings in O(26 * n) time.
     *
     * Once we've found all the possible substrings, this is a standard problem:
     * interval scheduling maximization problem (ISMP) (https://en.wikipedia.org/wiki/Interval_scheduling)
     *
     * We can solve this in O(n) time by greedily taking the next non-overlapping substring with the left-most endpoint.
     *
     * Time: O(n)
     * Space: O(1)
     */
    private static class Interval {
        public int begin;
        public int end;

        public Interval(int b, int e) {
            this.begin = b;
            this.end = e;
        }
    }

    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        int[] left = new int[26];
        Arrays.fill(left, n);
        int[] right = new int[26];
        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'a';
            if (left[idx] == n) {
                left[idx] = i;
            }
            right[idx] = i;
        }

        List<Interval> t = new ArrayList<Interval>();
        for (int i = 0; i < 26; i++) {
            if (left[i] < n) {
                int begin = left[i];
                int end = right[i];
                for (int j = begin; j <= end; j++) {
                    // extend range to include characters between [begin, end]
                    begin = Math.min(begin, left[s.charAt(j) - 'a']);
                    end = Math.max(end, right[s.charAt(j) - 'a']);
                }
                if (begin == left[i]) {
                    // Every possible substring will begin with the first occurrence of some letter (and end with the last occurrence of possibly some other letter).
                    // I'm just making sure I only count each possible substring once by only "expanding" the range from left-to-right.
                    //
                    //You could do the same thing from right-to-left and check end == lst[c].
                    //
                    // Consider the string abababc. The valid substrings are ababab and c.
                    // Notice that babab is not a valid substring because it doesn't include all occurrences of a.
                    // babab is invalid because after we start from the first b and expand the range to include the all occurrences of a, start != fst[i].
                    // We will count the ababab string when we start from the first a.
                    // Any range that gets expanded to the left (start < fst[i]) will start with a different letter than i + 'a', so we don't want to double count it.
                    // Anyhow, expanding to both the left and right is more complicated because we have to cover the ranges of all letters in between.
                    // ? Think about bababac
                    // so basically, for letter a and b, the substring is [0,5]
                    // we have two choices:
                    // 1) count the interval when considering a by expanding left
                    // 2) count the interval when considering b by expanding right
                    // choose 1) OR 2). We can't count same interval twice
                    t.add(new Interval(begin, end));
                }
            }
        }

        // Now the question is like
        // given one meeting room and several meetings' interval
        // how to arrange most meetings
        Collections.sort(t, Comparator.comparing(i -> i.end));
        List<String> ans = new ArrayList<String>();
        int prev = -1;
        for (Interval i : t) {
            if (i.begin > prev) {
                ans.add(s.substring(i.begin, i.end + 1));
                prev = i.end;
            }
        }

        return ans;
    }

    /**
     * 1.1 Still Greedy, different implementation
     *
     */
    int expandToRight(String s, int start, int[] left, int[] right) {
        int end = right[s.charAt(start) - 'a'];
        for (int i = start; i <= end; ++i) {
            if (left[s.charAt(i) - 'a'] < start){
                return -1;
            }

            end = Math.max(end, right[s.charAt(i) - 'a']);
        }
        return end;
    }
    public List<String> maxNumOfSubstrings2(String s) {
        int left[] = new int[26];
        int right[] = new int[26];
        Arrays.fill(left, s.length());

        List<String> res = new ArrayList<String>();
        for (int i = 0; i < s.length(); ++i) {
            int idx= s.charAt(i) - 'a';
            left[idx] = Math.min(left[idx], i);
            right[idx] = i;
        }

        int end = -1;
        for (int i = 0; i < s.length(); ++i)
            if (i == left[s.charAt(i) - 'a']) {
                int newEnd = expandToRight(s, i, left, right);
                if (newEnd != -1) {
                    if (i > end) {
                        // I am using the back of the result to track the last valid substring.
                        // (We can use a string variable instead, and push that variable to the result.
                        // However, that will require another check in the end to make sure the latest substring is included.)
                        // We add a new element to the result when a new valid substring that does not overlap with the last valid substring (i > right).
                        // Otherwise, we just keep on updating the last valid substring.
                        // We also add a new element for the very first substring (res.empty()).
                        res.add("");
                    }
                    end = newEnd;
                    res.set(res.size() - 1, s.substring(i, end + 1));
                }
            }
        return res;
    }
}
