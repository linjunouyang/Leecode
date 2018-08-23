package BinarySearch;

public class PeakIndexInAMountainArray {
    /*
    For loop to find the first A[i] > A[i + 1]
    Time complexity: O(n)
    Space complexity: O(1)
     */
    public int solution1(int[] A) {
        for (int i = 1; i + 1 < A.length; ++i) {
            if (A[i] > A[i+1]) {
                return i;
            }
        }
        return 0;
    }

    /*
    Binary Search
    Time complexity: O(logn)
    Space complexity:

    l, r = 0, len(arr)
    l < r, l = mid + 1, r = mid - 1
       1    2    3    4    5    6
       l         m                   r
       l,m  r                            -> r = mid - 1
            l,r                          -> exit the loop

    l, r  = 0， len(arr)
    l <=r, l = mid + 1, r = mid - 1
       1    2    3    4     5   6
       l         m                   r
       l,m  r                            -> r = mid - 1
            l,m,r                        -> l <= r


     */
    public int solution2(int[] A) {
        int l = 0, r = A.length - 1, mid;
        while (l < r) {              // l <= r is fine
            mid = l + (r - l) / 2;
            if (A[mid] < A[mid + 1]) {
                l = mid;
            } else if (A[mid - 1] > A[mid]) {
                r = mid;
            } else {
                return mid;
            }
        }
        return 0;
    }
}
