import java.util.*;

/**
 * ConcurrentModificationException
 *
 * Generally:
 * used to fail-fast when something we are iterating on is modified
 *
 * Cases:
 * 1. One thread modifies aa Collection, while another thread is iterating over it
 * the results of the iteration are undefined under these circumstances.
 *
 * 2. a single thread issues a sequence of method invocations that violates the contract of an object,
 * the object may throw this exception. For example, if a thread modifies a collection directly while it is iterating over the collection with a fail-fast iterator, the iterator will throw this exception.
 *
 * Solutions and reference:
 * https://www.baeldung.com/java-concurrentmodificationexception
 *
 */
public class _0126WordLadderII {
    /**
     * Intuition:
     *
     * Starting from begin word, connect each word with its next word (parent -> child), we will have a tree
     * We're looking for such paths:
     * 1) end leaf node is endWord
     * 2) length is minimum
     *
     * When it comes to tree/maptraversal -> BFS or DFS
     *
     * 1) BFS: find out the min level that endWord shows up -> no need to visit any deeper level
     * 2) BFS: store (node -> list of next node) in HashMap, so during DFS we can directly visit next words
     *
     *
     */


    /**
     * 1. Two-end BFS + DFS
     *
     * Two-end BFS is good for problems that ask for shortest path given start and end
     *
     * Explanation:
     * https://leetcode.wang/leetCode-126-Word-LadderII.html
     *
     * Time:
     * building dict: O(size of wordList)
     * bfs: number of calls O(shortest path length <= size of dict) * time for single level O(size of startSet <= size of dict * word length * word length)
     * dfs: number of calls O(number of nodes in the map) * O(
     *
     * 假设 beginword 和 endword 之间的距离是 d。每个节点可以扩展出 k 个节点。
     *
     * 那么正常的时间复杂就是 k^d
     * 双向搜索的时间复杂度就是 k^{d/2} + k^{d/2}
     *
     * Space:
     * bfs / dfs call stack: O(height of tree = shortest path length)
     *
     */
    public List<List<String>> findLadders(String beginWord,
                                          String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }

        // Two-end BFS for construct the graph given a starting and ending point
        // Think of two unconnected graph trying to connect
        // word and its next word (viewing from top to bottom -> from beginWord to endWord)
        HashMap<String, List<String>> map = new HashMap<>();
        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        endSet.add(endWord);
        startSet.add(beginWord);
        bfs(startSet, endSet, map, dict, false);

        // DFS
        List<String> list = new ArrayList<>();
        list.add(beginWord);
        dfs(res, list, beginWord, endWord, map);

        return res;
    }

    private void bfs(Set<String> startSet, Set<String> endSet,
                     HashMap<String, List<String>> map, Set<String> dict, boolean reverse) {
        if (startSet.size() == 0) {
            // startSet 为空了，就直接结束
            // 比如下边的例子就会造成 startSet 为空
            //  beginWord:"hot" endWord: "dog" dict["hot","dog"]
            // dict remove startSet: ["dog"]
            // nextLevel for start: []
            // -> start graph and end graph is not connected
            // -> no path from beginWord to endWord
            return;
        }

        if (startSet.size() > endSet.size()) {
            bfs(endSet, startSet, map, dict, !reverse);
            return;
        }

        Set<String> nextLevel = new HashSet<>();
        // we don't remove words in endSet because our goal is to connect two graphs
        dict.removeAll(startSet); // O(size of startSet < number of nodes in the tree)
        boolean finish = false;

        // O(size of startSet * word length * word length)
        for (String s : startSet) {
            char[] chs = s.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                char old = chs[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    // We can also CONTINUE when c == old

                    chs[i] = c;
                    String word = new String(chs);

                    if (dict.contains(word)) {
                        if (endSet.contains(word)) {
                            // we don't stop here because there could be multiple shortest paths
                            finish = true;
                        } else {
                            // we could also add word to nextLevel if endSet contains word
                            // because nextLevel doesn't matter, our graph is constructed
                            nextLevel.add(word);
                        }

                        String key = reverse ? word : s;
                        String val = reverse ? s : word;

                        if (map.get(key) == null) {
                            map.put(key, new ArrayList<>());
                        }

                        map.get(key).add(val);
                    }
                }

                chs[i] = old;
            }
        }

        if (!finish) {
            bfs(nextLevel, endSet, map, dict, reverse);
        }
    }

    private void dfs(List<List<String>> res, List<String> list,
                     String word, String endWord, HashMap<String, List<String>> map) {
        if (word.equals(endWord)) {
            res.add(new ArrayList(list));
            return;
        }

        if (map.get(word) == null) {
            // if precede -> NullPointerException
            return;
        }

        for (String next : map.get(word)) {
            list.add(next);
            dfs(res, list, next, endWord, map);
            list.remove(list.size() - 1);
        }
    }

    /**
     * 2.
     */
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        List<List<String>> res = new ArrayList<>();
        if (!dict.contains(endWord)) {
            return res;
        }
        HashMap<String, List<String>> map = getChildren(beginWord, endWord, dict);
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        findLadders(beginWord, endWord, map, res, path);
        return res;

    }

    public void findLadders(String beginWord, String endWord, HashMap<String, List<String>> map, List<List<String>> res, List<String> path) {
        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(path));
        }
        if (!map.containsKey(beginWord)) {
            return;
        }
        for (String next : map.get(beginWord)) {
            path.add(next);
            findLadders(next, endWord, map, res, path);
            path.remove(path.size() - 1);
        }
    }

    public HashMap<String, List<String>> getChildren(String beginWord, String endWord, Set<String> dict) {
        HashMap<String, List<String>> map = new HashMap<>();
        Set<String> start = new HashSet<>();
        start.add(beginWord);
        Set<String> end = new HashSet<>();
        end.add(endWord);
        boolean found = false;
        boolean isBackward = false;
        while (!start.isEmpty() && !found) {
            if (start.size() > end.size()) {
                Set<String> tem = start;
                start = end;
                end = tem;
                isBackward = !isBackward;
            }
            Set<String> set = new HashSet<>();
            for (String cur : start) {
                dict.remove(cur);
                for (String next : getNext(cur, dict)) {
                    if (!dict.contains(next) || start.contains(next)) {
                        continue;
                    }
                    if (end.contains(next)) {
                        found = true;
                    }
                    set.add(next);
                    String parent = isBackward ? next : cur;
                    String child = isBackward ? cur : next;
                    if (!map.containsKey(parent)) {
                        map.put(parent, new ArrayList<>());
                    }
                    map.get(parent).add(child);

                }
            }
            start = set;
        }
        return map;

    }
    private List<String> getNext(String cur, Set<String> dict) {
        List<String> res = new ArrayList<>();
        char[] chars = cur.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char old = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == old) {
                    continue;
                }
                chars[i] = c;
                String next = new String(chars);
                if (dict.contains(next)) {
                    res.add(next);
                }
            }
            chars[i] = old;
        }
        return res;
    }
}
