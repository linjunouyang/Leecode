package BFS;

import java.util.*;

class DirectedGraphNode {
    int label;
    ArrayList<DirectedGraphNode> neighbors;
    DirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<>();
    }
}

public class TopologicalOrdering {

    /**
     * queue and result list are synchronized.
     *
     * map.get returns null if the map contains no mapping for the key
     *
     *
     * @param graph
     * @return
     */
    public List<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        List<DirectedGraphNode> result = new ArrayList<>();

        // store nodes and their indegree
        Map<DirectedGraphNode, Integer> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                if (map.containsKey(neighbor)) {
                    map.put(neighbor, map.get(neighbor) + 1);
                } else {
                    map.put(neighbor, 1);
                }
            }
        }

        // put all the nodes whose indegree is 0 to the queue
        Queue<DirectedGraphNode> q = new LinkedList<>();
        for (DirectedGraphNode node: graph) {
            if (!map.containsKey(node)) {
                q.offer(node);
                result.add(node);
            }
        }

        // bfs
        while (!q.isEmpty()) {
            DirectedGraphNode node = q.poll();
            for (DirectedGraphNode n : node.neighbors) {
                map.put(n, map.get(n) - 1);
                if (map.get(n) == 0) {
                    result.add(n);
                    q.offer(n);
                }
            }
        }

        if (result.size() == graph.size()) {
            return result;
        }

        return null;
    }
}
