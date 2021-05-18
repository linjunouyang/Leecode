/**
 * There are several other data structures, like balanced trees and hash tables,
 * which give us the possibility to search for a word in a dataset of strings.
 * Although hash table has O(1) time complexity for looking for a key,
 * it is not efficient in the following operations:
 * 1. Finding all keys with a common prefix.
 * 2. Enumerating a dataset of strings in lexicographical order.
 *
 * Search performance issues arise when there are lots of keys:
 * (n: number of keys, m: key length)
 * HashTable
 * 1. Time: hash collisions -> O(1) deteriorate to O(n)
 * 2. Space: O(n * average len)
 *
 * Balance Tree:
 * 1. Time: O(m log n)
 * 2. Space: O(n * average len)
 *
 * Trie:
 * 1. Time: O(m)
 * 2. Space: save space for common prefix
 */
public class _0208ImplementTrie {
    class TrieNode {
        public boolean isWord;
        public TrieNode[] children;

        public TrieNode() {
            isWord = false;
            children = new TrieNode[26];
        }
    }

    public class Trie {
        private TrieNode root;

        // Time: O(1), space: O(1)
        public Trie() {
            root = new TrieNode();
        }

        // Time: O(word len), Space: O(word len)
        public void insert(String word) {
            TrieNode curr = root;
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                if(curr.children[c - 'a'] == null){
                    curr.children[c - 'a'] = new TrieNode();
                }
                curr = curr.children[c - 'a'];
            }
            curr.isWord = true;
        }

        // Time: O(prefix len), space: O(1)
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            if (node == null || !node.isWord) {
                return false;
            }
            return true;
        }

        // Time: O(prefix len), space: O(1)
        public boolean startsWith(String prefix) {
            TrieNode node = searchPrefix(prefix);
            return node != null;
        }

        // Time: O(prefix len), space: O(1)
        private TrieNode searchPrefix(String prefix) {
            TrieNode curr = root;
            for (int i = 0; i < prefix.length(); i++){
                char c = prefix.charAt(i);
                if (curr.children[c - 'a'] == null) {
                    return null;
                }
                curr = curr.children[c - 'a'];
            }
            return curr;
        }
    }
}
