import java.util.ArrayList;
import java.util.HashSet;

public class _0834SumOfDistancesInTree {
    /**
     * 1. Pre-order + Post-order
     *
     * What if given a tree, with a certain root 0?
     * In O(N) we can find sum of distances in tree from root and all other nodes.
     *
     * Now for all N nodes?
     * Of course, we can do it N times and solve it in O(N^2).
     * C++ and Java may get accepted luckily, but it's not what we want.
     *
     * When we move our root from one node to its connected node,
     * one part of nodes get closer, one the other part get further.
     *
     * If we know exactly how many nodes in both parts, we can solve this problem.
     *
     * With one single traversal in tree, we should get enough information for it and
     * don't need to do it again and again.
     *
     * ------
     *
     * 1. We need to know:
     * 1.1 List<Set<Integer>> tree
     * For each node i, all the nodes that are connected to node i.
     * e.g. tree.get(0) is a set of all the nodes connected to node 0,
     * we can initialize the tree list during edges array iteration
     *
     * 1.2 int[] count
     * count[i]: How many nodes are in the subtree with node i as the root (inclusive)
     *
     * 1.3 int[] res (which we will return)
     * res[i]: distance sum from node i to all other nodes
     *
     * 2. In postDFS, we get
     * 1) count: number of nodes of subtree with node i as root (inclusive)
     * 2) partial res: the sum distances from root to all its children nodes (res array)
     *
     *                        1 ---------- count: (1) + 4 + 1 = 6
     *                      /   \          res: (1) + 0 + 4 + 4 = 9
     *                     /     \
     *         count: 1 - 2       3 ------ count: 1 + 2 + 1 = 4
     *         res: 0           /   \      res: 1 + 0 + 2 + 1 = 4
     *                         /     \
     *         count: 1 ----- 4       5 -- count: 1 + 1 = 2
     *         res: 0                  \   res: 1 + 0 = 1
     *                                  \
     *                                   6 count: 1
     *                                     res: 0
     *
     * 3. In preDFS, we do res[i] = res[root] - count[i] + count.length - count[i].
     * After step2, the res at root 1 is correct.
     * But at other nodes, it counts only distances from root to nodes in its subtrees.
     *
     * We will use res[root] as the base value, and using 'difference' between root and i
     * to calculate other res[i]
     *
     * What's the difference:
     * For example, for node 5.
     * There are count[5] nodes (including 5) which are 1 step closer to 5 than to 3, and
     * N - count[5] nodes (including 3) are 1 step farther to 5 than to 3.
     *
     * So it is:
     * res[i] = res[root] - count[i] + (N - count[i]).
     * Notice that previous res[i] value is completed covered, but we still need it in Post-order
     * because res[root] needs them
     *
     *      	                     1 ---------- count: 6
     *     	                       /   \          res: 9
     *     	                      /     \
     * count: 1 --------------- 2        3 ------ count: 4
     * res: 9 - 1 + 6 - 1 = 13         /   \      res: 9 - 4 + 6 - 4 = 7
     *                                /     \
     * count: 1 -------------------- 4       5 -- count: 2
     * res: 7 - 1 + 6 - 1 = 11                \   res: 7 - 2 + 6 - 2 = 9
     *                                         \
     *                                          6 count: 1
     *                                            res: 9 - 1 + 6 - 1 = 13
     *
     * Time: O(n)
     * Space: O(n)
     */
    int[] res, count;
    ArrayList<HashSet<Integer>> tree;
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        tree = new ArrayList<HashSet<Integer>>();
        res = new int[N];
        count = new int[N];
        for (int i = 0; i < N ; ++i)
            tree.add(new HashSet<Integer>());
        for (int[] e : edges) {
            tree.get(e[0]).add(e[1]);
            tree.get(e[1]).add(e[0]);
        }
        postOrder(0, -1);
        preOrder(0, -1);
        return res;
    }

    /**
     * PostOrder traversal of subtree (rooted at 'root')
     * Pre is for avoid visiting its parent
     * Count the number of nodes of subtree rooted at root (don't forget root itself)
     * Calculate the distance from root to all the other nodes in the subtree
     */
    public void postOrder(int root, int pre) {
        count[root]++; // root is also a node in the subtree rooted at root
        for (int i : tree.get(root)) {
            if (i == pre) {
                continue; // only care about i's children
            }
            postOrder(i, root);
            count[root] += count[i];
            // i is a child of root.
            // res[i]: distance of node i to its all descendents.
            // res[root]: distance of node root to all descendents
            // Here, all children of node i is just one node away from node root.
            // -> res[i] + count[i]( = total nodes in subtree i): the distance of all nodes in subtree i to root.
            // -> res[root] = sum(res[i]) + sum(count[i]).
            res[root] += res[i] + count[i];
        }
    }


    public void preOrder(int root, int pre) {
        for (int i : tree.get(root)) {
            if (i == pre) {
                continue;
            }
            res[i] = res[root] - count[i] + count.length - count[i];
            preOrder(i, root);
        }
    }
}
