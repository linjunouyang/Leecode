import java.util.ArrayList;
import java.util.List;

public class _0254FactorCombinations {
    /**
     * 1. Backtracking
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(logn)
     *
     * @param n
     * @return
     */
    public List<List<Integer>> getFactors(int n) {
        // write your code here
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), n, 2);
        return result;
    }

    public void helper(List<List<Integer>> result, List<Integer> item, int n, int start){
        if (n == 1) {
            if (item.size() > 1) {
                result.add(new ArrayList<Integer>(item));
            }
            return;
        }

        for (int i = start; i <= n; ++i) {
            if (n % i == 0) {
                item.add(i);
                helper(result, item, n/i, i);
                item.remove(item.size()-1);
            }
        }
    }

    /**
     * 2. DFS
     *
     * Time complexity: O(sqrt(n) * log sqrt(n))
     * Space complexity: O(logn)
     *
     * @param n
     * @return
     */
    public List<List<Integer>> getFactors2(int n) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(2, n, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int start, int n, List<Integer> list, List<List<Integer>> res) {
        for(int i = start; i * i <= n; i++) {
            if(n % i == 0) {
                list.add(i);
                list.add(n / i);
                res.add(new ArrayList<>(list));
                list.remove(list.size() - 1);
                backtrack(i, n / i, list, res);
                list.remove(list.size() - 1);
            }
        }
    }


}
