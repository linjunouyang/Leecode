import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class _0847ShortestPathVisitingAllNodes {
    class State {
        int curNode;
        int visited;

        public State(int curNode, int visited) {
            this.curNode = curNode;
            this.visited = visited;
        }

        public String toString() {
            return curNode + "," + visited;
        }
    }


    /**
     * 1. BFS
     *
     * shortest path -> BFS
     * ------
     * States:
     * In traditional BFS, we use a hashset to remember visited nodes
     * to prevent revisiting same nodes -> endless loop.
     * Visited nodes is the 'state' that we need to remember, need to avoid revisiting.
     *
     * In this question, we can revisit same node if the node might leads to unvisited nodes
     * The 'state' is [visited nodes && curr node]
     * ------
     * we can start and stop at any node
     * -> multiple start BFS
     *
     * Time: O(n * 2^n)
     * Every state enters and leaves queue once,
     * total time complexity depends on number of states
     *
     * Space: O(n * 2^n)
     */
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int allNodesMask = (1 << n) - 1;

        Deque<State> queue = new ArrayDeque<>();
        HashSet<String> states = new HashSet<>();
        for (int i = 0; i < n; i++) {
            State state = new State(i, 1 << i);
            queue.offer(state);
            states.add(state.toString());
        }

        int minLen = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State state = queue.poll();
                if (state.visited == allNodesMask) {
                    return minLen;
                }
                for (int nextNode: graph[state.curNode]) {
                    int nextVisited = state.visited | (1 << nextNode);
                    State nextState = new State(nextNode, nextVisited);
                    if (!states.contains(nextState.toString())) {
                        states.add(nextState.toString());
                        queue.offer(nextState);
                    }
                }
            }
            minLen++;
        }

        return minLen;
    }
}
