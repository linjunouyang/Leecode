import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class _0403FrogJump {
    /**
     * 1. Brute Force
     *
     * canCross(stones, current Pos, current jumpSize)
     *
     * Small optimization: use binary search
     *
     * Time complexity: O(3 ^ n) (think of a tree)
     * Space complexity: O(n)
     */
    public boolean canCross(int[] stones) {
        return can_Cross(stones, 0, 0);
    }

    public boolean can_Cross(int[] stones, int ind, int jumpSize) {
        for (int i = ind + 1; i < stones.length; i++) {
            int gap = stones[i] - stones[ind];
            if (gap >= jumpSize - 1 && gap <= jumpSize + 1) {
                if (can_Cross(stones, i, gap)) {
                    return true;
                }
            }
        }
        return ind == stones.length - 1;
    }

    /**
     * 2. Brute Force with memoization
     *
     * we can same function calls coming through different paths
     * e.g. For a given currentIndex, we can call the recursive function canCross with the jumpSize say n.
     * This nn could be resulting from previous jumpSize being n-1, n or n+1.
     *
     * Time complexity: O(n ^ 3)
     * n^2 entries, linear scan
     *
     * using binary search -> O(n^2 log n)
     *
     * How to analyze time complexity for recursion with memorization:
     * https://stackoverflow.com/questions/53143539/how-does-complexity-get-reduced-to-on2-from-o2n-in-case-of-memoization
     * Think about how many unique combinations for recursion method
     *
     * Space complexity: O(n ^ 2)
     */

    /**
     * 3. DP with BFS
     *
     * https://leetcode.com/problems/frog-jump/discuss/193816/Concise-and-fast-DP-solution-using-2D-array-instead-of-HashMap-with-text-and-video-explanation.
     * Similar idea but using 2D array (Notice that max step is N - 1 suppose we always choose k + 1)
     *
     * [0,1,3,6,10,15,21,28,36,45]
     * map: {0=[1], 1=[1, 2], 3=[1, 2, 3], 36=[7, 8, 9], 21=[5, 6, 7], 6=[2, 3, 4], 10=[3, 4, 5], 28=[6, 7, 8], 45=[], 15=[4, 5, 6]}
     * map space: O(24)
     * max step: 9, number of elements: 10
     *
     * [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45]
     *{0=[1],     1
     * 1=[1, 2], 2=[1, 2],    2 * 2 = 4
     * 3=[1, 2, 3], 4=[1, 2, 3], 5=[1, 2, 3],  3 * 3 = 9
     * 6=[1, 2, 3, 4], 7=[1, 2, 3, 4], 8=[1, 2, 3, 4], 9=[1, 2, 3, 4], 4 * 4 = 16
     * 10=[1, 2, 3, 4, 5], 11=[1, 2, 3, 4, 5], 12=[1, 2, 3, 4, 5], 13=[1, 2, 3, 4, 5], 14=[1, 2, 3, 4, 5],  5 * 5 = 25
     * 15=[1, 2, 3, 4, 5, 6], 16=[1, 2, 3, 4, 5, 6], 17=[1, 2, 3, 4, 5, 6], 18=[1, 2, 3, 4, 5, 6], 19=[1, 2, 3, 4, 5, 6], 20=[1, 2, 3, 4, 5, 6], 6 * 6 = 36
     * 21=[1, 2, 3, 4, 5, 6, 7], 22=[1, 2, 3, 4, 5, 6, 7], 23=[1, 2, 3, 4, 5, 6, 7], 24=[1, 2, 3, 4, 5, 6, 7], 25=[1, 2, 3, 4, 5, 6, 7], 26=[1, 2, 3, 4, 5, 6, 7], 27=[1, 2, 3, 4, 5, 6, 7], 7 * 7 = 49
     * 28=[1, 2, 3, 4, 5, 6, 7, 8], 29=[1, 2, 3, 4, 5, 6, 7, 8], 30=[1, 2, 3, 4, 5, 6, 7, 8], 31=[1, 2, 3, 4, 5, 6, 7, 8], 32=[1, 2, 3, 4, 5, 6, 7, 8], 33=[1, 2, 3, 4, 5, 6, 7, 8], 34=[1, 2, 3, 4, 5, 6, 7, 8], 35=[1, 2, 3, 4, 5, 6, 7, 8], 8 * 8 = 64
     * 36=[1, 2, 3, 4, 5, 6, 7, 8, 9], 37=[1, 2, 3, 4, 5, 6, 7, 8, 9], 38=[1, 2, 3, 4, 5, 6, 7, 8, 9],  27
     * 39=[2, 3, 4, 5, 6, 7, 8, 9],  8
     * 40=[3, 4, 5, 6, 7, 8, 9],  7
     * 41=[4, 5, 6, 7, 8, 9],  6
     * 42=[5, 6, 7, 8, 9],  5
     * 43=[6, 7, 8, 9], 4
     * 44=[7, 8, 9], 3
     * 45=[]}
     * map space: O(264)
     * max step: 44, number of elements: 45
     *
     *
     * Time complexity: O(n * sqrt(n))
     * Space complexity: O(n * sqrt(n))
     *
     * ???
     * To get a jump K, we need at least the stones 0-1-3-6-10-15...(k+1)th element,
     *
     * Worst case is when we have one stone at each step (creating a lot of possible jumpsize),
     * meaning to have [a set of length K] possible jumps,
     * we need to have n stones such that n = sum(i) for i in 0..K = k * (k + 1) / 2 meaning the second loop is O(sqrt(n))
     *
     */
    public boolean canCross3(int[] stones) {
        if (stones == null) {
            return false;
        }

        // stone position (not arr index) -> possible next jump steps on this stone
        HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(stones.length);

        for (int i = 0; i < stones.length; i++) {
            if (i > 0 && stones[i] - stones[i-1] > i) {
                return false;
            }
            map.put(stones[i], new HashSet<>());
        }
        map.get(0).add(1);

        for (int i = 0; i < stones.length - 1; i++) {
            int stonePos = stones[i];
            for (int step : map.get(stonePos)) {
                int nextPos = step + stonePos;
                if (nextPos == stones[stones.length - 1]) {
                    return true;
                }
                HashSet<Integer> set = map.get(nextPos);
                if (set != null) {
                    set.add(step);
                    if (step - 1 > 0) {
                        set.add(step - 1);
                    }
                    set.add(step + 1);
                }
            }
            // small optimization for space
            map.remove(stonePos);
        }

        return false;
    }

    /**
     * DFS
     *
     * https://leetcode.com/problems/frog-jump/discuss/264202/Something-that-you-may-not-realize-about-BFS-and-DFS-solution
     * https://leetcode.com/problems/frog-jump/discuss/88804/JAVA-DFS-17ms-beat-99.28-so-far
     *
     * Pre-check is necessary (otherwise Time Limit Exceed) :
     * if (i > 0 && stones[i] - stones[i-1] > i) return false;
     * Reason: when the answer if false, the time complexity of DFS will be exp, but pre-check will detect it before beginning DFS.
     *
     * During DFS, we should try to jump as far as we can cause this strategy is more likely to reach the destination.
     * Therefore we should try unit+preStep+1 then unit+preStep and finally unit+preStep-1.
     * If we try unit+preStep-1 first, it will be Time Limit Exceed.
     *
     * Time complexity:
     * Space complexity:
     *
     */
    public boolean canCross4(int[] stones) {
        if (stones[1] != 1) return false;

        Set<Integer> units = new HashSet<>(); // stone locations
        for (int i = 0; i < stones.length; i++) {
            if (i > 0 && stones[i] - stones[i-1] > i) {
                return false; // necessary!
            }
            units.add(stones[i]);
        }
        return cross(units, 1, 1, stones[stones.length-1]);
    }

    private boolean cross(Set<Integer> units, int unit, int preStep, int destination) {
        if (preStep <= 0) {
            return false;
        }
        if (!units.contains(unit)) {
            return false;
        }
        if (unit == destination) {
            return true;
        }

        return cross(units, unit+preStep+1, preStep+1, destination) ||
                cross(units, unit+preStep, preStep, destination) ||
                cross(units, unit+preStep-1, preStep-1, destination);
    }
}
