public class _0273IntegerToEnglishWords {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"Billion", "Million", "Thousand", ""};
    private final int[] radix = {1000000000, 1000000, 1000, 1};

    /**
     * 1.
     *
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * Technically the time complexity the above solution is O(1), since it has a constant upper bound (a number can only be so long),
     * but if we want to get an even tighter bound, then we can define n to be the number of non-zero digits,
     * in which the time complexity is O(n^2) where n is the number of non-zero digits.
     * This is because this solution doesn't use a StringBuilder, so each concat operation needs to re-traverse every character of the previously concatenated strings,
     * and the number of characters is a function of the number of non-zero digits.
     *
     *
     * Runtime: 2 ms, faster than 92.31% of Java online submissions for Integer to English Words.
     * Memory Usage: 35.9 MB, less than 100.00% of Java online submissions for Integer to English Words.
     *
     * @param num
     * @return
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < radix.length; i++) {
            if (num / radix[i] == 0) {
                continue;
            }
            sb.append(helper(num / radix[i])).append(THOUSANDS[i]).append(' ');
            num = num % radix[i];
        }

        return sb.toString().trim();
    }

    private String helper(int num) {
        if (num == 0) {
            return "";
        } else if (num < 20) {
            return LESS_THAN_20[num] + " ";
        } else if (num < 100) {
            // Notice 20, 30, ..., 90, helper(num % 10) needs to be "";
            return TENS[num / 10] + " " + helper(num % 10);
        } else {
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
        }
    }



}
