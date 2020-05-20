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
     *
     * Runtime: 7 ms, faster than 47.48% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 43.7 MB, less than 26.24% of Java online submissions for Merge k Sorted Lists.
     *
     * @param lists
     * @return
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
     * Space commplexity: O(1)
     *
     * Runtime: 338 ms, faster than 5.10% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 57.3 MB, less than 5.47% of Java online submissions for Merge k Sorted Lists.
     *
     * Notice the pattern:
     * Find minimum O(k), add it to the list, and take into next element into consideration O(1)
     * ->
     * Priority Queue
     * Find minimum O(logk), add it to the list, and take into next element into consideration O(logk)
     *
     * @param lists
     * @return
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
     *
     * @param lists
     * @return
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
     *
     * Runtime: 5 ms, faster than 75.88% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 41.2 MB, less than 43.17% of Java online submissions for Merge k Sorted Lists.
     *
     */
    public ListNode mergeKList31(ListNode[] lists) {
        Comparator<ListNode> cmp = new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        };

        // Dummy head
        ListNode head = new ListNode(0);
        ListNode p = head;

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(cmp);
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }

        while (!minHeap.isEmpty()) {
            p.next = minHeap.poll();
            p = p.next;
            if (p.next != null) {
                minHeap.offer(p.next);
            }
        }

        return head.next;
    }


    /**
     * 4. Merge list one by one
     *
     * Time complexity: if equal length, O(nk)
     * Space complexity: O(1)
     *
     *
     * Runtime: 114 ms, faster than 16.33% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 56.8 MB, less than 5.47% of Java online submissions for Merge k Sorted Lists.
     *
     *
     * @param lists
     * @return
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
     *
     *
     * @param lists
     * @return
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
