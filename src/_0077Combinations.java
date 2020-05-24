import java.util.*;

/**
 * https://leetcode.wang/leetCode-77-Combinations.html
 */

public class _0077Combinations {
    /**
     *
     * 1. Backtracking
     *
     * Time complexity: O(k * C(n, k))
     *
     * 1. Adding a number is O(1). We have C(n, k) lists, each list has k elements.
     * Selecting cost O(k * C(n, k))
     *
     * 2. We need to create a copy of the current set
     * because we reuse the original one to build all the valid subsets.
     * This copy costs O(k), which is called C(n, k) times.
     * O(k * C(n, k))
     *
     * Space complexity: O(k * C(n, k))
     * the number of subsets O(C(n, k))
     * the average length of subset O(k)
     *
     * - For recursion: max depth the call stack is k
     * - For output: we're creating C(n, k) subsets where the set size is k
     *
     * we don't always include output in space complexity,
     * if not included, O(k) because of the call stack
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Combinations.
     * Memory Usage: 41 MB, less than 32.61% of Java online submissions for Combinations.
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        combineHelper(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private void combineHelper(int n, int k, int start, List<Integer> temp, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i <= n - k + 1; i++) {
            temp.add(i);
            combineHelper(n, k - 1, i + 1, temp, res);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * 2. Iteration
     *
     * https://leetcode.com/problems/combinations/discuss/26992/Short-Iterative-C%2B%2B-Answer-8ms
     *
     * 参考这里，完全按照解法一回溯的思想改成迭代。我们思考一下，回溯其实有三个过程。
     *
     * for 循环结束，也就是 i == n + 1，然后回到上一层的 for 循环
     * temp.size() == k，也就是所需要的数字够了，然后把它加入到结果中。
     * 每个 for 循环里边，进入递归，添加下一个数字
     *
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for(int i = 0;i<k;i++){
            temp.add(0);
        }
        int i = 0;
        while (i >= 0) {
            temp.set(i, temp.get(i)+ 1); //当前数字加 1
            //当前数字大于 n，对应回溯法的 i == n + 1，然后回到上一层
            if (temp.get(i) > n) {
                i--;
                // 当前数字个数够了
            } else if (i == k - 1) {
                ans.add(new ArrayList<>(temp));
                //进入更新下一个数字
            }else {
                i++;
                //把下一个数字置为上一个数字，类似于回溯法中的 start
                temp.set(i, temp.get(i-1));
            }
        }
        return ans;
    }

    /**
     * 3. Iteration B
     *
     * 解法二的迭代法是基于回溯的思想，还有一种思想，参考这里。类似于46题的解法一，找 k 个数，我们可以先找出 1 个的所有结果，然后在 1 个的所有结果再添加 1 个数，变成 2 个，然后依次迭代，直到有 k 个数。
     *
     * 比如 n = 5， k = 3
     *
     * [image]
     *
     *
     * 第 1 次循环，我们找出所有 1 个数的可能 [ 1 ]，[ 2 ]，[ 3 ]。4 和 5 不可能，解法一分析过了，因为总共需要 3 个数，4，5 全加上才 2 个数。
     *
     * 第 2 次循环，在每个 list 添加 1 个数， [ 1 ] 扩展为 [ 1 , 2 ]，[ 1 , 3 ]，[ 1 , 4 ]。[ 1 , 5 ] 不可能，因为 5 后边没有数字了。 [ 2 ] 扩展为 [ 2 , 3 ]，[ 2 , 4 ]。[ 3 ] 扩展为 [ 3 , 4 ]；
     *
     * 第 3 次循环，在每个 list 添加 1 个数， [ 1，2 ] 扩展为[ 1，2，3]， [ 1，2，4]， [ 1，2，5]；[ 1，3 ] 扩展为 [ 1，3，4]， [ 1，3，5]；[ 1，4 ] 扩展为 [ 1，4，5]；[ 2，3 ] 扩展为 [ 2，3，4]， [ 2，3，5]；[ 2，4 ] 扩展为 [ 2，4，5]；[ 3，4 ] 扩展为 [ 3，4，5]；
     *
     * 最后结果就是，[[ 1，2，3]， [ 1，2，4]， [ 1，2，5]，[ 1，3，4]， [ 1，3，5]， [ 1，4，5]， [ 2，3，4]， [ 2，3，5]，[ 2，4，5]， [ 3，4，5]]。
     *
     * 上边分析很明显了，三个循环，第一层循环是 1 到 k ，代表当前有多少个数。第二层循环就是遍历之前的所有结果。第三次循环就是将当前结果扩展为多个。
     *
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine3(int n, int k) {
        if (n == 0 || k == 0 || k > n) return Collections.emptyList();
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        //个数为 1 的所有可能
        for (int i = 1; i <= n + 1 - k; i++) res.add(Arrays.asList(i));
        //第一层循环，从 2 到 k
        for (int i = 2; i <= k; i++) {
            List<List<Integer>> tmp = new ArrayList<List<Integer>>();
            //第二层循环，遍历之前所有的结果
            for (List<Integer> list : res) {
                //第三次循环，对每个结果进行扩展
                //从最后一个元素加 1 开始，然后不是到 n ，而是和解法一的优化一样
                //(k - (i - 1） 代表当前已经有的个数，最后再加 1 是因为取了 n
                for (int m = list.get(list.size() - 1) + 1; m <= n - (k - (i - 1)) + 1; m++) {
                    List<Integer> newList = new ArrayList<Integer>(list);
                    newList.add(m);
                    tmp.add(newList);
                }
            }
            res = tmp;
        }
        return res;
    }

    /**
     * 4. Recursion
     *
     * https://leetcode.com/problems/combinations/discuss/27019/A-short-recursive-Java-solution-based-on-C(nk
     *
     * 参考这里C(n-1k-1)%2BC(n-1k)>)。基于这个公式 C ( n, k ) = C ( n - 1, k - 1) + C ( n - 1, k ) 所用的思想，这个思想之前刷题也用过，但忘记是哪道了。
     *
     * 从 n 个数字选 k 个，我们把所有结果分为两种，包含第 n 个数和不包含第 n 个数。这样的话，就可以把问题转换成
     *
     * 从 n - 1 里边选 k - 1 个，然后每个结果加上 n
     * 从 n - 1 个里边直接选 k 个。
     * 把上边两个的结果合起来就可以了。
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine4(int n, int k) {
        if (k == n || k == 0) {
            List<Integer> row = new LinkedList<>();
            for (int i = 1; i <= k; ++i) {
                row.add(i);
            }
            return new LinkedList<>(Arrays.asList(row));
        }
        // n - 1 里边选 k - 1 个
        List<List<Integer>> result = combine(n - 1, k - 1);
        //每个结果加上 n
        result.forEach(e -> e.add(n));
        //把 n - 1 个选 k 个的结果也加入
        result.addAll(combine(n - 1, k));
        return result;
    }

    /**
     * 5. Dynamic Programming
     *
     * https://leetcode.com/problems/combinations/discuss/27090/DP-for-the-problem
     *
     * 参考这里，既然有了解法四的递归，那么一定可以有动态规划。递归就是压栈压栈压栈，然后到了递归出口，开始出栈出栈出栈。而动态规划一个好处就是省略了出栈的过程，我们直接从递归出口网上走。
     *
     * 接下来就是动态规划的常规操作了，空间复杂度的优化，我们注意到更新 dp [ i ] [ * ] 的时候，只用到dp [ i - 1 ] [ * ] 的情况，所以我们可以只用一个一维数组就够了。和72题解法二，以及5题，10题，53题等等优化思路一样，这里不详细说了。
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine5(int n, int k) {
        List<List<Integer>>[] dp = new ArrayList[k + 1];
        // i 从 1 到 n
        dp[0] = new ArrayList<>();
        dp[0].add(new ArrayList<Integer>());
        for (int i = 1; i <= n; i++) {
            // j 从 1 到 i 或者 k
            List<List<Integer>> temp = new ArrayList<>(dp[0]);
            for (int j = 1; j <= i && j <= k; j++) {
                List<List<Integer>> last = temp;
                if(dp[j]!=null){
                    temp = new ArrayList<>(dp[j]);
                }
                // 判断是否可以从 i - 1 里边选 j 个
                if (i <= j) {
                    dp[j] = new ArrayList<>();
                }
                // 把 i - 1 里边选 j - 1 个的每个结果加上 i
                for (List<Integer> list : last) {
                    List<Integer> tmpList = new ArrayList<>(list);
                    tmpList.add(i);
                    dp[j].add(tmpList);
                }
            }
        }
        return dp[k];
    }






}
