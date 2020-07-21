import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0763PartitionLabels {
    public List<Integer> partitionLabels(String S) {
        List<Integer> subStrSizes = new ArrayList<>();
        if (S == null) {
            return subStrSizes;
        }

        Map<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (map.containsKey(c)) {
                int[] arr = map.get(c);
                arr[1] = i;
            } else {
                int[] arr = new int[2];
                arr[0] = i;
                arr[1] = i;
                map.put(c, arr);
            }
        }

        helper(S, map, subStrSizes, 0);

        return subStrSizes;
    }

    private void helper(String S, Map<Character, int[]> map, List<Integer> subStrSizes, int start) {
        if (start >= S.length()) {
            return;
        }

        int[] arr = map.get(S.charAt(start));
        int rightMost = arr[1];

        for (int i = start + 1; i <= rightMost; i++) {
            arr = map.get(S.charAt(i));
            int last = arr[1];
            rightMost = Math.max(rightMost, last);
        }

        subStrSizes.add(rightMost - start + 1);
        helper(S, map, subStrSizes, rightMost + 1);
    }

    public List<Integer> partitionLabels2(String S) {
        List<Integer> subStrSizes = new ArrayList<>();
        if (S == null || S.length() == 0) {
            return subStrSizes;
        }

        Map<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (map.containsKey(c)) {
                int[] arr = map.get(c);
                arr[1] = i;
            } else {
                int[] arr = new int[2];
                arr[0] = i;
                arr[1] = i;
                map.put(c, arr);
            }
        }

        int start = 0;
        int rightMost = 0;

        for (int i = start; i <= rightMost || i <= S.length() - 1; i++) {
            int[] arr = map.get(S.charAt(i));
            int last = arr[1];
            rightMost = Math.max(rightMost, last);
            if (i == rightMost) {
                subStrSizes.add(rightMost - start + 1);
                start = rightMost + 1;
            }
        }

        return subStrSizes;
    }

    public List<Integer> partitionLabels3(String S) {
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
