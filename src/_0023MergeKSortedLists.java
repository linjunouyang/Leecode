import java.util.*;

/**
 * Haven't checked out the discussion part
 */
public class _0023MergeKSortedLists {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x;}
    }

    /**
     * 1. Brute Force: Sorting
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */
    public ListNode mergeKLists1(ListNode[] lists) {
        List<Integer> vals = new ArrayList<>();

        for (ListNode list : lists) {
            while (list != null) {
                vals.add(list.val);
                list = list.next;
            }
        }

        Collections.sort(vals);

        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int val : vals) {
            ListNode node = new ListNode(val);
            p.next = node;
            p = p.next;
        }
        return head.next;
    }

    /**
     * 2. Compare one column by one column
     *
     * n : number of total nodes.
     * k : number of lists
     *
     * Time complexity: O(nk)
     * Space complexity: O(1)
     *
     * Notice the pattern:
     * Find minimum O(k), add it to the list, and take into next element into consideration O(1)
     * ->
     * Priority Queue
     * Find minimum O(logk), add it to the list, and take into next element into consideration O(logk)
     */
    public ListNode mergeKList2(ListNode[] lists) {
        // Dummy Node
        ListNode head = new ListNode(0);
        ListNode p = head;

        while (true) {
            boolean isDone = true;
            int min = Integer.MAX_VALUE;
            int minIndex = 0;

            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    if (min > lists[i].val) {
                        min = lists[i].val;
                        minIndex = i;
                    }
                    isDone = false;
                }
            }

            if (isDone) {
                break;
            }

            p.next = lists[minIndex];
            lists[minIndex] = lists[minIndex].next;
            p = p.next;
        }

        return head.next;
    }

    /**
     * 3.0 Priority Queue (min heap)
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */
    public ListNode mergeKLists30(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (ListNode list : lists) {
            while (list != null) {
                minHeap.offer(list.val);
                list = list.next;
            }
        }

        // takes care of [[]]
        if (minHeap.isEmpty()) {
            return null;
        }

        ListNode start = new ListNode(minHeap.poll());
        ListNode p = start;
        while (!minHeap.isEmpty()) {
            p.next = new ListNode(minHeap.poll());
            p = p.next;
        }

        return start;
    }

    /**
     * 3.1 Priority Queue
     *
     * lists = null
     * lists = []
     * lists = [[]]
     *
     * Time complexity: O(nlogk)
     * Space complexity: O(k)
     */
    public ListNode mergeKList31(ListNode[] lists) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.val));

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            prev.next = node;
            prev = node;
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }


    /**
     * 4. Merge list one by one
     *
     * Time complexity: if equal length, O(nk)
     * Space complexity: O(1)
     */
    public ListNode mergeKList4(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        if (lists.length == 1) {
            return lists[0];
        }

        ListNode merged = mergeTwoLists(lists[0], lists[1]);

        for (int i = 2; i < lists.length; i++) {
            merged = mergeTwoLists(merged, lists[i]);
        }

        return merged;
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Dummy head
        ListNode dummyHead = new ListNode(0);
        ListNode p = dummyHead;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null) {
            p.next = l1;
        }

        if (l2 != null) {
            p.next = l2;
        }

        return dummyHead.next;
    }

    /**
     * 5. Merge two lists + divide and conquer
     *
     * https://leetcode.wang/leetCode-23-Merge-k-Sorted-Lists.html
     *
     * Time complexity: Suppose equal length n
     * O(nlogk)
     * Space complexity: O(1)
     */
    public ListNode mergeKList5(ListNode[] lists) {
        int interval = 1;

        while (interval < lists.length) {
            for (int i = 0; i + interval < lists.length; i = i + interval * 2) {
                lists[i] = mergeTwoLists(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }

        return lists[0];
    }

}
