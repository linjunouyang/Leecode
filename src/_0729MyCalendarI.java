import java.util.TreeMap;

public class _0729MyCalendarI {
    /**
     * No conflicts:
     * one of them starts after the other one ends
     * e1 <= s2 OR e2 <= s1
     *
     * has conflicts
     * e1 > s2 && e2 > s1
     *
     * floorKey(K key)
     * Returns the greatest key less than or equal to the given key, or null if there is no such key.
     *
     * lowerKey(K key)
     * Returns the greatest key strictly less than the given key, or null if there is no such key.
     */

    /**
     * 1. Brute Force:
     *
     * When booking a new event [start, end),
     * check if EVERY current event conflicts with the new event.
     * If none of them do, we can book the event.
     *
     * Time: O(nlogn)
     * Space: O(n)
     *
     */
    class MyCalendar {

        private TreeMap<Integer, Integer> startToEnd;

        public MyCalendar() {
            startToEnd = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            Integer prevStart = startToEnd.floorKey(start),
                    nextStart = startToEnd.ceilingKey(start);
            if ((prevStart == null || startToEnd.get(prevStart) <= start) &&
                    (nextStart == null || end <= nextStart)) {
                startToEnd.put(start, end);
                return true;
            }
            return false;

            // for events that start at or after 'end', no conflicts for sure
//            Integer low = startToEnd.lowerKey(end);
//
//            if (low == null || (low < start && startToEnd.get(low) <= start)) {
                  // actually we can omit low < start here
//                startToEnd.put(start, end);
//                return true;
//            }
//            return false;
        }
    }

    // [1, 5) [low 4, 5)
}
