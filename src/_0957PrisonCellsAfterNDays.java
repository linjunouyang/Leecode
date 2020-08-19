import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * I didn't realize there might be a loop:
 * Cells only have 2 ^ 6 = 64 states, but N could be very big
 * -> cycle
 */
public class _0957PrisonCellsAfterNDays {
    /**
     * 1. HashMap (State string -> day)
     *
     * how to write a start -> end version off this
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> seen = new HashMap<>();
        while (N > 0) {
            int[] cells2 = new int[8];
            seen.put(Arrays.toString(cells), N--);
            for (int i = 1; i < 7; ++i)
                cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            cells = cells2;
            if (seen.containsKey(Arrays.toString(cells))) {
                N %= seen.get(Arrays.toString(cells)) - N;
            }
        }
        return cells;
    }


    /**
     * 2. HashSet detecting cycle ?
     *
     * https://leetcode.com/problems/prison-cells-after-n-days/discuss/266854/Java%3A-easy-to-understand
     * Lyn: 'cycle' is not cycle length, is the length from day 1 to we find a cycle
     * This solution assumes cycle starts with day1
     *
     * Time complexity:
     * Space complexity:
     *
     * @param cells
     * @param N
     * @return
     */
}
