import java.util.*;

public class _0399EvaluateDivision {
    /**
     * 1. Path search (given two end nodes) in Graph
     *
     * This one is BFS, could also be done in DFS:
     * https://leetcode.com/problems/evaluate-division/solution/
     *
     * optimization:
     * rename neighbor->node neighbor.node->node.id
     *
     * consider dfs before query processing?
     *
     */
    class Neighbor {
        String node;
        double val;

        public Neighbor(String n, double v) {
            node = n;
            val = v;
        }
    }

    class Result {
        double val;
        boolean isValid;

        public Result(boolean valid, double v) {
            val = v;
            isValid = valid;
        }
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Neighbor>> map = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);

            map.putIfAbsent(equation.get(0), new ArrayList<>());
            map.get(equation.get(0)).add(new Neighbor(equation.get(1), values[i]));

            map.putIfAbsent(equation.get(1), new ArrayList<>());
            map.get(equation.get(1)).add(new Neighbor(equation.get(0), 1 / values[i]));
        }

        double[] res = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String x = query.get(0);
            String y = query.get(1);
            res[i] = -1.0;
            if (map.containsKey(x) && map.containsKey(y)) {
                Set<String> visited = new HashSet<>();
                for (String node : map.keySet()) {
                    if (!visited.contains(node)) {
                        Result result = bfs(map, visited, x, y);
                        if (result.isValid) {
                            res[i] = result.val;
                            break;
                        }
                    }
                }
            }
        }

        return res;
    }

    private Result bfs(Map<String, List<Neighbor>> map, Set<String> visited, String start, String end) {
        Deque<Neighbor> queue = new ArrayDeque<>();
        queue.offer(new Neighbor(start, 1));
        visited.add(start);

        while (!queue.isEmpty()) {
            Neighbor node = queue.poll();
            for (Neighbor next : map.get(node.node)) {
                if (next.node.equals(end)) {
                    return new Result(true, node.val * next.val);
                }
                if (!visited.contains(next.node)) {
                    queue.offer(new Neighbor(next.node, node.val * next.val));
                    visited.add(next.node);
                }
            }
        }

        return new Result(false, -1.0);
    }

    /**
     * 2. Union Find
     *
     * Reference:
     * https://leetcode.com/problems/evaluate-division/solution/
     *
     * N: number of equations
     * M: number of queries
     *
     * Statement: If M operations, either Union or Find, are applied to N elements,
     * the total run time is O(M * log*N), where log* is iterated logarithm
     *
     * Time: O((M + N) * log*N)
     * union for each equation: O(N * log*N)
     * twice find() for each query, total: O(M  * log*N)
     *
     *
     * Space Complexity: O(n)
     *
     * Union-Find data structure is O(N) where we maintain a state for each variable.
     * find() function call stack: O(N).
     *
     */
    class Node {
        String parent;
        double weight;
        public Node(String parent, double weight) {
            this.parent = parent;
            this.weight = weight;
        }
    }

    public double[] calcEquation2(List<List<String>> equations, double[] values,
                                 List<List<String>> queries) {

        HashMap<String, Node> numMap = new HashMap<>();

        // Step 1). build the union groups
        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            double quotient = values[i];
            union(numMap, equation, quotient);
        }

        // Step 2). run the evaluation, with "lazy" updates in find() function
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String dividend = query.get(0);
            String divisor = query.get(1);

            if (!numMap.containsKey(dividend) || !numMap.containsKey(divisor)) {
                // case 1). at least one variable did not appear before
                results[i] = -1.0;
            } else {
                Node dividendNode = find(numMap, dividend);
                Node divisorNode = find(numMap, divisor);

                if (!dividendNode.parent.equals(divisorNode.parent)) {
                    // case 2). the variables do not belong to the same chain/group
                    results[i] = -1.0;
                } else {
                    // case 3). there is a chain/path between the variables
                    results[i] = dividendNode.weight / divisorNode.weight;
                }
            }
        }

        return results;
    }

    // uses path compression
    private Node find(HashMap<String, Node> numMap, String id) {
        if (!numMap.containsKey(id)) {
            numMap.put(id, new Node(id, 1.0));
        }

        Node node = numMap.get(id);
        // found inconsistency, trigger chain update
        if (!node.parent.equals(id)) {
            Node parent = find(numMap, node.parent);
            numMap.put(id, new Node(
                    parent.parent, node.weight * parent.weight));
        }

        return numMap.get(id);
    }

    // no union by size or rank
    private void union(HashMap<String, Node> numMap, List<String> equation, Double value) {
        Node dividend = find(numMap, equation.get(0));
        Node divisor = find(numMap, equation.get(1));

        // merge the two groups together,
        // by attaching the dividend group to the one of divisor
        if (!dividend.parent.equals(divisor.parent)) {
            // here we only update dividend's parent info (parent and weight)
            // discrepency between children of parent (which includes dividend) and divisor
            // will be corrected in future find();
            numMap.put(dividend.parent, new Node(divisor.parent,
                    divisor.weight * value / dividend.weight));
        }
    }
}
