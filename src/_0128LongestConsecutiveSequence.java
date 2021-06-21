import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * TO-DO:
 * Other solutions? Union Find?
 */
public class _0128LongestConsecutiveSequence {

    /**
     * 1. Brute Force
     *
     * Consider each number, attempting to count as high as possible from that number
     *
     * Time complexity: O(n^3)
     * Space complexity: O(1)
     */
    public int longestConsecutive1(int[] nums) {
        int maxLen = 0;

        for (int num : nums) {
            int currentNum = num;
            int curLen = 1;

            while (arrayContains(nums, currentNum + 1)) {
                currentNum += 1;
                curLen += 1;
            }

            maxLen = Math.max(maxLen, curLen);
        }

        return maxLen;
    }

    private boolean arrayContains(int[] arr, int num) {
        for (int number : arr) {
            if (number == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * 2. Sorting
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     */
    public int longestConsecutive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int maxLen = 1;
        int curLen = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                curLen++;
                maxLen = Math.max(maxLen, curLen);
            } else if (nums[i] > nums[i - 1] + 1) {
                curLen = 1;
            }
        }

        return maxLen;
    }

    /**
     * 3. HashSet
     *
     * Only start counting sequence length from sequence left boundary
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }

        int maxLen = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int curNum = num;
                int len = 1;

                while (set.contains(curNum + 1)) {
                    curNum += 1;
                    len += 1;
                }

                maxLen = Math.max(maxLen, len);
            }
        }

        return maxLen;
    }


    /**
     * 3.1 HashSet
     *
     * We have a set of nodes in a graph (nums[]). We want to find the largest connected component.
     * components are connected if they are a consecutive sequence.
     * So starting at each point, go -1 and +1 to find the largest connected component.
     * Use a set to keep track of unvisited.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int longestConsecutive31(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();

        for (int num : nums) {
            set.add(num);
        }

        int max = 0;

        for (int num : nums) {
            if (set.remove(num)) {
                int val = num;
                int sum = 1;

                while (set.remove(val - 1)) {
                    val--;
                }
                sum += num - val;

                val = num;
                while (set.remove(val + 1)) {
                    val++;
                }
                sum += val - num;

                max = Math.max(max, sum);
            }
        }

        return max;
    }

    /**
     * 4. Union Find
     */
    public int longestConsecutive4(int[] nums) {
        int n = nums.length;
        UnionFind uf = new UnionFind(n);
        HashMap<Integer, Integer> numToIdx = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (numToIdx.containsKey(nums[i])) {
                // avoid duplicates
                continue;
            }
            numToIdx.put(nums[i], i);
            int num = nums[i];

            if (numToIdx.containsKey(num - 1)) {
                uf.union(i, numToIdx.get(num - 1));
            }

            if (numToIdx.containsKey(num + 1)) {
                uf.union(i, numToIdx.get(num + 1));
            }
        }

        return uf.maxComponent();
    }

    class UnionFind {
        int size;
        int[] parents;
        int[] ranks;

        public UnionFind(int n) {
            size = n;
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
            ranks = new int[n];
        }

        public int find(int x) {
            if (parents[x] != x) {
                parents[x] = find(parents[x]);
            }
            return parents[x];
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot == yRoot) {
                return;
            }

            if (ranks[xRoot] < ranks[yRoot]) {
                parents[xRoot] = yRoot;
            } else {
                parents[yRoot] = xRoot;
            }
        }

        public int maxComponent() {
            int[] sizes = new int[size];
            int maxSize = 0;
            for (int i = 0; i < size; i++) {
                sizes[find(i)]++;
                maxSize = Math.max(maxSize, sizes[find(i)]);
            }
            return maxSize;
        }


    }

}
