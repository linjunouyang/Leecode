import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class _0406QueueReconstructionByHeight {
    /**
     * 1. Greedy
     *
     * k is only determined by people with equal or larger height,
     * so makes sense to insert in non-increasing order of height.
     * Because when we insert some person with height h and count k,
     * we know that we have found its correct position relative to people with equal and larger height.
     * And when we later insert other people with equal or smaller height,
     * we know that it will not affect this relative position. So the answer is right after we insert all people.
     *
     * pick up the tallest guy first
     * when insert the next tall guy, just need to insert him into kth position
     * repeat until all people are inserted into list
     *
     * More explanation on the thinking process:
     * https://leetcode.com/problems/queue-reconstruction-by-height/discuss/89359/Explanation-of-the-neat-Sort%2BInsert-solution
     *
     * Why is this greedy?
     * 1) for same height, prioritize smallest k
     * 2) shorter is invisible to higher
     *
     * Time complexity: O(n ^ 2)
     * Sorting: O(nlgn)
     * ArrayList add(index, element): O(n)
     *
     * Space complexity: O(n)
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
            Arrays.sort(people, new Comparator<int[]>(){
                @Override
                /**
                 * Non-increasing height
                 * If same height, put small k first
                 */
                public int compare(int[] o1, int[] o2){
                    return (o1[0] != o2[0]) ? (-o1[0] + o2[0]) : (o1[1] - o2[1]);
                }
            });

            // Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);

            List<int[]> res = new LinkedList<>();
            for(int[] cur : people){
                res.add(cur[1],cur);
            }
            return res.toArray(new int[people.length][]);
        }
}
