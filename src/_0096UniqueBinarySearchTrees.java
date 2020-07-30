/**
 * 6.16 Failed to come up with a solution. Never solved a DP problem before.
 * Other solutions?
 *
 */
public class _0096UniqueBinarySearchTrees {
    /**
     * 1. Dynamic Programming
     *
     * Given a sequence 1…n, to construct a Binary Search Tree (BST) out of the sequence,
     * we could enumerate each number i in the sequence, and use the number as the root,
     * the subsequence 1…(i-1) on its left side would lay on the left branch of the root,
     * and the right subsequence (i+1)…n lay on the right branch of the root.
     * We then can construct the subtree from the subsequence recursively.
     * Through the above approach, we could ensure that the BST that we construct are all unique,
     * since they have unique roots.
     * ____________________________________
     *
     * G(n): the number of unique BST for a sequence of length n.
     * F(i, n), 1 <= i <= n: the number of unique BST, where the number i is the root of BST,
     * and the sequence ranges from 1 to n.
     *
     * G(n) is the actual function we need to calculate in order to solve the problem.
     * And G(n) can be derived from F(i, n), which at the end, would recursively refer to G(n).
     *
     * G(n) = F(1, n) + F(2, n) + ... + F(n, n)
     * Base case: G(0) = 1
     * _____________________________________
     *
     * Given a sequence 1…n, we pick a number i out of the sequence as the root,
     * then the number of unique BST with the specified root F(i),
     * is the cartesian product of the number of BST for its left and right subtrees.
     * For example, F(3, 7): the number of unique BST tree with number 3 as its root.
     * To construct an unique BST out of the entire sequence [1, 2, 3, 4, 5, 6, 7] with 3 as the root,
     * we need to construct an unique BST from [1, 2] and another BST from [4, 5, 6, 7],
     * and then combine them together (i.e. cartesian product).
     *
     * The tricky part is that we could consider the number of unique BST out of sequence [1,2] as G(2),
     * and the number of of unique BST out of sequence [4, 5, 6, 7] as G(4).
     * Therefore, F(3,7) = G(2) * G(4).
     *
     * F(i, n) = G(i - 1) * G(n - i) (1 <= i <= n)
     *
     * combining two formulas, we obtain
     * G(n) = G(0) * G(n-1) + G(1) * G(n-2) + … + G(n-1) * G(0)
     * G(n/2) * G(n/2 - 1)
     * 0 3 / 1 2 / 2 1 / 3 0
     * 0 4 / 1 3 / 2 2 / 3 1 / 4 0
     *
     * In terms of calculation, we need to start with the lower number,
     * since the value of G(n) depends on the values of G(0) … G(n-1).
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     */
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i / 2; j++) {
                G[i] += 2 * G[j] * G[i - j - 1];
            }
            if (i % 2 == 1) {
                G[i] += G[i / 2] * G[i / 2];
            }
        }

        return G[n];
    }

}
