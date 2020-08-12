import java.util.HashSet;
import java.util.Set;

/**
 * 1. endless loop which doesn't include 1
 * 2. ends in 1 (all the following will be 1 too)
 *
 * digits / largest / next
 * 1 / 9 / 81
 * 2 / 99 / 162
 * 3 / 999 / 243
 * 4 / 9999 / 324
 * 13 / ... / 1053
 *
 */
public class _0202HappyNumber {
    /**
     * 1. HashSet
     *
     * Time complexity: O(logn)
     *
     * [based off of the insight that once a number reaches the <=243 threshold, it cannot get above it again]
     * 1. the amount of time it takes for a number to reach 243
     * 2. once a number reaches the <=243 threshold, the amount of type it takes to either
     * a) discover a cycle or b) get to 1
     *
     * 1. O(logn)
     * We know that the # of digits for a number n is log n.
     * Now, for an n above 243, the first getNext() takes O(log n) time.
     * And the next number we can get is 81*(log n) at most, supposing all digits are 9s.
     * Then the next getNext() call takes O(log (81*(log n))) ~ O(log log n) time,
     * and the next number we get is 81*log (81*(log n)) ~ 81*log log n... and so on.
     * This loop ends when getNext() returns a number lower than 243.
     *
     * 2.
     * worst case: 243 more getNext() before reaching a cycle or get to 1
     * [WHEN NUMBER <= 243] Each getNext call is O(3) -> O(243 * 3) = O(1)
     *
     * 1. + 2. -> O(logn)
     *
     * Space complexity: O(logn)
     * first number: n
     * second number (max): O(81 * logn) = O(logn)
     * third number (max): O(81 * log(81 * logn)) <<< O(logn)
     * After <= 243, similar as time complexity O(243 * 3) = O(1)
     *
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    /**
     * 2. Floyd's Cycle-Finding Algorithm
     *
     * len of non-cycle + 2 * step  = step + n * len of cycle
     * step = (n * len of cycle) - len of non-cycle (n = 1, ...)
     *
     * Time complexity: O(logn)
     *
     * 1) If no cycle: o(logn)
     * 2) If has cycle:
     *
     * start -> start of cycle: O(logn)
     * cycle length: at most O(243)
     *
     * Space complexity: O(1)
     *
     */
    public boolean isHappy2(int n) {
        int slowRunner = n;
        int fastRunner = getNext(n);
        while (fastRunner != 1 && slowRunner != fastRunner) {
            slowRunner = getNext2(slowRunner);
            fastRunner = getNext2(getNext2(fastRunner));
        }
        return fastRunner == 1;
    }

    public int getNext2(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }
}
