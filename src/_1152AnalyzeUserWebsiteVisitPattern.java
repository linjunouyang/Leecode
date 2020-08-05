import java.util.*;

/**
 * Each user needs to have visited all three in the same order.
 *
 * Avoid counting same sequence multiple times for the same user (using set)
 */
public class _1152AnalyzeUserWebsiteVisitPattern {
    class Pair {
        int time;
        String web;
        public Pair(int time, String web) {
            this.time = time;
            this.web = web;
        }
    }

    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        // username -> (time, website)
        Map<String, List<Pair>> map = new HashMap<>();
        int n = username.length;
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(username[i], new ArrayList<>());
            map.get(username[i]).add(new Pair(timestamp[i], website[i]));
        }

        // 3-sequence -> visit times
        String res = "";
        Map<String, Integer> count = new HashMap<>();
        for (String key : map.keySet()) {
            Set<String> set = new HashSet<>(); // this set is to avoid visit the same 3-seq in one user
            List<Pair> list = map.get(key);
            Collections.sort(list, (a, b)->(a.time - b.time)); // sort by time

            // brutal force O(N ^ 3)
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    for (int k = j + 1; k < list.size(); k++) {
                        String str = list.get(i).web + " " + list.get(j).web + " " + list.get(k).web;
                        StringBuilder sb = new StringBuilder();
                        sb.append(list.get(i).web).append(" ");
                        sb.append(list.get(j).web).append(" ");
                        sb.append(list.get(k).web);
                        String combo = sb.toString();

                        if (!set.contains(combo)) {
                            count.put(combo, count.getOrDefault(combo, 0) + 1);
                            set.add(combo);
                        }

                        if (res.equals("") || count.get(res) < count.get(combo)
                                || (count.get(res) == count.get(combo) && res.compareTo(combo) > 0)) {
                            // make sure the right lexi order
                            res = str;
                        }
                    }
                }
            }
        }

        // grab the right answer
        String[] r = res.split(" ");
        List<String> result = new ArrayList<>();
        for (String str : r) {
            result.add(str);
        }
        return result;

        // return Arrays.asList(res.split(" "));
    }

}
