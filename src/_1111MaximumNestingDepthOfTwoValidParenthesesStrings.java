public class _1111MaximumNestingDepthOfTwoValidParenthesesStrings {
    /**
     * 1. Split by half, count the number of level
     *
     * Since we can only split them into two groups, the minimum depth will always be ceil(maxDepth/2)
     * and we achieve it by putting one half in each group.
     * Trying to put less than half in one of the two groups, will leave the other group with more than one half.
     *
     * Any other stack, we can handle using the same strategy, making sure that no other parenthesis will increase the max depth.
     *
     * Interesting side note: Any stack in the sequence that is of max depth less or equal to ceil(globalMaxDepth/2) (of the entire sequence)
     * we can assign any which way we want (as long as we maintain the VPS property),
     * since they cannot increase the max depth of the resulting split.
     * E.g.: when looking at "((()))()()()()", we just gotta take good care of the first "((()))",
     * then we can assign each following parenthesis pair "()" to A or B any which way we want, since their max depth is less or equal to ceil(3/2) = 2.
     *
     * Solution
     * So what we need is a strategy to cut any stack in half while making sure that the resulting half-stacks are balanced VPS.
     * There is many ways of doing it, but one of the easiest (and seemingly a very common) approach is by going for an odd/even strategy:
     *
     * 1。Get the depth at every index of the string
     * 2。Put all odd-depth parentheses in one group, and all even-depth in the other
     *
     * NOTE: Using this solution, parentheses at the same depth are always in the same group,
     * so you can ensure that the resulting groups are balanced VPS.
     *
     * ___
     * 1.         ( ( ( ( ( ) ) ) ) )
     *    level   1 2 3 4 5 5 4 3 2 1
     *    A takes level 1,3,5 and B takes level 2,4 ==> A : ( ( ( ) ) ) , B : ( ( ) )
     * 2.         ( ( ) ) ( ( ( ) ) )
     *    level   1 2 2 1 1 2 3 3 2 1
     *    A takes level 1,3 and B takes level 2 ==> A : ( ) ( ( ) ) , B : ( ) ( )
     *
     * when to increase/decrese level ?
     * 1. meet a new '(' level up
     * 2. meet a new ')' level down
     *
     * if you can understand the level conception, coding is quite easy.
     * __
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     *
     * @param seq
     * @return
     */
    public int[] maxDepthAfterSplit(String seq) {
        int[] res = new int[seq.length()];
        int level = 0;

        for (int i = 0; i < seq.length(); i++) {
            boolean isOpen = seq.charAt(i) == '(';
            if (isOpen) {
                level++;
                res[i] = level % 2;  // 1, 2, 3, 4, 5
            } else {
                res[i] = level % 2;  //             5 (make sure correspondence)
                level--;
            }
        }

        return res;
    }


    /**
     * Other solutions:
     *
     * Solution 0 and Solution 1, which seems not clear to me
     * https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/discuss/328841/JavaC%2B%2BPython-O(1)-Extra-Space-Except-Output
     */


}
