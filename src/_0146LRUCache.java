import java.util.HashMap;
import java.util.Map;

/**
 * The standard interview strategy for a candidate is never to rush into the implementation.
 * If you were interviewing me, I'd follow the article and list two solutions here :
 * 1. hashmap + linked list, O(1) 2. ordered dictionary = OrderedDict, O(1).
 * Do you think the solutions have the best time complexity? Yes. Cool, so which one would you like me to implement?
 *
 * At this moment I already gained scores for 1. data structures 2. algorithms 3. communication.
 * And I think you'd propose me to implement ordered dictionary only we were running out of time at this moment.
 * Otherwise, there is a place for your further questions like "ok, what if you'd need to implement LFU cache instead ?"
 *
 */
class Node {
    // can't set them to private
    int key;
    int val;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.val = value;
    }
}

class DoubleLinkedList {
    private Node dummyHead;
    private Node dummyTail;

    public DoubleLinkedList() {
        dummyHead = new Node(0, 0);
        dummyTail = new Node(0, 0);

        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    public void moveToFront(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        addToFront(node);
    }

    public void addToFront(Node node) {
        Node tmp = dummyHead.next;
        tmp.prev = node;

        dummyHead.next = node;

        node.next = tmp;
        node.prev = dummyHead;
    }

    public void removeTail() {
        Node newTail = dummyTail.prev.prev;
        newTail.next = dummyTail;
        dummyTail.prev = newTail;
    }

    public Node getTail() {
        return dummyTail.prev;
    }
}

public class _0146LRUCache {
    /**
     * The key in DLinkedNode would help us to remove the node itself from the cache
     * at the moment the node becomes stale and is removed along with the invocation of the function popTail.
     * We need to keep the key along with the node itself, because when the node is removed,
     * we are not blessed with the key from the caller of LRU cache.
     *
     * put value in node, so that we can get value & the degree of how recently used from nodes
     * one HashMap would be enough -> easier to maintain
     */
    private DoubleLinkedList list;
    private Map<Integer, Node> map;
    private int capacity;

    public _0146LRUCache(int capacity) {
        list = new DoubleLinkedList();
        map = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public int get(int key) {
        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        list.moveToFront(node);

        return node.val;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public void put(int key, int value) {
        Node node = map.get(key);

        if (node != null) {
            list.moveToFront(node);
            node.val = value;
        } else {
            // if we don't put it in else branch, node == null will also execute this block!!
            if (map.size() == capacity) {
                Node tail = list.getTail();
                list.removeTail();

                map.remove(tail.key); // we need to know the key for a given node, so store key in node
            }

            Node newNode = new Node(key, value);
            list.addToFront(newNode);

            map.put(key, newNode);
        }
    }
}
