import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * Summary:
 *
 * https://leetcode.com/problems/logger-rate-limiter/discuss/391558/Review-of-four-different-solutions%3A-HashMap-Two-Sets-Queue-with-Set-Radix-buckets-(Java-centric)
 *
 * Concurrency concern:
 * https://leetcode.com/problems/logger-rate-limiter/discuss/83298/Thread-Safe-Solution
 * double checked lock?
 * concurrentHashMap not enough?
 *
 */
public class _0359LoggerRateLimiter {
    /**
     * 1. HashMap
     *
     * Follow up on capacity management and thread-safe hardening
     *
     * Our hash table could grow up to number of unique messages.
     * This will end up using lot of memory. How do we resolve this issue ?
     *
     * We could have a linked-list associated with map ( just like LRU cache implementation ).
     * [1] We store a key-value in map, where value also has a pointer to linked-list node (this node will be added to the front of the list)
     * [2] Every time we update a map entry, we also remove the previous node and add a node to the front of linked list. O(1) operation.
     * [3] We can spawn another thread for periodically cleaning up map.
     * [4] This clean-up thread will look at back of list for stale entries and start removing those entries in the map, keeping memory usage low for map.
     * [5] Also having every single hash table entry protected with a mutex, instead of entire hash-table with a single mutex , helps to make things more concurrent.
     *
     *
     *
     */
    private Map<String, Integer> map;

    // Time complexity: O(1) Space complexity: O(1)
    public _0359LoggerRateLimiter() {
        map = new HashMap<>();
    }

    // Time complexity: O(1) or O(message len)
    // Space complexty: O(1)
    public boolean shouldPrintMessage(int timestamp, String message) {
        Integer lastPrintTime = map.get(message);
        if (lastPrintTime == null || timestamp - lastPrintTime >= 10) {
            // Concurrency concern:
            // (m1, 1), (m1, 1)
            // Logger is called with (m1, 1) twice
            // The second call will possibly print the message if the 1st call hasn't had a chance to create the HashMap entry for m1 yet.
            // Fix:
            // synchronized(lock)
            map.put(message, timestamp);
            return true;
        }
        return false;
    }

    /**
     * 2. Queue + Set
     *
     * Can be visualized as a sliding window of 10s length
     *
     * Queue: last 10s (now - 10, now] printed msg;
     * Set: last 10s msg content. This is for checking duplicate in O(1)
     *
     * advantage of this solution:
     * memory efficient (not change in terms of number of unique messages increasing)
     *
     * Another important note is that if the messages are not chronologically ordered
     * then we would have to iterate through the entire queue to remove the expired messages, rather than having early stopping.
     * Or one could use some sorted queue such as Priority Queue to keep the messages.
     *
     * Time Complexity: O(N)
     * where N is the size of the queue. In the worst case, all the messages in the queue become obsolete. As a result, we need clean them up.
     *
     * but it's amortized O(1)
     *
     * Space Complexity: O(N)
     * where N is the size of the queue. We keep the incoming messages in both the queue and set.
     * The upper bound of the required space would be 2N2N, if we have no duplicate at all.
     */
    class Log {
        String message;
        int timestamp;

        public Log(String message, int timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    private LinkedList<Log> msgQueue;
    private HashSet<String> msgSet;

    /** Initialize your data structure here. */
//    public _0359LoggerRateLimiter() {
//        msgQueue = new LinkedList<log>();
//        msgSet = new HashSet<String>();
//    }

    /**
     * Returns true if the message should be printed in the given timestamp, otherwise returns false.
     *
     * (1, "foo") t, (2, "bar") t, (3, "foo") f, (8, "bar") f, (10, "foo") f, (11, "foo)
     *
     * Queue: (1, "foo") (deleted at 11), (2, "bar"), (11, "foo")
     * Set: "foo" (deleted at 11), "bar", "foo"
     *
     * Another important note is that if the messages are not chronologically ordered then we would have to
     * iterate through the entire queue to remove the expired messages,
     * rather than having early stopping. Or one could use some sorted queue such as Priority Queue to keep the messages.
     *
     */
    public boolean shouldPrintMessage2(int timestamp, String message) {

        // clean up.
        while (msgQueue.size() > 0) {
            Log head = msgQueue.getFirst();
            if (timestamp - head.timestamp >= 10) {
                msgQueue.removeFirst();
                msgSet.remove(head.message);
            } else {
                break;
            }
        }

        if (!msgSet.contains(message)) {
            Log log = new Log(message, timestamp);
            msgQueue.addLast(log);
            msgSet.add(message);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 3. Two HashMap
     *
     * mapNew keeps the messages of [timeNew, timeNew +10);
     * mapOld keeps messages at most 20 seconds before.
     *
     * Time complexity: O(1)
     * Space complexity: O(max number of unique messages in 20s)
     *
     */

    /**
     * 4. Radix Sort and Buckets
     */


}
