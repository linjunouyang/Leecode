import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0140WordBreakII {
    /**
     * 1. DFS
     *
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        return DFS(s, wordDict, new HashMap<String, List<String>>());
    }

    public List<String> DFS(String s, List<String> wordDict, Map<String, List<String>> map){

        if(map.containsKey(s)) {
            return map.get(s);
        }

        List<String> result = new ArrayList<>();
        for(String word : wordDict){
            if(s.startsWith(word)){
                String remain = s.substring(word.length());
                if(remain.length() == 0) {
                    result.add(word);
                } else{
                    for(String w : DFS(remain, wordDict, map)) {
                        result.add(word + " " + w);
                    }
                }
            }
        }
        map.put(s, result);
        return result;
    }
}
