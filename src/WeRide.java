import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

class Range {
    int start;
    int end;
    TreeMap<Integer, Range> subs;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
        subs = new TreeMap<>();
    }
}

public class WeRide {
    public static int[][] findRanges(int[][] ranges, int[] target) {
        // sort according to start date
        Arrays.sort(ranges, (e1, e2) -> Integer.compare(e1[0], e2[0]));

        // pre-processing ranges
        Range root = new Range(Integer.MIN_VALUE, Integer.MAX_VALUE);
        for (int[] range : ranges) {
            int start = range[0];
            int end = range[1];

            Range currRange = root;
            // Map.Entry<Integer, Range> closestSubEntry = currRange.subs.floorEntry(start);
            while (currRange != null) {
                Map.Entry<Integer, Range> closestSubEntry = currRange.subs.floorEntry(start);
                if (closestSubEntry != null && closestSubEntry.getValue().end >= end) {
                    // [ {   }]  ({}: range, []: closestSubRange)
                    currRange = closestSubEntry.getValue();
                } else {
                    // no part overlap possible -> [  ] { }
                    currRange.subs.put(start, new Range(start, end));
                    break;
                }
            }
        }

        // find ranges for targets
        int[][] res = new int[target.length][2];
        for (int i = 0; i < res.length; i++) {
            int num = target[i];

            Range currRange = root;
            // Map.Entry<Integer, Range> closestSubEntry = currRange.subs.floorEntry(num);
            while (currRange != null) {
                Map.Entry<Integer, Range> closestSubEntry = currRange.subs.floorEntry(num);
                if (closestSubEntry != null && closestSubEntry.getValue().end >= num) {
                    // [     num  ]  ([]: closestSubRange)
                    currRange = closestSubEntry.getValue();
                    // closestSubEntry = currRange.subs.floorEntry(num);
                } else {
                    // no more smaller subranges containing num
                    res[i] = new int[]{currRange.start, currRange.end};
                    break;
                }
            }
            // answer guaranteed to exist
        }

        return res;
    }


    public static void main(String[] args) {
        // System.out.println("Hello, world!");
        int[][] input1 = new int[][]{{1,10},{2,3},{4,9}, {5,8},{6,7}};
        int[] input2 = new int[]{1,2,4,5,6,9};
        int[][] res = findRanges(input1, input2);
        for (int[] range : res) {
            System.out.println("[" + range[0] + "," + range[1] + "]");
        }
    }
}
