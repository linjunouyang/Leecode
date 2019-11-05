public class _0134GasStation {

    /**
     * 1. Greedy
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * https://leetcode.com/problems/gas-station/solution/
     *
     * 1. total > 0 -> a solution exists ?
     * [to-do]
     *
     * 2. Algorithm directly ensures that it's possible to go from Ns
     * to the station 0. But what about the last part of the round trip from the station 0 to the station Ns?
     *
     * 3. If car starts at A and can not reach B.
     * Any station in (A, B) can't reach B.(B is the first station that A can not reach.)
     *
     * Proof: In any station between A and B, let's say C.
     * C will have gas left in our tank, if we go from A to that station.
     * We can't reach B from A with some gas(may be 0) left in the tank in C, so we can't reach B from C with an empty tank.
     *
     * https://www.jiuzhang.com/solution/gas-station/#tag-other
     *
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int total_tank = 0;
        int curr_tank = 0;
        int start = 0;

        for (int i = 0; i < n; i++){
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];

            if (curr_tank < 0) {
                start = i + 1;
                curr_tank = 0;
            }
        }

        return total_tank >= 0 ? start : -1;
    }
}
