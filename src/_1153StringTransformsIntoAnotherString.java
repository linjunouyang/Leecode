import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class _1153StringTransformsIntoAnotherString {
    /**
     * 1. Graph
     *
     * Thinking process
     * https://leetcode.com/problems/string-transforms-into-another-string/discuss/399352/Complete-Logical-Thinking-(This-is-why-only-check-if-str2-has-unused-character)
     *
     * Why map?
     * we will convert one letter to another, which forms chains of nodes -> map.
     * & storing the intermediate string would be too space-consuming
     *
     * If a value shows up later as a key, then it makes a linkedlist structure,
     * and if a value has already been a key, then there is a cycle (in this case, the last "a" is the key in the first row).
     *
     * For linkedlist without cycle we can just backward substitute the key with the value,
     * there exists a way of converting s1 to s2 for sure.
     *
     * a -> c -> e -> a
     * For linkedlist with a cycle, such as "a -> c -> e -> a",
     * we need to break the loop and use a temporary variable to cache the point of break,
     * in this case, it becomes the transformation with two steps: "a -> tmp" and "tmp -> c -> e -> a".
     * Now the bottleneck is if we can find a temporary variable to carry the conversion, if there is one, then the conversion is viable.
     * this temporary variable must not be mapped
     * ---
     * Possible structure in this graph:
     * https://leetcode.com/problems/string-transforms-into-another-string/discuss/355725/Graph-theory-analysis-of-this-problem
     * 1. self-loop
     * 2. chain
     * 3. cycle
     * 4. lollipop
     *
     * 1. 一对一，每一个char互相对应转换即可 a->b
     * 2. 多对一， aabcc,ccdee, a->c, c->e，其实只要有未在target string出现过的char，那么就可以拿来
     * 作为temp char桥梁，比如 a->g->c这样转换就不会同时影响c->e的转换
     *
     * source和target的unique char的数量是一样的时候，如果此时是26个
     * 则说明完全不能转换，因为没有extra的temp char作为转换的桥梁
     *
     * 3. 一对多，a->f, a->g 这样是绝对不可能的，因为char会被同时影响
     * -------
     * Time: O(n)
     * running time can be improved if count available character during the scan.
     *
     * Space: O(26)
     */
    public boolean canConvert(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        }
        Map<Character, Character> dp = new HashMap<>();
        for (int i = 0; i < str1.length(); ++i) {
            if (dp.getOrDefault(str1.charAt(i), str2.charAt(i)) != str2.charAt(i)) {
                // same char should transform to same char
                return false;
            }

            dp.put(str1.charAt(i), str2.charAt(i));
        }
        // 1. linked list mapping -> OK
        // 2. cycle
        // -> must exclude cases where every char is part of cycle / no char's in-degree is 0 / no unused char
        // consider abc -> bca (not OK)
        // abc -> bcb (OK, a is not involved in cycle, )

        return new HashSet<Character>(dp.values()).size() < 26;
        // dp.keySet().size() < 26 is not correct:
        // linkedlist mapping: a->z, n to 1: (z, p) -> q
        // "abcdefghijklmnopqrstuvwxyz"
        // "bcdefghijklmnopqrstuvwxyzq"
        // a->b b->c c->d d->b keys:4, values:3
        //
        // output: false, expected: true
    }
}
