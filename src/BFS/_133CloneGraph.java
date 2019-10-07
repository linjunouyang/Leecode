package BFS;

import java.util.*;

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {

    }

    public Node(int _val, List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}


public class _133CloneGraph {
    /**
     * 1. BFS
     *
     * BFS find all the nodes in the original graph
     * During BFS, clone every node
     * During BFS, clone every edge
     *
     * Notice the undirected graph is a simple graph,
     * no repeated edges, and no [self]-loops in the graph
     * -> still can have loop
     * -> at most n*(n-1) / 2 edges
     *
     * Time complexity:
     * O(n^2) because of edge copy.
     * There could be at most n^2 edges
     *
     * Space complexity:
     * O(n)
     *
     * https://www.youtube.com/watch?v=vma9tCQUXk8
     * TO_WATCH
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        // for bfs
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        // for mapping between old and new nodes
        Map<Node, Node> map = new HashMap<Node, Node>();
        map.put(node, new Node(node.val, new ArrayList<>()));

        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();

            // handle the neighbors
            for (Node neighbor : oldNode.neighbors) {
                if (!map.containsKey(neighbor)) {
                    // clone the neighbor node
                    map.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    queue.add(neighbor);
                }

                // clone the neighbor edge
                map.get(oldNode).neighbors.add(map.get(neighbor));
            }
        }

        return map.get(node);
    }
}
