import java.util.*;

/**
 * Better way to deal with binary search?
 */
public class _0981TimeBasedKey_ValueStore {
    class Data {
        String val;
        int time;
        public Data(String val, int time) {
            this.val = val;
            this.time = time;
        }
    }
    /**
     * 1. Map<String, List>
     *
     * List will be sorted because the timestamps for all TimeMap.set operations are strictly increasing.
     * Otherwise / OR, we can use a PriorityQueue or TreeMap (floorKey)
     */
    HashMap<String, List<Data>> map;
    public _0981TimeBasedKey_ValueStore() {
        map = new HashMap<String, List<Data>>();
    }

    /**
     * Time complexity:
     * string hash: O(str len)
     *
     * Space complexity: O(1)
     */
    public void set(String key, String value, int timestamp) {
        List<Data> list = map.getOrDefault(key, new ArrayList<>());
        list.add(new Data(value, timestamp));
        map.put(key, list);
    }

    /**
     * Time complexity:
     * string hash: O(str len)
     * binary search: O(log (list len))
     *
     * Space complexity: O(1)
     */
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        return binarySearch(map.get(key), timestamp);
    }

    private String binarySearch(List<Data> list, int time) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid).time == time) {
                return list.get(mid).val;
            } else if (list.get(mid).time < time) {
                if (low == mid) {
                    if (list.get(high).time <= time) {
                        return list.get(high).val;
                    } else {
                        return list.get(low).val;
                    }
                } else {
                    low = mid;
                }
            } else {
                high = mid - 1; //  high  low
            }
        }
        return list.get(low).time <= time ? list.get(low).val : "";
    }


}
