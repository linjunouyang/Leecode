import java.util.HashMap;

/**
 * Two main elements of HashMap
 * 1. Hash function design: distribute key evenly
 * 2. collision handling: chaining or open addressing
 *
 * Things to ask during interview:
 * For simplicity, are the keys integers only?
 * For collision resolution, can we use chaining?
 * Do we have to worry about load factors?
 * Can we assume inputs are valid or do we have to validate them?
 * Can we assume this fits memory?
 *
 *
 */
public class _0706DesignHashMap {
    class ListNode {
        int key, val;
        ListNode next;

        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * 1. Modulo + Array
     *
     * Things to improve:
     * 1. self-adaption feature: enlarge the base of modulo to extend the scope of keys when it starts to get a bit saturated,
     * in order to re-balance the distribution of values, and eventually reducing the potential collisions.
     *
     * rehash and load factor
     * https://leetcode.com/problems/design-hashmap/discuss/270685/Java-Separate-Chaining-with-rehashing
     *
     */

    // All keys and values will be in the range of [0, 1000000].
    // It's suggested to use prime number as modulo.
    private ListNode[] nodes = new ListNode[10001];

    /**
     * Time complexity: O(len of collision list)
     * Space complexity: O(1)
     */
    public void put(int key, int value) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int i = key % nodes.length;
        if (nodes[i] == null) {
            nodes[i] = new ListNode(-1, -1);
        }
        ListNode prev = find(nodes[i], key);
        if (prev.next == null) {
            prev.next = new ListNode(key, value);
        } else {
            prev.next.val = value;
        }
    }

    /**
     * Time complexity: O(len of collision list)
     * Space complexity: O(1)
     */
    public int get(int key) {
        int i = key % nodes.length;
        if (nodes[i] == null) {
            return -1;
        }
        ListNode node = find(nodes[i], key);
        return node.next == null ? -1 : node.next.val;
    }

    public void remove(int key) {
        int i = key % nodes.length;
        if (nodes[i] == null) {
            return;
        }
        ListNode prev = find(nodes[i], key);
        if (prev.next == null) {
            return;
        }
        prev.next = prev.next.next;
    }

    /**
     * Find the previous node of the node which has the given key
     *
     * Time complexity: O(len of collision list)
     * Space complexity: O(1)
     */
    private ListNode find(ListNode bucket, int key) {
        ListNode prev = null;
        ListNode node = bucket;
        while (node != null && node.key != key) {
            prev = node;
            node = node.next;
        }
        return prev;
    }
}
