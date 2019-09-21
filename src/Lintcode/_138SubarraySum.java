package Lintcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _138SubarraySum {
    public List<Integer> subarraySum(int[] nums) {
        List<Integer> res = new ArrayList<>();

        if (nums == null && nums.length == 0) {
            return res;
        }


        int sum = 0;

        Map<Integer, Integer> map = new HashMap<>();
        // in case of [0]
        map.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (map.containsKey(sum)) {
                res.add(map.get(sum) + 1);
                res.add(i);
                return res;
            }

            map.put(sum, i);
        }

        return res;
    }
}
