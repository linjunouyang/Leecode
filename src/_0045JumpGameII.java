public class _0045JumpGameII {

    /**
     * 1. Greedy (BFS)
     *
     * [..., curEnd]: current jump range
     * curFarthest: farthest point that all points in [..., curEnd] can reach
     *
     * i == curEnd : visited all the items on the current level
     * jumps++     : increment the level
     * curEnd = curFarthest : getting the level (queue) size for the next level
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 99.97% of Java online submissions for Jump Game II.
     * Memory Usage: 38.7 MB, less than 100.00% of Java online submissions for Jump Game II.
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int last_jump_max = 0;
        int curr_jump_max = 0;
        int steps = 0;

        for (int i = 0; i < nums.length - 1; i++) {
//            If the problem definition is changed: you can't always reach the last index
//            if (i > curFarthest) {
//                return -1;
//            }

            curr_jump_max = Math.max(curr_jump_max, nums[i] + i);

            // early exit
            if (curr_jump_max >= nums.length - 1) {
                steps++;
                break;
            }

            if (i == last_jump_max) {
                steps++;
                last_jump_max = curr_jump_max;
            }
        }

        return steps;
    }

    /**
     * 1.1 Greedy, BFS
     *
     * Just a different way to write
     *
     * @param nums
     * @return
     */
    public int jump11(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        int curMax = 0; // to mark the last element in a level
        int level = 0;
        int i = 0;

        while (i <= curMax) {
            int furthest = curMax; // to mark the last element in the next level
            for (; i <= curMax; i++) {
                furthest = Math.max(furthest, nums[i] + i);
                if (furthest >= nums.length - 1) return level + 1;
            }
            level++;
            curMax = furthest;
        }

        return -1; // if i > curMax, i can't move forward anymore (the last element in the array can't be reached)
    }

    /**
     * 2. Dynamic Programming:
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     * @param nums
     * @return
     */
    public int jump2(int[] nums) {
        // state
        int[] steps = new int[nums.length];

        // initialization
        steps[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            steps[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // [0, 0, 1, 0], state: [0, MAX, MAX, MAX]
                // i = 2 is unreachable -> steps[2] = Integer.MAX_VALUE;
                if (steps[j] != Integer.MAX_VALUE && j + nums[j] >= i) {
                    steps[i] = Math.min(steps[i], steps[j] + 1);
                }
            }
        }

        return steps[nums.length - 1];

    }
}
