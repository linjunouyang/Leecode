import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 1. Not Directed Acyclic Graph
 * 2. Might have duplicate edges
 */
public class _0322ReconstructItinerary {
    /**
     * 1. DFS (backtracking) + Greedy
     *
     * Similar idea, but save time spent on dfs list.remove, list.add:
     * using visitMap (Solution 1)
     * https://leetcode.com/problems/reconstruct-itinerary/solution/
     *
     * d: maximum number of neighbors
     * Time:
     * Sorting: O(V * dlogd)
     *
     * In the worst case, the graph is not balanced at all, i.e. we have a sort of star shape,
     * the origin departure concentrates on a single airport (e.g. JFK).
     * In this case, the JFK would assume half of the flights (since we still need the return flight).
     * As a result, the sorting operation on this airport would be exceptionally expensive,
     * i.e. N log N, where N = |E|/2. And this would be the final complexity as well, since it dominates the rest of the calculation.
     *
     * Space:
     * O(V + E) for map
     * recursion call stack: O(E)
     *
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        if (tickets == null) {
            return res;
        }
        HashMap<String, ArrayList<String>> map = new HashMap();

        for (List<String> ticket: tickets){
            String from = ticket.get(0);
            String to = ticket.get(1);
            map.putIfAbsent(from, new ArrayList<String>());
            map.get(from).add(to);
        }

        for (String airport: map.keySet()) {
            Collections.sort(map.get(airport));
        }

        dfs("JFK", map, res, tickets.size() + 1);
        return res;
    }

    private boolean dfs(String from, HashMap<String, ArrayList<String>> map, List<String> res, int end){
        res.add(from);

        if (res.size() == end){
            //res.add(from);
            return true;
        }

        if (!map.containsKey(from)) {
            res.remove(res.size() - 1);
            return false;
        }

        int n = map.get(from).size();

        for (int i = 0; i < n; i++) {
            String to = map.get(from).get(i);
            map.get(from).remove(i);
            if (dfs(to, map, res, end)) {
                return true;
            }

            map.get(from).add(i, to);
        }
        res.remove(res.size() - 1);
        return false;
    }

    /**
     * 2. Ano
     */
}
