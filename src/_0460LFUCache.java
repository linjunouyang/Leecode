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

    /**
     * 2. HashMap, Doubly Linked list
     *
     */
    class LFUCache2 {
        // key -> Node(key, value, counter)
        HashMap<Integer, Node> keyToNode;
        int minCounter;
        int size;
        HashMap<Integer, DLList> counterToList;

        public LFUCache2(int capacity) {
            keyToNode = new HashMap<>();
            minCounter = 0;
            size = capacity;
            counterToList = new HashMap<>();
        }

        // Time: O(1)
        public int get(int key) {
            Node node = keyToNode.get(key);
            if (node == null) {
                return -1;
            }
            update(node);
            return node.value;
        }

        // Time: O(1)
        public void put(int key, int value) {
            if (size == 0) {
                // don't forget
                return;
            }

            Node node = keyToNode.get(key);
            if (node == null) {
                node = new Node(key, value);

                if (keyToNode.size() + 1 > size) {
                    DLList minList = counterToList.get(minCounter);
                    Node removed = minList.removeFirst();
                    keyToNode.remove(removed.key);
                }

                keyToNode.put(key, node);
                minCounter = 1;

                DLList list = counterToList.getOrDefault(node.counter, new DLList());
                list.addLast(node);
                counterToList.put(node.counter, list);
            } else {
                node.value = value; // don't forget
                update(node);
            }
        }

        // Time: O(1)
        private void update(Node node) {
            DLList oldList = counterToList.get(node.counter);
            oldList.remove(node);
            if (node.counter == minCounter // don't forget
                    && oldList.size == 0) {
                minCounter++;
            }

            node.counter++;

            DLList nextList = counterToList.getOrDefault(node.counter, new DLList());
            nextList.addLast(node);
            counterToList.put(node.counter, nextList);
        }
    }

    class Node {
        int key;
        int value;
        int counter;
        Node prev;
        Node next;

        public Node(int k, int v) {
            key = k;
            value = v;
            counter = 1;
            prev = null;
            next = null;
        }
    }

    class DLList {
        Node dummy;
        int size;

        public DLList() {
            dummy = new Node(0, 0);
            dummy.next = dummy;
            dummy.prev = dummy;
        }

        public void addLast(Node node) {
            Node end = dummy.prev;

            end.next = node;
            node.prev = end;
            node.next = dummy;
            dummy.prev = node;

            size++;
        }

        // pre-cond: node is in the list
        public void remove(Node node) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            next.prev = prev;

            size--;
        }

        public Node removeFirst() {
            Node head = dummy.next;
            remove(head);
            return head;
        }
    }

}
