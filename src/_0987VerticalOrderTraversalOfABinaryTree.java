import java.util.*;

public class _0987VerticalOrderTraversalOfABinaryTree {
    /**
     * 1. BFS/DFS with Global Sorting
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(n)
     */
    class Element {
        private TreeNode node;
        private int x;
        private int y;

        public Element(TreeNode node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
    }

    private void BFS(TreeNode root, List<Element> nodeList) {
        Queue<Element> queue = new ArrayDeque();
        int x = 0, y = 0;
        queue.offer(new Element(root, x, y));

        while (!queue.isEmpty()) {
            Element triplet = queue.poll();
            root = triplet.node;
            x = triplet.x;
            y = triplet.y;

            if (root != null) {
                nodeList.add(new Element(root, x, y));
                queue.offer(new Element(root.left, x - 1, y + 1));
                queue.offer(new Element(root.right, x + 1, y + 1));
            }
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {

        List<List<Integer>> output = new ArrayList();
        if (root == null) {
            return output;
        }

        // step 1). BFS traversal
        List<Element> nodeList = new ArrayList<>();
        BFS(root, nodeList);

        // step 2). sort the global list by <column, row, value>
        Collections.sort(nodeList, new Comparator<Element>() {
            @Override
            public int compare(Element t1,
                               Element t2) {
                if (t1.x == t2.x) {
                    if (t1.y == t2.y) {
                        return t1.node.val - t2.node.val;
                    } else{
                        return t1.y - t2.y;
                    }
                } else {
                    return t1.x - t2.x;
                }
            }
        });

        // step 3). extract the values, partitioned by the column index.
        List<Integer> currColumn = new ArrayList();
        Integer currX = nodeList.get(0).x;

        for (Element element : nodeList) {
            Integer x = element.x, value = element.node.val;
            if (x == currX) {
                currColumn.add(value);
            } else {
                output.add(currColumn);
                currX = x;
                currColumn = new ArrayList();
                currColumn.add(value);
            }
        }
        output.add(currColumn);

        return output;
    }


    /**
     * 2. BFS/DFS with Partition Sorting
     *
     * partitioning the list of coordinates into subgroups based on the column index.
     * [ it would be faster to sort a series of subgroups than sorting them all together in a single group. ]
     * proof:
     * https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/solution/
     *
     * Time complexity: O(N * log(N/k))
     * Space complexity: O(n)
     *
     * @param root
     */
}
