import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {
    /**
     * Traverse
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param root
     * @return
     */
    public List<String> binaryTreePaths1(TreeNode root) {
        List<String> paths = new ArrayList<String>();
        if (root != null) {
            helper1(root, "", paths);
        }
        return paths;
    }

    private void helper1(TreeNode root, String path, List<String> paths) {
        if (root.left == null && root.right == null) {
            paths.add(path + root.val);
        }
        if (root.left != null) {
            helper1(root.left, path + root.val + "->", paths);
        }
        if (root.right != null) {
            helper1(root.right, path + root.val + "->", paths);
        }
    }

    /**
     * Optimize with StringBuilder
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        helper2(res, root, sb);
        return res;
    }

    private void helper2(List<String> res, TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }
        int len = sb.length();
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(sb.toString());
        } else {
            sb.append("->");
            helper2(res, root.left, sb);
            helper2(res, root.right, sb);
        }
        // to cut-off the extra things we appended in else part. Assume node 4 has
        // a left child with value 6 and one right child with value 3 and both 3 and
        // 6 are leaf nodes. We add 3 to 5->4, it becomes 5->4->3. Now node 4 has
        // left node which is 6, then we need to chop off the ->3 part to make new
        // path 5->4->6
        sb.setLength(len);
    }
}
