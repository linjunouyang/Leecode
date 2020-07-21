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
     *
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        List<Integer> subStrSizes = new ArrayList<>();
        if (S == null || S.length() == 0) {
            return subStrSizes;
        }

        int[] lastOccur = new int[26];
        for (int i = 0; i < S.length(); i++) {
            lastOccur[S.charAt(i) - 'a'] = i;
        }

        int start = 0;
        int rightMost = 0;

        for (int i = start; i <= rightMost || i <= S.length() - 1; i++) {
            int last = lastOccur[S.charAt(i) - 'a'];
            rightMost = Math.max(rightMost, last);
            if (i == rightMost) {
                subStrSizes.add(rightMost - start + 1);
                start = rightMost + 1;
            }
        }

        return subStrSizes;
    }
}
