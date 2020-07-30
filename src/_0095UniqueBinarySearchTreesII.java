import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


public class _0095UniqueBinarySearchTreesII {
    /**
     * 1. Recursion (pre-order)
     *
     * I was stuck at recursion definition (what to return, how to keep track of root)
     *
     * Time complexity: Catalan number (Gn) * n times = nGn
     * Gn grow as (4^n)/(n ^ 1.5)
     * Total: O(4^n) / (n ^ 0.5)
     *
     * Space complexity: Gn trees, n elements each -> O(4^n / n^0.5)
     *
     * Binomial coefficients: the coefficient of X^k term in the polynomial expansion of the binomial power (1+x)^n
     * https://en.wikipedia.org/wiki/Binomial_coefficient
     * (n) = (n!) / (k!(n-k)!)
     * (k)
     *
     */
    public ArrayList<TreeNode> generateTrees(int n) {
        return generate(1, n);
    }

    private ArrayList<TreeNode> generate(int start, int end){
        ArrayList<TreeNode> rst = new ArrayList<TreeNode>();

        if(start > end){
            rst.add(null);
            return rst;
        }

        for(int i=start; i<=end; i++){
            ArrayList<TreeNode> left = generate(start, i-1);
            ArrayList<TreeNode> right = generate(i+1, end);
            for(TreeNode l: left){
                for(TreeNode r: right){
                    // should new a root here because it need to
                    // be different for each tree
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    root.right = r;
                    rst.add(root);
                }
            }
        }
        return rst;
    }

    /**
     * 2. Dynamic Programming (bottom-up)
     *
     * Observation:
     * for same length ranges, all the possible structures are the same
     *
     * Notice that we don't clone left subtrees here, we just share them
     * Illustration: https://leetcode.wang/leetCode-95-Unique-Binary-Search-TreesII.html
     *
     * Time complexity:
     * Space complexity:
     *
     */
    public List<TreeNode> generateTrees2(int n) {
        ArrayList<TreeNode>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<TreeNode>();
        if (n == 0) {
            return dp[0];
        }
        dp[0].add(null);
        //长度为 1 到 n
        for (int len = 1; len <= n; len++) {
            dp[len] = new ArrayList<TreeNode>();
            //将不同的数字作为根节点，只需要考虑到 len
            for (int root = 1; root <= len; root++) {
                int left = root - 1;  //左子树的长度
                int right = len - root; //右子树的长度
                for (TreeNode leftTree : dp[left]) {
                    for (TreeNode rightTree : dp[right]) {
                        TreeNode treeRoot = new TreeNode(root);
                        treeRoot.left = leftTree;
                        //克隆右子树并且加上偏差
                        treeRoot.right = clone(rightTree, root);
                        dp[len].add(treeRoot);
                    }
                }
            }
        }
        return dp[n];
    }

    private TreeNode clone(TreeNode n, int offset) {
        if (n == null) {
            return null;
        }
        TreeNode node = new TreeNode(n.val + offset);
        node.left = clone(n.left, offset);
        node.right = clone(n.right, offset);
        return node;
    }

    /**
     * 3. Dynamic Programming (bottom up)
     *
     * Think about insertion.
     * Where to insert
     * 1) as root
     * 2) as right child all the way down
     *
     * Time complexity:
     * Space complexity:
     *
     */
    public List<TreeNode> generateTrees3(int n) {
        List<TreeNode> res = new ArrayList<>();
        if(n <= 0)
            return res;

        res.add(null);
        //insert i into tree that stores values from n to i+1
        for(int i = n; i > 0; i--) {
            // `next` stores all possible trees that store values from n to i
            List<TreeNode> next = new ArrayList<>();
            for(TreeNode node: res) {
                /* Case 1: put n on the root,
                 * and the original @node tree is its right tree
                 * since i is the smallest value in the @node tree (from n to i+1)
                 */
                TreeNode root = new TreeNode(i);
                root.right = node;
                next.add(root);

                /* Other Cases: put n on root.left, root.left.left, root.left....left,
                 * the root of the new tree is still @node,
                 * i put on insertParent.left,
                 * and the original left tree of the insertParent is set as the right subtree of the new node since i is small than values in the subtree.
                 */
                TreeNode insertParent = node;
                while (insertParent != null) {
                    /* Step 1: generate a new tree from the @node tree */
                    TreeNode cRoot = new TreeNode(node.val);
                    //clone left subtree since we need to change it by inserting i
                    cRoot.left = treeCopy(node.left);
                    // reusing the right tree since it is fixed
                    cRoot.right = node.right;

                    /* Step 2: insert i into the new tree */
                    TreeNode insertParentInNewTree = getValNode(cRoot, insertParent.val);
                    TreeNode tmp = insertParentInNewTree.left;
                    insertParentInNewTree.left = new TreeNode(i);
                    insertParentInNewTree.left.right = tmp;

                    next.add(cRoot);
                    insertParent = insertParent.left;
                }
            }
            res = next;
        }
        return res;
    }

    private TreeNode getValNode(TreeNode n, int val) { //find the cutoff node in the new tree
        while(n != null) {
            if(n.val == val) break;
            n = n.left;
        }
        return n;
    }


    private TreeNode treeCopy(TreeNode root) {
        if (root == null) {
            return root;
        }

        TreeNode newRoot = new TreeNode(root.val);
        ArrayDeque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(new TreeNode[]{root, newRoot});

        while (!stack.isEmpty()) {
            TreeNode[] pair = stack.pop();
            pair[1].val = pair[0].val;

            if (pair[0].right != null) {
                pair[1].right = new TreeNode(0);
                stack.push(new TreeNode[]{pair[0].right, pair[1].right});
            }

            if (pair[0].left != null) {
                pair[1].left = new TreeNode(0);
                stack.push(new TreeNode[]{pair[0].left, pair[1].left});
            }
        }

        return newRoot;
    }
}
