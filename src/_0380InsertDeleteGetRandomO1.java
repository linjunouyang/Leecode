import java.util.*;

/**
 * Remember that:
 *
 * it's perfectly reasonable to assume hash table add/delete operations are O(1)
 * in real world, hash table could degrade to O(n) due to collision
 *
 */
public class _0380InsertDeleteGetRandomO1 {
    /**
     * 1. HashMap + ArrayList
     *
     * The List is used to store numbers and serve the getRandom() method.
     * The Map contains the mapping between the value and its index in the ArrayList.
     * The Map helps to check whether a value is already inserted or not.
     *
     * The main trick is when you remove a value.
     * ArrayList's remove method is O(n) if you remove from random location.
     * To overcome that, we swap the values between (randomIndex, lastIndex) and always remove the entry from the end of the list.
     * After the swap, you need to update the new index of the swapped value (which was previously the end) in the map.
     *
     * Time complexity.
     * GetRandom is always O(1).
     *
     * Insert and Delete both have O(1) average time complexity,
     * and O(N) in the worst-case when the operation exceeds the capacity of currently allocated array/hashmap
     * and invokes space reallocation.
     *
     * Space complexity: O(N), to store N elements.
     */
    private Map<Integer, Integer> dict; // int -> index
    private List<Integer> list; // index -> int
    private Random rand;

    /** Initialize your data structure here. */
    public _0380InsertDeleteGetRandomO1() {
        dict = new HashMap();
        list = new ArrayList();
        rand = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (dict.containsKey(val)) {
            return false;
        }
        dict.put(val, list.size());
        list.add(list.size(), val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (! dict.containsKey(val)) {
            return false;
        }

        // move the last element to the place idx of the element to delete
        int lastElement = list.get(list.size() - 1);
        int idx = dict.get(val);
        list.set(idx, lastElement);
        dict.put(lastElement, idx);

        // delete the last element
        list.remove(list.size() - 1);
        dict.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
