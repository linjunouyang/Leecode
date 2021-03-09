import java.util.*;

/**
 * 1. all letters are in lowercase.
 * 2. if the order is invalid, return an empty string.
 * 3. There may be multiple valid order of letters, return any one of them is fine.
 * 4. The input can contain words followed by their prefix, for example, abcd and then ab. These cases will never result in a valid alphabet (because in a valid alphabet, prefixes are always first). You'll need to make sure your solution detects these cases correctly.
 * 5. Your output string must contain all unique letters that were within the input list, including those that could be in any position within the ordering. It should not contain any additional letters that were not in the input.
 *
 * follow-up:
 * how many valid alphabet orderings there are
 *
 */
public class _0269AlienDictionary {
    /**
     * 1. BFS
     *
     * N: the total number of strings in the input list.
     *
     * C: the total length of all the words in the input list, added together.
     *
     * U: the total number of unique letters in the alien alphabet.
     * While this is limited to 26 in the question description,
     * we'll still look at how it would impact the complexity if it was not limited (as this could potentially be a follow-up question).
     *
     * Time:
     * a) identify all relations
     * b) put them into an adjacency list
     * c) covert it into a valid alphabet ordering
     *
     * worst case: a) and b) needs checking every letter of every word O(C).
     * c): BFS: O(V + E) = O(U + min(U^2, N - 1))
     * edge num is bound by vertex number and num of rules extracted from rules min(U^2, N - 1))
     * ->
     * total: O(C + U + min(U^2, N)), considering N < C and U < C
     * total: O(C + min(U^2, N))
     * Now, to simplify the rest, consider two cases:
     * 1) U^2 < N < C, so O(C)
     * 2) U^2 > N, O(C)
     *
     * Space:
     * adjacency list: O(V + E) = O(U + min(U^2, N))
     * in this case, u = 26, total: O(1)
     *
     */
    public String alienOrder(String[] words) {
        // Step 0: Create data structures and find all unique letters.
        HashMap<Character, List<Character>> adjList = new HashMap<>();
        HashMap<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Find the first non match and insert the corresponding relation.
            int j = 0;
            int end = Math.min(word1.length(), word2.length());
            while (j < end) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
                j++;
            }

            if (j == end && word1.length() > word2.length()) {
                return "";
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new ArrayDeque<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c).equals(0)) {
                queue.add(c);
            }
        }

        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next).equals(0)) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }

    /**
     * 2. DFS
     *
     * Recall that in a depth-first search, nodes are returned once they either have no outgoing links left,
     * or all their outgoing links have been visited.
     * Therefore, the order in which nodes are returned by the depth-first search will be the reverse of a valid alphabet order.
     *
     * If we made a reverse adjacency list instead of a forward one,
     * the output order would be correct (without needing to be reversed).
     * Remember that when we reverse the edges of a directed graph, t
     * he nodes with no incoming edges became the ones with no outgoing edges.
     * This means that the ones at the start of the alphabet will now be the ones returned first.
     *
     * One issue we need to be careful of is cycles.
     * In directed graphs, we often detect cycles by using graph coloring.
     * All nodes start as white, and then once they're first visited they become grey,
     * and then once all their outgoing nodes have been fully explored, they become black.
     * We know there is a cycle if we enter a node that is currently grey
     * (it works because all nodes that are currently on the stack are grey.
     * Nodes are changed to black when they are removed from the stack).
     *
     */
    public String alienOrder2(String[] words) {
        // Step 0: Put all unique letters into reverseAdjList as keys.
        HashMap<Character, List<Character>> reverseAdjList = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                reverseAdjList.putIfAbsent(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges and add reverse edges to reverseAdjList.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    reverseAdjList.get(word2.charAt(j)).add(word1.charAt(j));
                    break;
                }
            }
        }

        HashMap<Character, Boolean> seen = new HashMap<>();
        StringBuilder output = new StringBuilder();
        // Step 2: DFS to build up the output list.
        for (Character c : reverseAdjList.keySet()) {
            boolean result = dfs(reverseAdjList, c, seen, output);
            if (!result) {
                return "";
            }
        }

        if (output.length() < reverseAdjList.size()) {
            return "";
        }
        return output.toString();
    }

    // Return true iff no cycles detected.
    private boolean dfs(HashMap<Character, List<Character>> reverseAdjList, Character c, HashMap<Character, Boolean> seen, StringBuilder output) {
        if (seen.containsKey(c)) {
            return seen.get(c); // If this node was grey (false), a cycle was detected.
        }
        seen.put(c, false);
        for (Character next : reverseAdjList.get(c)) {
            boolean result = dfs(reverseAdjList, next, seen, output);
            if (!result) return false;
        }
        seen.put(c, true);
        output.append(c);
        return true;
    }
}
