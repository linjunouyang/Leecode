public class _0640SolveTheEquation {
    /**
     * 1. Regex Expression
     *
     * https://leetcode.com/problems/solve-the-equation/discuss/105311/Concise-Java-Solution
     *
     */
    public String solveEquation(String equation) {
        int[] res = evaluateExpression(equation.split("=")[0]),
                res2 = evaluateExpression(equation.split("=")[1]);
        res[0] -= res2[0]; // coefficient
        res[1] = res2[1] - res[1]; // number on the right side
        if (res[0] == 0 && res[1] == 0) {
            return "Infinite solutions";
        }
        if (res[0] == 0) {
            return "No solution";
        }
        return "x=" + res[1] / res[0];
    }

    public int[] evaluateExpression(String exp) {
        // [-+]: '-' or '+'. []:  a character class that describes the options for one single character
        // ?=: Match a suffix but exclude it from capture.
        // (): a capturing group.
        // Difference between [] and (): https://stackoverflow.com/questions/3789417/whats-the-difference-between-and-in-regular-expression-patterns
        // OR
        // String[] tokens = exp.replace("+", "#+").replace("-", "#-").split("#");
        String[] tokens = exp.split("(?=[-+])");
        int[] res =  new int[2];
        for (String token : tokens) {
            if (token.equals("+x") || token.equals("x")) {
                res[0] += 1;
            } else if (token.equals("-x")) {
                res[0] -= 1;
            } else if (token.contains("x")) {
                res[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
            } else {
                res[1] += Integer.parseInt(token);
            }
        }
        return res;
    }

    /**
     * 2. No Regex, Iteration
     * @param equation
     * @return
     */
    public String solveEquation2(String equation) {
        String[] sides = equation.split("=");
        int[] left = solveSide(sides[0]);
        int[] right = solveSide(sides[1]);
        int coeff = left[1] - right[1];
        int num = right[0] - left[0]; // dividend
        if (coeff == 0) {
            if (num == 0) {
                return "Infinite solutions";
            } else {
                return "No solution";
            }
        }
        return "x=" + num/coeff;
    }

    public int[] solveSide(String str) {
        int[] res = new int[2]; // {constant num, xCoeff}
        int sign = 1;
        int temp = 0;
        boolean hasCoeff = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                temp = temp * 10 + (c - '0');
                hasCoeff = true;
            } else if (c == 'x') {
                if (!hasCoeff) {
                    // Corner case
                    // case1: x+...
                    // case2: ...+x+
                    temp = 1;
                }
                res[1] += sign * temp;
                hasCoeff = false;
                temp = 0;
            } else { // + or -
                res[0] += sign * temp;
                temp = 0;
                sign = c == '+' ? 1 : -1;
                hasCoeff = false;
            }
        }
        res[0] += sign * temp; // corner case
        return res;
    }
}
