package BFS;

import java.util.*;

public class _127WordLadder {
    /**
     * 1. BFS
     *
     * 其他写法还没看
     *
     * 通解写法：https://www.jiuzhang.com/solutions/word-ladder/
     *
     * 这个方法快点，简介点
     *
     * Takeaway:
     * 1. new HashSet<>(list)
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordDict = new HashSet<>(wordList);
        Set<String> reached = new HashSet<>();
        reached.add(beginWord);

        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<>();
            for (String each : reached) {
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) {
                return 0;
            }
            reached = toAdd;
        }

        return distance;
    }


}
