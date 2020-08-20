import java.util.*;

/**
 * Similar Q:
 * 139 Word Break
 * 140 Word Break II
 */
public class _0472ConcatenatedWords {
    /**
     * 1. DP
     * The intuition behind this approach is that the given problem (ss) can be divided into sub-problems s1 and s2.
     * If these sub-problems individually satisfy the required conditions, the complete problem, s also satisfies the same.
     * <p>
     * Of course it is also obvious that a word can only be formed by words shorter than it.
     * So we can first sort the input by length of each word, and only try to form one word by using words in front of it.
     * <p>
     * Time complexity: O(N * L ^ 3)
     * String HashCode takes O(L) for 1st computation
     * substring: O(L)
     * <p>
     * Space complexity:
     * preWords: O(n)
     * sort: O(logn) to O(n)
     * dp[]: O(L)
     */
    public static List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<>();
        Set<String> preWords = new HashSet<>();

        /**
         * Even when the hashSet is larget, contains is still O(1). We can remove sort
         *
         *         for (String word : words) {
         *             preWords.add(word);
         *         }
         *
         *         for(int i = 0; i < words.length; i++) {
         *             preWords.remove(words[i]);
         *             if(canForm( words[i], preWords) && !words[i].equals(""))  {
         *                result.add(words[i]);
         *             }
         *             preWords.add(words[i]);
         *         }
         */

