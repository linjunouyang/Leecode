import java.util.HashMap;
import java.util.Map;

public class _0170TwoSumII_DataStructureDesign {

    // number -> count
    private Map<Integer, Integer> map;
    // Solution 2 add another instance variable: private List<Integer> nums;

    public _0170TwoSumII_DataStructureDesign() {
        map = new HashMap<>();
        // nums = new ArrayList<>();
    }

    /**
     * Add
     *
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * @param number
     */
    public void add(int number) {
        // nums.add(number)
        if (!map.containsKey(number)) {
            map.put(number, 1);
        } else {
            // can't put this outside else branch
            map.put(number, map.get(number) + 1);
        }
    }

    /**
     * Find
     *
     * Time complexity: O(number of elements in the map)
     * Space complexity: O(number of elements in the map)
     *
     * @param value
     * @return
     */
    public boolean find(int value) {
        // We can use list for loop
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();

            if ((num == value - num && count > 1) || (num != value - num && map.containsKey(value - num))) {
                return true;
            }
        }

        return false;

    }
}
