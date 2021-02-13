import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class _0339NestedListWeightSum {
    /**
     * 1. Recursive DFS
     *
     * n: number of integers
     * l: maximum level of nesting
     *
     * Time: O(n)
     * Space: O(l)
     */
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return 0;
        }
        return dfs(nestedList, 1);
    }

    // Why type is List: we only go 1 level down if current element is list
    private int dfs(List<NestedInteger> nestedList, int level)  {
        int sum = 0;
        for (NestedInteger element : nestedList) {
            if (element.isInteger()) {
                sum += level * element.getInteger();
            } else {
                sum += dfs(element.getList(), level + 1);
            }
        }
        return sum;
    }

    /**
     * 2. Iterative level order traversal (BFS)
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int depthSum2(List<NestedInteger> nestedList) {
        // base cases
        if (nestedList == null) {
            return 0;
        }

        int sum = 0;
        int level = 1;
        Deque<NestedInteger> queue = new ArrayDeque<>();
        // Notice input is List<NI>, and queue is of type NI, so we should put each element in
        for (NestedInteger element : nestedList) {
            queue.offer(element);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger element = queue.poll();
                if (element.isInteger()) {
                    sum += level * element.getInteger();
                } else {
                    for (NestedInteger nestedElement : element.getList()) {
                        queue.offer(nestedElement);
                    }
                    // queue.addAll(nested.getList());
                }
            }
            level++;
        }

        return sum;
    }

    /**
     * 3. Iterative DFS
     *
     * Time: O(n)
     * Space: O(h)
     *
     */
    public int depthSum3(List<NestedInteger> nestedList) {
        // base cases
        if (nestedList == null) {
            return 0;
        }

        int sum = 0;
        Deque<NestedInteger> elementStack = new ArrayDeque<>();
        Deque<Integer> levelStack = new ArrayDeque<>();
        for (NestedInteger element : nestedList) {
            elementStack.push(element);
            levelStack.push(1);
        }

        while (!elementStack.isEmpty()) {
            NestedInteger element = elementStack.pop();
            int level = levelStack.pop();
            if (element.isInteger()) {
                sum += level * element.getInteger();
            } else {
                for (NestedInteger nestedElement : element.getList()) {
                    elementStack.push(nestedElement);
                    levelStack.push(level + 1);
                }
            }
        }

        return sum;
    }


}
