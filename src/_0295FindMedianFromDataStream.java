import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 */
public class _0295FindMedianFromDataStream {
    /**
     * 1. Max heap and min heap
     *
     * The elements of the priority queue are ordered according to their natural ordering,
     * The head of this queue is the least element with respect to the specified ordering.
     *
     *
     * MedianOfTwoSortedArray
     */
    class MedianFinder {
        private Queue<Integer> small; // [5], 6, 7, 8
        private Queue<Integer> large; // 1, 2, 3, [4]

        public MedianFinder() {
            small = new PriorityQueue<>();
            large = new PriorityQueue<>(Collections.reverseOrder()); // or ((a,b) -> b - a)
        }

        // Time: O(n)
        // large size = small size or large size = small size + 1
        public void addNum(int num) {
            large.add(num); // 5-9
            small.add(large.poll()); // 1-[5] 6-9
            if (large.size() < small.size()) // 4 < 5
                large.add(small.poll());  // 1-[4] [5]-9
        }

        // Time: O(1)
        // Returns the median of current data stream
        public double findMedian() {
            if (large.size() > small.size()) {
                return large.peek();
            } else {
                // long 64 bit, double 64 bit
                return ((long) large.peek() + small.peek()) / 2.0;
            }
        }
    }

    /**
     * 2. BST
     *
     */
    class MedianFinder2 {
        class TreeNode{
            int val;
            TreeNode parent,left,right;
            TreeNode(int val, TreeNode p){
                this.val=val;
                this.parent=p;
                left=null;
                right=null;
            }
            void add(int num){
                if(num>=val){
                    if(right==null)
                        right=new TreeNode(num,this);
                    else
                        right.add(num);
                }else{
                    if(left==null)
                        left=new TreeNode(num,this);
                    else
                        left.add(num);
                }
            }
            TreeNode next(){
                TreeNode ret;
                if(right!=null){
                    ret=right;
                    while(ret.left!=null)
                        ret=ret.left;
                }else{
                    ret=this;
                    while(ret.parent.right==ret)
                        ret=ret.parent;
                    ret=ret.parent;
                }
                return ret;
            }
            TreeNode prev(){
                TreeNode ret;
                if(left!=null){
                    ret=left;
                    while(ret.right!=null)
                        ret=ret.right;
                }else{
                    ret=this;
                    while(ret.parent.left==ret)
                        ret=ret.parent;
                    ret=ret.parent;
                }
                return ret;
            }
        }

        int n;
        TreeNode root, curr;
        // Adds a number into the data structure.
        // Time: O(n)
        public void addNum(int num) {
            if(root==null){
                root = new TreeNode(num,null);
                curr=root;
                n=1;
            }else{
                root.add(num);
                n++;
                if(n%2==1){
                    if(curr.val<=num) {
                        curr = curr.next();
                    }
                } else {
                    if (curr.val > num) {
                        curr = curr.prev();
                    }
                }
            }
        }

        // Returns the median of current data stream
        // Time: O(1)
        public double findMedian() {
            if(n%2==0){
                return ((double)curr.next().val+curr.val)/2;
            }else
                return curr.val;
        }
    };

    /**
     * 3. Follow Up:
     * https://leetcode.com/problems/find-median-from-data-stream/discuss/275207/Solutions-to-follow-ups
     * https://leetcode.com/problems/find-median-from-data-stream/discuss/286238/Java-Simple-Code-Follow-Up
     */

    /**
     *     5
     *      10
     *     9
     *    8
     *     3
     */
}
