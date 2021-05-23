import java.util.*;

public class _0857MinimumCostToHireKWorkers {
    /**
     * 1. Greedy
     *
     * At least one worker will be paid their minimum wage expectation.
     * If not, we could scale all payments down by some factor and still keep everyone earning more than their wage expectation.
     *
     * 员工={最低价格，能力}，K个员工的价格= KMAX(最低价格/能力) * SUM(能力)。
     * 降低总价方法：减少MAX(最低价格/能力)，减少SUM(能力)，
     * 因为只有两项，那我先让某一项是最低的，比如SUM(能力)。
     * 我让ratio从小到大排序，那么最开始ratio就是最小的，
     *
     * if we hire a person with ratio X at minimum wage, we can hire anyone with a ratio <= X while paying the person minimum wage.
     *
     * 对于后面的员工，如果他的quality比现在已有的K个人的quality还要高，那他一定对于降低总价没有用。
     * 如果他比k个人的MAX quality低，那他可能有用，(现在考虑现有的K个人里面拥有MAX(最低能力/价格)的这个人，对于所有MAX(最低价格/能力)等于他的序列，当前序列已经是总价最小的了（因为SUM能力已经是最小的了），所以他可以被弹出去，把新的这个人加进来。)
     * 这种贪心，值是两个值的乘积/和，先让一个值保持降序，这样我再弹出另一个值最差的元素的时候，对于所有该元素的值存在的序列，已经没有比我们看过的序列更优的解可。
     * 让某一个维度保持最优，然后扫描一遍另一个维度造成的影响，可以得到最优值。
     *
     * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group
     * -> wage[i] : wage[j] = quality[i] : quality[j]
     * -> wage[i] : quality[i] = wage[j] : quality[j]
     *
     * total wage : ratio * total quality
     * to minimize the total wage, we want a small ratio.
     * So we sort all workers with their expected ratio, and pick up K first worker.
     * Now we have a minimum possible ratio for K worker and we their total quality.
     *
     * As we pick up next worker with bigger ratio, we increase the ratio for whole group.
     * Meanwhile we remove a worker with highest quality so that we keep K workers in the group.
     * We calculate the current ratio * total quality = total wage for this group.
     *
     * We redo the process and we can find the minimum total wage.
     * Because workers are sorted by ratio of wage/quality.
     * [For every ratio, we find the minimum possible total quality of K workers.]
     *
     * Question: "it is possible that current worker has the highest quality, so you removed his quality in the last step,
     * which leads to the problem that you are "using his ratio without him".
     * Answer: It doesn't matter. The same group will be calculated earlier with smaller ratio.
     * And it doesn't obey my logic here: For a given ratio of wage/quality, find minimum total wage of K workers.
     *
     * Time: O(NlogN) + O(NlogK)
     * Space: O(n)
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        List<Integer> workers = new ArrayList<>();
        for (int i = 0; i < quality.length; ++i) {
            workers.add(i);
        }
        Collections.sort(workers,
                // notice comparator usage
                Comparator.comparingDouble(a -> wage[a] / (double) quality[a]));

        PriorityQueue<Integer> maxQ = new PriorityQueue<>(
                // notice Integer.compare is not a comparator
                (a, b) -> Integer.compare(quality[b], quality[a])
        );
        double totalWage = Double.MAX_VALUE;
        int qualitySum = 0;
        for (int worker : workers) {
            maxQ.add(worker);
            qualitySum += quality[worker];
            if (maxQ.size() > k) {
                qualitySum -= quality[maxQ.remove()];
            }
            if (maxQ.size() == k) {
                double ratio = wage[worker] / (double) quality[worker];
                totalWage = Math.min(totalWage, qualitySum * ratio);
            }
        }
        return totalWage;
    }
}
