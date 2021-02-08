import java.util.TreeMap;
import java.util.Map;

public class _0731MyCalendarII {

    class MyCalendarTwo {
        /**
         * 1. Scanning Line
         *
         * Similar:
         * Calendar III
         * Range Addition
         * Meeting Rooms II
         *
         * Map: remove(Object key, Object value):
         * Removes the entry for the specified key only if it is currently mapped to the specified value.
         *
         * Why no ConcurrentModificationException?
         * This solution simply returns after removal.
         * Basically, ConcurrentModificationException is thrown when the iterator checks the size of the map in method "iterator.hasNext()"
         *
         * Time: O(n)
         * Space: O(n)
         */
        private TreeMap<Integer, Integer> map;

        public MyCalendarTwo() {
            map = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);
            int count = 0;
            for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
                count += entry.getValue();
                if(count > 2) {
                    map.put(start, map.get(start) - 1);
                    // map.remove(start, 0);
                    map.put(end, map.get(end) + 1);
                    // map.remove(end, 0);
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Segment Tree:
     * https://leetcode.com/problems/my-calendar-ii/discuss/109528/nlogd-Java-solution-using-segment-tree-with-lazy-propagation-(for-the-general-case-of-K-booking)
     *
     * Binary Search Tree:
     * https://leetcode.com/problems/my-calendar-ii/discuss/114882/Java-Binary-Search-Tree-method-clear-and-easy-to-undertand-beats-99
     */
}