        Arrays.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });

        for (int i = 0; i < words.length; i++) {
            if (canForm(words[i], preWords)) {
                result.add(words[i]);
            }
            preWords.add(words[i]);
        }

        return result;
    }

    /**
     * The most intuitive way would be coming up with all breakdown for the word
     * and for each breakdown, we check whether each substring is available in dict.
     * -> a lot of repetitive work (overlapping sub-problems)
     *
     * ? How to describe its optimal substructure
     *
     * dp[i] = 1 if exists j[0, i - 1] that dp[j] = true && dict contains word[j, i)
     */
    private static boolean canForm(String word, Set<String> dict) {
//        if (dict.isEmpty()) {
//            return false;
//        }
        boolean[] dp = new boolean[word.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= word.length(); i++) {
            // Intuition for (int j = 0; j < i; j++)
            // For long words, it's faster to use stored result rather than take substring and check in set
            for (int j = i - 1; j >= 0; j--) {
                if (!dp[j]) {
                    continue;
                }
                if (dict.contains(word.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        /**
         * Another way of DP
         * Time complexity: [s len][dict size][avg len of words in dic]
         *
         * Further optimization
         * 1. remove [size of dict] by using Tire
         * 2. remove [avg length of words in dict] by KMP, and what's more
         * 3. remove both [size of dict] and [avg length of words in dict] by AC-automata.
         *
         *         for(int i = 1; i <= s.length(); i++){
         *             // substring = previous examined prefix + dict word
         *             for(String str: dict){
         *                 if(str.length() <= i){
         *                     if(f[i - str.length()]){
         *                         if(s.substring(i - str.length(), i).equals(str)){
         *                             f[i] = true;
         *                             break;
         *                         }
         *                     }
         *                 }
         *             }
         *         }
         */
        return dp[word.length()];
    }

    /**
     * 2. HashSet and DFS
     * <p>
     * ou need it for memorization of previous seen words, this is a smart solution that it used set as memo dict,
     * and by expanding set with valid word strings, the complexity of the problem is brought down.
     * <p>
     * Time complexity: O(n ^ 3)?
     * Space complexity: O(n ^ 3)?
     */
    public List<String> findAllConcatenatedWordsInADict2(String[] words) {

        // we sort the words array by length of the words
        Arrays.sort(words, (w1, w2) -> w1.length() - w2.length());

        // we maintain a set of words already seen so that it is easy for us to chek the prefix and see if the word can be made
        Set<String> set = new HashSet<>();
        List<String> res = new ArrayList<>();

        // we check every word if it can be made by the other words if yes then we add to res
        // we add every word to set to check if it can be used to make other word

        for (String word : words) {
            if (canBeFormed(word, set)) {
                res.add(word);
            }
            // add every word in the prefix set
            set.add(word);
        }
        return res;
    }

    public boolean canBeFormed(String word, Set<String> set) {
        // if word is already present in the set then return
        if (set.contains(word)) return true;

        // else check each substring on the word,
        // starting from 0-1 if it is present then check if rest substring is present
        // if both are present that means the word can be formed by using the prefixes in the string

        for (int i = 1; i <= word.length(); i++) {

            if (set.contains(word.substring(0, i))) {

                // then check if the set has rest of the substing
                if (canBeFormed(word.substring(i), set)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 3. Trie + DFS
     *
     * We have to answer a question recursively: is a substring(word[x, word.length()-1]) prefixed with another word in words?
     * That's natural to prefix tree(trie).
     *
     * Time complexity: O(n * 2^k)
     * Building Trie O(n * k)
     * Validation O(n * 2 ^ k) (there is a finding process inside DP sub-processing which is O(1) with HashSet.)
     *
     * Space complexity: O(n * k)
     * Trie: O(n * k)
     * chars array in addWord: O(k)
     * testWord: O(k)
     */
    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }

    public List<String> findAllConcatenatedWordsInADict3(String[] words) {
        List<String> res = new ArrayList<String>();
        if (words == null) {
            return res;
        }

        TrieNode root = new TrieNode();
        for (String word : words) { // construct Trie tree
            addWord(word, root);
        }

        for (String word : words) { // test word is a concatenated word or not
            if (word.length() == 0) {
                continue;
            }
            if (testWord(word.toCharArray(), 0, root, 0)) {
                res.add(word);
            }
        }
        return res;
    }

    public void addWord(String str, TrieNode root) {
        char[] chars = str.toCharArray();
        TrieNode cur = root;
        for (char c : chars) {
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a'];
        }
        cur.isEnd = true;
    }

    /**
     * test word (chars) [index, len) can be formed from words
     *
     * count means how many words during the search path
     *
     * suppose every substring of word "abcdz" exists in the words except for substrings containing 'z'.
     * Then you need to check
     * a-bcdz
     * ab-cdz,
     * abc-dz,
     * abcd-z in the first loop.
     *
     * O(L) = O(L-1) + O(L-2) + ... + O(1) = 2(O(L-2) + O(L-3) + ... + O(1)) = 2(2(O(L-3) + ... + O(1))) = 2^L
     *
     */
    public boolean testWord(char[] chars, int index, TrieNode root, int count) {
        TrieNode cur = root;
        int n = chars.length;
        for (int i = index; i < n; i++) {
            if (cur.children[chars[i] - 'a'] == null) {
                return false;
            }
            if (cur.children[chars[i] - 'a'].isEnd) {
                if (i == n - 1) {
                    // no next word, so test count to get result.
                    return count >= 1;
                }
                if (testWord(chars, i + 1, root, count + 1)) {
                    return true;
                }
            }
            cur = cur.children[chars[i] - 'a'];
        }
        return false;
    }

    /**
     * Concise Trie
     */
    public List<String> findAllConcatenatedWordsInADict31(String[] words) {
        List<String> res = new LinkedList<>();
        Trie root = new Trie();
        for(String s: words){//build tree
            if(s.length()==0) continue; //skip "" !
            Trie p = root;
            for(char c: s.toCharArray()){
                if(p.next[c-'a']==null) p.next[c-'a'] = new Trie();
                p = p.next[c-'a'];
            }
            p.word = s;
        }
        for(String s: words)
            if(dfs(s, root, 0, root)) res.add(s); //check each word using the tree
        return res;
    }
    private boolean dfs(String s, Trie t, int p, Trie root){
        //return true, if we reach a word at the end & the original word s is a concatenated word
        if(p==s.length()) return t.word!=null && !t.word.equals(s);
        if(t.next[s.charAt(p)-'a']==null) return false;
        //reached a word, try to start from root again
        if(t.next[s.charAt(p)-'a'].word!=null && dfs(s, root, p+1, root)) return true;
        //keep going
        return dfs(s, t.next[s.charAt(p)-'a'] ,p+1, root);
    }
    class Trie{
        Trie[] next = new Trie[26];
        String word = null;
    }
}
