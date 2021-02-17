import java.util.*;
import java.util.Map;

/**
 * Notice:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * It's guaranteed that the answer is unique, in other words the set of the top k frequent elements is unique.
 * You can return the answer in any order.
 *
 * Similar Question: 692
 *
 * QuickSort: 215
 */
public class _0347TopKFrequentElements {
    /**
     * 1. HashMap + PriorityQueue
     *
     * Time complexity: O(N * logk)
     * if K = N, we can solve it in O(N)
     *
     * 1. first k elements: O(log 1 + log 2 + ... + log k) = O(log k!) = O(k log k)
     * https://mathworld.wolfram.com/StirlingsApproximation.html
     * 2. remaining n - k: O((n - k) log k)
     * 3. construct output array: O(k log k)
     *
     * Space complexity: O(k)
     *
     */
    class Element {
        int num;
        int occurence;

        public Element(int num, int occurence) {
            this.num = num;
            this.occurence = occurence;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null) {
            return new int[k];
        }

        if (k == nums.length) {
            return nums;
        }

        // num -> occurence
        HashMap<Integer, Integer> numCount = new HashMap<>();
        for (int num : nums) {
            int val = numCount.getOrDefault(num, 0);
            numCount.put(num, val + 1);
        }

        PriorityQueue<Element> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.occurence));
        // or (n1, n2) -> numCount.get(n1) - numCount.get(n2)
        for (Map.Entry<Integer, Integer> entry : numCount.entrySet()) {
            minHeap.add(new Element(entry.getKey(), entry.getValue()));
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            res[i] = minHeap.poll().num;
        }

        return res;
    }

    /**
     * 2. QuickSelect
     *
     * How to introduce QuickSelect to this problem?
     *
     * Intuitively, we sort unique numbers based on frequency (least -> most),
     * from (n - k)th to the last element is our answer
     *
     * which can be translated into
     * find (n - k)th (or (n - k + 1)th) least frequent number,
     * & less frequent nums on the left, more frequent nums on the right
     * -> quickselect
     *
     * -------
     * Lyn: Because we don't care about the order, no need to QuickSort
     *
     * QuickSelect (also known as Hoare's selection algorithm) is typically used to "find kth element"
     * (kth smallest, kth largest, kth most frequent, kth least frequent)
     *
     * Average: O(n), worst: O(N ^ 2) (very rare)
     *
     * Basic idea:
     * chooses a pivot and defines (finds?) its pos in a sorted array in linear time with partition algorithm.
     * compare pivot_index with N - K. (Equal -> done, otherwise, choose corresponding side to proceed)
     *
     * Notice our partition scheme should be able to deal with duplicate frequencies.
     *
     * --------
     * Lomuto's Partition: can't deal with duplicates
     * Hoare's partition: more efficient than Lomuto's
     * because a) 3 times fewer swaps on average,
     * and b) creates efficient partitions even when all values are equal.
     *
     * Hoare's
     * 1. Move pivot to the end of the array using swap.
     * 2. Set the pointer at the beginning of the array store_index = left.
     * 3. Iterate over the array and move all less frequent elements to the left swap(store_index, i).
     * (no need to deal with more frequent element)
     * Move store_index one step to the right after each swap.
     * 4. Move the pivot to its final place (store_index), and return this index.
     * ------
     * Time complexity:
     * T(N) = T(N/2) + N -> O(n) )random pivots)
     * bad chosen pivots -> problem not divided byy half, just one element less -> O(n^2)
     *
     * Space complexity: O(n)
     */
    int[] unique;
    HashMap<Integer, Integer> count;

    public int[] topKFrequent2(int[] nums, int k) {
        // build hash map : character and how often it appears O(n)
        count = new HashMap();
        for (int num: nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        // array of unique elements O(n)
        int n = count.size();
        unique = new int[n];
        int i = 0;
        for (int num: count.keySet()) {
            unique[i] = num;
            i++;
        }

        // kth top frequent element is (n - k)th less frequent.
        // Do a partial sort: from less frequent to the most frequent, till
        // (n - k)th less frequent element takes its place (n - k) in a sorted array.
        // on the left: less frequent, on the right: more frequent.
        // We can also choose (n - k + 1) here, but need to handle (left > right) in quickselect
        quickselect(0, n - 1, n - k);

        // Return top k frequent elements (from n-k+1 to n-1, but array index starts with 0 and copyOfRange last param is not inclusive)
        return Arrays.copyOfRange(unique, n - k, n);
    }

    /*
        Sort a list within left..right till kth less frequent element
        takes its place.

        optimization: use iteration while (left < right)
        */
    public void quickselect(int left, int right, int k_smallest) {
        // wiki says base case is == (but still can pass LeetCode if base case not handled)
        // Analysis:
        // n = 3, k = [1, 2, 3] -> k_s = (n - k) : [0, 2]
        // pivot [left, right] [0, 2]
        // Extreme:
        // a) k_s = 0 < pivot = 1 -> quickselect(0, 0) -> k_s = pivot
        // b) k_s = 2 > pivot = 1 -> quickselect(2, 2) -> k_s = pivot
        if (left >= right) {
            return;
        }

        // 1. select a random pivot_index (nextInt param must be +)
        Random random_num = new Random();
        int pivot_index = left + random_num.nextInt(right - left + 1);

        // 2. find the pivot position in a sorted list
        pivot_index = partition(left, right, pivot_index);

        // 3. if the pivot is in its final sorted position
        if (k_smallest == pivot_index) {
            return;
        } else if (k_smallest < pivot_index) {
            // go left
            quickselect(left, pivot_index - 1, k_smallest);
        } else {
            // go right
            quickselect(pivot_index + 1, right, k_smallest);
        }
    }

    // Lomuto's
    public int partition(int left, int right, int pivot_index) {
        int pivot_frequency = count.get(unique[pivot_index]);

        // 1. move pivot to end
        swap(pivot_index, right);
        int store_index = left;

        // 2. move all less frequent elements to the left
        for (int i = left; i <= right - 1; i++) {
            // remember we are compare two 'unique number' not index (i or pivot_index)
            if (count.get(unique[i]) < pivot_frequency) {
                swap(store_index, i);
                store_index++;
            }
        }

        // 3. move pivot to its final place
        swap(store_index, right);

        return store_index;
    }

    public void swap(int a, int b) {
        int tmp = unique[a];
        unique[a] = unique[b];
        unique[b] = tmp;
    }

    /**
     * 3. Bucket Sort
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Yelp interviewer think BucketSort is not space efficient when nums is very long
     */
    public int[] topKFrequent3(int[] nums, int k) {
        // num -> frequency
        HashMap<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
        for (int n : nums) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }

        // corner case: if there is only one number in nums, we need the bucket has index 1.
        // frequency -> list of numbers that have same frequency
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }

        int[] res = new int[k];
        int i = k - 1;

        for (int pos = bucket.length - 1; i >= 0; pos--) {
            if (bucket[pos] != null) {
                List<Integer> list = bucket[pos];
                int j = list.size() - 1;
                while (i >= 0 && j >= 0) {
                    res[i] = list.get(j);
                    i--;
                    j--;
                }
            }
        }

        return res;
    }

    /**
     * 4. Quick select (new version)
     */
    public int[] topKFrequent4(int[] nums, int k) {
        HashMap<Integer, Integer> numToCount = new HashMap<>();
        for (int num : nums) {
            int oldCount = numToCount.getOrDefault(num, 0);
            numToCount.put(num, oldCount + 1);
        }

        int[] unique = new int[numToCount.size()];
        int i = 0;
        for (int num : numToCount.keySet()) {
            unique[i] = num;
            i++;
        }

        int left = 0;
        int right = unique.length - 1;
        while (left <= right) {
            int pivotIdx = partition(unique, numToCount, left, right);
            if (pivotIdx == unique.length - k) {
                break;
            } else if (pivotIdx < unique.length - k) {
                left = pivotIdx + 1;
            } else {
                right = pivotIdx - 1;
            }
        }

        int[] res = new int[k];
        for (i = 0; i < k; i++) {
            res[i] = unique[unique.length - 1 - i];
        }

        return res;
    }

    private int partition(int[] nums, HashMap<Integer, Integer> numToCount, int left, int right) {
        // right as pivot
        int lessFrequentEnd = left;
        for (int i = left; i < right; i++) {
            if (numToCount.get(nums[i]) < numToCount.get(nums[right])) {
                swap(nums, i, lessFrequentEnd);
                lessFrequentEnd++;
            }
        }
        swap(nums, lessFrequentEnd, right);
        return lessFrequentEnd;
    }

    private int partition2(int[] nums, HashMap<Integer, Integer> numToCount, int left, int right) {
        // left as pivot
        int pivot = left;
        while (left <= right) {
            while (left <= right && numToCount.get(nums[left]) <= numToCount.get(nums[pivot])) {
                left++;
            }

            while (left <= right && numToCount.get(nums[right]) >= numToCount.get(nums[pivot])) {
                right--;
            }

            if (left <= right) {
                swap(nums, left, right);
            }
        }

        swap(nums, pivot, right);
        return right;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
