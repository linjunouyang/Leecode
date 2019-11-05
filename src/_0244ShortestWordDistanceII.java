import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Three possible methods:
 * 1. hashmap for postions
 * 2. hashmap for positions + cache queried distance
 * 3. hashmap for every possible query -> time: O(n^2) space: O(1)
 *
 *  Lets take an example say n = 64 and shortest is called 20 times.
 *  Now if we look at the approximate total number of operations
 *  in our O(n^2) + O(1) solution we would get 64^2 + 20 = 4116.
 *  now if you observe others O(n)+O(n) solution their approximate total number of operations would be 64 + (20 * 64) = 1280.
 *
 *  -> Method 3 only makes sense if the shortest() will be called k times, where k is greater than n
 *
 *  -> Method 2 is the best:
 *  This way won't waste a single computation if calls with the same pairs are made,
 *  and won't compute a useless distance of a pair if it is never called.
 *
 */
public class _0244ShortestWordDistanceII {

    private Map<String, List<Integer>> hashmap;
    private Map<String, Integer> cache;

    /**
     * 1. HashMap
     *
     * getOrDefault(Object key, V defaultValue)
     *
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     *
     * @param words
     */
    public _0244ShortestWordDistanceII(String[] words) {
        hashmap = new HashMap<>();
        cache = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            List<Integer> list = hashmap.getOrDefault(words[i], new ArrayList<>());
            list.add(i);
            hashmap.put(words[i], list);
        }
    }

    /**
     *
     * Notice the ArrayList is in order, no need for nested for loop search.
     * Use two pointers instead.
     *
     * Cache the result in the event the same pairs are queried again (notice the two word order could be reversed)
     *
     * Bail out immediately once you find that the min diff is 1 since you can never do better than that
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param word1
     * @param word2
     * @return
     */
    public int shortest(String word1, String word2) {
        String key1 = word1 + ":" + word2;
        String key2 = word2 + ":" + word1;

        if (cache.containsKey(key1)) {
            return cache.get(key1);
        }

        if (cache.containsKey(key2)) {
            return cache.get(key2);
        }


        List<Integer> list1 = hashmap.get(word1);
        List<Integer> list2 = hashmap.get(word2);

        int l1 = 0;
        int l2 = 0;
        int min = Integer.MAX_VALUE;

        while (l1 < list1.size() && l2 < list2.size()) {
            min = Math.min(min, Math.abs(list1.get(l1) - list2.get(l2)));

            if (min == 1) {
                return min;
            }

            if (list1.get(l1) < list2.get(l2)) {
                l1++;
            } else {
                l2++;
            }
        }

        cache.put(key1, min);
        return min;

    }

}
