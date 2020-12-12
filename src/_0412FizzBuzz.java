import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0412FizzBuzz {
    /**
     * 1. Iteration
     *
     * Multithreading:
     * Just split by ranges, assign each range to each thread and combine the result.
     *
     * % is slow, how to improve it?
     * count 3 and 5 & reset.
     * https://leetcode.com/problems/fizz-buzz/discuss/89931/Java-4ms-solution-Not-using-%22%22-operation
     *
     * Time: O(n)
     * Space: O(1)
     */
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();
        for (int num = 1; num <= n; num++) {
            boolean divisibleBy3 = (num % 3 == 0);
            boolean divisibleBy5 = (num % 5 == 0);
            if (divisibleBy3 && divisibleBy5) {
                res.add("FizzBuzz");
            } else if (divisibleBy3) {
                res.add("Fizz");
            } else if (divisibleBy5) {
                res.add("Buzz");
            } else {
                res.add(String.valueOf(num));
            }
        }

        return res;
    }

    /**
     * 2. StringBuilder for follow-up
     *
     * What if there are multiple mappings
     * https://leetcode.com/problems/fizz-buzz/solution/
     */

    /**
     * 3. Follow up
     *
     * How do you optimize your code to make it more concise and extendable.
     *
     * However, If there are more conditions, for example if i % 7 == 0 print xxx,
     * it seems our method is not convenient for extension,
     * then I come up with using composite design pattern. The first thing I need is to design a action interface:
     *
     * https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8
     * Use lambda expression in method
     *
     */
    interface Rule{
        boolean apply(int i);
    }

    private Map<Rule, String> ruleContainers = new HashMap<Rule,String>();
    private ArrayList<Rule> rules  = new ArrayList<Rule>();

//    public Solution() {
//        addRule(i -> i % 15 == 0, "FizzBuzz");
//        addRule(i -> i % 3 == 0, "Fizz");
//        addRule(i -> i % 5 == 0, "Buzz");
//    }

    public void addRule(Rule rule, String res) {
        rules.add(rule);
        ruleContainers.put(rule, res);
    }

    public String getValue(int i) {
        for (Rule rule : rules) {
            if (rule.apply(i)) {
                return ruleContainers.get(rule);
            }
        }
        return String.valueOf(i);
    }

    public List<String> fizzBuzz2(int n) {
        List<String> res = new ArrayList<String>();
        for(int i = 1; i <= n; i++){
            res.add(getValue(i));
        }
        return res;
    }
}

