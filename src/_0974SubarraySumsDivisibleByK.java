/**
 * Similar Questions: 523
 *
 * Whenever you see 'Subarray', think of prefix sum or sliding window
 *
 * I came up with
 * 1) brute force: O(n ^ 3) = O(n ^ 2) sub arrays * O(n) calculating sum
 * 2) prefix sums: O(n) build prefix sums + O(n ^ 2) sub arrays sum (Time limit exceeded)
 */
public class _0974SubarraySumsDivisibleByK {
    /**
     * 1. Prefix sum with math optimization
     *
     * For prefix sum sol, we need to find all pairs (i,j) such that (p[j] - p[i]) % K == 0.
     * But this is only true, if and only if p[j] % K == p[i] % K
     *
     * Proof:
     * p[j] = bK + r0 where 0 <= r0 < K
     * p[i] = aK + r1 where 0 <= r1 < K
     *
     * p[j] - p[i] = (b*K + r0) - (a*K + r1)
     * = b*K - a*K + r0 - r1 = K*(b-a) + r0 - r1
     * K only divides p[j] - p[i] iff r0-r1 = 0 <=> r0 = r1
     *
     * Don't forget elements in the array that do not need a pairing, namely those that are are divisible by K.
     * That's why I add mod[0] at the end.
     *
     * Remember:
     * (a + b) % k = (a % k + b % k) % k
     * (3 + 3) % 4 = (3 + 3) % 4 = 2
     *
     * Time complexity: O(n)
     * Space complexity: O(k)
     */
    public int subarraysDivByK(int[] A, int K) {
        //There K mod groups 0...K-1
        //For each prefix sum that does not have remainder 0 it needs to be paired with 1 with the same remainder
        //this is so the remainders cancel out.
        int[] modGroups = new int[K];
        int sum = 0;
        for (int a : A){
            sum += a;
            int remainder = sum % K;

            if (remainder < 0) {
                // Because -1 % 5 = -1, but we need the positive mod 4
                // so -1 can pair up with all remainders like -1, 4
                // 4 % 5 = 4, -1 % 5 = -1 -> 4 - (-1) = 5, or -1 - 4 = -5

                remainder += K; //Java has negative modulus so correct it
            }
            modGroups[remainder]++;
        }

        // Dont forget all numbers that divide K
        int total = modGroups[0];
        for (int x : modGroups){
            if (x > 1) {
                // The number of pairs of choosing any 2 from x
                total += (x * (x - 1)) / 2;
            }
        }

        return total;
    }
}
