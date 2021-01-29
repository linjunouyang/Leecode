import java.util.*;

public class _0716MaxStack {
    /**
     * 1. Two Stacks
     *
     * check isEmpty() before peek()
     */
    class MaxStack {
        Deque<Integer> stack;
        Deque<Integer> maxStack;

        // Time: O(1), space: O(1)
        public MaxStack() {
            stack = new ArrayDeque<>();
            maxStack = new ArrayDeque<>();
        }

        // Time: O(1), space: O(1)
        public void push(int x) {
            stack.push(x);
            int prevMax = maxStack.isEmpty() ? Integer.MIN_VALUE : maxStack.peek();
            maxStack.push(Math.max(x, prevMax));
        }

        // pre: at least one element
        // Time: O(1), space: O(1)
        public int pop() {
            int num = stack.pop();
            maxStack.pop();
            return num;
        }

        // pre: at least one element
        // Time: O(1), space: O(1)
        public int top() {
            return stack.peek();
        }

        // pre: at least one element
        // Time: O(1), space: O(1)
        public int peekMax() {
            return maxStack.peek();
        }

        // pre: at least one element
        // Time: O(n), space: O(n)
        public int popMax() {
            int max = maxStack.peek();
            Deque<Integer> temp = new ArrayDeque<>();
            while (stack.peek() != max) {
                temp.push(pop());
            }

            pop();

            int prevMax = maxStack.isEmpty() ? Integer.MIN_VALUE : maxStack.peek();
            while (!temp.isEmpty()) {
                push(temp.pop());
            }
            return max;
        }
    }

    /**
     * 2. Doubly Linked List + TreeMap
     *
     * Why list: efficient for adding
     * Why doubly linked: efficient popMax() since max can be in the middle of the list
     * Why TreeMap: maintain max
     *
     * Java TreeMap
     * guaranteed log(n) time cost for the containsKey, get, put and remove operations
     *
     * Java PriorityQueue
     * O(log(n)) time for the enqueing and dequeing methods (offer, poll, remove() and add);
     * linear time for the remove(Object) and contains(Object) methods;
     * and constant time for the retrieval methods (peek, element, and size).
     *
     */
    class Node {
        int val;
        Node prev;
        Node next;

        public Node(int v, Node p, Node n) {
            val = v;
            prev = p;
            next = n;
        }
    }

    class DLList {
        Node dummyHead;
        Node dummyTail;

        // Time: O(1), space: O(1)
        public DLList() {
            dummyHead = new Node(Integer.MIN_VALUE, null, null);
            dummyTail = new Node(Integer.MIN_VALUE, null, null);
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
        }

        // Time: O(1), space: O(1)
        public Node add(int x) {
            Node node = new Node(x, dummyTail.prev, dummyTail);
            node.prev.next = node;
            dummyTail.prev = node;
            return node;
        }

        // Time: O(1), space: O(1)
        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // Time: O(1), space: O(1)
        public Node removeLast() {
            Node last = dummyTail.prev;
            remove(last);
            return last;
        }

        // Time: O(1), space: O(1)
        public int last() {
            return dummyTail.prev.val;
        }
    }

    class MaxStack2 {
        DLList list;
        TreeMap<Integer, List<Node>> numToNodes;

        // Time: O(1), space: O(1)
        public MaxStack2() {
            list = new DLList();
            numToNodes = new TreeMap<>();
        }

        // Time: O(log unique keys), space: O(1)
        public void push(int x) {
            Node node = list.add(x);
            List<Node> nodes = numToNodes.getOrDefault(x, new ArrayList<>());
            nodes.add(node);
            numToNodes.put(x, nodes);
        }

        // Time: O(log unique keys) from TreeMap get and remove. O(n) from nodes.remove()
        // space: O(1)
        public int pop() {
            Node last = list.removeLast();
            List<Node> nodes = numToNodes.get(last.val);
            nodes.remove(last);
            if (nodes.size() == 0) {
                numToNodes.remove(last.val);
            }
            return last.val;
        }

        // Time: O(1), space: O(1)
        public int top() {
            return list.last();
        }

        // Time: O(log unique keys), space: O(1)
        public int peekMax() {
            return numToNodes.lastKey();
        }

        // Time: O(log unique keys), space: O(1)
        public int popMax() {
            int max = numToNodes.lastKey();
            List<Node> nodes = numToNodes.get(max);
            Node node = nodes.remove(nodes.size() - 1);
            if (nodes.size() == 0) {
                numToNodes.remove(max);
            }
            list.remove(node);
            return max;
        }
    }



}
