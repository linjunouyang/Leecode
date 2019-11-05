public class _0299BullsAndCows {
    /**
     * 1. One pass.
     *
     * Add / Subtract represents different occurance.
     *
     * numbers[s] is negative
     * only if this character appeared in the guess more times then in the secret
     * which means that this secret digit can be matched with one of the previous guess digits.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] numbers = new int[10];

        for (int i = 0; i<secret.length(); i++) {
            int s = Character.getNumericValue(secret.charAt(i));
            int g = Character.getNumericValue(guess.charAt(i));

            if (s == g) {
                bulls++;
            } else {
                // secret: 11[2]3
                // guess : 01[1]1
                // numbers[-1, 1 -> 0, 0 -> 1]

                if (numbers[s] < 0) cows++;
                if (numbers[g] > 0) cows++;
                numbers[s] ++;
                numbers[g] --;
            }
        }
        return bulls + "A" + cows + "B";
    }

    /**
     * 1.1 Similar idea but with two arrays
     *
     * first loop: get bulls, and count 0-9 occurrence in each string (excluding bulls)
     * second loop: sum up the cows
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint11(String secret, String guess) {
        int bulls = 0;
        int cows = 0;

        int[] count_secret = new int[10];
        int[] count_guess = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bulls++;
            } else {
                count_secret[secret.charAt(i) - '0']++;
                count_guess[guess.charAt(i) - '0']++;
            }
        }

        for (int i = 0; i <= 9; i++) {
            cows += Math.min(count_secret[i], count_guess[i]);
        }

        return bulls + "A" + cows + "B";
    }


}
