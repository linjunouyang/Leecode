public class _0277FindTheCelebrity {

    /**
     * 1. Two Passes
     *
     * knows(a, b):
     * 1) if a knows b, then a can't be a cele
     * 2) if a doesn't know b, then b can't be a cele
     * -> either way, we can eliminate one person
     *
     * First Loop: Find the candidate k.
     * Suppose the candidate after the first for loop is person k,
     * 1）0 to k-1 cannot be the celebrity, otherwise it will not pass the candidate position to k
     * 2) k knows no one in (k+1, n-1), k+1 to n-1 can not be the celebrity either.
     * Therefore, k is the only possible celebrity, if there exists one.
     *
     * Second loop: Check if k indeed does not know any other persons and all other persons know k.
     * in 1st pass, we've already known candidate do not know the people after him.
     * in 2nd pass,
     * 1) before candidate, we check them whether know each other,
     * 2) after candidate, only check whether people know candidate will be ok, which will save n/2 ask for average.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 6 ms, faster than 97.57% of Java online submissions for Find the Celebrity.
     * Memory Usage: 44.7 MB, less than 58.33% of Java online submissions for Find the Celebrity.
     *
     * @param n
     * @return
     */
    public int findCelebrity(int n) {
        if (n == 0) {
            return -1;
        }

        int candidate = 0;

        for(int i = 1; i < n; i++){
            if(knows(candidate, i))
                candidate = i;
        }

        for(int i = 0; i < n; i++){
            if(i < candidate && knows(candidate, i) || !knows(i, candidate)) {
                // candidate doesn't know (0, k -1)  OR (0, n-1) doesn't know candidate
                return -1;
            }

            if(i > candidate && !knows(i, candidate)) {
                // (k + 1, n - 1) doesn't know candidate
                return -1;
            }
        }

        return candidate;
    }

    private boolean knows(int a, int b) {
        return true;
        // return false;
    }

    // 2. Using Stack (not intuitive) https://leetcode.com/problems/find-the-celebrity/discuss/71240/AC-Java-solution-using-stack
}
