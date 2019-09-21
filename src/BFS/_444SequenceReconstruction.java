package BFS;

import java.util.*;

public class _444SequenceReconstruction {

    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();

        // initialization map and indegree
        for (int num : org) {
            map.put(num, new HashSet<>());
            indegree.put(num, 0);
        }

        // count indegree
        int n = org.length;
        int count = 0;
        for (int[] seq : seqs) {
            count += seq.length;

            // seq.length >= 1 prevents ArrayOutOfBounds
            if (seq.length >= 1 && (seq[0] <= 0 || seq[0] > n)) {
                return false;
            }

            for (int i = 1; i < seq.length; i++) {
                if (seq[i] <= 0 || seq[i] > n) {
                    return false;
                }

                // return true if set did not already contain seq[i]
                // make sure every edge is only counted once.
                if (map.get(seq[i-1]).add(seq[i])) {
                    indegree.put(seq[i], indegree.get(seq[i]) + 1);
                }
            }
        }

        // case: [1], []
        //
        if (count < n) {
            return false;
        }

        // push all the nodes whose indegree is 0 into the queue
        Queue<Integer> q = new ArrayDeque<>();
        for (int key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                q.add(key);
            }
        }

        int cnt = 0;

        // 如果在某一个数的时候，它的下一个可能的值有多于1个，那么我们希望答案返回false；
        // 我们写q.size() ==1的话如果这种情况出现的时候cnt不会增大，
        // 然后cnt == org.length就会返回false，因为cnt比org.length小；

        // 因为要求最后的sequence是唯一的，也就是说拓扑排序的时候减in degree，
        // 不能有两个节点的入度同时变成0。换言之，不可能同时往queue里加一个以上（不包括一个）的元素，
        // 所以queue的大小一直都是1。当queue的大小不是1的时候，就不符合要求，直接可以退出循环。
        while (q.size() == 1) {
            int ele = q.poll();

            for (int next : map.get(ele)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) {
                    q.add(next);
                }
            }

            // 确保形成的sequence和org 每个位置都对应
            if (ele != org[cnt]) {
                return false;
            }

            cnt++;
        }

        return cnt == org.length;
    }
}
