import java.util.*;

public class _0281ZigzagIterator {

    /**
     * No need to store v1, v2 as instance variables
     *
     * Runtime: 2 ms, faster than 77.84% of Java online submissions for Zigzag Iterator.
     * Memory Usage: 40.8 MB, less than 100.00% of Java online submissions for Zigzag Iterator.
     */
    private ListIterator<Integer> iter1;
    private ListIterator<Integer> iter2;
    private ListIterator<Integer> temp;

    // O(1) O(1)
    public _0281ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        iter1 = v1.listIterator();
        iter2 = v2.listIterator();
    }

    // O(1) O(1)
    public int next() {
        if (iter1.hasNext()) {
            temp = iter1;
            iter1 = iter2;
            iter2 = temp;
        }
        return iter2.next();
    }

    // O(1)
    public boolean hasNext() {
        return iter1.hasNext() || iter2.hasNext();
    }

    /**
     * Generalized K-vector solution
     */
    private Queue<Iterator> q;

//    public _0281ZigzagIterator(List<Integer> v1, List<Integer> v2) {
//        q = new LinkedList<>();
//        if (!v1.isEmpty()) {
//            q.offer(v1.iterator());
//        }
//        if (!v2.isEmpty()) {
//            q.offer(v2.iterator());
//        }
//    }

    public int next2() {
        Iterator iter = q.poll();
        int result = (Integer) iter.next();
        if (iter.hasNext()) {
            q.offer(iter);
        }
        return result;
    }

    public boolean hasNext2() {
        return q.peek() != null;
    }
}
