public class _0055JumpGame {


    /**
     * 1. Backtracking
     *
     * Time complexity: O(2 ^ n)
     * Space complexity: O(n)
     *
     * Detailed Analysis:
     * https://leetcode.com/problems/jump-game/solution/
     *
     */
    public boolean canJump(int[] nums) {
        return canJumpFromPosition(0, nums);
    }


    private boolean canJumpFromPosition(int position, int[] nums) {
        if (position == nums.length - 1) {
            return true;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);

        for (int nextPosition = furthestJump; nextPosition > position; nextPosition--) {
            if (canJumpFromPosition(nextPosition, nums)){
                return true;
            }
        }

        return false;
    }

    /**
     * 2. Dynamic Programming Top-down
     *
     * enum: enumerations
     * - for representing a group of named constants
     * - Enums are used when we know all possible values at compile time
     * - Can add variables, methods and constructors to it.
     *
     * Top-down Dynamic Programming can be thought of as optimized backtracking.
     * It relies on the observation that once we determine that a certain index is good / bad, this result will never change.
     * This means that we can store the result and not need to recompute it every time.
     *
     * Time complexity: O(n ^ 2)
     * For every element in the array, say i, we are looking at the next nums[i] elements to its right aiming to find a GOOD index.
     * nums[i] can be at most n, where n is the length of array nums.
     *
     * Space complexity: O(n)
     * recursion + memo table
     *
     */
    enum Index {
        GOOD, BAD, UNKNOWN
    }

    Index[] memo;

    public boolean canJump1(int[] nums) {
        memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;
        return canJumpFromPosition1(0, nums);
    }

    private boolean canJumpFromPosition1(int position, int[] nums) {
        if (memo[position] != Index.UNKNOWN) {
            return memo[position] == Index.GOOD ? true : false;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);

        for (int nextPosition = furthestJump; nextPosition >= position + 1; nextPosition--) {
            if (canJumpFromPosition1(nextPosition, nums)) {
                memo[position] = Index.GOOD;
                return true;
            }
        }

        memo[position] = Index.BAD;
        return false;
    }

    /**
     * 3. Dynamic Programming Bottom-up:
     *
     * Top-down to bottom-up conversion is done by eliminating recursion.
     * The recursion is usually eliminated by reversing the order of the steps from the top-down approach.
     * ->
     * We only ever jump to the right. It means if we start from the right of the array,
     * every time we will query a position to our right, that position has already be determined as being GOOD or BAD.
     * This means we don't need to recurse anymore, as we will always hit the memo table.
     *
     * Benefits:
     * 1. better performance: no longer have the method stack overhead
     * 2. benefit from caching
     * 3. possibilities for future optimization
     *
     * Time complexity: O(n ^ 2)
     * For every element in the array, say i,
     * we are looking at the next nums[i] elements to its right aiming to find a GOOD index.
     * nums[i] can be at most n, where nn is the length of array nums.
     *
     * Space complexity: O(n)
     * usage of the memo table.
     *
     * Runtime: 186 ms, faster than 27.25% of Java online submissions for Jump Game.
     * Memory Usage: 41.7 MB, less than 26.50% of Java online submissions for Jump Game.
     *
     * @param nums
     * @return
     */
    public boolean canJump3(int[] nums) {
        Index[] memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (memo[j] == Index.GOOD) {
                    memo[i] = Index.GOOD;
                    break;
                }
            }
        }

        return memo[0] == Index.GOOD;
    }

    /**
     * 4. Greedy
     *
     * From a given position, when we try to see if we can jump to a GOOD position,
     * we only ever use one - the first one (see the break statement).
     * In other words, the left-most one.
     * If we keep track of this left-most GOOD position as a separate variable,
     * we can avoid searching for it in the array.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public boolean canJump4(int[] nums) {
        int lastPos = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }

        return lastPos == 0;
    }

    /**
     * 4.1 Greedy start to end
     *
     * previous version:
     * loops runs for n times no matter what.
     * This might not be necessary if we compute from start to end
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public boolean canJump41(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < i) {
                return false;
            }
            if (max >= nums.length - 1) {
                return true;
            }
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }



}
