import java.util.*;
import java.util.Map;

public class _1244DesignALeaderboard {
    /**
     * 1. HashMap + QuickSelect
     *
     */
    private HashMap<Integer, Integer> scores;

    public _1244DesignALeaderboard() {
        scores = new HashMap<>();
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public void addScore(int playerId, int score) {
        scores.put(playerId, score + scores.getOrDefault(playerId, 0));
    }

    /**
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int top(int K) {
        if (K <= 0) {
            return 0;
        }

        int size = scores.size();
        int[] arr = new int[size];
        int i = 0;
        for (int score : scores.values()) {
            arr[i] = score;
            i++;
        }

        // top k most points: (size - k) right: (size - k + 1) th to last -> arr index: size - k - 1
        quickSelect(arr, 0, size - 1, size - K - 1);

        int sum = 0;
        for (i = size - K; i < size; i++) {
            sum += arr[i];
        }
        return sum;
    }

    private void quickSelect(int[] arr, int left, int right, int targetIdx) {
        // base case
        if (left >= right) {
            return;
        }

        Random random = new Random();
        int pivotIdx = left + random.nextInt(right - left + 1 );

        pivotIdx = partition(arr, left, right, pivotIdx);

        if (pivotIdx == targetIdx) {
            return;
        } else if (pivotIdx < targetIdx) {
            quickSelect(arr, pivotIdx + 1, right, targetIdx);
        } else {
            quickSelect(arr, left, pivotIdx - 1, targetIdx);
        }
    }

    private int partition(int[] arr, int left, int right, int pivotIdx) {
        int pivotVal = arr[pivotIdx];
        swap(arr, right, pivotIdx);

        int pivotFinalIdx = left;
        int i = left;
        while (i < right) {
            // it's easy to forget the swap, and write arr[i] < arr[pivotIdx]
            // so pivotVal will help you avoid this mistake
            if (arr[i] < pivotVal) {
                swap(arr, i, pivotFinalIdx);
                pivotFinalIdx++;
            }
            i++;
        }

        // Don't forget to put pivot into its position!
        swap(arr, pivotFinalIdx, right);

        return pivotFinalIdx;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public void reset(int playerId) {
        scores.remove(playerId);
    }

    /**
     * 2. HashMap + TreeMap
     *
     * TreeMap idea:
     * a. Use HashMap to record the people's score
     * b. Use TreeMap to find the topK in O(klogn) by traverse the treemap
     * c. Reset we can just remove the key from the treemap which is O(log n), same for addScore().
     *
     * TreeMap keySet(): O(1)
     * 1. The set's iterator returns the keys in ascending order.
     * 2. The set is backed by the map, so changes to the map are reflected in the set, and **vice-versa**.
     * a) If the map is modified while an iteration over the set is in progress (except through the iterator's own remove operation),
     * the results of the iteration are undefined.
     * b) The set supports element removal, which removes the corresponding mapping from the map,
     * via the Iterator.remove, Set.remove, removeAll, retainAll, and clear operations.
     * It does not support the add or addAll operations.
     *
     */
    private HashMap<Integer, Integer> map; // playerId -> score
    private TreeMap<Integer, Integer> sorted; // score -> number of ppl with this score

//    public _1244DesignALeaderboard() {
//        map = new HashMap<>();
//        sorted = new TreeMap<>(Collections.reverseOrder());
//    }

    /**
     * Time complexity: O(logn)
     * Space complexity: O(1)
     */
    public void addScore2(int playerId, int score) {
        if (!map.containsKey(playerId)) {
            map.put(playerId, score);
            sorted.put(score, sorted.getOrDefault(score, 0) + 1);
        } else {
            int preScore = map.get(playerId);
            sorted.put(preScore, sorted.get(preScore) - 1);
            if (sorted.get(preScore) == 0) {
                sorted.remove(preScore);
            }

            int newScore = preScore + score;
            map.put(playerId, newScore);
            sorted.put(newScore, sorted.getOrDefault(newScore, 0) + 1);
        }
    }

    /**
     * Time complexity: O(k)
     * Space complexity: O(n)
     */
    public int top2(int K) {
        int count = 0;
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            int score = entry.getKey();
            int times = entry.getValue();
            int n = Math.min(times, K);
            sum += n * score;
            K -= n;
            if (K == 0) {
                break;
            }
        }
        return sum;
    }

    /**
     * Time complexity: O(logn)
     * Space complexity: O(1)
     */
    public void reset2(int playerId) {
        int preScore = map.get(playerId);
        map.remove(playerId);
        sorted.put(preScore, sorted.get(preScore) - 1);
        if (sorted.get(preScore) == 0) {
            sorted.remove(preScore);
        }
    }

    /**
     * 3. HashMap + PriorityQueue
     */
    Map<Integer, Integer> map3;

//    public _1244DesignALeaderboard() {
//        map = new HashMap<>();
//    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public void addScore3(int playerId, int score) {
        map.put(playerId, map.getOrDefault(playerId, 0) + score);
    }

    /**
     * Time complexity: O(nlogk)
     * Space complexity: O(n)
     */
    public int top3(int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int res = 0;
        for(int score : map.values()) {
            pq.offer(score);
            if(pq.size() > K) pq.poll();
        }
        while(!pq.isEmpty())
            res += pq.poll();

        return res;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public void reset3(int playerId) {
        map.put(playerId, 0);
    }
}
