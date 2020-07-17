import java.util.Arrays;

public class _0455AssignCookies {
    /**
     * 1. Greedy Algorithm
     *
     * choose the smallest size of cookie among all the cookies that can satisfy this children.
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(1)?
     * O(max(logm + logn))
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        if (g == null || s == null) {
            return 0;
        }

        Arrays.sort(g);
        Arrays.sort(s);
        int ans = 0;

        for (int i = 0; i < s.length && ans < g.length; i++) {
            if (s[i] >= g[ans]) {
                ans++;
            }
        }

        return ans;
    }
}
