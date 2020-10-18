import java.util.*;

public class _0399EvaluateDivision {
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
                Map<String, Boolean> visited = new HashMap<>();
                for (String node : map.keySet()) {
                    visited.put(node, false);
                }
                for (String node : visited.keySet()) {
                    if (!visited.get(node)) {
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

    private Result bfs(Map<String, List<Neighbor>> map, Map<String, Boolean> visited, String start, String end) {
        Deque<String> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(start);
        Deque<Double> valQueue = new ArrayDeque<>();
        valQueue.offer(1.0);
        visited.put(start, true);

        while (!nodeQueue.isEmpty()) {
            String node = nodeQueue.poll();
            double val = valQueue.poll();
            for (Neighbor next : map.get(node)) {
                if (next.node.equals(end)) {
                    return new Result(true, val * next.val);
                }
                if (!visited.get(next.node)) {
                    nodeQueue.offer(next.node);
                    valQueue.offer(val * next.val);
                    visited.put(next.node, true);
                }
            }
        }

        return new Result(false, -1.0);
    }

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
}
