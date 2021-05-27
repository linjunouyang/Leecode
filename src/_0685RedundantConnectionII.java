/**
 * https://leetcode.com/problems/redundant-connection-ii/discuss/278105/topic
 * 去除一条边，就能变成一个根树（有向）
 * -> 反过来说一定存在（可以存在很多）某个根树加了一条边变成了现在的图。
 *
 * 1. from any node to root
 * Feature: every node has an in-degree of 1.
 * Solution: delete any one edge in the cycle
 *
 * 2. from any node to a non-root node in the parent chain
 * Feature: one node has an in-degree of 2
 * Solution: delete the edge in the cycle that points to the 2 in-degree node
 *
 * 3. from any node to a node not in the parent chain
 * Feature: one node has an in-degree of 2
 * Solution: delete one of the edges that points to the 2 in-degree node
 *
 */
public class _0685RedundantConnectionII {
    /**
     * 1. Union Find
     *
     * Time: O(n)
     * Space: O(n)
     */
    int[] anc;//并查集
    int[] parent;// record the father of every node to find the one with 2 fathers,记录每个点的父亲，为了找到双入度点

    public int[] findRedundantDirectedConnection(int[][] edges) {
        anc = new int[edges.length + 1];
        parent = new int[edges.length + 1];

        int[] edge1 = null;
        int[] edge2 = null;
        int[] lastEdgeCauseCircle = null;

        for (int[] pair:edges){
            int u = pair[0];
            int v = pair[1];

            // init the union-find set  初始化并查集
            if (anc[u] == 0) {
                anc[u] = u;
            }
            if (anc[v] == 0) {
                anc[v] = v;
            }

            if (parent[v] != 0){
                // node v already has a parent, so we just skip the union of this edge
                // and check if there will be a circle (in the end)
                edge1 = new int[]{parent[v], v};
                edge2 = pair;
            } else {
                parent[v] = u;
                int ancU = find(u);
                int ancV = find(v);
                if (ancU != ancV) {
                    anc[ancV] = ancU;
                } else {
                    // meet a circle
                    lastEdgeCauseCircle = pair;
                }
            }
        }

        if (edge1 != null && edge2 != null) {
            // there's a node with 2 in-degree

            if (lastEdgeCauseCircle == null) {
                // case 3, case 2
                return edge2;
            } else {
                // case 2
                return edge1;
            }
        } else {
            // case1
            return lastEdgeCauseCircle;
        }
    }

    private int find(int node){
        if (anc[node] == node) {
            return node;
        }
        anc[node] = find(anc[node]);
        return anc[node];
    }
}
