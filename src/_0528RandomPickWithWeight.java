import java.util.Random;

public class _0528RandomPickWithWeight {
    Random random;
    int[] wSums;

    public _0528RandomPickWithWeight(int[] w) {
        this.random = new Random();
        for(int i=1; i<w.length; ++i)
            w[i] += w[i-1];
        this.wSums = w;
    }

    /**
     * 1. Binary Search
     *
     * lo <= hi, lo = mid + 1, hi = mid - 1; return left
     * lo < hi, lo = mid + 1, hi = mid; return left or right
     *
     * w[] = {2,5,3,4} => wsum[] = {2,7,10,14}
     * then get random val random.nextInt(14)+1, idx is in range [1,14]
     *
     * idx in [1,2] return 0
     * idx in [3,7] return 1
     * idx in [8,10] return 2
     * idx in [11,14] return 3
     *
     *
     * or using built-in bs
     * int i = Arrays.binarySearch(wSums, idx);
     * return i >= 0 ? i : -i-1;
     *
     * For any one wondering: Arrays.binarySearch() returns the index of the element if it's in the array or ( -(insertion point) - 1).
     * Insertion point is the point where the key (i.e idx) would be inserted in the array and it's either the index of the first element greater than the key or the array length
     * if the key is greater than all the array elements.
     *
     * For example using the array [1,3,6] and idx of 4. Arrays.binarySearch() will return (-(2)-1) = -3
     *
     * 2 is the index of 6 which is the first element greater than 4. So to return the index we need (i.e index of 6) we did -(-3) -1 = 2
     *
     */
    public int pickIndex() {
        int len = wSums.length;
        int idx = random.nextInt(wSums[len-1]) + 1;
        int left = 0, right = len - 1;
        // search position
        while(left < right){ // <=
            int mid = left + (right-left)/2;
            if(wSums[mid] == idx)
                return mid;
            else if(idx > wSums[mid])
                left = mid + 1;
            else
                right = mid; // mid - 1
        }
        return left;
    }
}
