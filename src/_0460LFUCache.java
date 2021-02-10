import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * TODO: other solutions in the discussion
 */
public class _0460LFUCache {
    /**
     * 1. HashMap, LinkedHashSet
     *
     * https://leetcode.com/problems/lfu-cache/discuss/94547/Java-O(1)-Solution-Using-Two-HashMap-and-One-DoubleLinkedList
     *
     * fast (key, val) get, put -> HashMap
     * LFU + LRU -> minCount
     * multiple keys can have same count, for fast access, using hashset
     * but we also need to know the order of early/recent usage -> LinkedHashSet
     *
     * Time: (1), space: O(1)
     *
     */
    class LFUCache {
        int capacity;
        int minCount;
        HashMap<Integer, Integer> keyToVal;
        HashMap<Integer, Integer> keyToCount;
        HashMap<Integer, LinkedHashSet<Integer>> countToKeys;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            minCount = 0;
            keyToVal = new HashMap<>();
            keyToCount = new HashMap<>();
            countToKeys = new HashMap<>();
        }

        public int get(int key) {
            if (!keyToVal.containsKey(key)) {
                return -1;
            }
            update(key);
            return keyToVal.get(key);
        }

        public void put(int key, int value) {
            if (capacity <= 0) {
                return;
            }
            if (keyToVal.size() == capacity && !keyToVal.containsKey(key)) {
                deleteLeast();
            }

            keyToVal.put(key, value);
            update(key);
        }


        private void update(int key) {
            int oldCount = keyToCount.getOrDefault(key, 0);
            keyToCount.put(key, oldCount + 1);

            if (oldCount != 0) {
                countToKeys.compute(oldCount, (count, keys) -> {
                    keys.remove(key);
                    if (keys.size() == 0) {
                        if (oldCount == minCount) {
                            minCount++;
                        }
                        return null;
                    }
                    return keys;
                });

                // countToKeys.get(oldCount).remove(key);
                // if (countToKeys.get(oldCount).size() == 0) {
                //     countToKeys.remove(oldCount);
                //     if (oldCount == minCount) {
                //         minCount++;
                //     }
                // }
            } else {
                minCount = 1;
            }

            LinkedHashSet<Integer> newCountSet = countToKeys.getOrDefault(oldCount + 1, new LinkedHashSet<>());
            newCountSet.add(key);
            countToKeys.put(oldCount + 1, newCountSet);
        }

        private void deleteLeast() {
            int keyToDelete = countToKeys.get(minCount).iterator().next();
            keyToVal.remove(keyToDelete);
            keyToCount.remove(keyToDelete);
            countToKeys.get(minCount).remove(keyToDelete);
            if (countToKeys.get(minCount).size() == 0) {
                countToKeys.remove(minCount);
            }
            minCount = 1;
        }
    }
}
