import java.util.*;

public class _0937ReorderDataInLogFiles {
    /**
     * 1. Comparator
     *
     * String.split(String regex, int limit):
     * limit <= 0: pattern applied as many times as possible, the array can have any length,
     * limit = 0: &  railing empty strings will be discarded.
     * limit > 0: the array's length will be no greater than n, and the array's last entry will contain all input beyond the last matched delimiter
     *
     * boo:and:foo
     * :    0   { "boo", "and", "foo" }
     * :	2	{ "boo", "and:foo" }
     * :	5	{ "boo", "and", "foo" }
     * :	-2	{ "boo", "and", "foo" }
     * o	5	{ "b", "", ":and:f", "", "" }
     * o	-2	{ "b", "", ":and:f", "", "" }
     * o	0	{ "b", "", ":and:f" } (the 1st empty string is because we split boo at two 'o')
     *
     * Arrays.sort(Object[] a),  sort(T[] a, Comparator<? super T> c:
     * natural ordering, elements implementing Comparable, stable
     * iterative mergesort
     * far fewer than n lg(n) comparisons when the input array is partially sorted,
     * traditional when array is randomly ordered
     * near n comparisons if nearly sorted
     *
     * temporary storage:
     * a small constant for nearly sorted input arrays to
     * n/2 object references for randomly ordered input arrays.
     *
     * Arrays.sort(int[] a):
     * Dual-Pivot Quicksort O(nlogn)
     * space: O(logn)
     *
     * -------
     *
     * n: number of logs, m: max length of a log
     * Time:
     * sort: O(n * logn) (how many times compare will be called)
     * each compare: O(m) (compare content of logs)
     *
     * Space:
     * O(n * m) merge sort
     */
    public String[] reorderLogFiles(String[] logs) {
        Comparator<String> myComp = new Comparator<String>() {
            @Override
            public int compare(String log1, String log2) {
                // split each log into two parts: <identifier, content>
                int idx1 = log1.indexOf(' ');
                int idx2 = log2.indexOf(' ');

                boolean isDigit1 = Character.isDigit(log1.charAt(idx1 + 1));
                boolean isDigit2 = Character.isDigit(log1.charAt(idx2 + 1));

                // case 1). both logs are letter-logs
                if (!isDigit1 && !isDigit2) {
                    // first compare the content
                    int cmp = log1.substring(idx1 + 1).compareTo(log2.substring(idx2 + 1));
                    if (cmp != 0) {
                        return cmp;
                    } 
                    // logs of same content, compare the identifiers
                    return log1.substring(0, idx1).compareTo(log2.substring(0, idx2));
                }

                // case 2). one of logs is digit-log
                if (!isDigit1 && isDigit2) {
                    // the letter-log comes before digit-logs
                    return -1;
                } else if (isDigit1 && !isDigit2) {
                    return 1;
                } else {
                    // case 3). both logs are digit-log
                    return 0;
                }
            }
        };

        Arrays.sort(logs, myComp);
        return logs;
    }
}
