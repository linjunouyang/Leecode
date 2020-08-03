public class _0270ClosestBinarySearchTreeValue {

    /**
     * 1. Binary Search / Iterative pre-order traversal (with pruning)
     *
     * Remember:
     * When performing operations (including comparisons) with two different numerical types,
     * Java will perform an implicit widening conversion.
     * This means that when you compare a double with an int,
     * the int is converted to a double so that Java can then compare the values as two doubles.
     *
     * Time complexity: O(h) h can be as big as n
     * Space complexity: O(1)
     *
     */
    public int closestValue(TreeNode root, double target) {
        TreeNode curr = root;
        int res = 0;
        double diff = Double.MAX_VALUE;

        while (curr != null) {
            if (curr.val == target) {
                return curr.val;
            } else {
                if (Math.abs(curr.val - target) < diff) {
                    diff = Math.abs(curr.val - target);
                    res = curr.val;

                    if (Math.abs(curr.val - target) <= 0.5) {
                        break;
                    }
                }

                if (curr.val > target) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }

            }
        }

        return res;
    }
}
