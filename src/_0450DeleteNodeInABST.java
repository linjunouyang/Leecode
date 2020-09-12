public class _0450DeleteNodeInABST {
    /**
     * I transplant the successor to replace the node to be deleted,
     * which is a bit harder to implement than just transplant (the left subtree of the deleted node) to (the left child of its successor).
     * The former way is used in many text books too.
     * Why? My guess is that transplanting the successor can keep the height of the tree almost unchanged,
     * while transplanting the whole left subtree could increase the height and thus making the tree more unbalanced.
     */

    /**
     * 1. Binary Search + Recursion (using successor)
     *
     * Some solutions delete node by swapping value, which is not good because
     *
     * 1) In real world, val could be a complex data structure, copy val would be in-efficient
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else {
            if (root.left == null) {
                // CASE 1: only have right child
                // CASE 2: no children
                return root.right;
            } else if (root.right == null) {
                // CASE 3: only have left child
                return root.left;
            } else {
                // CASE 4: have two children
                // Using successor as newRoot (left most node in the right subtree)
                TreeNode newRoot = root.right;
                TreeNode par = null;
                while (newRoot.left != null){
                    par = newRoot;
                    newRoot = newRoot.left;
                }
                if (par == null){
                    // CASE 4.1: root.right is the successor
                    // root.right has no left child
                    newRoot.left = root.left;
                    return newRoot;
                }
                // CASE 4.2: successor is somewhere deep down
                // (root.right has left child, doesn't care about root.right's right child)
                par.left = newRoot.right;
                newRoot.left = root.left;
                newRoot.right = root.right;
                return newRoot;
            }
        }
        return root;
    }

    /**
     * 1.1 Binary Search + Iteration (using successor)
     *
     *
     */
    public TreeNode deleteNode11(TreeNode root, int key) {
        TreeNode cur = root;
        TreeNode pre = null;
        while(cur != null && cur.val != key) {
            pre = cur;
            if (key < cur.val) {
                cur = cur.left;
            } else if (key > cur.val) {
                cur = cur.right;
            }
        }
        if (pre == null) {
            return deleteRootNode(cur);
        }
        if (pre.left == cur) {
            pre.left = deleteRootNode(cur);
        } else {
            pre.right = deleteRootNode(cur);
        }
        return root;
    }

    private TreeNode deleteRootNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }
        TreeNode next = root.right;
        TreeNode pre = null;
        for(; next.left != null; pre = next, next = next.left);
        next.left = root.left;
        if(root.right != next) {
            pre.left = next.right;
            next.right = root.right;
        }
        return next;
    }

    /**
     * 1.3 transplant (the left subtree of the deleted node) to (the left child of its successor).
     *
     * If the node is found, delete the node.
     * We need a function deleteRoot to delete the root from a BST.
     *
     * If root==null, then return null
     * If root.right==null, then return root.left
     * If root.right!=null, the the new root of the BST is root.right;
     * And what we should do is to put root.left into this new BST.
     * As all nodes in root.left is smaller than the new tree, we just need to find the left-most node.
     */

    public TreeNode deleteNode2(TreeNode root, int key) {
        TreeNode cur = root;
        TreeNode pre = null;
        while(cur != null && cur.val != key) {
            pre = cur;
            if (key < cur.val) {
                cur = cur.left;
            } else if (key > cur.val) {
                cur = cur.right;
            }
        }
        if (pre == null) {
            return deleteRoot(cur);
        }
        if (pre.left == cur) {
            pre.left = deleteRoot(cur);
        } else {
            pre.right = deleteRoot(cur);
        }
        return root;
    }

    private TreeNode deleteRoot(TreeNode root) {
        if (root==null) {
            return null;
        }
        if (root.right==null) {
            // no children OR only has left child
            return root.left;
        }
        // only has right child OR have left and right children
        TreeNode successor = root.right; // root.right should be the new root
        while (successor.left!=null) {
            successor = successor.left; // find the left-most node
        }
        successor.left = root.left;
        return root.right;
    }


    /**
     * 2. Using Predecessor
     *
     * First check whether the given root is the node we want to delete. If so, use helper function to return new root. Else, use binary search to find the node we want to delete, once we find it, call helper function to delete it and return new node.
     *
     * The idea of helper function is assumed the given node is the node we want to delete. So we check if its left child is null, if so we can return right child directly. Vice versa. But if both child not null, we will find the largest node of left subtree, then set right child of given node to be the right child of this largest node of left subtree, then return left child of given node.
     */
    public TreeNode deleteNode3(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            return helper(root);
        }
        TreeNode dummy = root;
        while (root != null) {
            if (root.val > key) {
                if (root.left != null && root.left.val == key) {
                    root.left = helper(root.left);
                    break;
                } else {
                    root = root.left;
                }
            } else {
                if (root.right != null && root.right.val == key) {
                    root.right = helper(root.right);
                    break;
                } else {
                    root = root.right;
                }
            }
        }
        return dummy;
    }
    public TreeNode helper(TreeNode root) {
        if (root.left == null) {
            return root.right;
        } else if (root.right == null){
            return root.left;
        } else {
            TreeNode rightChild = root.right;
            TreeNode lastRight = findLastRight(root.left);
            lastRight.right = rightChild;
            return root.left;
        }
    }
    public TreeNode findLastRight(TreeNode root) {
        if (root.right == null) {
            return root;
        }
        return findLastRight(root.right);
    }
}
