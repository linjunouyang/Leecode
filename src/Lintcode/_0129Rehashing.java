package Lintcode;

class ListNode {
    int val;
    ListNode next;
    ListNode (int x) {
        val = x;
        next = null;
    }
}

public class _0129Rehashing {

    /**
     * Time complexity: O(number of elements in the old hash table)
     * Space complexity: O(1)
     *
     * @param hashTable
     * @return
     */
    public ListNode[] rehashing(ListNode[] hashTable) {
        if (hashTable == null || hashTable.length == 0) {
            return new ListNode[0];
        }

        int newCapcity = 2 * hashTable.length;
        ListNode[] newTable = new ListNode[newCapcity];

        for (int i = 0; i < hashTable.length; i++) {
            ListNode p = hashTable[i];
            while (p != null) {
                int hash = (p.val % newTable.length + newTable.length) % newTable.length;

                if (newTable[hash] == null) {
                    newTable[hash] = new ListNode(p.val);
                } else {
                    // insert at front or at end
                    ListNode old = newTable[hash];
                    newTable[hash] = new ListNode(p.val);
                    newTable[hash].next = old;
                    // ListNode q = newTable[hash];
                    // while (q.next != null) {
                    //     q = q.next;
                    // }
                    // q.next = new ListNode(p.val);
                }

                p = p.next;
            }
        }

        return newTable;
    }

}
