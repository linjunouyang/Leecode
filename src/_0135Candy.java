import java.util.Arrays;

public class _0135Candy {
    /**
     * 1. Brute Force:
     *
     * 1-d array, candies to keep a track of the candies given to the students.
     * initialize 1 for every student
     *
     * keep scanning from left to right until no update in one iteration:
     *   if ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]
     *      candies[i] = candies[i - 1] + 1
     *   if ratings[i] > ratings[i + 1] && candies[i] <= candies[i - 1]
     *      candies[i] = candies[i + 1] + 1
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     * 1 2 3 4 3 3 2 1 2
     * -----------------
     * 1 1 1 1 1 1 1 1 1
     * 1 2 3 4 1 2 2 1 2
     * 1 2 3 4 1 3 2 1 2
     *
     */

    /**
     * 2. Two Array:
     *
     * left2Right
     * right2Left
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */

    /**
     * 3. One Array
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int candy3(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        int[] count = new int[ratings.length];
        Arrays.fill(count, 1);
        int sum = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }

        for (int i = ratings.length - 1; i >= 1; i--) {
            sum += count[i];
            if (ratings[i - 1] > ratings[i] && count[i - 1] <= count[i]) { // second round has two conditions
                count[i - 1] = count[i] + 1;
            }
        }
        sum += count[0];
        return sum;
    }

    /**
     * 3.1 One Array (my own implementation)
     *
     * @param ratings
     * @return
     */
    public int candy31(int[] ratings) {
        if (ratings == null) {
            return 0;
        }

        int[] candies =  new int[ratings.length];
        candies[0] = 1;

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] == ratings[i - 1]) {
                candies[i] = 1;
            } else if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                int end = i;
                while (end + 1 < ratings.length && ratings[end + 1] < ratings[end]) {
                    end++;
                }
                candies[end] = 1;

                for (int j = end; j >= i; j--) {
                    if (candies[j - 1] <= candies[j]) {
                        candies[j - 1] = candies[j] + 1;
                    }
                }
                i = end;
            }
        }

        int total = 0;
        for (int i = 0; i < candies.length; i++) {
            total += candies[i];
        }

        return total;
    }

    /**
     * 4. Slop
     *
     * up, down: steps of up, down
     *
     * [0, 1, 20, 9, 8, 7]
     * 1st child: 1 candy;
     * 2nd child: 2 candies, up = 1, peak = 1
     * 3rd child: 3 candies, up = 2, **peak = 2**
     * 4th child: 1 candy, down = 1 (3rd child still has 3 candies since peak=2)
     * 5th child: 1 candy, down = 2 (4th child needs 1 more candy now but 3rd child no need more)
     * 6th child: 1 candy, **down = 3** (both 5th and 4th needs 1 more candy now, and the peak, the third child need 1 more as well)
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     */
    public int Candy4(int[] ratings) {
        if (ratings.length == 0) {
            return 0;
        }
        int ret = 1;
        int up = 0, down = 0, peak = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i - 1] < ratings[i]) {
                down = 0;
                up++;
                peak = up;
                ret += 1 + up;
            } else if (ratings[i - 1] == ratings[i])  {
                peak = up = down = 0;
                ret += 1;
            } else {
                up = 0;
                down++;
                ret += down;
                if (peak < down) {
                    /**
                     *  peak = 2, down = 3
                     *
                     *  / \
                     * /  \
                     *    \
                     *
                     * For example, [0, 1, 20, 9, 8, 7], for the first 5 number, we need to assign [1,2,3,2,1] candies.
                     * But when 7 comes up, peak = 2, down = 3
                     * we need to raise the value of the peak, which is 3 above, it need to be 4, [1,2,4,3,2,1]
                     * This solution here, make it to be [1,2,3,1,2,4], the sum are same. Really brilliant.
                     *
                     */
                    ret++;
                }
            }
        }

        return ret;
    }


}
