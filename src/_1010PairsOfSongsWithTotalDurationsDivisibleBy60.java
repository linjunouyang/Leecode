public class _1010PairsOfSongsWithTotalDurationsDivisibleBy60 {
    /**
     * 1. Brute Force
     *
     * Examine every possible pair
     *
     * Time: O(n^2)
     * Space: O(1)
     */

    /**
     * 2. Two sum?
     *
     * (a + b) % 60 = 0;
     * ((a % 60) + (b % 60)) % 60 = 0
     * either:
     * 1. a % 60 = 0 && b % 60 = 0
     * 2. (a % 60) + (b % 60) = 60
     *
     * instead of counting after traversal,
     * we can count along the way.
     * for current number, current [count += ] will pair cur num with previous nums
     * future elements' [count += ] will pair with curr element once
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int numPairsDivisibleBy60(int[] time) {
        int remainders[] = new int[60];
        int count = 0;
        for (int t: time) {
            if (t % 60 == 0) {
                // check if a%60==0 && b%60==0
                count += remainders[0];
            } else {
                // check if a%60+b%60==60
                count += remainders[60 - t % 60];
            }
            remainders[t % 60]++; // remember to update the remainders
        }
        return count;
    }
}
