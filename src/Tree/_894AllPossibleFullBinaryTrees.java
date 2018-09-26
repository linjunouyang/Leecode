package Tree;
import java.util.*;

/**
 * 9.25 First, not solved.
 * To-do:
 * 1. map, HashMap and relevant methods;
 * 2. list, LinkedList and relevant methods;
 * 3. Complexity Analysis seems too complicated.
 */
public class _894AllPossibleFullBinaryTrees {
    /*
    1. Recursion

    Link: https://leetcode.com/problems/all-possible-full-binary-trees/solution/

    Intuition:
    FBT(1) = 1, FBT(2) = NAN, FBT(3) = 1; -> no FBT with even number.
    For x >= 3, FBT(x) has 2 children at its root. Each one is itself FBT. -> RECURSION
    cache previous results so we don't recalculate them in the recursion

    *** STILL DON'T UNDERSTAND ***
    Time complexity: O(2^N)
    Space complexity: O(2^N)
    */
    Map<Integer, List<TreeNode>> memo = new HashMap<>();

    public List<TreeNode> allPossibleFBT(int N) {
        if (!memo.containsKey((N))) {
            List<TreeNode> ans = new LinkedList();
            if (N == 1) {
                ans.add(new TreeNode(0));
            } else if (N % 2 == 1){
                for (int x = 1; x < N; x += 2) {  // 0
                    int y = N - 1 - x;   // 4
                    for (TreeNode left: allPossibleFBT(x))
                        for (TreeNode right: allPossibleFBT(y)) {
                            TreeNode bns = new TreeNode(0);
                            bns.left = left;
                            bns.right = right;
                            ans.add(bns);
                        }
                }
            }
            memo.put(N, ans);
        }

        return memo.get(N);
    }

    /*
    2. Recursion without cache

    Link: https://leetcode.com/problems/all-possible-full-binary-trees/discuss/163433/Java-Recursive-Solution-with-Explanation

    N is even -> no results.
    N is 1 -> one node.
    N is odd (not 1) -> recursively find the possible children of each node at each level

    Iterate through all possible combinations of children
    setting current node's children
    add to the result list.

    Note:
    List is abstract, can't be instantiated.
     */
    public List<TreeNode> allPossibleFBT2(int N) {
        List<TreeNode> res = new ArrayList<>();
        if (N == 1) {
            res.add(new TreeNode(0));
            return res;
        }
        for (int i = 1; i < N - 1; i += 2) {
            List<TreeNode> left = allPossibleFBT2(i);
            List<TreeNode> right = allPossibleFBT2(N - 1 - i);
            for (TreeNode nl : left) {
                for (TreeNode nr : right) {
                    TreeNode cur = new TreeNode(0);
                    cur.left = nl;
                    cur.right = nr;
                    res.add(cur);
                }
            }
        }
        return res;
    }

    /*
    3. Recursion

    cache -> don't repeat N values.

    One possible problem:
    left subtree and right subtree might be the same tree.
          0
        0   0
       0 0 0 0
     */
    Map<Integer,List<TreeNode>> cache= new HashMap<>();
    public List<TreeNode> allPossibleFBT3(int N) {
        List<TreeNode> res = new ArrayList<>();
        if( N % 2 == 0){
            return res;
        }
        if(cache.containsKey(N)){
            return cache.get(N);
        }
        if( N == 1){
            res.add(new TreeNode(0));
            return res;
        }

        for(int leftNum = 1; leftNum < N - 1; leftNum += 2){
            List<TreeNode> left = allPossibleFBT3(leftNum);
            List<TreeNode> right = allPossibleFBT3(N - 1 - leftNum);
            for(TreeNode nl: left){
                for(TreeNode nr:right){
                    TreeNode cur = new TreeNode(0);
                    cur.left=nl;
                    cur.right=nr;
                    res.add(cur);
                }
            }
        }
        cache.put(N,res);
        return res;
    }
}
