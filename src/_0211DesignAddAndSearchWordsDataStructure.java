/**
 * Notice the relationship between TrieNode curr and word:
 * Initially, curr is at root, which doesn't represent any character,
 * we are really asking: do we have word in the sub-Trie rooted at root's child
 * so curr is more like a predecessor to the character that we're looking at.
 *
 * Trie can be considered as nested hashmap
 *
 * Trie is good for efficient add / search operations with Strings
 *
 * Trie Types: 1) Standard 2) Bitwise tries
 *
 *
 */
public class _0211DesignAddAndSearchWordsDataStructure {
    public class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        // no need to (store word & compare search word with node's word)
        // because we have verified every character along the way
        public boolean isWord;
    }

    private TrieNode root;

    /** Initialize your data structure here. */
//    public WordDictionary() {
//        root = new TrieNode();
//    }

    /**
     * Adds a word into the data structure.
     *
     * Things to think about
     *
     * 1) What character can word contains?
     * lower-case letters.
     * if we contains more characters, we need to change our TrieNode children
     *
     * 2) Adding same word?
     * We don't know it's added in the beginning, we have to traverse all the way down to find out
     * Even in HashMap case, you need to compute HashCode (which also involves visiting each character)
     *
     * 3) Empty string? no empty string in test case, but add works with empty string
     *
     * Time: O(n)
     * Space: O(1)
     */
    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    /**
     * Returns if the word is in the data structure. A word could
     * contain the dot character '.' to represent any one letter.
     *
     * Things to think about
     * 1) What chars does search word contains
     * lower-case letters
     * dot (as a wild card)
     *
     *
     * m: total numbers of characters in the Trie
     * n: length of the search word
     * Time: O(m) or O(26 ^ n)
     * worst case like ****z (only last combination **** has a child z)
     *
     * Space: O(depth of Trie)
     *
     */
    public boolean search(String word) {
        return match(word, 0, root);
    }

    /**
     * Do we have word[k, END] starting at one of child of node
     *
     * Because . can lead to multiple choices at this point -> backtracking
     *
     */
    private boolean match(String word, int k, TrieNode node) {
        if (node == null) {
            return false;
        }

        if (k == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(k);
        if (c == '.') {
            for (int i = 0; i < node.children.length; i++) {
                if (match(word, k + 1, node.children[i])) {
                    return true;
                }
            }
            return false;
        } else {
            return match(word, k + 1, node.children[c - 'a']);
        }
    }

    /**
     * 2. reduces level of recursion
     */
    private boolean match2(String word, int idx, TrieNode node) {
        if (node == null) {
            return false;
        }

        if (word.length() == idx) {
            return node.isWord;
        }

        while (idx < word.length() && word.charAt(idx) != '.') {
            if (node.children[word.charAt(idx) - 'a'] == null) {
                return false;
            }
            node = node.children[word.charAt(idx) - 'a'];
            idx++;
        }

        if (idx == word.length()) {
            return node.isWord;
        }

        for (int i = 0; i < node.children.length; i++) {
            if (match2(word, idx + 1, node.children[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * 2. Map: Integer word length -> Set of words with same length
     */
}
