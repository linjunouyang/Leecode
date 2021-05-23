import java.util.*;
import java.util.Map;

/**
 * Summary:
 * https://leetcode.com/problems/top-k-frequent-words/discuss/431008/Summary-of-all-the-methods-you-can-imagine-of-this-problem
 *
 * String hash time complexity:
 * https://cs.stackexchange.com/questions/124475/time-complexity-of-hash-table-lookup
 *
 * String hash collision:
 * LinkedList -> Balanced Trees (from JDK 8)
 * https://javarevisited.blogspot.com/2016/01/how-does-java-hashmap-or-linkedhahsmap-handles.html
 *
 * <p>
 * Similar Questions:
 * <p>
 * 347 Top K Frequent Elements
 * 451 Sort Characters By Frequency
 */
public class _0692TopKFrequentWords {
    /**
     * 0. Brute Force
     *
     * HashMap: word -> number of occurence.
     * Put every entry into a list
     * Collections.sort with a self-defined comparator
     * Put first k elements into the result list
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */

    /**
     * 1. HashMap + PriorityQueue (max-heap)
     *
     * <p>
     * Time complexity: O(nlogn + Klogn) = O(nlogn)
     * ? String Hash O(n * length of word)
     *
     * ? In the worst case, all words have same frequency, when offered into max-heap, need to compare string
     * n words, each appear 2 times -> O(n/2 * log(n/2) * len) = O(len * n * logn)
     *
     * Space complexity: O(n)
     */
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                        return e1.getValue().equals(e2.getValue()) ? e1.getKey().compareTo(e2.getKey()) : e2.getValue() - e1.getValue();
                    }
                });
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            pq.offer(e);
        }
        List<String> ans = new LinkedList<>();
        for (int i = 0; i <= k - 1; i++) {
            ans.add(pq.poll().getKey());
        }
        return ans;
    }

    /**
     * 2. HashMap + PriorityQueue (min-heap)
     *
     * Java PriorityQueue
     *
     * The head of the queue is the least element with respect to the specified ordering
     *
     * O(n): remove(Object), contains(Object)
     * O(logn): enquing and dequing (offer(E e), add(E, e), poll(), remove)
     * O(1): peek(), element, size()
     *
     * Time complexity: O(nlogk)
     *
     * ? String Hash: O(n * number of words)
     *
     * ? In the worst case, all words have same frequency, when offered into max-heap, need to compare string
     * n words, each appear 2 times -> O(n/2 * log(k) * len) = O(len * n * logk)
     * but once a string's hash is calculated, it will be cached.
     *
     * https://cs.stackexchange.com/questions/124475/time-complexity-of-hash-table-lookup
     *
     * Space complexity: O(k)
     */
    public List<String> topKFrequent2(String[] words, int k) {

        List<String> result = new LinkedList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], map.getOrDefault(words[i], 0) + 1);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                // You can't compare two Integer with a simple == they're objects
                // so most of the time references won't be the same.
                // There is a trick, with Integer between -128 and 127,
                // references will be the same as autoboxing uses Integer.valueOf() which caches small integers.

                // Why a.getKey().compareTo(b.getKey()) instead of the other way around?
                // a word that has a higher alphabetical order can stay higher in the min-heap.
                // e.g. when min-heap has "abc", "abd", "bun", min-heap should have them in this order to be removed: "bob", "bun", "ban".
                (a, b) -> a.getValue().equals(b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue()
        );

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        while (!pq.isEmpty()) {
            result.add(0, pq.poll().getKey());
        }

        return result;
    }

    /**
     * 3. Bucket Sort + Trie
     *
     * bucketSort()
     *   create N buckets each of which can hold a range of values
     *   for all the buckets
     *     initialize each bucket with 0 values
     *   for all the buckets
     *     put elements into buckets matching the range
     *   for all the buckets
     *     sort elements in each bucket
     *   gather elements from each bucket
     * end bucketSort
     *
     * This method is derived from 347. Top K Frequent Elements.
     * At 347, we use bucket sort (LinkedList in each bucket) to find top K integers and we can choose any one if there is a tie of frequency .
     * But in this we needd to compare the lexicographic order. Thus using bucket sort(LinkedList in each bucket) is not good.
     * The way to solve the tie problem is to use either trie or BST.
     *
     * m: len of words, n: number of words
     * Time Complexity: O(nm) = O(n), m time to construct trie for each word and m is a constant
     * Space Complexity: O(nm) = O(n), m space for each bucket and m is a constant
     */
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null;
    }

    class Trie {
        TrieNode root = new TrieNode();

        /**
         * Time complexity: O(len of word)
         * Space complexity: O(len of word)
         */
        public void addWord(String word){
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                if (cur.children[c-'a'] == null) {
                    cur.children[c-'a'] = new TrieNode();
                }
                cur = cur.children[c-'a'];
            }
            cur.word = word;
        }

        /**
         * Time complexity: O(depth)
         * Space complexity: O(depth)
         */
        public void getWords(TrieNode node, List<String> ans){
            //use DFS to get lexicograpic order of all the words with same frequency
            if (node == null) {
                return;
            }
            if (node.word != null) {
                ans.add(node.word);
                // can't return here ["a", "aa", "aaa] k = 2
                // expected ["a", "aa"] instead of ["a']
            }
            for (int i = 0;i <= 25; i++) {
                if (node.children[i] != null){
                    getWords(node.children[i], ans);
                }
            }

        }
    }

    public List<String> topKFrequent3(String[] words, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        for(String word:words){
            map.put(word, map.getOrDefault(word, 0)+1);
        }

        Trie[] buckets = new Trie[words.length]; // frequency -> words
        for(Map.Entry<String, Integer> e:map.entrySet()){
            //for each word, add it into trie at its bucket
            String word = e.getKey();
            int freq = e.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new Trie();
            }
            buckets[freq].addWord(word);
        }

        List<String> ans = new LinkedList<>();

        for (int i = buckets.length -1; i >= 0; i--){
            //for trie in each bucket, get all the words with same frequency in lexicographic order. Compare with k and get the result
            if (buckets[i] != null) {
                List<String> l = new LinkedList<>();
                buckets[i].getWords(buckets[i].root, l);
                if (l.size() < k) {
                    ans.addAll(l);
                    k = k - l.size();
                } else {
                    for(int j = 0; j < k; j++){
                        ans.add(l.get(j));
                    }
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * 4. Bucket Sort + BST
     */

    /**
     * 5. Quick Select
     */
}
