import java.util.*;

// 1 <= n <= 8
public class _0095UniqueBinarySearchTreesII {
    /**
     * 1. Recursion (pre-order)
     *
     * duplicate re-computation for cases like
     *      y                         x
     *    x                              y
     *     ! (x < i < y)               ! (x < i < y)
     *
     * I was stuck at recursion definition (what to return, how to keep track of root)
     *
     * Time complexity: Catalan number (Gn) * n times = nGn
     * Gn grow as (4^n)/(n ^ 1.5)
     * Total: O(4^n) / (n ^ 0.5)
     *
     * Space complexity: Gn trees, n elements each -> O(4^n / n^0.5)
     *
     * Binomial coefficients: https://en.wikipedia.org/wiki/Binomial_coefficient
     * the coefficient of X^k term in the polynomial expansion of the binomial power (1+x)^n
     * (n) = (n!) / (k!(n-k)!)
     * (k)
     */
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> trees = generateTreeHelper(1, n);
        return trees;
    }

    // generate trees using all values in [min, max], return the list of roots
    private List<TreeNode> generateTreeHelper(int min, int max) {
        List<TreeNode> roots = new ArrayList<>();

        if (min > max) {
            roots.add(null);
            return roots;
        }

        for (int rootVal = min; rootVal <= max; rootVal++) {
            // we can't initialize root here because it need to
            // be different for each tree
            List<TreeNode> leftRoots = generateTreeHelper(min, rootVal - 1);
            List<TreeNode> rightRoots = generateTreeHelper(rootVal + 1, max);
            for (TreeNode leftRoot : leftRoots) {
                for (TreeNode rightRoot : rightRoots) {
                    TreeNode root = new TreeNode(rootVal);
                    root.left = leftRoot;
                    root.right = rightRoot;
                    roots.add(root);
                }
            }
        }

        return roots;
    }

    // using memoization and deep copy
    private List<TreeNode> generateTreeHelper11(int min, int max, HashMap<String, List<TreeNode>> rangeToTrees) {
        List<TreeNode> roots = new ArrayList<>();

        if (min > max) {
            roots.add(null);
            return roots;
        }

        String range = min + "," + max;
        if (rangeToTrees.containsKey(range)) {
            return rangeToTrees.get(range);
        }

        for (int rootVal = min; rootVal <= max; rootVal++) {
            // we can't initialize root here because it need to
            // be different for each tree
            List<TreeNode> leftRoots = generateTreeHelper11(min, rootVal - 1, rangeToTrees);
            List<TreeNode> rightRoots = generateTreeHelper11(rootVal + 1, max, rangeToTrees);
            for (TreeNode leftRoot : leftRoots) {
                for (TreeNode rightRoot : rightRoots) {
                    TreeNode root = new TreeNode(rootVal);
                    root.left = clone(leftRoot);
                    root.right = clone(rightRoot);
                    roots.add(root);
                }
            }
        }

        rangeToTrees.put(range, roots);
        return roots;
    }

    /**
     * 2. Dynamic Programming (bottom-up)
     *
     * we could turn 1) into iterative DP
     * i > j: null (getOrDefault a empty list, add null to it)
     * i = j: i (special processing before iteration)
     * dp[i, j] = sum of (dp[i, x-1] combined with dp[x+1, j])  (i <= x <= j)
     *
     * Observation:
     * for same length ranges, all the possible structures are the same
     *
     * Notice that we don't clone left subtrees here, we just share them
     * Illustration: https://leetcode.wang/leetCode-95-Unique-Binary-Search-TreesII.html
     *
     * Time complexity:
     * Space complexity:
     */
    public List<TreeNode> generateTrees2(int n) {
        ArrayList<TreeNode>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<TreeNode>();
        if (n == 0) {
            return dp[0];
        }
        dp[0].add(null);
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
     * Think about inserting i into all the trees of range [i + 1, n]
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

                /* Other Cases: put n on root.left, root.left.left, root.left....left (xNode.left)
                 * the root of the new tree is still xNode
                 * xNode.left becomes i's right subtree
                 * i becomes xNode.left
                 */
                TreeNode insertParent = node;
                while (insertParent != null) {
                    /* Step 1: generate a new tree from the @node tree */
                    TreeNode cRoot = new TreeNode(node.val);
                    //clone left subtree since we need to change it by inserting i
                    cRoot.left = clone(node.left);
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


    private TreeNode clone(TreeNode root) {
        if (root == null) {
            return null;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        TreeNode cloneRoot = new TreeNode(root.val);
        queue.offer(root);
        queue.offer(cloneRoot);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode cloneNode = queue.poll();

            if (node.left != null) {
                TreeNode cloneLeft = new TreeNode(node.left.val);
                cloneNode.left = cloneLeft;
                queue.offer(node.left);
                queue.offer(cloneLeft);
            }

            if (node.right != null) {
                TreeNode cloneRight = new TreeNode(node.right.val);
                cloneNode.right = cloneRight;
                queue.offer(node.right);
                queue.offer(cloneRight);
            }
        }

        return cloneRoot;
    }
}
