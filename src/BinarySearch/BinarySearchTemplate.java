package BinarySearch;

public class BinarySearchTemplate {
    public int findPosition(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        /**
         * while循环只负责缩小问题范围，不解决问题本身（不return），缩小到可以用
         * 几个if语句检查的时候再处理。
         *
         * start + 1 < end 意味着相邻就退出 比如start = 1, end = 2
         *
         * 如果写成start < end，那么当题目[2, 2]要求last position时, mid = 0,
         * nums[mid] == target; start = mid = 0, 即区间没有缩小，出现死循环
         */
        while (start + 1 < end) {
            /**
             * 如果写成 mid = (start + end) / 2, 当start, end ~ 2^31，越界
             * if start and end are large positive numbers, this won't overflow.
             */
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                /**
                 * （1）如果题目求any position，那么return mid;
                 * （2）如果题目求first position, 那么end = mid;
                 * 检查前方是否还有，不能end = mid - 1，因为可能此时mid就是解
                 *  (3) 如果题目求last position, 那么start = mid;
                 * 检查后方是否还有，不能start = mid + 1，因为可能此时mid就是解
                 *
                 * 最终==的处理方案可能和<或者>相同，但是一开始还是分开写，之后想清楚了再合并
                 */
                end = mid;
                start = mid;
                return mid;
            } else if (nums[mid] < target) {
                /**
                 * 可以写成start = mid + 1, 因为中止条件是start + 1 < end
                 * 1 2 3
                 *   s e
                 */
                start = mid;
            } else {
                /**
                 * 可以写成end = mid - 1, 因为中止条件是start + 1 < end
                 */
                end = mid;
            }
        }

        /**
         * 面试时并不会因为各种double check就否定你
         *
         * double check 的原因：除非数组里只有一个数，退出循环的时候start, end
         * 为相邻关系，需要看哪个是题目所求解。
         * （1）如果是求first position，则先验证start
         * （2）如果是求last  position, 则先验证end
         */
        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}