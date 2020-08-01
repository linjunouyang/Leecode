import java.util.*;

/**
 * Remember: a tree is an acyclic connected graph (directed?)
 *
 * Do BFS with queue
 */
public class _0863AllNodesDistanceKInBinaryTree {

    /**
     * 1. Bidirect Search (BFS) on Tree (Treated as a graph) with Help of NodeToParent hashtable
     *
     * n: num of nodes, m: num of edges
     * Time complexity: O(m + n) = O(n - 1 + n) = O(n)
     * Space complexity: O(n)
     *
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        // Build a 'Graph'
        // node -> its parent
        HashMap<TreeNode, TreeNode> nodeToParent = new HashMap<>();

        buildNodeToParentMap(nodeToParent, root, null);

        // BFS
        int currentLevel = 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(target);

        /*
          The is an undirected graph now that we can go to and from nodes.
          Before we could only go down the tree.
          Therefore, we need a hashtable to keep track of nodes we have
          visited so that we do not go back and revisit what has already
          been processed and cause an infinite cycle
        */
        Set<TreeNode> seen = new HashSet<>();
        seen.add(target);

        while (!queue.isEmpty()) {
            if (currentLevel == K) {
                return extractQueueContent(queue);
            }

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (node.left != null && !seen.contains(node.left)) {
                    queue.offer(node.left);
                    seen.add(node.left);
                }

                if (node.right != null && !seen.contains(node.right)) {
                    queue.offer(node.right);
                    seen.add(node.right);
                }

                TreeNode parent = nodeToParent.get(node);

                if (parent != null && !seen.contains(parent)) {
                    queue.offer(parent);
                    seen.add(parent);
                }
            }
            currentLevel++;
            if (currentLevel > K) {
                break;
            }
        }

        return new ArrayList<>();
    }

    /**
     *   When this recursion is done we will know all nodes' parents. The tree then
     *   can be treated and searched like any other graph
     * @param nodeToParentMap
     * @param root
     * @param parent
     */
    private void buildNodeToParentMap(
            Map<TreeNode, TreeNode> nodeToParentMap,
            TreeNode root,
            TreeNode parent
    ) {
        if (root == null) {
            return;
        }

        // Map the node to its parent
        nodeToParentMap.put(root, parent);

        /*
          Go left and after that go right. The call that we make next
          will have what we call 'root' now as the 'parent' value so
          we can do the mapping in THAT call stack frame...and so on
          and so on...
        */
        buildNodeToParentMap(nodeToParentMap, root.left, root);
        buildNodeToParentMap(nodeToParentMap, root.right, root);
    }

    private List<Integer> extractQueueContent(Queue<TreeNode> queue) {
        List<Integer> res = new ArrayList();
        for (TreeNode node: queue) {
            res.add(node.val);
        }
        return res;
    }

    /**
     * 2.
     * if the distance from a node n to target node is k,
     * the distance from its child to the target node is k + 1
     * (unless the child node is closer to the target node -> the target node is in n's subtree)
     *
     * To avoid this situation, we need to travel the tree first to find the path from root to target, to:
     * store the value of distance in hashmap from the [all nodes in that path] to target
     *
     * Then we can easily use dfs to travel the whole tree.
     *
     * Every time when we meet a treenode which has already stored in map,
     * use the stored value in hashmap instead of the value from its parent node.
     */


    public List<Integer> distanceK2(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new LinkedList<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        find(root, target, map);
        dfs(root, target, K, map.get(root), res, map);
        return res;
    }

    // find target node first and store the distance in that path that we could use it later directly
    private int find(TreeNode root, TreeNode target, Map<TreeNode, Integer> map) {
        if (root == null) return -1;
        if (root == target) {
            map.put(root, 0);
            return 0;
        }
        int val = find(root.left, target, map);
        if (val == -1) {
            val = find(root.right, target, map);
        }
        if(val == -1) {
            return -1;
        }
        map.put(root, val + 1);
        return val;
    }

    private void dfs(TreeNode root, TreeNode target, int K, int length, List<Integer> res, Map<TreeNode, Integer> map) {
        if (root == null) return;
        if (map.containsKey(root)) length = map.get(root);
        if (length == K) res.add(root.val);
        dfs(root.left, target, K, length + 1, res, map);
        dfs(root.right, target, K, length + 1, res, map);
    }
}
