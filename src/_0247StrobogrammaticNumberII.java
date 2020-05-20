import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _0247StrobogrammaticNumberII {
    /**
     * 1. Recursion
     *
     * Time complexity:
     * Upper limit is Math.pow(5, n) because each position has at most 5 options 0, 1, 8, 6, 9 and we have n/2 positions to fill
     * Space complexity:
     * O(n) for recursion stacks.
     *
     * Runtime: 2 ms, faster than 100.00% of Java online submissions for Strobogrammatic Number II.
     * Memory Usage: 49.7 MB, less than 100.00% of Java online submissions for Strobogrammatic Number II.
     *
     * @param n
     * @return
     */
    public List<String> findStrobogrammatic(int n) {
        findStrobogrammaticHelper(new char[n], 0, n - 1);
        return res;
    }

    List<String> res = new ArrayList<String>();

    public void findStrobogrammaticHelper(char[] a, int l, int r) {
        if (l > r) {
            res.add(new String(a));
            return;
        }

        if (l == r) {
            a[l] = '0'; res.add(new String(a));
            a[l] = '1'; res.add(new String(a));
            a[l] = '8'; res.add(new String(a));
            return;
        }

        if (l != 0) {
            a[l] = '0'; a[r] = '0';
            findStrobogrammaticHelper(a, l+1, r-1);
        }

        a[l] = '1'; a[r] = '1';
        findStrobogrammaticHelper(a, l+1, r-1);
        a[l] = '8'; a[r] = '8';
        findStrobogrammaticHelper(a, l+1, r-1);
        a[l] = '6'; a[r] = '9';
        findStrobogrammaticHelper(a, l+1, r-1);
        a[l] = '9'; a[r] = '6';
        findStrobogrammaticHelper(a, l+1, r-1);
    }

    /**
     * 2. Iteration
     *
     * Time complexity:
     * Space complexity:
     *
     * Runtime: 10 ms, faster than 68.80% of Java online submissions for Strobogrammatic Number II.
     * Memory Usage: 51.3 MB, less than 71.43% of Java online submissions for Strobogrammatic Number II.
     *
     * @param n
     * @return
     */
    public List<String> findStrobogrammatic2(int n) {
        List<String> odd = Arrays.asList("0", "1", "8");
        List<String> even = Arrays.asList("");
        List<String> prev = n % 2 == 1 ? odd : even;

        for(int i = (n % 2) + 2; i <= n; i++){
            List<String> newList = new ArrayList<>();
            for(String str : prev){
                if(i != n - 1)
                    newList.add("0" + str + "0");
                newList.add("1" + str + "1");
                newList.add("6" + str + "9");
                newList.add("8" + str + "8");
                newList.add("9" + str + "6");
            }
            prev = newList;
        }
        return prev;

    }
}
