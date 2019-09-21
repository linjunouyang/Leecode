package LinkedList;

/**
 *
 * --
 * 9.13 Passed. WTF?
 *
 *  let's analyze why this problem isn't a good interview question.
 * The whole point of asking any candidates a linked list problem is to test if the candidates think about edge cases, including:
 *
 * Dereferencing Null Pointer, usually targeting tail pointer
 * When given Head is None
 * When there are duplications in the list
 * This question specifically mentioned all the above edge cases and extracted them out for you
 *
 */
public class _237DeleteNodeInALinkedList {
    /**
     * 1.
     *
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
