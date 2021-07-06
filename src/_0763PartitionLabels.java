import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0763PartitionLabels {
    /**
     * 1. Greedy
     *
     * choose the smallest left-justified partition.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public List<Integer> partitionLabels(String s) {
        HashMap<Character, Integer> charToLastIndex = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            charToLastIndex.put(s.charAt(i), i);
        }

        List<Integer> partSizes = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            end = Math.max(end, charToLastIndex.get(c));
            if (i == end) {
                partSizes.add(end - start + 1);
                start = i + 1;
                end = i + 1;
            }
        }

        return partSizes;
    }
}
