import java.util.*;

/**
 * Prequel version:
 *
 * team of any number engineers:
 * we sort the engineers in descending order of efficiencies.
 * for each eff as min eff, choose all engineers with higher effs to maximize speed sum
 *
 */
public class _1383MaximumPerformanceOfATeam {
    /**
     * 1. Greedy
     *
     * max perf = min eff * sum of speeds (two variables)
     * -> Fix one value, and discuss the other
     *
     * 1. Treat each candidate as the one with min eff,
     * 2. select others: eff >= min eff, ranked by speed
     *
     * This strategy has pruning involved:
     * Bc min eff is fixed -> only consider team size of k
     *
     * Proof:
     * https://leetcode.com/problems/maximum-performance-of-a-team/discuss/540535/Formal-proof-of-correctness
     * https://leetcode.com/problems/maximum-performance-of-a-team/discuss/540663/A-PROOF-of-the-Priority-Queue-Solution-(will-help-you-understand)
     *
     * Time: O(nlogn)
     * list sorting: O(nlogn)
     * PQ operations: O(nlogk)
     *
     * Space: O(n)
     * list: O(n)
     * pq: O(k)
     */
    class Engineer {
        int speed;
        int efficiency;

        public Engineer(int speed, int efficiency) {
            this.speed = speed;
            this.efficiency = efficiency;
        }
    }

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        List<Engineer> engineers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Engineer engineer = new Engineer(speed[i], efficiency[i]);
            engineers.add(engineer);
        }
        Collections.sort(engineers, (a, b) -> b.efficiency - a.efficiency);

        PriorityQueue<Engineer> minSpeedHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.speed));
        long maxPerf = Long.MIN_VALUE;
        long speedSum = 0;
        int mod = (int)(1e9 + 7);

        // as we walk down the list, min eff decreases,
        // to make bigger maxPerf, get rid of smaller speed
        for (Engineer engineer : engineers) {
            int minEff = engineer.efficiency;
            if (minSpeedHeap.size() == k) {
                Engineer slowest = minSpeedHeap.poll();
                speedSum -= slowest.speed;
            }
            minSpeedHeap.add(engineer);
            speedSum += engineer.speed;
            long perf = speedSum * minEff;
            maxPerf = Math.max(maxPerf, perf);
        }

        return (int)(maxPerf % mod);
    }
}
