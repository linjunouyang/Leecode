public class _0227BasicCalculatorII {
    /**
     * 1. Iteration
     *
     * Optimized from stack version:
     * https://leetcode.com/problems/basic-calculator-ii/discuss/63003/Share-my-java-solution
     * Whenever we encounter a sign, a number is formed.
     *
     *
     *
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int calculate(String s) {
        int sum = 0;
        int tempSum = 0;
        int num = 0;
        char lastSign = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (i == s.length() - 1 || (!Character.isDigit(c) && c!=' ')) {
                // lastSign tempSum currentSign/lastChar
                switch(lastSign) {
                    case '+':
                        sum+=tempSum;
                        tempSum = num;
                        break;
                    case '-':
                        sum+=tempSum;
                        tempSum = -num;
                        break;
                    case '*':
                        tempSum *= num;
                        break;
                    case '/':
                        tempSum /= num;
                        break;
                }
                lastSign = c;
                num=0; // THIS!!
            }
        }
        sum+=tempSum; // 3+2*2
        return sum;
    }
}
